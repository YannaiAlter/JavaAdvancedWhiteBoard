import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.event.*;
import javafx.util.*;

import java.io.File;
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

	public void onConfigurationClick()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File f = fileChooser.showOpenDialog(null);
		DBFinals.updateConfigurationFromFile(f.getAbsolutePath());
		State.loadJDBCManager();
		State.loadRoomManager();
	}

	public void onRegisterClick(ActionEvent actionEvent)
	{
		try {
			if (username.getText().isBlank() || password.getText().isBlank()) {
				status.setText("You must fill both fields");
				status.setVisible(true);
				return;
			}
			if (State.jdbcManager.createUser(username.getText(), password.getText())) {
				status.setText("Account created successfully");
				status.setVisible(true);

			}
			else {
				status.setText("Username is already taken");
				status.setVisible(true);

			}
		}
		catch (Exception e) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setHeaderText("SQL Connection Failed");
			errorAlert.setContentText("Please check that SQL Server is available and configured well");
			errorAlert.showAndWait();
		}
	}
	public void onLoginMouse(ActionEvent actionEvent) {
		try {
			if(username.getText().isBlank() || password.getText().isBlank()){
				status.setText("You must fill both fields");
				status.setVisible(true);
				return;
			}
			int state=State.jdbcManager.checkLogin(username.getText(), password.getText());
			//0 fine, 1 username not exists, 2 wrong password, 3 user already logged in

			switch (state) {
				case 0:
					status.setText("Success");
					State.jdbcManager.LogInOutUser(username.getText(),true);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyDesign.fxml"));
					Parent lobbyParent = loader.load();
					Scene lobbyScene = new Scene(lobbyParent);
					Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
					mainStage.setScene(lobbyScene);
					mainStage.show();
					State.mainController = loader.getController();

					State.username=username.getText();
					//    ((LobbyController)State.mainController).setUsername(username.getText());
					Lobby lobby = new Lobby();
					Timeline roomListTimer = new Timeline(new KeyFrame(Duration.millis(DBFinals.ROOMLIST_UPDATES_INTERVAL_TIME), event -> Platform.runLater(lobby)));
					lobby.setTimerUpdateList(roomListTimer);
					roomListTimer.setCycleCount(Timeline.INDEFINITE);
					roomListTimer.play();

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
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setHeaderText("SQL Connection Failed");
			errorAlert.setContentText("Please check that SQL Server is available and configured well");
			errorAlert.showAndWait();

		}
	}
}
