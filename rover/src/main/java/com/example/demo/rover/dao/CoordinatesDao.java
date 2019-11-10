package com.example.demo.rover.dao;

import com.example.demo.rover.domain.Coordinate;
import com.example.demo.rover.domain.LocationInfo;
import com.example.demo.rover.utils.FileUtils;

public class CoordinatesDao {
	private CoordinatesDao() {
	}
	private static class HoldInstance {
		private static final CoordinatesDao INSTANCE = new CoordinatesDao();
	}

	public static CoordinatesDao getInstance() {
		return HoldInstance.INSTANCE;
	}
	
	public void persistLocationInfo(LocationInfo locationInfo) {
		FileUtils.writeLocationInfo(locationInfo);
	}

	public LocationInfo getLocationInfoState() {
		return FileUtils.readLocationInfo();
	}

	public void persistSearchArea(Coordinate coords) {
		FileUtils.writeSearchCoords(coords);
	}

	public Coordinate getSearchAreaState() {
		return FileUtils.readSearchCoords();
	}

}
