package kmeans.publisher;

import java.util.concurrent.locks.ReentrantLock;

import kmeans.reducer.Reducer;
import cluster.Cluster;
import cluster.Clustering;
import cluster.Point4D;

import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventListener;
import com.pcbsys.nirvana.client.nEventProperties;

public class DataListener implements nEventListener {
    private final int EVENT_LIMIT_COUNT = 1;
    
    private ClusteringPublisher publisher;
    private Reducer reducer;
    private ReentrantLock lock;
    private int receivedEvents;

    public DataListener() {
	publisher = new ClusteringPublisher();
	lock = new ReentrantLock();
	reducer = new Reducer();
	receivedEvents = 0;
    }

    @Override
    public void go(nConsumeEvent event) {
	lock.lock();
	
	nEventProperties props = event.getProperties();
	nEventProperties clusters[] = (nEventProperties[]) props.get("clusters");
	Clustering clustering = new Clustering();
	
	for(int i = 0; i < clusters.length; i++) {
	    double[] centerValues = clusters[i].getDoubleArray("center");
	    double[] sums = clusters[i].getDoubleArray("sum");
	    int totalCount = clusters[i].getInt("pointsCount");
	    
	    Point4D center = new Point4D(centerValues[0], centerValues[1], centerValues[2], (int) centerValues[3]);
	    Cluster cluster = new Cluster(center, totalCount, sums[0], sums[1], sums[2], (int) sums[3]);
	    clustering.addCluster(cluster);
	}
	
	reducer.addClustering(clustering);
	
	receivedEvents++;
	if (receivedEvents == EVENT_LIMIT_COUNT) {
	    publisher.publish(reducer.getClustering());
	}
	
	lock.unlock();
    }

}
