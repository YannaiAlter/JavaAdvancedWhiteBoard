import javafx.animation.Timeline;

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
			if(!State.roomManager.isChatUpdated(curRoom,roomController.outputChat.getText())) {
				roomController.outputChat.setText(State.roomManager.getChatOfRoom(curRoom).getChatConversation());
				roomController.outputChat.setScrollTop(Double.MAX_VALUE);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
