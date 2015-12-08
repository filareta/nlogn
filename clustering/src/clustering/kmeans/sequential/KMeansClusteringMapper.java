package clustering.kmeans.sequential;

import um.event.EventConstants;
import cluster.Cluster;
import cluster.Point4D;
import clustering.kmeans.data.ClusteringMockData;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

import data.CorrelationMockData;
import mapreduce.base.map.BaseMapper;

public class KMeansClusteringMapper extends BaseMapper {

	private Cluster[] resClusters;
	
    public KMeansClusteringMapper() {
        super();
        maxEventsNumber = ClusteringMockData.TOTAL_POINTS_COUNT_IN_MOCK_DATA_SET;
        resClusters = new Cluster[ClusteringMockData.DEFAULT_CLUSTERS_COUNT];
    }
    
	@Override
	protected void map(nConsumeEvent event) {

		double[] center = event.getProperties().getDoubleArray("center");
		if (center != null && center.length > 0) {
			for (int i = 0; i < ClusteringMockData.DEFAULT_CLUSTERS_COUNT; i++) {
				if (resClusters[i] == null) {
					double sharePrice = center[0];
					double deltaDay = center[1];
					double deltaHour = center[2];
					int quantity = (int) center[3]; 
					Point4D point4D = new Point4D(sharePrice, deltaDay, deltaHour, quantity);
					resClusters[i] = new Cluster(point4D, 0, 0, 0, 0, 0);
					break;
				}
			}
		} else {
		
			double[] share = event.getProperties().getDoubleArray("share");
			double sharePrice = share[0];
			double deltaDay = share[1];
			double deltaHour = share[2];
			int quantity = (int) share[3]; 
			
			Point4D nextPoint = new Point4D(sharePrice, deltaDay, deltaHour, quantity);
			assignPointToCluster(nextPoint);
		}
	}
	
	private void assignPointToCluster(final Point4D nextPoint) {
		
		// Find the index of that cluster, which center is closest to the given point.
		int clusterIndex = findClosestCluster(nextPoint);
		
		// Assign the given point to its closest cluster and update the cluster values accordingly.
		// Increment the total points count of the closest cluster with 1.		
		resClusters[clusterIndex].incrementTotalPointsCount();
		int totalPointsCount = resClusters[clusterIndex].getTotalPointsCount();
		
		// Increment the corresponding sums.
		resClusters[clusterIndex].incrementPriceSum(nextPoint.getPricePerShare());
		resClusters[clusterIndex].incrementDeltaDaySum(nextPoint.getDeltaDay());
		resClusters[clusterIndex].incrementDeltaHourSum(nextPoint.getDeltaHour());
		resClusters[clusterIndex].incrementQuantitySum(nextPoint.getQuantity());
		
		Point4D newCentroid = new Point4D(resClusters[clusterIndex].getPriceSum()/totalPointsCount, 
				resClusters[clusterIndex].getDeltaDaySum()/totalPointsCount, 
				resClusters[clusterIndex].getDeltaHourSum()/totalPointsCount, 
				resClusters[clusterIndex].getQuantitySum()/totalPointsCount);
		
		resClusters[clusterIndex].setCenter(newCentroid);
	}
	
	private int findClosestCluster(final Point4D nextPoint) {
		
		double minDistance = -1;
		double tempDistance = -1;
		int clusterIndex = -1;
		
		// Find the index of that cluster, which center is closest to the given point.
		for (int i = 0; i < resClusters.length; i++) {
			
			tempDistance = resClusters[i].getCenter().distance(nextPoint);
			
			if (minDistance < 0 || minDistance > tempDistance) {
				minDistance = tempDistance;
				clusterIndex = i;
			} 
		}
		return clusterIndex;
	}

	@Override
	protected void submitResults() {
		nSessionAttributes sessionAttr;
		try {
			sessionAttr = new nSessionAttributes(EventConstants.SESSION_ATTRIBUTE);
			nSession session = nSessionFactory.create(sessionAttr);
			session.init();

			nChannelAttributes channelAttr = new nChannelAttributes(EventConstants.KEY_MAPPER_DATA_CHANNEL);
			nChannel chan = session.findChannel(channelAttr);
			
			nEventProperties clusters[] = new nEventProperties[resClusters.length];
			for (int i = 0 ; i < resClusters.length; i++) {
				clusters[i] = new nEventProperties();
				clusters[i].put(EventConstants.KEY_CENTER, resClusters[i].getCenter().toDoubleArray());
				clusters[i].put(EventConstants.KEY_SUM_ARRAY, resClusters[i].getSums());
				clusters[i].put(EventConstants.KEY_POINTS_COUNT, resClusters[i].getTotalPointsCount());
			}
			
			nEventProperties props = new nEventProperties();
			props.put(EventConstants.KEY_CLUSTERS, clusters);
			
			nConsumeEvent evt = new nConsumeEvent(props, "Clusters".getBytes());
			
			System.out.println("Publish result of clustering as an event.");
			chan.publish(evt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
