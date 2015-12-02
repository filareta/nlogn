package data;

import com.pcbsys.nirvana.client.*;

import java.util.Random;

/**
 * Created by vlm on 11/29/2015.
 */
public class CorrelationMockData implements MockDataGenerator {
    public static final int NUMBER_OF_COMPANIES = 500;
    public static final int NUMBER_OF_INPUTS = 120000;

    @Override
    public void generateData() {
        nSessionAttributes inAttr = null;
        double[] nextInput = new double[NUMBER_OF_COMPANIES];
        try {
            inAttr = new nSessionAttributes("nsp://localhost:9000");
            nSession inputSession = nSessionFactory.create(inAttr);
            inputSession.init();

            nChannelAttributes inputChannelAttributes = new nChannelAttributes("inputData");
            nChannel inputChannel = inputSession.findChannel(inputChannelAttributes);

            nEventProperties props;
            nConsumeEvent event;
            Random random = new Random();

            for (int i = 0; i < NUMBER_OF_INPUTS; i++) {
                for (int j = 0; j < NUMBER_OF_COMPANIES; j++) {
                    nextInput[j] = random.nextDouble() * 50;
                }
                props = new nEventProperties();
                props.put("prices", nextInput);
                event = new nConsumeEvent(props, "sharePrices".getBytes());

                inputChannel.publish(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
