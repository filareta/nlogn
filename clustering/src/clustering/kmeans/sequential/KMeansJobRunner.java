package clustering.kmeans.sequential;

import java.util.Queue;

import cluster.Cluster;
import cluster.Point4D;
import clustering.kmeans.job.AbstractJobRunner;
import datagen.PointGenerator;

public class KMeansJobRunner extends AbstractJobRunner {
	
	/** The final number of clusters, which the algorithm will generate. */
	private final static int DEFAULT_CLUSTERS_COUNT = 3;
	
	/** The default number of points to be generated in each mock data set. */
	private final static int DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET = 3;
	
	private final static int DEFAULT_OFFSET = 20;
	
	private Cluster[] resClusters;
	
	@Override
	public void setup() {
		super.setup();
		resClusters = new Cluster[DEFAULT_CLUSTERS_COUNT];
	}
	
	@Override
	public void run() {
		
		// Generate initial centroids for the algorithm.
		resClusters = InitialDataGenerator.generateInitialClusters(DEFAULT_CLUSTERS_COUNT);
		
		// Generate @{code DEFAULT_CLUSTERS_COUNT} mock data sets and cluster it sequentially.
		for (int i = 0; i < DEFAULT_CLUSTERS_COUNT; i++) {
			
			int offset = DEFAULT_OFFSET + (i * 20);
			
			Point4D randPoint = resClusters[i].getCenter();
			
			PointGenerator dataGenerator = new PointGenerator(randPoint, 
					DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET, offset);
			
			Queue<Point4D> mockDataSet = dataGenerator.generatePointsAround();
			
			while (!mockDataSet.isEmpty()) {
				Point4D nextPoint = mockDataSet.poll();
				assignPointToCluster(nextPoint);
			}
		}
	}
	
	private void assignPointToCluster(final Point4D nextPoint) {
		
		// Find the index of that cluster, which center is closest to the given point.
		int clusterIndex = findClosestCluster(nextPoint);
		
		// Assign the given point to its closest cluster and update the cluster values accordingly.
		// Increment the total points count of the closest cluster with 1.		
		resClusters[clusterIndex].incrementTotalPointsCount();
		int totalPointsCount = resClusters[clusterIndex].getTotalPointsCount();
		
		// Increment the corresponding sums.
		resClusters[clusterIndex].incrementPriceSum(nextPoint.getPricePerShare());
		resClusters[clusterIndex].incrementDeltaDaySum(nextPoint.getDeltaDay());
		resClusters[clusterIndex].incrementDeltaHourSum(nextPoint.getDeltaHour());
		resClusters[clusterIndex].incrementQuantitySum(nextPoint.getQuantity());
		
		// Calculate new center: mi + (1/ni)*( x - mi)
		Point4D closestCentroid = resClusters[clusterIndex].getCenter();
		
		Point4D newCentroid = ((nextPoint.minus(closestCentroid))
				.divide(totalPointsCount))
				.add(closestCentroid);
		
		resClusters[clusterIndex].setCenter(newCentroid);
	}
	
	private int findClosestCluster(final Point4D nextPoint) {
		
		double minDistance = -1;
		double tempDistance = -1;
		int clusterIndex = -1;
		
		// Find the index of that cluster, which center is closest to the given point.
		for (int i = 0; i < resClusters.length; i++) {
			
			tempDistance = resClusters[i].getCenter().distance(nextPoint);
			
			if (minDistance < 0) {
				minDistance = tempDistance;
				clusterIndex = i;
			
			} else if (minDistance > tempDistance) {
				minDistance = tempDistance;
				clusterIndex = i;
			}
		}
		return clusterIndex;
	}
	
	@Override
	public void cleanup() {
		super.cleanup();
		resClusters = null;
	}
}
