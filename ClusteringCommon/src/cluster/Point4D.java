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

	public double distance(Point4D point) {
		return Math
				.sqrt(Math.pow((pricePerShare - point.getPricePerShare()), 2)
						+ Math.pow((deltaDay - point.getDeltaDay()), 2)
						+ Math.pow((deltaHour - point.getDeltaHour()), 2)
						+ Math.pow((quantity - point.getQuantity()), 2));
	}
	
	public final Point4D minus(final Point4D other) {
		
		return new Point4D(other.getPricePerShare() - pricePerShare, 
				other.getDeltaDay() - deltaDay, 
				other.getDeltaHour() - deltaHour, 
				other.getQuantity() - quantity);
	}
	
	public final Point4D add(final Point4D other) {
		
		return new Point4D(other.getPricePerShare() + pricePerShare, 
				other.getDeltaDay() + deltaDay, 
				other.getDeltaHour() + deltaHour, 
				other.getQuantity() + quantity);
	}
	
	public Point4D divide(final int divider) {
		
		return new Point4D(pricePerShare/divider, 
				deltaDay/divider, 
				deltaHour/divider, 
				quantity/divider);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(deltaDay);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(deltaHour);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pricePerShare);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
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
		if (!(obj instanceof Point4D)) {
			return false;
		}
		Point4D other = (Point4D) obj;
		if (Double.doubleToLongBits(deltaDay) != Double
				.doubleToLongBits(other.deltaDay)) {
			return false;
		}
		if (Double.doubleToLongBits(deltaHour) != Double
				.doubleToLongBits(other.deltaHour)) {
			return false;
		}
		if (Double.doubleToLongBits(pricePerShare) != Double
				.doubleToLongBits(other.pricePerShare)) {
			return false;
		}
		if (quantity != other.quantity) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Point4D [pricePerShare=" + pricePerShare + ", deltaDay="
				+ deltaDay + ", deltaHour=" + deltaHour + ", quantity="
				+ quantity + "]";
	}
}