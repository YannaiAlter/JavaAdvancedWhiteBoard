import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
/*
The controller of the Room window, which provides functionality to the room window
Includes the toolbox and the chat.
*/
public class RoomController {
    @FXML
    TextArea outputChat;
    @FXML
    TextField inputChat;


/* In case of sending a message on chat, appendChat function will be used to update the room conversation on the RMI RoomManager instance,
   This will allow clients on network to see the update and update their own UI.
 */
    void appendChat(String newMessage) {
        try {
           State.roomManager.setRoomConversation(State.roomManager.getClientRoom(State.username), newMessage +"\r\n");
        }
        catch(Exception e)
        {
            System.out.println("Cant send chat: " + e);
        }
    }


    public void enterClick(ActionEvent actionEvent) {
    appendChat(inputChat.getText());
    }
}