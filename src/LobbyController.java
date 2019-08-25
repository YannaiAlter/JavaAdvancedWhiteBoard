import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.util.*;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class LobbyController {
    @FXML
    Button createRoomButton;
    @FXML
    ListView roomList;
    @FXML
    Button refresh;

    @FXML
    Button disconnect;

        public void refreshList()
    {
        Platform.runLater(new Lobby());
    }
    public void addToRoomList(String roomName)
    {
        roomList.getItems().add(roomName);
        try{RoomManager.getRoomManager().addRoom(roomName);}
        catch (Exception e){};
    }
    public void resetRoomList()
    {
        roomList.getItems().clear();
    }
    public boolean createRoom()
    {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Room's Name");
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

    public void disconnect(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
            Parent loginParent = loader.load();
            Scene loginScene = new Scene(loginParent);
            Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainStage.setScene(loginScene);
            mainStage.show();
            Login.mainController=loader.getController();
        }
        catch (Exception e){}
    }
}
