package datagen;

import java.util.concurrent.ConcurrentLinkedQueue;

import cluster.Point4D;

public class PointGenerator {
	private Point4D center;
	private int count;
	private int start;
	
	public PointGenerator(Point4D center, int count, int start) {
		this.center = center;
		this.count = count;
		this.start = start;
	};
	
	private static double randomDouble(double min, double max) {
	    double diff = max - min;
	    return min + Math.random() * diff;
	}
	
	private static int randomInt(int min, int max) {
		int diff = max - min;
		return (int) (min + Math.random() * diff);
	}
	
	public ConcurrentLinkedQueue<Point4D> generatePointsAround() {
		ConcurrentLinkedQueue<Point4D> listOfPoints = new ConcurrentLinkedQueue<Point4D>();
		
		for (int i = 0; i < count; i++) {
			double newPPS = randomDouble((double) start, start + 20.0) + center.getPricePerShare();
			double newDD = randomDouble((double) start, start + 20.0) + center.getDeltaDay();
			double newDH = randomDouble((double) start, start + 20.0) + center.getDeltaHour();
			int newQ = randomInt(start, start + 20) + center.getQuantity();
			
			Point4D point = new Point4D(newPPS, newDD, newDH, newQ);
			listOfPoints.add(point);
		}
		
		return listOfPoints;
	}
}