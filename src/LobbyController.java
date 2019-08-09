import javafx.fxml.FXML;

import javafx.scene.control.Button;
import java.util.*;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

public class LobbyController {
    @FXML
    Button createRoomButton;
    @FXML
    ListView roomList;

    public void addToRoomList(String roomName)
    {
        roomList.getItems().add(roomName);
    }

    public boolean createRoom()
    {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
            addToRoomList(result.get());
        }

        //RoomManager.addRoom();
        return true;
    }
}
