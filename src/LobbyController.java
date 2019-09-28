
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
            State.roomManager.setRoomConversation(clickedRoomName,"[Server]: User " + State.username + " has joined the chat. \r\n");
            /*Calling Timer Chat interval*/
            Timeline oneSecondTimerUpdateList = new Timeline(new KeyFrame(Duration.seconds(1), event -> Platform.runLater(chat)));
            oneSecondTimerUpdateList.setCycleCount(Timeline.INDEFINITE);
            oneSecondTimerUpdateList.play();

            chat.setTimerUpdateList(oneSecondTimerUpdateList);

            GraphicsIntervalRunner graphics = new GraphicsIntervalRunner();
            Timeline updateGraphicsTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> Platform.runLater(graphics)));
            updateGraphicsTimer.setCycleCount(Timeline.INDEFINITE);
            updateGraphicsTimer.play();
            graphics.setTimerUpdateList(oneSecondTimerUpdateList);
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

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Room's name: " + result.get());
            addToRoomList(result.get()); // adding to gui
           try {
               State.roomManager.addRoom(result.get()); // adding to rmi server

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
