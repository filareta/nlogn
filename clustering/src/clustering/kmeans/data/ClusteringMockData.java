package clustering.kmeans.data;

import java.util.Queue;
import java.util.Random;

import cluster.Cluster;
import cluster.Point4D;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

import data.MockDataGenerator;
import datagen.PointGenerator;

public class ClusteringMockData implements MockDataGenerator {

	/** The final number of clusters, which the algorithm will generate. */
	public final static int DEFAULT_CLUSTERS_COUNT = 4;
	
	/** The default number of points to be generated in each mock data set. */
	public final static int DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET = 500;
	
	/** The default number of points to be generated in each mock data set. */
	public final static int TOTAL_POINTS_COUNT_IN_MOCK_DATA_SET = 
			DEFAULT_CLUSTERS_COUNT * DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET;
	
	private final static int DEFAULT_OFFSET = 10;
	
	@Override
	public void generateData() {
		
        nSessionAttributes inAttr = null;
        try {
            inAttr = new nSessionAttributes("nsp://localhost:9000");
            nSession inputSession = nSessionFactory.create(inAttr);
            inputSession.init();

            nChannelAttributes inputChannelAttributes = new nChannelAttributes("inputData");
            nChannel inputChannel = inputSession.findChannel(inputChannelAttributes);

            nEventProperties props;
            nConsumeEvent event;
            
    		// Generate {@code DEFAULT_CLUSTERS_COUNT} random points, which to serve as initial cluster centers.
    		Cluster[] initialClusters = generateInitialClusters(DEFAULT_CLUSTERS_COUNT);
    		
    		System.out.println();
    		for (int i = 0; i < initialClusters.length; i++) {
    			
    			Point4D randPoint = initialClusters[i].getCenter();
    			props = new nEventProperties();
                props.put("center", randPoint.toDoubleArray());
                event = new nConsumeEvent(props, "initialClusterCenter".getBytes());
                inputChannel.publish(event);
    			
                System.out.println("Initial center for the " + (i + 1) + "th cluster is: " + randPoint.toString());
    		}
            
    		for (int i = 0; i < DEFAULT_CLUSTERS_COUNT; i++) {
    			
    			int offset = DEFAULT_OFFSET + 20;
    			
    			Point4D randPoint = initialClusters[i].getCenter();
    			
    			PointGenerator dataGenerator = new PointGenerator(randPoint, 
    					DEFAULT_POINTS_COUNT_IN_MOCK_DATA_SET, offset);
    			
    			Queue<Point4D> mockDataSet = dataGenerator.generatePointsAround();
    			
    			while (!mockDataSet.isEmpty()) {
    				Point4D nextPoint = mockDataSet.poll();
    				
    				props = new nEventProperties();
                    props.put("share", nextPoint.toDoubleArray());
                    event = new nConsumeEvent(props, "shareData".getBytes());

                    inputChannel.publish(event);
    			}
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public final static Cluster[] generateInitialClusters(final int clustersCount) {
		
		Cluster[] result = new Cluster[clustersCount];
		
		double minPrice = 5;
		double maxPrice = 25;
		
		double minDeltaDay = 1;
		double maxDeltaDay = 2;
		
		double minDeltaHours = 1;
		double maxDeltaHours = 2;
		
		int minQuantity = 100;
		int maxQuantity = 300;
		
		Point4D randPoint;
		Random randGenerator =  new Random();
		
		for(int i = 0; i < clustersCount; i++) {
			
			randPoint = generateRandomPoint4D(minPrice, maxPrice, 
					minDeltaDay, maxDeltaDay, 
					minDeltaHours, maxDeltaHours, 
					minQuantity, maxQuantity, 
					randGenerator);
			
			result[i] = new Cluster(randPoint, 0, 0, 0, 0, 0);
			
			minPrice = minPrice + 20;
			maxPrice = maxPrice + 25;
			
			minDeltaDay = minDeltaDay + i + 1; 
			maxDeltaDay = maxDeltaDay + i + 2;
			
			minDeltaHours = minDeltaHours + i + 1; 
			maxDeltaHours = maxDeltaHours + i + 4;
			
			minQuantity = minQuantity + 100;
			maxQuantity = 300 + (200 * (i + 1));
		}
		
		return result;
	}
	
	public final static Point4D  generateRandomPoint4D(double minPrice, double maxPrice, 
			double minDeltaDay, double maxDeltaDay, double minDeltaHours, double maxDeltaHours, 
			int minQuantity, int maxQuantity, final Random randGenerator) {
		
		double randPrice = generateRandomDouble(minPrice, maxPrice, randGenerator);
		double randDeltaDay = generateRandomDouble(minDeltaDay, maxDeltaDay, randGenerator);
		double randDeltaHours = generateRandomDouble(minDeltaHours, maxDeltaHours, randGenerator);
		int randQuantity = generateRandomInt(minQuantity, maxQuantity, randGenerator);
		
		return new Point4D(randPrice, randDeltaDay, randDeltaHours, randQuantity);
	}

	public final static double generateRandomDouble(double min, double max, final Random randGenerator) {
		double randNumber = min + ((max - min) * randGenerator.nextDouble());
		return randNumber;
	}
	
	public final static int generateRandomInt(int min, int max, final Random randGenerator) {
		int randNumber = randGenerator.nextInt((max - min) + 1) + min;
		return randNumber;
	}
}
