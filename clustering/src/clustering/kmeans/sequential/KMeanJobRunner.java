package clustering.kmeans.sequential;

import java.util.Queue;

import cluster.Point4D;
import clustering.kmeans.job.AbstractJobRunner;
import datagen.PointGenerator;

public class KMeanJobRunner extends AbstractJobRunner {
	
	/** The final number of clusters, which the algorithm will generate. */
	private final static int DEFAULT_CLUSTERS_COUNT = 4;
	
	/** The default number of points to be generated in each mock data set. */
	private final static int DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET = 10;
	
	private final static int DEFAULT_OFFSET = 20;
	
	@Override
	public void run() {
		
		// Generate initial centroids for the algorithm.
		Point4D[] centroids = InitialDataGenerator.generateInitialCentroids(DEFAULT_CLUSTERS_COUNT);
		
		// Generate @{code DEFAULT_CLUSTERS_COUNT} mock data sets and cluster it sequentially.
		for (int i = 0; i < DEFAULT_CLUSTERS_COUNT; i++) {
			
			int offset = DEFAULT_OFFSET + (i * 20);
			PointGenerator dataGenerator = new PointGenerator(centroids[i], 
					DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET, offset);
			
			Queue<Point4D> mockDataSet = dataGenerator.generatePointsAround();
		}
	}
}
