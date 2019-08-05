import java.sql.*;

public class JDBCManager {
    public static void main(String args[]) {
        try {

        }
        catch (Exception e) {}
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

            pt = connection.prepareStatement("select username,password from accounts where username=?");

            // process query results
            pt.setString(1, username);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) //username already exists
                return false;


            // query database
            pt = connection.prepareStatement("insert into accounts values(?,?);");

            // process query results
            pt.setString(1, username);
            pt.setString(2, password);
            pt.executeUpdate();

        }//end try
        catch (Exception e) {
            throw e;
        }

        return true;
    }

    public static boolean checkLogin(String username, String password)
            throws Exception{

        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {

            // establish connection to database
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(DBFinals.url, DBFinals.user, DBFinals.password);

            // query database
            pt = connection.prepareStatement("select username,password from accounts where username=?");

            // process query results
            pt.setString(1, username);
            ResultSet rs = pt.executeQuery();
            String orPass = "";

            if (!rs.next()) //username not exists
                return false;
            else
                orPass = rs.getString("password");

            if (orPass.equals(password)) {
                //do something
                rs.close();
                return true;
            }
        }//end try
        catch (Exception e) {
            throw e;
        } //end catch

        return false;
    }
}