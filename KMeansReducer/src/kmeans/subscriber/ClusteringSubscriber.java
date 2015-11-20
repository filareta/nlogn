package kmeans.subscriber;

import kmeans.publisher.DataListener;
import um.event.EventConstants;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

public class ClusteringSubscriber {
    
    public void subscribe() {
	try {
	    nSessionAttributes sessionAttr = new nSessionAttributes(EventConstants.SESSION_ATTRIBUTE);
	    nSession session = nSessionFactory.create(sessionAttr);
	    session.init();

	    nChannelAttributes channelAttr = new nChannelAttributes(EventConstants.KEY_MAPPER_DATA_CHANNEL);
	    nChannel chan = session.findChannel(channelAttr);
	    DataListener dataListener = new DataListener();
	    chan.addSubscriber(dataListener);
	    
	    while(true) {
		dataListener.monitorEvents();		
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
