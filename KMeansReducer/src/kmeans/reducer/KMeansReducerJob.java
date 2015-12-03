package kmeans.reducer;

import mapreduce.base.reduce.BaseReducer;

public class KMeansReducerJob {

    public static void main(String[] args) throws InterruptedException {
	BaseReducer reducer = new ClusteringReducer();
	reducer.init();
	
	synchronized(reducer) {
	    reducer.wait();
	}
    }
}