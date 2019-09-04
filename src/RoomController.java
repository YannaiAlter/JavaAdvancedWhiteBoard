import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RoomController {
    @FXML
    TextField outputChat;
    @FXML
    TextField inputChat;

    void appendChat(String newMessage) {
        TextField t1 = new TextField(newMessage);
        outputChat.setText(outputChat.getText()+t1.getText());
        try {
           RoomManager.getRoomManager().setTextOfRoom("hello", outputChat.getText());
        }
        catch(Exception e)
        {
            System.out.println("Cant send chat: " + e);
        }
    }


    public void enterClick(ActionEvent actionEvent) {
    appendChat(inputChat.getText() +"\n");
    }
}