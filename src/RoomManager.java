import javafx.scene.canvas.Canvas;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
/*
RoomManager Class is a class which his instance is created only one time and passed by the RMI to all clients.
This class contains a list of all of the rooms that are registered, and provides the data of the rooms - chat conversation, etc.
 */
public class RoomManager extends UnicastRemoteObject implements RoomInterface {

	private final Object chatLock = new Object();
	private HashMap roomLocker=new HashMap(); //Locks specific room with synchronized

	ArrayList<Room> room;
	Registry registry;
	HashMap<String, String> roomOfClient; //Client username to room name

	public RoomManager() throws RemoteException {
		System.err.println("Server ready");
		room = new ArrayList<>();
		roomOfClient = new HashMap<String, String>();
		try {
			System.setProperty("java.rmi.server.hostname", DBFinals.RMIHost);
			registry = LocateRegistry.createRegistry(DBFinals.RMIPort);
			registry.rebind("RoomManager", this);
			System.out.println("Created");
		} catch (Exception x) {
			System.err.println("Error" + x);

		}

	}

	public static RoomInterface getRoomManager() {
		try {
			Registry registry = LocateRegistry.getRegistry(DBFinals.RMIHost, DBFinals.RMIPort);
			RoomInterface rooms = (RoomInterface) registry.lookup("RoomManager");
			return rooms;
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public void addShapeToRoom(String roomName, Shape shape)
	{
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room x = getRoom(roomName);
			x.addShape(shape);		}
		roomLocker.remove(roomName);
	}

	@Override
	public ArrayList<String> getRoomsAsString() {
		ArrayList<String> list = new ArrayList<String>();
		for (Room x : room) {
			list.add(x.getRoomName());
		}
		return list;
	}

	public void addRoom(String roomName)  {

		Room newRoom = new Room(roomName);
		room.add(newRoom);
	}
	public void setRoomConversation(String roomName,String text) {
		synchronized (chatLock) {
			for (Room x : room) {
				if (x.getRoomName().equals(roomName)) {
					x.getChat().setChatConversation(getChatOfRoom(roomName).getChatConversation() + text);
					break;
				}
			}
		}
	}
	public boolean isChatUpdated(String roomName,String clientChat)
	{

		roomLocker.put(roomName,new Object());
		Room x;
		synchronized (roomLocker) {
			 x = getRoom(roomName);
			}
		roomLocker.remove(roomName);
		if(x != null)
		return x.getChat().getChatConversation().equals(clientChat);

		return true;

	}
	public  Chat getChatOfRoom(String roomName)
	{
		Chat chat = null;
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			for (Room x : room) {
				if (x.getRoomName().equals(roomName)) {
					chat = x.getChat();
				}
			}
		}
		roomLocker.remove(roomName);
		if(chat != null)
		return chat;

		return null;

	}
	public void setClientRoom(String key, String value) {
		roomOfClient.put(key,value);
	}

	public String getClientRoom(String key){
		return roomOfClient.get(key);
	}

	public static void main(String args[]) {

		try {
			RoomManager room = new RoomManager();
			getRoomManager();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Room getRoom(String roomName)
	{
		for (Room x : room) {
			if (x.getRoomName().equals(roomName)) {
				return x;
			}
		}
		return null;
	}

	public boolean isBoardUpdated(String roomName,Date clientLastUpdateTime) {
		roomLocker.put(roomName,new Object());
		Room room;
		synchronized (roomLocker) {
			 room = getRoom(roomName);
		}
		roomLocker.remove(roomName);
		return room.getDate().equals(clientLastUpdateTime);
	}

	public void  updateGraphicsTime(String roomName)
	{
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room room = getRoom(roomName);
			room.doUpdate();
		}
		roomLocker.remove(roomName);
	}
	public Date getWhiteBoardUpdateTimeOfRoom(String roomName)
	{
		return getRoom(roomName).getDate();
	}

	public ArrayList<Shape> getAllShapesOfRoom(String roomName)
	{
		Room room;
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			room = this.getRoom(roomName);
		}
		roomLocker.remove(roomName);
		return room.getShapes();

	}
	public void undoShapeOfRoom(String roomName)
	{

		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room room = this.getRoom(roomName);
			room.undoShape();
		}
		roomLocker.remove(roomName);
	}
	public void redoShapeOfRoom(String roomName)
	{

		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room room = this.getRoom(roomName);
			room.redoShape();
		}
		roomLocker.remove(roomName);
	}
}