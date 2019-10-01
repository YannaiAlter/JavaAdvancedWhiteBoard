import java.io.Serializable;
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
	String roomAdminUserName;
	Chat chat;
	ArrayList<Shape> shape = new ArrayList<>();
	ArrayList<Shape> undoShapes = new ArrayList<>();
	ArrayList<String> clients = new ArrayList<>();
	Date graphicsUpdateTime;
	Date roomListUpdateTime;

	public Room(String roomName,String roomAdminUserName) {
		this.name=roomName;
		chat = new Chat("");
		graphicsUpdateTime=new GregorianCalendar(2000, Calendar.OCTOBER, 27).getTime();
		roomListUpdateTime=new GregorianCalendar(2000, Calendar.OCTOBER, 27).getTime();
		this.roomAdminUserName = roomAdminUserName;
	}
	public Date getGraphicsUpdateDate() { return this.graphicsUpdateTime; }
	public Date getRoomListUpdateTime() { return this.roomListUpdateTime; }
	public Chat getChat() { return this.chat; }
	public String getRoomName(){
		return this.name;
	}
	public void addShape(Shape shape)
	{
		this.shape.add(shape);
	}
	public void doGraphicsUpdate() {this.graphicsUpdateTime=new Date(System.currentTimeMillis());}
	public void doRoomListUpdate() {this.roomListUpdateTime=new Date(System.currentTimeMillis());}
	public boolean isAdmin(String userName) {
		return this.roomAdminUserName.equals(userName);
	}
	public void deleteUser(String userName)
	{
		for(int i=0;i<clients.size();i++)
		{
			if(clients.get(i).equals(userName)) clients.remove(i);
		}
	}
	public ArrayList<Shape> getShapes() { return this.shape; }
	public void undoShape()
	{
		if(this.shape.size() == 0) return;//If we are pressing undo while there is no objects on screen.
		undoShapes.add(shape.get(shape.size()-1)); // adding to undoShapes which will behave like a stack saver
		this.shape.remove(this.shape.size()-1);
		doGraphicsUpdate();
	}
	public void redoShape()
	{
		if(this.undoShapes.size() == 0) return;//If we are pressing redo while there is no what to redo.
		this.shape.add(this.undoShapes.get(this.undoShapes.size()-1));
		this.undoShapes.remove(this.undoShapes.size()-1);
		doGraphicsUpdate();
	}
	public void clearUndoShapes() //New state in case of drawing
	{
		undoShapes.clear();
	}
	public void addClient(String userName)
	{
		clients.add(userName);
	}

	public ArrayList<String> getAllClients() {
		return clients;
	}
}
