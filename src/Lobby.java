import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lobby extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RoomDesign.fxml"));
            Scene scene = new Scene(root, 700, 500);

            stage.setTitle("Lobby");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){}

    }
}
