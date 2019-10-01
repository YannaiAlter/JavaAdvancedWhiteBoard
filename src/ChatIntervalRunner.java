import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class ChatIntervalRunner  implements Runnable {
	Timeline timerUpdateList;

	public void setTimerUpdateList(Timeline timerUpdateList){
		this.timerUpdateList=timerUpdateList;
	}

	public void run()
	{
		//   System.out.println("timer");
		try {
			if(! (State.mainController instanceof RoomController))
			{
				timerUpdateList.stop();
				return;
			}

			RoomController roomController=(RoomController) State.mainController;
			String curRoom = State.roomName; //Getting current room using hashmap in roommanager
			if(!State.roomManager.isChatUpdated(curRoom, State.lastTimeUpdatedChat)) {
				roomController.outputChat.setText(State.roomManager.getChatOfRoom(curRoom).getChatConversation());
				roomController.outputChat.setScrollTop(Double.MAX_VALUE);
				State.lastTimeUpdatedChat = State.roomManager.getChatOfRoom(curRoom).getChatUpdateTime();
			}

			if(!State.roomManager.isRoomListUpdated(curRoom,State.lastTimeUpdatedRoomList)) {
				ArrayList<String> allClientsList = State.roomManager.getAllClientsOfRoom(State.roomName);
				roomController.roomList.setItems(FXCollections.observableList(allClientsList));
				State.lastTimeUpdatedRoomList = State.roomManager.getRoomListUpdateTimeOfRoom(curRoom);
			}

		}
		catch (Exception e) { //Chat deleted
			State.graphicsUpdateTimer.stop();
			try
			{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyDesign.fxml"));
			Parent lobbyParent = loader.load();
			Scene lobbyScene = new Scene(lobbyParent);
			Stage mainStage = (Stage) ((RoomController)State.mainController).roomList.getScene().getWindow();
			mainStage.setScene(lobbyScene);
			mainStage.setTitle("Lobby");
			mainStage.show();
			State.mainController = loader.getController();
			}
			catch (Exception ed) {e.printStackTrace();}
			State.chatUpdateTimer.stop();
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setHeaderText("Room has been removed");
			errorAlert.setContentText("Admin closed the room");
			errorAlert.showAndWait();

			Lobby lobby = new Lobby();
			Timeline roomListTimer = new Timeline(new KeyFrame(Duration.millis(DBFinals.ROOMLIST_UPDATES_INTERVAL_TIME), event -> Platform.runLater(lobby)));
			lobby.setTimerUpdateList(roomListTimer);
			roomListTimer.setCycleCount(Timeline.INDEFINITE);
			roomListTimer.play();
		}
	}
}
