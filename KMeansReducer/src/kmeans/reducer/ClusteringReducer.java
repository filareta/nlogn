package kmeans.reducer;

import mapreduce.base.reduce.BaseReducer;
import um.event.EventConstants;
import cluster.Cluster;
import cluster.Clustering;
import cluster.Point4D;

import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;
import com.pcbsys.nirvana.client.nIllegalArgumentException;
import com.pcbsys.nirvana.client.nSecurityException;
import com.pcbsys.nirvana.client.nSessionNotConnectedException;
import com.pcbsys.nirvana.client.nSessionPausedException;

public class ClusteringReducer extends BaseReducer {
    public static final int EVENT_TRESHOLD = 5;
    
    private Reducer reducer;
    
    public ClusteringReducer() {
	super();
	inputChannelName = EventConstants.KEY_MAPPER_DATA_CHANNEL;
	outputChannelName = EventConstants.KEY_CLUSTERING_CAHNNEL;
	maxEventsNumber = EVENT_TRESHOLD;
	reducer = new Reducer();
    }

    @Override
    public void reduce(nConsumeEvent event) {
	nEventProperties props = event.getProperties();
	nEventProperties clusters[] = (nEventProperties[]) props.get(EventConstants.KEY_CLUSTERS);
	Clustering clustering = new Clustering();
	
	for(int i = 0; i < clusters.length; i++) {
	    double[] centerValues = clusters[i].getDoubleArray(EventConstants.KEY_CENTER);
	    double[] sums = clusters[i].getDoubleArray(EventConstants.KEY_SUM_ARRAY);
	    int totalCount = clusters[i].getInt(EventConstants.KEY_POINTS_COUNT);
	    
	    Point4D center = new Point4D(centerValues[0], centerValues[1], centerValues[2], (int) centerValues[3]);
	    Cluster cluster = new Cluster(center, totalCount, sums[0], sums[1], sums[2], (int) sums[3]);
	    clustering.addCluster(cluster);
	}
	
	reducer.addClustering(clustering);
	System.out.println("Number of reduced events so far " + eventsNumber + " ...");
    }

    @Override
    protected void submitResults() {
	System.out.println("Submiting results...");
	Clustering clustering = reducer.getClustering();
	
	nEventProperties clusters[] = new nEventProperties[clustering.size()];
	for (int j = 0; j < clustering.size(); j++) {
	    clusters[j] = new nEventProperties();
	}

	int i = 0;
	for (Cluster cluster : clustering) {
	    clusters[i].put(EventConstants.KEY_PRICE_PER_SHARE, cluster.getCenter().getPricePerShare());
	    clusters[i].put(EventConstants.KEY_DELTA_DAY, cluster.getCenter().getDeltaDay());
	    clusters[i].put(EventConstants.KEY_DELTA_HOUR, cluster.getCenter().getDeltaHour());
	    clusters[i].put(EventConstants.KEY_QUANTITY, cluster.getCenter().getQuantity());
	    i++;
	}

	nEventProperties props = new nEventProperties();
	props.put(EventConstants.KEY_CLUSTERS, clusters);
	nConsumeEvent evt = new nConsumeEvent(props, EventConstants.KEY_CLUSTERING.getBytes());
	    
	try {
	    System.out.println("Publishing clustering to output channel...");
	    outputChannel.publish(evt);
	    reducer = new Reducer();
	    eventsNumber = 0;
	} catch (nSessionNotConnectedException | nSessionPausedException
	    | nIllegalArgumentException | nSecurityException e) {
	    e.printStackTrace();
	}
    }
}
