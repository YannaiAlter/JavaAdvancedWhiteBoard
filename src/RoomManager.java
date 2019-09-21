import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.rmi.server.UnicastRemoteObject;

/*
RoomManager Class is a class which his instance is created only one time and passed by the RMI to all clients.
This class contains a list of all of the rooms that are registered, and provides the data of the rooms - chat conversation, etc.
 */
public class RoomManager extends UnicastRemoteObject implements RoomInterface {

	ArrayList<Room> room;
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
	public ArrayList<String> getRoomsAsString() {
		ArrayList<String> list = new ArrayList<String>();
		for (Room x : room) {
			list.add(x.getName());
		}
		return list;
	}

	public void addRoom(String roomName)  {
		Room newRoom = new Room(roomName);
		room.add(newRoom);
	}
	public synchronized void setRoomConversation(String roomName,String text) {
		for (Room x : room) {
			if (x.getName().equals(roomName)) {
				x.getChat().setChatConversation(getChatOfRoom(roomName).getChatConversation() + text);
				break;
			}
		}
	}
	public synchronized boolean isChatUpdated(String roomName,String clientChat)
	{
		for (Room x : room) {
			if (x.getName().equals(roomName)) {
				if(x.getChat() == null) return false;
				return x.getChat().getChatConversation().equals(clientChat);
			}
		}
		return false;
	}
	public synchronized Chat getChatOfRoom(String roomName)
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