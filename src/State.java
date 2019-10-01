import javafx.animation.Timeline;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.Date;

/*This class represents the c\*/
public class State {

	static Timeline chatUpdateTimer;
	static Timeline graphicsUpdateTimer;
	static Object mainController;
	static RoomInterface roomManager;
	static SQLInterface jdbcManager;
	static Point lastClick;
	static Date lastTimeUpdatedGraphics;
	static Date lastTimeUpdatedChat;
	static Date lastTimeUpdatedRoomList;

	static Shape.Type drawState = Shape.Type.LINE;
	static Label currentToolBoxItemClicked;
	static String username;
	static String roomName;
	/*static
	  {
	  loadRoomManager();
	  loadJDBCManager();
	  }*/
	public static void loadRoomManager()
	{
		roomManager = RoomManager.getRoomManager();
	}
	public static void loadJDBCManager()
	{
		jdbcManager = JDBCManager.getJDBCManager();
	}
}
