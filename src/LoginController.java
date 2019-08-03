import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.event.*;
public class LoginController {

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Label status;


    public void onRegisterClick(ActionEvent actionEvent)
    {
        if(JDBCManager.createUser(username.getText(),password.getText()))
        {
            status.setText("Create successfully");
            status.setVisible(true);

        }
        else
        {
            status.setText("Username is already taken");
            status.setVisible(true);

        }
    }
    public void onLoginMouse(ActionEvent actionEvent) {
        try {
            if(JDBCManager.checkLogin(username.getText(), password.getText())) {
                System.out.println("Success"); // Will switch scene
                status.setText("Success");
                status.setVisible(true);
                Parent lobbyParent = FXMLLoader.load(getClass().getResource("LobbyDesign.fxml"));
                Scene lobbyScene = new Scene(lobbyParent);
                Stage mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                mainStage.setScene(lobbyScene);
                mainStage.show();
            }
            else
            {
                status.setText("Please check details");
                status.setVisible(true);
            }
        }catch (Exception e)
        {

            System.out.println("An error occured when connecting to SQL Database");
        }

    }
}
