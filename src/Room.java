import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/*
This class contains the Room properties. RoomManager instance which is common-instance for all clients handles a list of all Rooms,
every room is passed in the RMI and this is why the class is serialized.
 */
class Room implements Serializable
{
    String name;
    Chat chat;
    ArrayList<Shape> shape = new ArrayList<>();
    public Room(String roomName) {
        this.name=roomName;
        chat = new Chat("");

    }
    public Chat getChat() { return this.chat; }
    public String getRoomName(){
        return this.name;
    }
    public void addShape(Shape shape)
    {
        this.shape.add(shape);
    }
    public static void main(String[]args) {

    }
}
