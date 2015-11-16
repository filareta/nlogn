package test.clustering.kmeans.sequential;

import cluster.Cluster;
import clustering.kmeans.sequential.InitialDataGenerator;


public class InitialDataGeneratorTest {
	
	public static void main(String[] args) {
		InitialDataGeneratorTest instance = new InitialDataGeneratorTest();
		instance.testGenerateInitialCentroids();
	}
	
	public void testGenerateInitialCentroids() {
		Cluster[] initClusters = InitialDataGenerator.generateInitialClusters(4);
		for (int i = 0; i < initClusters.length; i++) {
			System.out.println(initClusters[i].getCenter());
		}
	}
}
