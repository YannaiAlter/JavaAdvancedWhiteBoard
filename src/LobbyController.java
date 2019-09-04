import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.*;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LobbyController {
    @FXML
    Button createRoomButton;
    @FXML
    ListView roomList;
    @FXML
    Button refresh;
    @FXML
    Button disconnect;

    String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void joinRoom(javafx.event.ActionEvent actionEvent)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDesign.fxml"));
        try {
            Parent lobbyParent = loader.load();
            Scene lobbyScene = new Scene(lobbyParent);
            Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainStage.setScene(lobbyScene);
            mainStage.show();
            Login.mainController = loader.getController();
            ChatIntervalRunner chat = new ChatIntervalRunner();
            Timeline oneSecondTimerUpdateList = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.runLater(chat);
                }
            }));
            oneSecondTimerUpdateList.setCycleCount(Timeline.INDEFINITE);
            oneSecondTimerUpdateList.play();

            chat.setTimerUpdateList(oneSecondTimerUpdateList);
        }
        catch(Exception e) { System.out.println("Cant load RoomDesign: " +e);}


    }
    public void refreshList()
    {
        Platform.runLater(new Lobby());
    }
    public void addToRoomList(String roomName)
    {
        roomList.getItems().add(roomName);
    }
    public void resetRoomList()
    {
        roomList.getItems().clear();
    }
    public boolean createRoom()
    {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Room's Name");
        //dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter the wanted room's name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Room's name: " + result.get());
            addToRoomList(result.get()); // adding to gui
           try {
               RoomManager.getRoomManager().addRoom(result.get()); // adding to rmi server
           }
           catch (Exception e)
           {
               System.out.println("Can't add room - rmi error");
           }
        }

        //RoomManager.addRoom();
        return true;
    }

    public void disconnect(javafx.event.ActionEvent actionEvent) {
        try {
            logOut();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
            Parent loginParent = loader.load();
            Scene loginScene = new Scene(loginParent);
            Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainStage.setScene(loginScene);
            mainStage.show();
            Login.mainController=loader.getController();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void logOut(){
        JDBCManager.LogInOutUser(username,false);
    }
}
