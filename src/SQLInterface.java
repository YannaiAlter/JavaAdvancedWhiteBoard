
import java.rmi.Remote;
import java.util.*;
import java.rmi.RemoteException;
/*
RoomInterface is the interface of RoomManager and order to pass those functions on RMI.
 */
public interface SQLInterface extends Remote {
    boolean createUser(String username, String password) throws RemoteException;

    int checkLogin(String username, String password) throws RemoteException;

    void LogInOutUser(String username, boolean in) throws RemoteException;
}
