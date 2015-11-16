package cluster;

public class Cluster {
	private Point4D ceneter;
	private int totoalPointsCount;
	private double priceSum;
	private double deltaDaySum;
	private double delataHourSum;
	private int quantitySum;
	
	public Cluster(Point4D center, int totalPointsCount, double priceSum, double deltaDaySum, double deltaHourSum, int quantitySum) {
		this.ceneter = center;
		this.priceSum = priceSum;
		this.delataHourSum = deltaHourSum;
		this.deltaDaySum = deltaDaySum;
		this.quantitySum = quantitySum;
	}

	public Point4D getCeneter() {
		return ceneter;
	}

	public int getTotoalPointsCount() {
		return totoalPointsCount;
	}

	public double getPriceSum() {
		return priceSum;
	}

	public double getDeltaDaySum() {
		return deltaDaySum;
	}

	public double getDelataHourSum() {
		return delataHourSum;
	}

	public int getQuantitySum() {
		return quantitySum;
	}
}
