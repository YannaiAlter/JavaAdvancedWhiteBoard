import java.rmi.Remote;
import java.util.*;
import java.rmi.RemoteException;
public interface RoomInterface extends Remote {
   //public RoomManager getAllRooms() throws RemoteException;;
   public void addRoom(String roomName) throws RemoteException;
   public ArrayList<Room> getRooms() throws RemoteException;
   public ArrayList<String> getRoomsAsString() throws RemoteException;

}
