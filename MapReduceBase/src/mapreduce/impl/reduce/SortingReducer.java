package mapreduce.impl.reduce;

import com.pcbsys.nirvana.client.nConsumeEvent;
import mapreduce.base.reduce.BaseReducer;

/**
 * Created by vlm on 11/21/2015.
 */
public class SortingReducer extends BaseReducer {
    private final int NUMBER_OF_MAPPERS = 4;
    private int resultsReceived = 0;
    private int[][] mapResults = new int[NUMBER_OF_MAPPERS][];
    private int[] mapResultLengths = new int[NUMBER_OF_MAPPERS];
    private int[] currentPositions = new int[NUMBER_OF_MAPPERS];
    private int totalNumbersCount = 0;

    private int[] result;

    public SortingReducer() {
        super();
        inputChannelName = "outputData";
        maxEventsNumber = NUMBER_OF_MAPPERS;
    }

    @Override
    public void reduce(nConsumeEvent event) {
        mapResults[resultsReceived] = event.getProperties().getIntArray("numbers");
        mapResultLengths[resultsReceived] = event.getProperties().getInt("length");
        totalNumbersCount += mapResultLengths[resultsReceived];
        resultsReceived++;

        if (resultsReceived == NUMBER_OF_MAPPERS) {
            result = new int[totalNumbersCount];
            int currentMin;
            int currentMinPosition;
            for (int i = 0; i < totalNumbersCount; i++) {
                currentMin = Integer.MAX_VALUE;
                currentMinPosition = -1;

                for (int j = 0; j < NUMBER_OF_MAPPERS; j++) {
                    if (mapResults[j][currentPositions[j]] <= currentMin &&
                        currentPositions[j] < mapResultLengths[j]) {
                        currentMin = mapResults[j][currentPositions[j]];
                        currentMinPosition = j;
                    }
                }

                if (currentMinPosition > -1) {
                    result[i] = mapResults[currentMinPosition][currentPositions[currentMinPosition]];
                    currentPositions[currentMinPosition]++;
                } else {
                    return;
                }
            }
        }
    }

    @Override
    protected void submitResults() {

    }
}
