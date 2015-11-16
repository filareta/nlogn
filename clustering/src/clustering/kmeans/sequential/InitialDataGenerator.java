package clustering.kmeans.sequential;

import java.util.Random;

import cluster.Cluster;
import cluster.Point4D;

public final class InitialDataGenerator {
	
	public final static Cluster[] generateInitialClusters(final int clustersCount) {
		
		Cluster[] result = new Cluster[clustersCount];
		
		double minPrice = 1000;
		double maxPrice = 2000;
		
		double minDeltaDay = 0;
		double maxDeltaDay = 100;
		
		double minDeltaHours = 0;
		double maxDeltaHours = 100;
		
		int minQuantity = 100;
		int maxQuantity = 1000;
		
		long seed = 2;
		
		Point4D randPoint;
		
		for(int i = 0; i < clustersCount; i++) {
			
			randPoint = generateRandomPoint4D(minPrice, maxPrice, 
					minDeltaDay, maxDeltaDay, 
					minDeltaHours, maxDeltaHours, 
					minQuantity, maxQuantity, 
					seed);
			
			result[i] = new Cluster(randPoint, 0, 0, 0, 0, 0);
			
			minPrice = minPrice + 1000;
			maxPrice = maxPrice + 1000;
			
//			minDeltaDay = minDeltaDay + 100; // minDelaDay is 0.
			maxDeltaDay = maxDeltaDay + 100;
			
//			minDeltaHours = minDeltaHours + 100; // minDelaHours is 0.
			maxDeltaHours = maxDeltaHours + 100;
			
			seed = seed + i + 1;
		}
		
		return result;
	}
	
	public final static Point4D  generateRandomPoint4D(double minPrice, double maxPrice, 
			double minDeltaDay, double maxDeltaDay, double minDeltaHours, double maxDeltaHours, 
			int minQuantity, int maxQuantity, long seed) {
		
		double randPrice = generateRandomDouble(minPrice, maxPrice, seed);
		double randDeltaDay = generateRandomDouble(minDeltaDay, maxDeltaDay, seed);
		double randDeltaHours = generateRandomDouble(minDeltaHours, maxDeltaHours, seed);
		int randQuantity = generateRandomInt(minQuantity, maxQuantity, seed);
		
		return new Point4D(randPrice, randDeltaDay, randDeltaHours, randQuantity);
	}

	public final static double generateRandomDouble(double min, double max, long seed) {
		Random rand = new Random(seed);
		double randNumber = min + ((max - min) * rand.nextDouble());
		return randNumber;
	}
	
	public final static int generateRandomInt(int min, int max, long seed) {
		Random rand = new Random(seed);
		int randNumber = rand.nextInt((max - min) + 1) + min;
		return randNumber;
	}
}
