import java.sql.*;

public class JDBCManager {
    public static void main(String args[]) {
        try {
            System.out.println(checkLogin("hello", "admin"));
        }
        catch (Exception e) {}
    }

    public static boolean checkLogin(String username, String password)
            throws SQLException {

        Connection connection = null; // manages connection
        PreparedStatement pt = null; // manages prepared statement

        // connect to database usernames and query database
        try {

            // establish connection to database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "yanayyanay");

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