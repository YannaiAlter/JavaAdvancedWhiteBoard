import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.event.*;
public class LoginController {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Label status;


    public void onRegisterClick(ActionEvent actionEvent)
    {
        try {
            if (username.getText().isBlank() || password.getText().isBlank()) {
                status.setText("You must fill both fields");
                status.setVisible(true);
                return;
            }
            if (JDBCManager.createUser(username.getText(), password.getText())) {
                status.setText("Account created successfully");
                status.setVisible(true);

            }
            else {
                status.setText("Username is already taken");
                status.setVisible(true);

            }
        }
        catch (Exception e) {
            System.out.println("An error occurred when connecting to SQL Database");
        }
    }
    public void onLoginMouse(ActionEvent actionEvent) {
        try {
            if(username.getText().isBlank() || password.getText().isBlank()){
                status.setText("You must fill both fields");
                status.setVisible(true);
                return;
            }
            if(JDBCManager.checkLogin(username.getText(), password.getText())) {
                status.setText("Success");
                status.setVisible(true);
                FXMLLoader loader =new FXMLLoader(getClass().getResource("LobbyDesign.fxml"));
                Parent lobbyParent = loader.load();
                Scene lobbyScene = new Scene(lobbyParent);
                Stage mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                mainStage.setScene(lobbyScene);
                mainStage.show();
                Lobby lobby = new Lobby(loader.getController());
                Thread thread = new Thread(lobby);
                thread.start();
            }
            else {
                status.setText("Unidentified username or invalid password.");
                status.setVisible(true);
            }
        }catch (Exception e) {
            System.out.println("An error occurred when connecting to SQL Database");
        }

    }
}
