import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.lang.reflect.Array;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.util.Arrays;

public class RoomManager extends UnicastRemoteObject implements RoomInterface {

	ArrayList<Room> room = new ArrayList<Room>();
	Registry registry;

	public RoomManager() throws RemoteException {
		System.err.println("Server ready");
		room = new ArrayList<>();
		try {
			System.setProperty("java.rmi.server.hostname", DBFinals.RMIHost);
			registry = LocateRegistry.createRegistry(DBFinals.RMIPort);
			registry.rebind("RoomManager", this);
			System.out.println("Created");
		} catch (Exception x) {
			System.err.println("Error" + x);

		}

	}


	/*	public ArrayList<String> getRoomNamesFromRegistry()throws MalformedURLException, RemoteException, NotBoundException
		{
			ArrayList<String>roomNames = new ArrayList<String>();
			String[]boundNames =registry.list();
			System.out.println("hey");

			for(String name : boundNames)
			{
				RoomInterface room = (RoomInterface)registry.lookup(name);
				System.out.println("Room: "+room.getName());
				roomNames.add(room.getName());

			}
			return roomNames;
		}

	 */
	public Room getRoomWithName(String name) {
		for (Room x : room) if (x.getName().equals(name)) return x;
		return null;
	}



	public ArrayList<Room> getRooms() {
		return this.room;
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

	@Override
	public ArrayList<String> getRoomsAsString() throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();
		for (Room x : room) {
			list.add(x.getName());
		}
		return list;
	}

	public void addRoom(String roomName) throws RemoteException {
		Room newRoom = new Room(roomName);
		room.add(newRoom);
	}
	public void setTextOfRoom(String roomName,String text) {
		for (Room x : room) {
			if (x.getName().equals(roomName)) {
				x.getChat().setChatConversation(text);
				break;
			}
		}
	}
	public boolean isChatUpdated(String roomName,String clientChat)
	{
		for (Room x : room) {
			if (x.getName().equals(roomName)) {
				if(x.getChat() == null) return false;
				return x.getChat().getChatConversation().equals(clientChat);
			}
		}
		return false;
	}
	public Chat getChatOfRoom(String roomName)
	{
		for (Room x : room) {
			if (x.getName().equals(roomName)) {
				return x.getChat();
			}
		}
		return null;
	}
	public static void main(String args[]) {

		try {
			RoomManager room = new RoomManager();
			getRoomManager();
			while (true) {
				for (Room x : room.getRooms()) {
					System.out.print(x);
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}