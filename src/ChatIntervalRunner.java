import javafx.animation.Timeline;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatIntervalRunner  implements Runnable {
    Timeline timerUpdateList;
    RoomInterface roomManager;

    public void setTimerUpdateList(Timeline timerUpdateList){
        this.timerUpdateList=timerUpdateList;
    }
    public void setRoomManager(RoomInterface room)
    {
        this.roomManager=room;
    }
    public void run()
    {
        System.out.println("timer");
        try {
            if(! (State.mainController instanceof RoomController))
            {
                timerUpdateList.stop();
                return;
            }

            RoomController roomController=(RoomController) State.mainController;
            String curRoom = roomManager.getClientRoom(State.username); //Getting current room using hashmap in roommanager
            if(!roomManager.isChatUpdated(curRoom,roomController.outputChat.getText()))
                roomController.outputChat.setText(roomManager.getChatOfRoom(curRoom).getChatConversation());
        }
        catch (Exception e) {
            e.printStackTrace();
        };
    }
}