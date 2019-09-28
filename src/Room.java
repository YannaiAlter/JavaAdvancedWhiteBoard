import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
This class contains the Room properties. RoomManager instance which is common-instance for all clients handles a list of all Rooms,
every room is passed in the RMI and this is why the class is serialized.
 */
class Room implements Serializable
{
    String name;
    Chat chat;
    ArrayList<Shape> shape = new ArrayList<>();
    Date updateTime;
    public Room(String roomName) {
        this.name=roomName;
        chat = new Chat("");
        updateTime=new GregorianCalendar(2000, Calendar.OCTOBER, 27).getTime();

    }
    public Date getDate() { return this.updateTime; }
    public Chat getChat() { return this.chat; }
    public String getRoomName(){
        return this.name;
    }
    public void addShape(Shape shape)
    {
        this.shape.add(shape);
    }
    public void doUpdate() {this.updateTime=new Date(System.currentTimeMillis());}
    public ArrayList<Shape> getShapes() { return this.shape; }
    public static void main(String[]args) {

    }
}
