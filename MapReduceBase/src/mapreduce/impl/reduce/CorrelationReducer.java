package mapreduce.impl.reduce;

import com.pcbsys.nirvana.client.nConsumeEvent;
import data.CorrelationMockData;
import mapreduce.base.reduce.BaseReducer;

/**
 * Created by vlm on 11/29/2015.
 */
public class CorrelationReducer extends BaseReducer {
    private final int NUMBER_OF_MAPPERS = 4;
    private final int companiesNum = CorrelationMockData.NUMBER_OF_COMPANIES;
    private final int priceInputsNum = CorrelationMockData.NUMBER_OF_INPUTS * NUMBER_OF_MAPPERS;
    private final double[] sum_xy;
    private final double[] sum_x;
    private double[][] covariance;
    private int inputsNumber = 0;

    public CorrelationReducer() {
        super();
        inputChannelName = "outputData";
        maxEventsNumber = NUMBER_OF_MAPPERS;
        sum_xy = new double[companiesNum * (companiesNum + 1) / 2];
        sum_x = new double[companiesNum];
        covariance = new double[companiesNum][companiesNum];
    }

    @Override
    public void reduce(nConsumeEvent event) {
        double[] current_sum_xy = event.getProperties().getDoubleArray("sum_xy");
        double[] current_sum_x = event.getProperties().getDoubleArray("sum_x");

        int current = 0;
        for (int i = 0; i < CorrelationMockData.NUMBER_OF_COMPANIES; i++) {
            sum_x[i] += current_sum_x[i];
            for (int j = i; j < CorrelationMockData.NUMBER_OF_COMPANIES; j++) {
                sum_xy[current] += current_sum_xy[current];
                current++;
            }
        }

        inputsNumber++;
        if (inputsNumber == NUMBER_OF_MAPPERS) {
            current = 0;
            for (int i = 0; i < CorrelationMockData.NUMBER_OF_COMPANIES; i++) {
                for (int j = i; j < CorrelationMockData.NUMBER_OF_COMPANIES; j++) {
                    covariance[i][j] = (sum_xy[current] / priceInputsNum) -
                                       (sum_x[i] / priceInputsNum) * (sum_x[j] / priceInputsNum);
                    current++;
                }
                covariance[i][i] = Math.sqrt(covariance[i][i]);
            }
            for (int i = 0; i < CorrelationMockData.NUMBER_OF_COMPANIES; i++) {
                for (int j = i + 1; j < CorrelationMockData.NUMBER_OF_COMPANIES; j++) {
                    covariance[i][j] /= covariance[i][i] * covariance[j][j];
                    current++;
                }
            }

            int a = 0;
        }
    }

    @Override
    protected void submitResults() {

    }
}
