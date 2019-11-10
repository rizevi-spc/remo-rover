package com.example.demo.rover.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.rover.dao.CoordinatesDao;
import com.example.demo.rover.domain.Coordinate;
import com.example.demo.rover.domain.LocationInfo;
import com.example.demo.rover.domain.ValidationResult;
import com.example.demo.rover.enumeration.Direction;
import com.example.demo.rover.enumeration.ErrorCode;
import com.example.demo.rover.enumeration.MoveAction;
import com.example.demo.rover.enumeration.OperationType;
import com.example.demo.rover.utils.FileUtils;
import com.example.demo.rover.utils.Utils;

public class CommandsService {
	private CommandsService() {
	}

	private static class HoldInstance {
		private static final CommandsService INSTANCE = new CommandsService();
	}

	public static CommandsService getInstance() {
		return HoldInstance.INSTANCE;
	}

	private LocationInfo locationInfo;
	private Coordinate searchAreaCoords;
	private CoordinatesDao coordinatesDao = CoordinatesDao.getInstance();

	public ValidationResult executeCommand(String command) {
		String[] commands = command.split("\\n");
		String initialLocCommand = null;
		String moveCommand = null;
		String searchArea = null;
		OperationType operationType = null;
		switch (commands.length) {
		case 1:
			moveCommand = commands[0];
			operationType = OperationType.MOVE;
			break;
		case 2:
			initialLocCommand = commands[0];
			operationType = OperationType.SET_INITAL_MOVE;
			moveCommand = commands[1];
			break;
		case 3:
			searchArea = commands[0];
			operationType = OperationType.SET_SEARCH_AREA_INITIAL_MOVE;
			initialLocCommand = commands[1];
			moveCommand = commands[2];
			break;
		default:
			return new ValidationResult(false, ErrorCode.INVALID_REQUEST);
		}

		ValidationResult validationResult = validateRequest(initialLocCommand, moveCommand, searchArea);
		if (!validationResult.isValid())
			return validationResult;
		List<MoveAction> moveActionList = null;
		switch (operationType) {
		case MOVE:
			moveActionList = setMoveActions(moveCommand);
			break;
		case SET_INITAL_MOVE:
			locationInfo = setLocationInfoAndPersist(initialLocCommand);
			moveActionList = setMoveActions(moveCommand);
			break;
		case SET_SEARCH_AREA_INITIAL_MOVE:
			searchAreaCoords = setSearchCoordsAndPersist(searchArea);
			locationInfo = setLocationInfoAndPersist(initialLocCommand);
			moveActionList = setMoveActions(moveCommand);
			break;
		default:
			return new ValidationResult(false, ErrorCode.INVALID_REQUEST);
		}

		moveActionList.forEach(this::executeAction);
		coordinatesDao.persistLocationInfo(locationInfo);
		return new ValidationResult(true, locationInfo.toString());
	}

	private void executeAction(MoveAction moveAction) {
		if (moveAction == MoveAction.M) {
			moveRover();
		} else {
			turnRover(moveAction);
		}
	}

	private void turnRover(MoveAction moveAction) {
		Direction headingDirection = locationInfo.getHeadingDirection();
		Direction directionAfterTurn = headingDirection.turn(moveAction);
		locationInfo.setHeadingDirection(directionAfterTurn);
	}

	private void moveRover() {
		switch (locationInfo.getHeadingDirection()) {
		case W: {
			int coordinateX = locationInfo.getCoordinateX();
			if (coordinateX > 0)
				locationInfo.setCoordinateX(coordinateX - 1);
			break;
		}
		case N: {
			int coordinateY = locationInfo.getCoordinateY();
			if (coordinateY < searchAreaCoords.getCoordinateY())
				locationInfo.setCoordinateY(coordinateY + 1);
			break;
		}
		case E: {
			int coordinateX = locationInfo.getCoordinateX();
			if (coordinateX < searchAreaCoords.getCoordinateX())
				locationInfo.setCoordinateX(coordinateX + 1);

			break;
		}
		case S: {
			int coordinateY = locationInfo.getCoordinateY();
			if (coordinateY > 0)
				locationInfo.setCoordinateY(coordinateY - 1);

			break;
		}

		default:
			break;
		}
	}

	private List<MoveAction> setMoveActions(String moveCommand) {
		List<MoveAction> moveActionList = new ArrayList<>();
		for (char moveChar : moveCommand.toCharArray()) {
			moveActionList.add(MoveAction.valueOf(String.valueOf(moveChar)));
		}
		return moveActionList;
	}

	private LocationInfo setLocationInfoAndPersist(String initialLocCommand) {
		String[] locationInfoStr = initialLocCommand.split("\\s");
		LocationInfo locationInfo = new LocationInfo(Integer.valueOf(locationInfoStr[0]),
				Integer.valueOf(locationInfoStr[1]), Direction.valueOf(locationInfoStr[2]));
		coordinatesDao.persistLocationInfo(locationInfo);
		return locationInfo;
	}

	private Coordinate setSearchCoordsAndPersist(String searchArea) {
		String[] searchCoordinate = searchArea.split("\\s");
		Coordinate coords = new Coordinate(Integer.valueOf(searchCoordinate[0]), Integer.valueOf(searchCoordinate[1]));
		coordinatesDao.persistSearchArea(coords);
		return coords;
	}

	private ValidationResult validateRequest(String initialLocCommand, String moveCommand, String searchArea) {
		if (searchArea != null) {
			String[] searchCoordinate = searchArea.split("\\s");
			for (String coord : searchCoordinate)
				if (!Utils.isNumericPositive(coord))
					return new ValidationResult(false, ErrorCode.INVALID_REQUEST);

		}
		if (initialLocCommand != null) {
			String[] initialLocChars = initialLocCommand.split("\\s");
			if (initialLocChars.length != 3)
				return new ValidationResult(false, ErrorCode.INVALID_REQUEST);
			for (int i = 0; i > 2; i++)
				if (Utils.isNumericPositive(initialLocChars[i]))
					return new ValidationResult(false, ErrorCode.INVALID_REQUEST);
			if (!Direction.checkValid(initialLocChars[2]))
				return new ValidationResult(false, ErrorCode.INVALID_REQUEST);

		}
		for (char actionChar : moveCommand.toCharArray())
			if (!MoveAction.checkValid(String.valueOf(actionChar)))
				return new ValidationResult(false, ErrorCode.INVALID_REQUEST);
		return new ValidationResult(true, "");
	}

}