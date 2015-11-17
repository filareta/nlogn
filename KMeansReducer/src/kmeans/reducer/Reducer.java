package kmeans.reducer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cluster.Cluster;
import cluster.Clustering;
import cluster.Point4D;

public class Reducer {
    private Clustering resultClustering;
    private Set<Cluster> mergedClusters;
    
    public Reducer() {
	resultClustering = null;
	mergedClusters = new HashSet<>();
    }
    
    public void addClustering(Clustering clustering) {
	if (resultClustering == null) {
	    resultClustering = clustering;
	    mergedClusters = new HashSet<>();
	} else {
	    addToResult(clustering);
	}
    }

    private void addToResult(Clustering clustering) {
	Clustering result = new Clustering();

	for (Cluster cluster : clustering) {
	    result.addCluster(mergeWithClosestCluster(cluster));
	}

	resultClustering = result;
	mergedClusters = new HashSet<Cluster>();
    }

    private Cluster mergeWithClosestCluster(Cluster cluster) {
	Iterator<Cluster> clustersIterator = resultClustering.iterator();
	Cluster closestCluster = null;

	while (clustersIterator.hasNext()) {
	    closestCluster = clustersIterator.next();
	    if (!mergedClusters.contains(closestCluster)) {
		break;
	    }
	}

	while (clustersIterator.hasNext()) {
	    Cluster current = clustersIterator.next();
	    if (!mergedClusters.contains(current)
		    && cluster.distance(current) < cluster
			    .distance(closestCluster)) {
		closestCluster = current;
	    }
	}

	mergedClusters.add(closestCluster);
	return mergeClusters(closestCluster, cluster);
    }

    private Cluster mergeClusters(Cluster firstCluster, Cluster secondCluster) {
	int totalClusterPoints = firstCluster.getTotalPointsCount() + secondCluster.getTotalPointsCount();

	double priceSum = (firstCluster.getPriceSum() + secondCluster.getPriceSum());
	double deltaDaySum = (firstCluster.getDeltaDaySum() + secondCluster.getDeltaDaySum());
	double deltaHourSum = (firstCluster.getDeltaHourSum() + secondCluster.getDeltaHourSum());
	int quantitySum = (firstCluster.getQuantitySum() + secondCluster.getQuantitySum());
	
	Point4D center = new Point4D(priceSum / totalClusterPoints, deltaDaySum
		/ totalClusterPoints, deltaHourSum / totalClusterPoints,
		quantitySum / totalClusterPoints);
	
	return new Cluster(center, totalClusterPoints, priceSum, deltaDaySum,
		deltaHourSum, quantitySum);
    }

    public Clustering getClustering() {
	return resultClustering;
    }
    
    public boolean isResultAvailable() {
	return resultClustering != null;
    }
}
