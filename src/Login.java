import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Login extends Application {
    static Object mainController;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
            Parent root = loader.load();
            mainController = loader.getController();
            Scene scene = new Scene(root, 700, 500);

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        if(mainController instanceof LobbyController)//to expend when there will be rooms
            ((LobbyController) mainController).logOut();
        Platform.exit();
    }
}
