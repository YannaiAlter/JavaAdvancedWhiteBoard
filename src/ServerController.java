import javafx.fxml.FXML;
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
            RoomManager.getRoomManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
