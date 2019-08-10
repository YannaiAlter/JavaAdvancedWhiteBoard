import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.util.*;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

public class LobbyController {
    @FXML
    Button createRoomButton;
    @FXML
    ListView roomList;
    @FXML
    Button refresh;

    public void refreshList()
    {
        Platform.runLater(new Lobby());
    }
    public void addToRoomList(String roomName)
    {
        roomList.getItems().add(roomName);
        try{RoomManager.addRoom(roomName);}
        catch (Exception e){};
    }
    public void resetRoomList()
    {
        roomList.getItems().clear();
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
