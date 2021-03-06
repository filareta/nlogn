package mapreduce.main;

import mapreduce.base.reduce.BaseReducer;
import mapreduce.impl.reduce.CorrelationReducer;
import mapreduce.impl.reduce.SortingReducer;

/**
 * Created by vlm on 11/21/2015.
 */
public class Reducer {
    public static void main(String[] args) throws InterruptedException {
        BaseReducer reducer = new CorrelationReducer();
        reducer.init();

        synchronized (reducer) {
            reducer.wait();
        }
    }
}
