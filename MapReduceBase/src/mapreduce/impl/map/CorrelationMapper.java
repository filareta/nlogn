package mapreduce.impl.map;

import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;
import data.CorrelationMockData;
import data.SortingMockData;
import mapreduce.base.map.BaseMapper;

/**
 * Created by vlm on 11/28/2015.
 */
public class CorrelationMapper extends BaseMapper {
    private double[][] sum_xy;
    private double[] sum_x;

    public CorrelationMapper() {
        super();
        maxEventsNumber = CorrelationMockData.NUMBER_OF_INPUTS;
        sum_xy = new double[CorrelationMockData.NUMBER_OF_COMPANIES][CorrelationMockData.NUMBER_OF_COMPANIES];
        sum_x = new double[CorrelationMockData.NUMBER_OF_COMPANIES];
    }

    @Override
    protected void map(nConsumeEvent event) {
        double[] prices = event.getProperties().getDoubleArray("prices");
        for (int i = 0; i < CorrelationMockData.NUMBER_OF_COMPANIES; i++) {
            sum_x[i] += prices[i];
            for (int j = i; j < CorrelationMockData.NUMBER_OF_COMPANIES; j++) {
                sum_xy[i][j] += prices[i] * prices[j];
            }
        }
    }

    @Override
    protected void submitResults() {
        int companiesNum = CorrelationMockData.NUMBER_OF_COMPANIES;
        double[] sum_xy_flat = new double[companiesNum * (companiesNum + 1) / 2];
        int current = 0;
        for (int i = 0; i < companiesNum; i++) {
            for (int j = i; j < companiesNum; j++) {
                sum_xy_flat[current] = sum_xy[i][j];
                current++;
            }
        }

        nEventProperties props = new nEventProperties();
        props.put("sum_xy", sum_xy_flat);
        props.put("sum_x", sum_x);

        nConsumeEvent event = new nConsumeEvent(props, "mapperData".getBytes());

        try {
            outputChannel.publish(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
