package rovercom.example.demo.rover;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.example.demo.rover.dao.CoordinatesDao;
import com.example.demo.rover.enumeration.ErrorCode;
import com.example.demo.rover.service.CommandsService;

//@RunWith(PowerMockRunner.class)
public class CommandsServiceTest {
	private CoordinatesDao dao;

	@Test
	public void testValidation() {
		String commandStr = "5 52\n" + "1 1 E2\n" + "MLMLM2";
		assertEquals(ErrorCode.INVALID_REQUEST.getDescription(),
				CommandsService.getInstance().executeCommand(commandStr));
	}

	@Test
	public void moveTest() {
		String commandStr = "5 5\n" + "1 1 E\n" + "MLMLM";
		assertEquals("1 2 W", CommandsService.getInstance().executeCommand(commandStr));
	}

	@Test
	public void moveBorderTest() {
		String commandStr = "5 5\n" + "1 1 E\n" + "MLMLMMM";
		assertEquals("0 2 W", CommandsService.getInstance().executeCommand(commandStr));
	}

	@Test
	public void moveWithLastLocationTest() {

//		PowerMockito.mockStatic(CoordinatesDao.class);
//		PowerMockito.when(CoordinatesDao.getInstance()).thenReturn(dao);
//		PowerMockito.when(dao.getLocationInfoState()).thenReturn(new LocationInfo(3, 4, Direction.W));

		String commandStr1 = "5 5\n" + "1 1 E\n" + "MLMLMMM";
		CommandsService.getInstance().executeCommand(commandStr1);
		String commandStr2 = "RMMMMRRL";
		assertEquals("0 5 E", CommandsService.getInstance().executeCommand(commandStr2));
	}

}
