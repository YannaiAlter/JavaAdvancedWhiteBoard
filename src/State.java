import java.awt.*;

/*This class represents the c\*/
public class State {
    static Object mainController;
    static RoomInterface roomManager = RoomManager.getRoomManager();
    static boolean isLoggedIn;
    static Point lastClick;
    static Shape.Type drawState = Shape.Type.LINE;
    static String username;
    static String roomName;
}
