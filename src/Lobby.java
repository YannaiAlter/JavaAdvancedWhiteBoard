import com.mysql.jdbc.log.Log;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lobby implements Runnable {
    Timeline timerUpdateList;
    public void setTimerUpdateList(Timeline timerUpdateList){
        this.timerUpdateList=timerUpdateList;
    }
    public void run()
    {
        System.out.println("timer");
        try {
            if(Login.mainController instanceof LobbyController) {
                LobbyController lobbyController=(LobbyController)Login.mainController;
                ArrayList<String> roomsList = RoomManager.getRoomManager().getRoomsAsString();
                List<String> oldList = lobbyController.roomList.getItems();
                System.out.println("List: " + Arrays.toString(roomsList.toArray()));

                if (!roomsList.equals(oldList)) { //Only if there is a new element to add
                    lobbyController.resetRoomList();
                    for (String name : roomsList) {
                        lobbyController.addToRoomList(name);
                    }
                }
            }
            else
                timerUpdateList.stop();
        }
        catch (Exception e) { };
    }

}
