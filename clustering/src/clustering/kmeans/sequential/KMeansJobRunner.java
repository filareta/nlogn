package clustering.kmeans.sequential;

import java.util.Queue;

import um.event.EventConstants;
import cluster.Cluster;
import cluster.Point4D;
import clustering.kmeans.job.AbstractJobRunner;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

import datagen.PointGenerator;

public class KMeansJobRunner extends AbstractJobRunner {
	/** The final number of clusters, which the algorithm will generate. */
	private final static int DEFAULT_CLUSTERS_COUNT = 4;
	
	/** The default number of points to be generated in each mock data set. */
	private final static int DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET = 10;
	
	private final static int DEFAULT_OFFSET = 20;
	
	private Cluster[] resClusters;
	
	public static void main(String[] args) {
				
		KMeansJobRunner jobRunner = new KMeansJobRunner();
		
		System.out.println("Setup clustering job.");
		jobRunner.setup();
		
		System.out.println("Run clustering job.");
		jobRunner.run();
		
		System.out.println("Obtaining result of clustering.");
		Cluster[] resClusters = jobRunner.getResClusters();
		
		nSessionAttributes sessionAttr;
		try {
			sessionAttr = new nSessionAttributes(EventConstants.SESSION_ATTRIBUTE);
			nSession session = nSessionFactory.create(sessionAttr);
			session.init();

			nChannelAttributes channelAttr = new nChannelAttributes(EventConstants.KEY_MAPPER_DATA_CHANNEL);
			nChannel chan = session.findChannel(channelAttr);
			
			nEventProperties clusters[] = new nEventProperties[resClusters.length];
			for (int i = 0 ; i < resClusters.length; i++) {
				clusters[i] = new nEventProperties();
				clusters[i].put(EventConstants.KEY_CENTER, resClusters[i].getCenter().toDoubleArray());
				clusters[i].put(EventConstants.KEY_SUM_ARRAY, resClusters[i].getSums());
				clusters[i].put(EventConstants.KEY_POINTS_COUNT, resClusters[i].getTotalPointsCount());
			}
			
			nEventProperties props = new nEventProperties();
			props.put(EventConstants.KEY_CLUSTERS, clusters);
			
			nConsumeEvent evt = new nConsumeEvent(props, "Clusters".getBytes());
			
			System.out.println("Publish result of clustering as an event.");
			chan.publish(evt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final Cluster[] getResClusters() {
		return resClusters;
	}

	@Override
	public void setup() {
		super.setup();
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
		
		Point4D newCentroid = new Point4D(resClusters[clusterIndex].getPriceSum()/totalPointsCount, 
				resClusters[clusterIndex].getDeltaDaySum()/totalPointsCount, 
				resClusters[clusterIndex].getDeltaHourSum()/totalPointsCount, 
				resClusters[clusterIndex].getQuantitySum()/totalPointsCount);
		
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
