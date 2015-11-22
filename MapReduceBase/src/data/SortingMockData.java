package data;

import com.pcbsys.nirvana.client.*;

import java.util.Random;

/**
 * Created by vlm on 11/22/2015.
 */
public class SortingMockData implements MockDataGenerator {
    public static int NUMBER_OF_EVENTS = 100000;
    private int eventsNumber = NUMBER_OF_EVENTS;

    @Override
    public void generateData() {
        nSessionAttributes inAttr = null;
        try {
            inAttr = new nSessionAttributes("nsp://localhost:9000");
            nSession inputSession = nSessionFactory.create(inAttr);
            inputSession.init();

            nChannelAttributes inputChannelAttributes = new nChannelAttributes("inputData");
            nChannel inputChannel = inputSession.findChannel(inputChannelAttributes);

            nEventProperties props;
            nConsumeEvent event;
            Random random = new Random();

            for (int i = 0; i < eventsNumber; i++) {
                props = new nEventProperties();
                props.put("number", random.nextInt());
                event = new nConsumeEvent(props, "randomNumber".getBytes());

                inputChannel.publish(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
