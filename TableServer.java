        
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TableServer implements RMIInterface {
        
    ArrayList<RoomProperties> table;
    Registry registry;

    public TableServer() {
    System.err.println("Server ready");
    try {
    registry = LocateRegistry.getRegistry();
    } catch (Exception x)
    {
    System.err.println("Error");
    }
    
    }

    public String helloTo(String x) {
        return "Hello, world!";
    }
        
	public void addRoom()
	{
	   
        try {
            TableServer obj = new TableServer();
            RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            
            registry.bind("obj1", stub);

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}
    public static void main(String args[]) {
  
	TableServer x = new TableServer();
    x.addRoom();
    
    }
}
