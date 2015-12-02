package mapreduce.main;

import data.MockDataGenerator;
import data.SortingMockData;
import mapreduce.base.map.BaseMapper;
import mapreduce.impl.map.CorrelationMapper;
import mapreduce.impl.map.SortingMapper;

/**
 * Created by vlm on 11/21/2015.
 */
public class Mapper {
    public static void main(String[] args) throws InterruptedException {
        BaseMapper mapper = new CorrelationMapper();
        mapper.init();
//        MockDataGenerator mockData = new SortingMockData();
//
//        mockData.generateData();

        synchronized (mapper) {
            mapper.wait();
        }
    }
}
