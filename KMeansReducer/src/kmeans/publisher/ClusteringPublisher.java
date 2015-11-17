package kmeans.publisher;

import um.event.EventConstants;
import cluster.Cluster;
import cluster.Clustering;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

public class ClusteringPublisher {
    public void publish(Clustering clustering) {
	try {
	    nSessionAttributes sessionAttr = new nSessionAttributes(EventConstants.SESSION_ATTRIBUTE);
	    nSession session = nSessionFactory.create(sessionAttr);
	    session.init();

	    nChannelAttributes channelAttr = new nChannelAttributes(EventConstants.KEY_CLUSTERING_CAHNNEL);
	    nChannel chan = session.findChannel(channelAttr);

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
	    chan.publish(evt);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
