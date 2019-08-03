import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


public class LoginController {

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Label status;

    public void onLoginMouse(javafx.event.ActionEvent actionEvent) {
        try {
            if(JDBCManager.checkLogin(username.getText(), password.getText())) {
                System.out.println("Success"); // Will switch scene
                status.setText("Success");
                status.setVisible(true);
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
