package mapreduce.base;

import com.pcbsys.nirvana.client.*;

/**
 * Created by vlm on 11/21/2015.
 */
public abstract class BaseTask implements nEventListener {
    protected String inputRealmName = "nhp://localhost:9000";
    protected String inputChannelName = "inputData";
    protected String outputRealmName = "nhp://localhost:9000";
    protected String outputChannelName = "outputData";

    protected nSession inputSession;
    protected nSession outputSession;
    protected nChannel inputChannel;
    protected nChannel outputChannel;

    protected int eventsNumber = 0;
    protected int maxEventsNumber = 1000;

    public void init() {
        try {
            nSessionAttributes inAttr = new nSessionAttributes(inputRealmName);
            nSessionAttributes outAttr = new nSessionAttributes(outputRealmName);
            inputSession = nSessionFactory.create(inAttr);
            outputSession = nSessionFactory.create(outAttr);
            inputSession.init();
            outputSession.init();

            nChannelAttributes inputChannelAttributes = new nChannelAttributes(inputChannelName);
            nChannelAttributes outputChannelAttributes = new nChannelAttributes(outputChannelName);
            inputChannel = inputSession.findChannel(inputChannelAttributes);
            outputChannel = outputSession.findChannel(outputChannelAttributes);

            inputChannel.addSubscriber(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void submitResults();
}
