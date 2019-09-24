import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.event.*;
import javafx.util.*;
/*
The controller of the Login window, which provides functionality to the login window,
which includes register and login button functionality.
In case of correct login details this class will be used to switch the window to the lobby's window.
*/

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
            int state=JDBCManager.checkLogin(username.getText(), password.getText());
            //0 fine, 1 username not exists, 2 wrong password, 3 user already logged in

            switch (state) {
                case 0:
                    status.setText("Success");
                    JDBCManager.LogInOutUser(username.getText(),true);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyDesign.fxml"));
                    Parent lobbyParent = loader.load();
                    Scene lobbyScene = new Scene(lobbyParent);
                    Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    mainStage.setScene(lobbyScene);
                    mainStage.show();
                    Login.mainController = loader.getController();
                    ((LobbyController)Login.mainController).setUsername(username.getText());
                    Lobby lobby = new Lobby();


                    Timeline oneSecondTimerUpdateList = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            Platform.runLater(lobby);
                        }
                    }));
                    oneSecondTimerUpdateList.setCycleCount(Timeline.INDEFINITE);
                    oneSecondTimerUpdateList.play();

                    lobby.setTimerUpdateList(oneSecondTimerUpdateList);
                    break;
                case 1:
                    status.setText("Unidentified username.");
                    break;
                case 2:
                    status.setText("Wrong password.");
                    break;
                case 3:
                    status.setText("User already logged in.");
                    break;
            }
            status.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred when connecting to SQL Database");
        }
    }
}
