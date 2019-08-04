import java.sql.*;

public class JDBCManager {
    public static void main(String args[]) {
        try {
            System.out.println(createUser("helalo", "admin"));
        }
        catch (Exception e) {}
    }

    public static boolean createUser(String username, String password)
    {
        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {

            // establish connection to database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://213.152.162.94:38363/javaproject", "root", "yanayyanay");

            pt = con.prepareStatement("select username,password from accounts where username=?");

            // process query results
            pt.setString(1, username);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) //username not exists
                return false;


            // query database
            pt = con.prepareStatement("insert into accounts values(?,?);");

            // process query results
            pt.setString(1, username);
            pt.setString(2, password);
            pt.executeUpdate();


        }//end try
        catch (Exception e)
        {
            System.out.println(e);

        }
        // return false;
        return true;
    }

    public static boolean checkLogin(String username, String password)
            throws SQLException {

        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {

            // establish connection to database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://213.152.162.94:38363/javaproject", "root", "yanayyanay");

            // query database
            pt = con.prepareStatement("select username,password from accounts where username=?");

            // process query results
            pt.setString(1, username);
            ResultSet rs = pt.executeQuery();
            String orgUname = "", orPass = "";
            if (!rs.next()) //username not exists
            return false;
            else
            {
                orgUname = rs.getString("username");
                orPass = rs.getString("password");
            } //end while
            if (orPass.equals(password)) {
                //do something
                rs.close();
                return true;

            }
        }//end try
        catch (Exception e) {
        } //end catch
        return false;
    } //end main

}