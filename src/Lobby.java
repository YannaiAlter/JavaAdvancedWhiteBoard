import com.mysql.jdbc.log.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lobby implements Runnable {


    public void run()
    {

            LobbyController lobbyController = (LobbyController)Login.mainController;
            try {;
                ArrayList<String> rooms = RoomManager.getRoomNamesFromRegistry();
                List<String> oldList = lobbyController.roomList.getItems();

                if(!rooms.equals(oldList)) { //Only if there is a new element to add
                    lobbyController.resetRoomList();
                    for (String name : rooms) {
                        lobbyController.addToRoomList(name);
                    }
                }

            }
            catch (Exception e) { };


    }

}
