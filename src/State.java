import java.awt.*;

/*This class represents the c\*/
public class State {
    static Object mainController;
    static RoomInterface roomManager = RoomManager.getRoomManager();
    static boolean isLoggedIn;
    enum Shape {LINE,RECTANGLE,CIRCLE,LINE_SECOND_CLICK}
    static Point lastClick;
    static Shape drawState = Shape.LINE;
    static String username;
    static String roomName;
}
