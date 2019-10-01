
import java.rmi.Remote;
import java.util.*;
import java.rmi.RemoteException;
/*
   SQLInteface is the interface of JDBCManager and order to pass those functions on RMI.
 */
public interface SQLInterface extends Remote {
	//returns false if user already exists and true if user is created.
	boolean createUser(String username, String password) throws RemoteException;

	//Returns: 0-password is correct and login successful, 1 - User does not exist, 2-wrong password, 3-user already logged in
	int checkLogin(String username, String password) throws RemoteException;

	//Marking on mysql that user has logged in/out depends on the boolean value
	void LogInOutUser(String username, boolean in) throws RemoteException;
}
