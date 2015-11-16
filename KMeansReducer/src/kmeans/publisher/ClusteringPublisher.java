package kmeans.publisher;

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
    public static final String SESSION_ATTRIBUTE = "nhp://DESKTOP-M6E6SPT:9000";

    public void publish(Clustering clustering) {
	try {
	    nSessionAttributes sessionAttr = new nSessionAttributes(
		    SESSION_ATTRIBUTE);
	    nSession session = nSessionFactory.create(sessionAttr);
	    session.init();

	    nChannelAttributes channelAttr = new nChannelAttributes(
		    "clustering");
	    nChannel chan = session.findChannel(channelAttr);

	    nEventProperties clusters[] = new nEventProperties[clustering
		    .size()];
	    for (int j = 0; j < clustering.size(); j++) {
		clusters[j] = new nEventProperties();
	    }

	    int i = 0;
	    for (Cluster cluster : clustering) {
		clusters[i].put("pricePerShare", cluster.getCenter()
			.getPricePerShare());
		clusters[i].put("deltaDay", cluster.getCenter().getDeltaDay());
		clusters[i]
			.put("deltaHour", cluster.getCenter().getDeltaHour());
		clusters[i].put("quantity", cluster.getCenter().getQuantity());
		i++;
	    }

	    nEventProperties props = new nEventProperties();
	    props.put("clusters", clusters);
	    nConsumeEvent evt = new nConsumeEvent(props,
		    "Clustering".getBytes());

	    chan.publish(evt);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
