import com.sun.glass.ui.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;

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

	public RoomManager() throws RemoteException {
		System.err.println("Server ready");
		room = new ArrayList<>();
		try {
			System.setProperty("java.rmi.server.hostname", DBFinals.RMIHost);
			registry = LocateRegistry.createRegistry(DBFinals.RMIPort);
			registry.rebind("RoomManager", this);
			System.out.println("Created RoomManager");
		} catch (Exception x) {
			System.err.println("Error" + x);

		}

	}
	public void updateRoomListTime(String roomName)
	{
		getRoom(roomName).doRoomListUpdate();
	}
	public boolean isClientsListUpdated(ArrayList<String> clientsList,String roomName)
	{
		try {
			return State.roomManager.getAllClientsOfRoom(roomName).equals(clientsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean isAdmin(String userName,String roomName)
	{
		Room r = getRoom(roomName);
		return r.isAdmin(userName);
	}
	public void addClientToRoom(String roomName,String userName)
	{
		Room r = getRoom(roomName);
		r.addClient(userName);
	}
	public ArrayList<String> getAllClientsOfRoom(String roomName)
	{
		return getRoom(roomName).getAllClients();
	}
	public void deleteRoom(String roomName)
	{
		System.out.println(roomName);
		for(int i=0; i<room.size(); i++) if(room.get(i).getRoomName().equals(roomName))room.remove(i);
	}
	public void deleteUserFromRoom(String roomName,String userName)
	{
		Room r = getRoom(roomName);
		r.deleteUser(userName);
	}
	public static RoomInterface getRoomManager() {
		try {
			Registry registry = LocateRegistry.getRegistry(DBFinals.RMIHost, DBFinals.RMIPort);
			RoomInterface rooms = (RoomInterface) registry.lookup("RoomManager");
			return rooms;
		} catch (Exception e) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setHeaderText("RMI Connection Failed");
			errorAlert.setContentText("Please check that RMI Server is available");
			errorAlert.showAndWait();
		}
		return null;
	}

	public void addShapeToRoom(String roomName, Shape shape)
	{
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room x = getRoom(roomName);
			x.addShape(shape);
			x.clearUndoShapes();
		}
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

	public synchronized boolean addRoom(String roomName,String adminUserName)  {
		Room r = getRoom(roomName); //check if room is already exists
		if(r != null) return false;
		Room newRoom = new Room(roomName, adminUserName);
		room.add(newRoom);
		return true;
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
	public boolean isChatUpdated(String roomName,Date clientUpdateDate)
	{

		Room room;
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			room = getRoom(roomName);
		}
		roomLocker.remove(roomName);
		return room.getChat().getChatUpdateTime().equals(clientUpdateDate);

	}

	public boolean isRoomListUpdated(String roomName,Date roomListLastUpdateTime)
	{
		Room room;
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			room = getRoom(roomName);
		}
		roomLocker.remove(roomName);
		return room.getRoomListUpdateTime().equals(roomListLastUpdateTime);
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
		Room room;
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			room = getRoom(roomName);
		}
		roomLocker.remove(roomName);
		return room.getGraphicsUpdateDate().equals(clientLastUpdateTime);
	}


	public void  updateGraphicsTime(String roomName)
	{
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room room = getRoom(roomName);
			room.doGraphicsUpdate();
		}
		roomLocker.remove(roomName);
	}
	public void  updateChatTime(String roomName)
	{
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room room = getRoom(roomName);
			room.getChat().doUpdate();
		}
		roomLocker.remove(roomName);
	}
	public void updateRoomList(String roomName)
	{
		roomLocker.put(roomName,new Object());
		synchronized (roomLocker) {
			Room room = getRoom(roomName);
			room.doRoomListUpdate();
		}
		roomLocker.remove(roomName);
	}
	public Date getWhiteBoardUpdateTimeOfRoom(String roomName)
	{
		return getRoom(roomName).getGraphicsUpdateDate();
	}
	public Date getRoomListUpdateTimeOfRoom(String roomName)
	{
		return getRoom(roomName).getRoomListUpdateTime();
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
