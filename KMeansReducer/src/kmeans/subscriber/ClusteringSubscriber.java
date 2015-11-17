package kmeans.subscriber;

import kmeans.publisher.DataListener;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

public class ClusteringSubscriber {
    public static final String SESSION_ATTRIBUTE = "nhp://DESKTOP-M6E6SPT:9000";

    public void subscribe() {
	try {
	    nSessionAttributes sessionAttr = new nSessionAttributes(SESSION_ATTRIBUTE);
	    nSession session = nSessionFactory.create(sessionAttr);
	    session.init();

	    nChannelAttributes channelAttr = new nChannelAttributes("mapperData");
	    nChannel chan = session.findChannel(channelAttr);
	    chan.addSubscriber(new DataListener());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
