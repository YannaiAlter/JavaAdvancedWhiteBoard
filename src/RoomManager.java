import java.lang.reflect.Array;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

public class RoomManager {

	static ArrayList<Room> room=new ArrayList<Room>();
	static Registry registry;

	public static void createServer() {
		System.err.println("Server ready");
		room = new ArrayList<>();
		try {
			registry = LocateRegistry.createRegistry(1100);
		} catch (Exception x)
		{
			System.err.println("Error" + x);
		}

	}

	public static ArrayList<String> getRoomNamesFromRegistry()throws MalformedURLException, RemoteException, NotBoundException
	{
		ArrayList<String>roomNames = new ArrayList<String>();
		String[]boundNames =registry.list();
		for(String name : boundNames)
		{
			RoomInterface room = (RoomInterface)registry.lookup(name);
			roomNames.add(room.getName());
		}
		return roomNames;
	}
	public static void initRegistry()throws MalformedURLException, RemoteException, NotBoundException
	{
		registry=LocateRegistry.getRegistry();
		System.out.println(registry);

	}
	public static void addRoom(String roomName) throws RemoteException {
		Room newRoom = new Room(roomName);
		room.add(newRoom);
		try
		{
			registry.rebind(roomName,newRoom);
			System.out.println("Added Room");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}    
	}

	public static void main(String args[]) {

	try{createServer();
		addRoom("hello");
		addRoom("sup");
		ArrayList<String>rooms = getRoomNamesFromRegistry();
		System.out.println(Arrays.toString(rooms.toArray()));
	}
	catch (Exception e)
	{
	System.out.println(e);
	}


	}
}
