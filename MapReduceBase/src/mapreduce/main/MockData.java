package mapreduce.main;

import data.CorrelationMockData;
import data.MockDataGenerator;
import data.SortingMockData;

/**
 * Created by vlm on 11/28/2015.
 */
public class MockData {

    public static void main(String[] args) {
        MockDataGenerator mockData = new CorrelationMockData();
        mockData.generateData();
    }
}
