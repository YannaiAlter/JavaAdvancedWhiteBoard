import javafx.animation.Timeline;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/*
Lobby class which checks for room updates using the RMI RoomManager instance, and updating the client's design in case of an update,
this class is run by a timer from the LoginController every X seconds.
*/
public class Lobby implements Runnable {
    Timeline timerUpdateList;
    RoomInterface roomManager;
    public Lobby()
    {
        this.roomManager=State.roomManager;
    }
    public void setTimerUpdateList(Timeline timerUpdateList){
        this.timerUpdateList=timerUpdateList;
    }
    public void run()
    {
        try {

            if(State.mainController instanceof LobbyController) {
                LobbyController lobbyController=(LobbyController)State.mainController;
                ArrayList<String> roomsList = roomManager.getRoomsAsString();
                List<String> oldList = lobbyController.roomList.getItems();
                System.out.println("List: " + Arrays.toString(roomsList.toArray()));

                if (!roomsList.equals(oldList)) { //Only if there is a new element to add
                    System.out.println("First list: " + roomsList);
                    System.out.println("Second list: " + oldList);

                    lobbyController.resetRoomList();
                    for (String name : roomsList) {
                        lobbyController.addToRoomList(name);
                    }
                }
            }
            else
                timerUpdateList.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        };
    }
}
