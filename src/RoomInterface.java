import java.rmi.Remote;
import java.util.*;
import java.rmi.RemoteException;
public interface RoomInterface extends Remote {
   //public RoomManager getAllRooms() throws RemoteException;;
   public void addRoom(String roomName) throws RemoteException;
   public ArrayList<Room> getRooms() throws RemoteException;
   public ArrayList<String> getRoomsAsString() throws RemoteException;
   public  void setRoomConversation(String roomName,String text) throws RemoteException;
   public boolean isChatUpdated(String roomName,String clientChat) throws  RemoteException;
   public Chat getChatOfRoom(String roomName) throws RemoteException;
}