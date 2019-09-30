
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.*;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
The controller of the Lobby window, which provides functionality to the Lobby window,
which includes refresh, join-room, create room button functionality.
In case of clicking the join room button this class will be used to switch the window to the room's window.
*/

public class LobbyController {
    @FXML
    Button createRoomButton;
    @FXML
    ListView roomList;
    @FXML
    Button refresh;
    @FXML
    Button disconnect;


    public void joinRoom(javafx.event.ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDesign.fxml"));
            String clickedRoomName = (String)roomList.getSelectionModel().getSelectedItem();
            Parent lobbyParent = loader.load();
            Scene lobbyScene = new Scene(lobbyParent);
            Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainStage.setScene(lobbyScene);
            mainStage.show();
            State.mainController = loader.getController();
            ChatIntervalRunner chat = new ChatIntervalRunner();

            /* Updating relevant states*/
            State.lastTimeUpdatedGraphics = new GregorianCalendar(2000, Calendar.OCTOBER, 27).getTime();
            State.roomName=clickedRoomName;
            State.roomManager.setClientRoom(State.username,clickedRoomName);//Updating in roomManager that a client has joined the room.
            State.roomManager.setRoomConversation(clickedRoomName,"[Server]: User " + State.username + " has joined the room. \r\n");
            /*Calling Timer Chat interval*/
            Timeline chatUpdateTimer = new Timeline(new KeyFrame(Duration.millis(DBFinals.CHAT_UPDATE_INTERVAL_TIME), event -> Platform.runLater(chat)));
            chatUpdateTimer.setCycleCount(Timeline.INDEFINITE);
            chatUpdateTimer.play();
            chat.setTimerUpdateList(chatUpdateTimer);
            State.chatUpdateTimer = chatUpdateTimer;

            GraphicsIntervalRunner graphics = new GraphicsIntervalRunner();
            Timeline graphicsUpdateTimer = new Timeline(new KeyFrame(Duration.millis(DBFinals.WHITEBOARD_UPDATES_INTERVAL_TIME), event -> Platform.runLater(graphics)));
            graphicsUpdateTimer.setCycleCount(Timeline.INDEFINITE);
            graphicsUpdateTimer.play();
            graphics.setTimerUpdateList(graphicsUpdateTimer);
            State.graphicsUpdateTimer = graphicsUpdateTimer;
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
        dialog.setContentText("Please enter the wanted room's name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Room's name: " + result.get());
           try {
              boolean roomExists = State.roomManager.addRoom(result.get()); // adding to rmi server
               if(!roomExists)
               {
                   Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                   errorAlert.setHeaderText("Room Already Exists");
                   errorAlert.setContentText("You can't choose a room name that is already exists");
                   errorAlert.showAndWait();
               }
               else
               addToRoomList(result.get()); // adding to gui
           }
           catch (Exception e)
           {
                e.printStackTrace();
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
            State.mainController=loader.getController();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void logOut(){
        JDBCManager.LogInOutUser(State.username,false);
    }
}
