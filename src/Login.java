import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginDesign.fxml"));
            Scene scene = new Scene(root, 700, 500);

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){}

    }
}
