import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Date;

/*This class represents the c\*/
public class State {
    static Object mainController;
    static RoomInterface roomManager = RoomManager.getRoomManager();
    static boolean isLoggedIn;
    static Point lastClick;
    static Date lastTimeUpdatedGraphics;
    static Shape.Type drawState = Shape.Type.LINE;
    static Label currentToolBoxItemClicked;
    static String username;
    static String roomName;
}
