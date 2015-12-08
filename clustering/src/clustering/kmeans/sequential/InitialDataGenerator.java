package clustering.kmeans.sequential;

import java.util.Random;

import cluster.Cluster;
import cluster.Point4D;

@Deprecated
public final class InitialDataGenerator {
	
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
