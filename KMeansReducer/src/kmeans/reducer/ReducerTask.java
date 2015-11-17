package kmeans.reducer;

import kmeans.subscriber.ClusteringSubscriber;

public class ReducerTask {

    public static void main(String[] args) {
	ClusteringSubscriber subscriber = new ClusteringSubscriber();
	subscriber.subscribe();
    }
}