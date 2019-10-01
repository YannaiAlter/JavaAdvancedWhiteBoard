import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.HashMap;

public class JDBCManager extends UnicastRemoteObject implements SQLInterface {

	private HashMap roomLocker=new HashMap(); //Locks specific room with synchronized

	public static void main(String args[]) {
		try {
			Connection connection = null; // manages connection
			PreparedStatement pt = null; // manages prepared statement
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DBFinals.url, DBFinals.user, DBFinals.password);
			pt = connection.prepareStatement("select username,password,loggedin from accounts for update");
			ResultSet rs = pt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
				System.out.println(rs.getString("loggedin"));
			}
		}
		catch (Exception e) {
			System.out.println("error");
		}
	}

	public JDBCManager() throws RemoteException
	{
		Registry registry = LocateRegistry.getRegistry(DBFinals.RMIHost, DBFinals.RMIPort);
		registry.rebind("JDBCManager", this);
		System.out.println("Created JDBCManager");
	}
	public static SQLInterface getJDBCManager() {
		try {
			Registry registry = LocateRegistry.getRegistry(DBFinals.RMIHost, DBFinals.RMIPort);
			SQLInterface rooms = (SQLInterface) registry.lookup("JDBCManager");
			return rooms;
		} catch (Exception e) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setHeaderText("SQL Connection Failed");
			errorAlert.setContentText("Please check that SQL Server is available");
			errorAlert.showAndWait();
		}
		return null;
	}

	public boolean createUser(String username, String password)
	{

		Connection connection = null; // manages connection
		PreparedStatement pt = null; // manages prepared statement

		// connect to database usernames and query database
		try {
			roomLocker.put(username,new Object());
			synchronized (roomLocker) {
				// establish connection to database
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(DBFinals.url, DBFinals.user, DBFinals.password);

				pt = connection.prepareStatement("select username,password from accounts where username=? for update"); //select for update will lock database

				// process query results
				pt.setString(1, username);
				ResultSet rs = pt.executeQuery();
				if (rs.next()) //username already exists
					return false;


				// query database
				pt = connection.prepareStatement("insert into accounts values(?,?,?);");

				// process query results
				pt.setString(1, username);
				pt.setString(2, password);
				pt.setBoolean(3, false);
				pt.executeUpdate();
			}
		}//end try
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			roomLocker.remove(username);
		}

		return true;
	}

	public int checkLogin(String username, String password)
	{
		Connection connection = null; // manages connection
		PreparedStatement pt = null; // manages prepared statement

		// connect to database usernames and query database
		try {
			roomLocker.put(username,new Object());
			synchronized (roomLocker) {
				// establish connection to database
				Class.forName("com.mysql.jdbc.Driver");

				connection = DriverManager.getConnection(DBFinals.url, DBFinals.user, DBFinals.password);

				// query database
				pt = connection.prepareStatement("select username,password,loggedin from accounts where username=? for update");

				// process query results
				pt.setString(1, username);
				ResultSet rs = pt.executeQuery();

				if (!rs.next()) //username not exists
					return 1;
				if (rs.getBoolean("loggedin") == true)//username already logged in
					return 3;

				if (rs.getString("password").equals(password)) {
					//do something
					rs.close();
					return 0;
				} else
					return 2;
			}
		}//end try
		catch (Exception e) {
			e.printStackTrace();
		} //end catch
		finally
		{
			roomLocker.remove("username");
		}
		return -1;
	}
	public void LogInOutUser(String username,boolean in){
		Connection connection = null; // manages connection
		PreparedStatement pt = null; // manages prepared statement

		// connect to database usernames and query database
		try {
			roomLocker.put(username,new Object());
			synchronized (roomLocker) {
				// establish connection to database
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(DBFinals.url, DBFinals.user, DBFinals.password);

				pt = connection.prepareStatement("update accounts set loggedin=? where username=?");

				pt.setBoolean(1, in);
				pt.setString(2, username);
				pt.executeUpdate();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			roomLocker.remove(username);
		}
	}
}
