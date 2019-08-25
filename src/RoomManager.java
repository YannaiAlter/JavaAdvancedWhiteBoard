import java.lang.reflect.Array;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.util.Arrays;

public class RoomManager extends UnicastRemoteObject implements RoomInterface
{

	ArrayList<Room> room=new ArrayList<Room>();
	Registry registry;

	public RoomManager () throws RemoteException
	{
		System.err.println("Server ready");
		room = new ArrayList<>();
		try {
			System.setProperty("java.rmi.server.hostname",DBFinals.RMIHost);
			registry = LocateRegistry.createRegistry(DBFinals.RMIPort);
			registry.rebind("RoomManager",this);
			System.out.println("Created");
		} catch (Exception x)
		{
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
	public ArrayList<Room> getRooms()
	{
		return this.room;
	}

	public void initRegistry()throws MalformedURLException, RemoteException, NotBoundException
	{
		registry=LocateRegistry.getRegistry();
		//System.out.println(registry);


	}
	public static RoomInterface getRoomManager()
	{
		try {
			Registry registry = LocateRegistry.getRegistry(DBFinals.RMIHost,DBFinals.RMIPort);
			RoomInterface rooms = (RoomInterface) registry.lookup("RoomManager");
			return rooms;
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return  null;
	}

	@Override
	public ArrayList<String> getRoomsAsString() throws RemoteException {
		ArrayList <String> list = new ArrayList<String>();
		for(Room x : room)
		{
			list.add(x.getName());
		}
		return list;
	}

	public void addRoom(String roomName) throws RemoteException {
		Room newRoom = new Room(roomName);
		room.add(newRoom);
		try
		{
			registry.rebind("RoomManager",this);
			System.out.println("Added Room");
		}
		catch(Exception e) {
			e.printStackTrace();
		}    
	}

	public static void main(String args[]) {

	try{
		RoomManager room = new RoomManager();
		getRoomManager();
		while(true)
		{
			for(Room x : room.getRooms()) {
			System.out.print(x);
			System.out.println();
			}
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}

	}
}
