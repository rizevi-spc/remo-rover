package com.example.demo.rover.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.demo.rover.domain.Coordinate;
import com.example.demo.rover.domain.LocationInfo;


public class FileUtils {
	private static final String LOCATION_INFO_TXT = ".loc/location-info.txt";
	private static final String SEARCH_AREA_TXT = ".loc/search-area.txt";
	private final static Logger LOGGER = LogManager.getLogger(FileUtils.class);

	private FileUtils() {
	}

	public static LocationInfo readLocationInfo() {
		File file = getFile(LOCATION_INFO_TXT);
		return Optional.ofNullable(file).map(FileUtils::readFromFile).orElse(null);
	}

	public static void writeLocationInfo(Coordinate locationInfo) {
		File file = getFile(LOCATION_INFO_TXT);
		FileUtils.writeToFile(locationInfo, file);
	}
	public static Coordinate readSearchCoords() {
		File file = getFile(SEARCH_AREA_TXT);
		return Optional.ofNullable(file).map(FileUtils::readFromFile).orElse(null);
	}
	
	public static void writeSearchCoords(Coordinate locationInfo) {
		File file = getFile(SEARCH_AREA_TXT);
		FileUtils.writeToFile(locationInfo, file);
	}

	private static LocationInfo readFromFile(File file) {
		try {
			FileInputStream fileIntputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileIntputStream);
			LocationInfo locationInfo = (LocationInfo) objectInputStream.readObject();
			objectInputStream.close();
			fileIntputStream.close();
			return locationInfo;
		} catch (IOException e) {
			LOGGER.error(e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.toString());
			return null;
		}
	}

	private static void writeToFile(Coordinate locationInfo, File file) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(locationInfo);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			LOGGER.error(e.toString());
		}
	}

	private static File getFile(String fileStr) {
		File file = new File(fileStr);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return file;
	}


}
