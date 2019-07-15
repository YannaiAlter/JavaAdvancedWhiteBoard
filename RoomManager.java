        
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RoomManager {
        
    ArrayList<Room> room;
    Registry registry;

    public RoomManager() {
    System.err.println("Server ready");
    try {
    registry = LocateRegistry.createRegistry(1099);
    } catch (Exception x)
    {
    System.err.println("Error" + x);
    }
    
    }

    public String helloTo(String x) {
        return "Hello, world!";
    }
        
	public void addRoom()
	{
	   
        try {
	   Room x = new Room();
           RoomInterface stub = (RoomInterface) UnicastRemoteObject.exportObject(x, 0);

            // Bind the remote object's stub in the registry
            
           registry.bind("obj1", x);

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}
    public static void main(String args[]) {
  
	RoomManager x = new RoomManager();
    x.addRoom();
   while(true); 
    }
}
