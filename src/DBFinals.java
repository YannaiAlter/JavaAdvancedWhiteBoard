import javafx.scene.control.Alert;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class DBFinals {

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

		try
		{
			FileReader reader = new FileReader(filePath);
			//Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONObject configJson = (JSONObject) obj;
			Map sqlConfig = ((Map)configJson.get("mysql"));
			url = "jdbc:mysql://" + sqlConfig.get("host") + "/javaproject?useSSL=false";
			user = (String)sqlConfig.get("user");
			password = (String)sqlConfig.get("password");
			Map rmiConfig = ((Map)configJson.get("rmi"));
			RMIHost = (String)rmiConfig.get("RMIHost");
			RMIPort =  ((Long) rmiConfig.get("RMIPort")).intValue();
			WHITEBOARD_UPDATES_INTERVAL_TIME = ((Long) rmiConfig.get("WHITEBOARD_UPDATES_INTERVAL_TIME")).intValue();
			ROOMLIST_UPDATES_INTERVAL_TIME = ((Long) rmiConfig.get("ROOMLIST_UPDATES_INTERVAL_TIME")).intValue();
			CHAT_UPDATE_INTERVAL_TIME = ((Long) rmiConfig.get("CHAT_UPDATE_INTERVAL_TIME")).intValue();
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
			return false;
		}
	}

}
