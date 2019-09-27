import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
/*
This class contains the Room properties. RoomManager instance which is common-instance for all clients handles a list of all Rooms,
every room is passed in the RMI and this is why the class is serialized.
 */
class Room implements Serializable
{
    String name;
    Chat chat;
    Canvas canvas;
    public Room(String roomName) {
        this.name=roomName;
        chat = new Chat("");

    }
    public Chat getChat() { return this.chat; }
    public String getRoomName(){
        return this.name;
    }
    public void addShape(Canvas canvas)
    {
     this.canvas=canvas;
    }
    public static void main(String[]args) {

    }
}
