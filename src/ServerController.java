import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;


public class ServerController {
	@FXML
		TextField configPath;
	@FXML
		Label status;
	public void initialize()
	{
		configPath.setText("default");
	}


	public void createServerClicked()
	{
		if(DBFinals.updateConfigurationFromFile(configPath.getText())) {
			status.setTextFill(Color.GREEN);
			status.setText("Server Status: Running");
			try {
				RoomManager room = new RoomManager();
			} catch (Exception e) {
				Alert errorAlert = new Alert(Alert.AlertType.ERROR);
				errorAlert.setHeaderText("RMI Creation Failed");
				errorAlert.setContentText("Please check that RMI port is available and RMI configured well");
				errorAlert.showAndWait();			}
			}
			try
			{
				JDBCManager jdbcManager = new JDBCManager();
			}
			catch (Exception e)
			{
				Alert errorAlert = new Alert(Alert.AlertType.ERROR);
				errorAlert.setHeaderText("SQL Creation Failed");
				errorAlert.setContentText("Please check that SQL Server is available and configured well");
				errorAlert.showAndWait();
			}

	}
	public void browseClick()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File f = fileChooser.showOpenDialog(null);
		configPath.setText(f.getAbsolutePath());
	}

}
