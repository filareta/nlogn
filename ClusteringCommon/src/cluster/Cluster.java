package cluster;

public class Cluster {
	private Point4D center;
	private int totalPointsCount;
	private double priceSum;
	private double deltaDaySum;
	private double deltaHourSum;
	private int quantitySum;

	public Cluster(Point4D center, int totalPointsCount, double priceSum,
			double deltaDaySum, double deltaHourSum, int quantitySum) {
		this.center = center;
		this.totalPointsCount = totalPointsCount;
		this.priceSum = priceSum;
		this.deltaHourSum = deltaHourSum;
		this.deltaDaySum = deltaDaySum;
		this.quantitySum = quantitySum;
	}

	public Point4D getCenter() {
		return center;
	}
	
	public final void setCenter(final Point4D newCenter) {
		this.center = newCenter;
	}

	public int getTotalPointsCount() {
		return totalPointsCount;
	}
	
	public final void incrementTotalPointsCount() {
		++totalPointsCount;
	}

	public double getPriceSum() {
		return priceSum;
	}
	
	public final void incrementPriceSum(final double price) {
		this.priceSum = priceSum + price;
	}

	public double getDeltaDaySum() {
		return deltaDaySum;
	}
	
	public final void incrementDeltaDaySum(final double deltaDay) {
		this.deltaDaySum = deltaDaySum + deltaDay;
	}

	public double getDeltaHourSum() {
		return deltaHourSum;
	}
	
	public final void incrementDeltaHourSum(final double deltaHour) {
		this.deltaHourSum = deltaHourSum + deltaHour;
	}

	public int getQuantitySum() {
		return quantitySum;
	}
	
	public final void incrementQuantitySum(final int quantitySum) {
		this.quantitySum = quantitySum + quantitySum;
	}

	public double distance(Cluster current) {
		return center.distance(current.getCenter());
	}
	
	public final double[] getSums() {
		return new double[] {priceSum, deltaDaySum, deltaHourSum, quantitySum};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		long temp;
		temp = Double.doubleToLongBits(deltaDaySum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(deltaHourSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(priceSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantitySum;
		result = prime * result + totalPointsCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cluster)) {
			return false;
		}
		Cluster other = (Cluster) obj;
		if (center == null) {
			if (other.center != null) {
				return false;
			}
		} else if (!center.equals(other.center)) {
			return false;
		}
		if (Double.doubleToLongBits(deltaDaySum) != Double
				.doubleToLongBits(other.deltaDaySum)) {
			return false;
		}
		if (Double.doubleToLongBits(deltaHourSum) != Double
				.doubleToLongBits(other.deltaHourSum)) {
			return false;
		}
		if (Double.doubleToLongBits(priceSum) != Double
				.doubleToLongBits(other.priceSum)) {
			return false;
		}
		if (quantitySum != other.quantitySum) {
			return false;
		}
		if (totalPointsCount != other.totalPointsCount) {
			return false;
		}
		return true;
	}
}
