import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
The first stage in the Client application, which is the login window.
The user can login and register in the window. The username and passwords fields are checked in case of login or added to the DB in case of registration
(Using the JDBCManager class).
 */
public class Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader loader  = new FXMLLoader(getClass().getResource("ServerDesign.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 420, 105);

            stage.setTitle("Server");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop()  {
        Platform.exit();
    }
}
