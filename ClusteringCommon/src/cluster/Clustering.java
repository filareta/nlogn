package cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clustering implements Iterable<Cluster> {
	private List<Cluster> clusters;

	public Clustering() {
		clusters = new ArrayList<Cluster>();
	}

	public void addCluster(Cluster cluster) {
		clusters.add(cluster);
	}

	@Override
	public Iterator<Cluster> iterator() {
		return clusters.iterator();
	}
}
