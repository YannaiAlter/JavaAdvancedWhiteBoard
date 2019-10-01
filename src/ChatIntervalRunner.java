import javafx.animation.Timeline;
import javafx.collections.FXCollections;

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
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
