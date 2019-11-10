package com.example.demo.rover.domain;

import com.example.demo.rover.enumeration.Direction;

public class LocationInfo extends Coordinate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7772006345489182922L;

	public LocationInfo(int coordinateX, int coordinateY, Direction headingDirection) {
		super(coordinateX, coordinateY);
		this.headingDirection = headingDirection;
	}

	private Direction headingDirection;

	public Direction getHeadingDirection() {
		return headingDirection;
	}

	public void setHeadingDirection(Direction headingDirection) {
		this.headingDirection = headingDirection;
	}

	@Override
	public String toString() {
		return String.format("%d %d %s", coordinateX, coordinateY, headingDirection.name());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((headingDirection == null) ? 0 : headingDirection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationInfo other = (LocationInfo) obj;
		if (headingDirection != other.headingDirection)
			return false;
		return true;
	}

}
