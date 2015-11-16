package cluster;

public class Point4D {
	private double pricePerShare;
	private double deltaDay;
	private double deltaHour;
	private int quantity;
	
	public Point4D(double pPS, double dD, double dH, int quantity) {
		this.pricePerShare = pPS;
		this.deltaDay = dD;
		this.deltaHour = dH;
		this.quantity = quantity;
	}
	
	public double getPricePerShare() {
		return pricePerShare;
	}
	
	public double getDeltaDay() {
		return deltaDay;
	}
	
	public double getDeltaHour() {
		return deltaHour;
	}
	
	public int getQuantity() {
		return quantity;
	}
}