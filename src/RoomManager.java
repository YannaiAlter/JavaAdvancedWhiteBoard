import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RoomManager {

	ArrayList<Room> room;
	Registry registry;

	public RoomManager() {
		System.err.println("Server ready");
		room = new ArrayList<Room>();
		try {
			registry = LocateRegistry.createRegistry(1099);
		} catch (Exception x)
		{
			System.err.println("Error" + x);
		}

	}

	public void addRoom() {
		Room newRoom = new Room();
		room.add(newRoom);
		try
		{
			registry.bind(String.valueOf(room.size()),newRoom);
		}
		catch(Exception e)
		{
			System.out.println("error - cant bind this room");
		}    
	}

	public static void main(String args[]) {

		RoomManager x = new RoomManager();
		x.addRoom();
	}
}
