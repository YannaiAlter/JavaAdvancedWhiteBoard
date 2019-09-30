import javafx.scene.control.Alert;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class DBFinals {

    // TODO: 2019-09-20  capital letters
    static String url="jdbc:mysql://localhost/javaproject?useSSL=false";
    static String user="root";
    static String password="yanayyanay";
    static String RMIHost = "localhost";
    static int RMIPort = 1099;
    static double WHITEBOARD_UPDATES_INTERVAL_TIME = 500; //in millis
    static double ROOMLIST_UPDATES_INTERVAL_TIME = 500; //in millis
    static int CHAT_UPDATE_INTERVAL_TIME = 500; //in millis
    public static boolean updateConfigurationFromFile(String filePath)
    {
        if(filePath.equals("default")) return true;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject configJson = (JSONObject) obj;
            Map sqlConfig = ((Map)configJson.get("mysql"));
            user = (String)sqlConfig.get("user");
            return true;
        } catch (FileNotFoundException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Configuration file not found");
            errorAlert.setContentText("Please check configuration file is in the config directory under the name config.json.");
            errorAlert.showAndWait();
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Configuration file bad syntax");
            errorAlert.setContentText("Please check configuration file is in the right syntax");
            errorAlert.showAndWait();
            e.printStackTrace();
            System.exit(0);
            return false;
        }
    }

}
