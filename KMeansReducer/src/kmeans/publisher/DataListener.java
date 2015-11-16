package kmeans.publisher;

import java.util.concurrent.locks.ReentrantLock;

import kmeans.reducer.Reducer;
import cluster.Cluster;
import cluster.Clustering;
import cluster.Point4D;

import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventListener;

public class DataListener implements nEventListener {
    private ClusteringPublisher publisher;
    private Reducer reducer;
    private ReentrantLock lock;

    public DataListener() {
	publisher = new ClusteringPublisher();
	lock = new ReentrantLock();
	reducer = new Reducer();
    }

    @Override
    public void go(nConsumeEvent event) {
	lock.lock();

	Clustering one = new Clustering();
	Clustering two = new Clustering();

	Cluster cl1 = new Cluster(new Point4D(5.40, 2.3, 1.4, 12), 4, 34.2,
		12.4, 10.5, 48);
	Cluster cl2 = new Cluster(new Point4D(8.40, 4.3, 8.4, 6), 5, 39.2,
		24.4, 21.5, 24);
	Cluster cl3 = new Cluster(new Point4D(12.40, 32.3, -12.4, 8), 3, 39.2,
		24.4, 21.5, 24);
	Cluster cl4 = new Cluster(new Point4D(6.40, 3.3, 2.4, 12), 2, 39.2,
		24.4, 21.5, 24);
	Cluster cl5 = new Cluster(new Point4D(9.40, 5.3, 8.4, 11), 3, 39.2,
		24.4, 21.5, 24);
	Cluster cl6 = new Cluster(new Point4D(10.40, 4.3, 8.4, 6), 5, 39.2,
		14.4, 21.5, 24);

	one.addCluster(cl1);
	one.addCluster(cl2);
	one.addCluster(cl3);
	two.addCluster(cl4);
	two.addCluster(cl5);
	two.addCluster(cl6);

	reducer.addClustering(one);
	reducer.addClustering(two);

	lock.unlock();
	publisher.publish(reducer.getClustering());
    }

}
