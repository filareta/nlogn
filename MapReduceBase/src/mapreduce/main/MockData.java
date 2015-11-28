package mapreduce.main;

import data.MockDataGenerator;
import data.SortingMockData;

/**
 * Created by vlm on 11/28/2015.
 */
public class MockData {

    public static void main(String[] args) {
        MockDataGenerator mockData = new SortingMockData();
        mockData.generateData();
    }
}
