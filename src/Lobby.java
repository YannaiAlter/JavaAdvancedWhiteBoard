import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lobby implements Runnable {

    LobbyController lobbyController;
    public Lobby(LobbyController lobbyController)
    {
        this.lobbyController=lobbyController;

    }
    public void run()
    {
    }

}
