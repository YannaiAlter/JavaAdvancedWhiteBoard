import java.sql.*;

public class JDBCManager {
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

    public static boolean createUser(String username, String password)
            throws Exception
    {
        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {

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

        }//end try
        catch (Exception e) {
            throw e;
        }

        return true;
    }

    public static int checkLogin(String username, String password)
            throws Exception{
        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {

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
            if(rs.getBoolean("loggedin")==true)//username already logged in
                return 3;

            if ( rs.getString("password").equals(password)) {
                //do something
                rs.close();
                return 0;
            }
            else
                return 2;
        }//end try
        catch (Exception e) {
            throw e;
        } //end catch
    }
    public static void LogInOutUser(String username,boolean in){
        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {
            // establish connection to database
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBFinals.url, DBFinals.user, DBFinals.password);

            pt = connection.prepareStatement("update accounts set loggedin=? where username=?");

            pt.setBoolean(1,in);
            pt.setString(2, username);
            pt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}