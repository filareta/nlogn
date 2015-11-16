package clustering.kmeans.sequential;

import java.util.Queue;

import cluster.Cluster;
import cluster.Point4D;
import clustering.kmeans.job.AbstractJobRunner;
import datagen.PointGenerator;

public class KMeansJobRunner extends AbstractJobRunner {
	
	/** The final number of clusters, which the algorithm will generate. */
	private final static int DEFAULT_CLUSTERS_COUNT = 4;
	
	/** The default number of points to be generated in each mock data set. */
	private final static int DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET = 10;
	
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
		Cluster[] clusters = InitialDataGenerator.generateInitialClusters(DEFAULT_CLUSTERS_COUNT);
		
		// Generate @{code DEFAULT_CLUSTERS_COUNT} mock data sets and cluster it sequentially.
		for (int i = 0; i < DEFAULT_CLUSTERS_COUNT; i++) {
			
			int offset = DEFAULT_OFFSET + (i * 20);
			
			Point4D randPoint = clusters[i].getCeneter();
			
			PointGenerator dataGenerator = new PointGenerator(randPoint, 
					DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET, offset);
			
			Queue<Point4D> mockDataSet = dataGenerator.generatePointsAround();
			
			while (!mockDataSet.isEmpty()) {
				
				Point4D nextPoint = mockDataSet.poll();
				
			}
		}
	}
	
	
}
