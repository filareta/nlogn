package cluster;

import java.util.ArrayList;
import java.util.List;

public class Clustering {
	private List<Cluster> clusters;
	
	public Clustering() {
		clusters = new ArrayList<Cluster>();
	}
	
	public void addCluster(Cluster cluster) {
		clusters.add(cluster);
	}
}
