import java.rmi.Remote;
import java.util.*;
import java.rmi.RemoteException;
/*
RoomInterface is the interface of RoomManager and order to pass those functions on RMI.
 */
public interface RoomInterface extends Remote {
    void addRoom(String roomName) throws RemoteException;
    ArrayList<String> getRoomsAsString() throws RemoteException;
    void setRoomConversation(String roomName,String text) throws RemoteException;
    boolean isChatUpdated(String roomName,String clientChat) throws  RemoteException;
    Chat getChatOfRoom(String roomName) throws RemoteException;
    void setClientRoom(String username, String roomname) throws RemoteException;
    String getClientRoom(String username) throws RemoteException;

}