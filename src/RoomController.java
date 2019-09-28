import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;


/*
The controller of the Room window, which provides functionality to the room window
Includes the toolbox and the chat.
*/
public class RoomController {
    @FXML
    TextArea outputChat;
    @FXML
    TextField inputChat;
    @FXML
    BorderPane WhiteBoard;
    @FXML
    Canvas canvasWhiteBoard;
    @FXML
    Label logout;
    @FXML
    Label undo;

    private GraphicsContext graphicsContext;


    @FXML
public void initialize() {
            graphicsContext = canvasWhiteBoard.getGraphicsContext2D();
            initDraw();
            canvasWhiteBoard.addEventHandler(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    if (State.drawState == Shape.Type.LINE) {
                        State.drawState = Shape.Type.LINE_SECOND_CLICK;
                        State.lastClick = new Point((int) event.getX(), (int) event.getY());

                    } else if (State.drawState == Shape.Type.LINE_SECOND_CLICK) {
                        Point firstClick = State.lastClick;
                        Point secondClick = new Point((int)event.getX(),(int)event.getY());
                        drawLine(firstClick,secondClick);
                        State.drawState = Shape.Type.LINE;
                        try {
                            State.roomManager.addShapeToRoom(State.roomName, new Line(State.lastClick, new Point((int) event.getX(), (int) event.getY())));
                            State.roomManager.updateGraphicsTime(State.roomName);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
}

    public void clearWhiteBoard()
    {
        graphicsContext.clearRect(0, 0, canvasWhiteBoard.getWidth(), canvasWhiteBoard.getHeight());
    }
    void drawLine(Point p1, Point p2)
    {
        initDraw();
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
    }
/* In case of sending a message on chat, appendChat function will be used to update the room conversation on the RMI RoomManager instance,
   This will allow clients on network to see the update and update their own UI.
 */
    void appendChat(String newMessage) {
        try {
           State.roomManager.setRoomConversation(State.roomManager.getClientRoom(State.username), "["+State.username +"]: " +newMessage +"\r\n");
        }
        catch(Exception e)
        {
            System.out.println("Cant send chat: " + e);
        }
    }
    /*Credit:
    http://java-buddy.blogspot.com/2013/04/free-draw-on-javafx-canvas.html
     */
    public void initDraw(){
        double canvasWidth = graphicsContext.getCanvas().getWidth();
        double canvasHeight = graphicsContext.getCanvas().getHeight();

        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(5);

        graphicsContext.fill();
        graphicsContext.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        graphicsContext.setFill(Color.RED);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setLineWidth(1);

    }

    public void logoutClicked(MouseEvent actionEvent)
    {
        try {
            State.roomManager.setRoomConversation(State.roomName,"[Server]: User " + State.username + " has left the chat. \r\n");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyDesign.fxml"));
            Parent lobbyParent = loader.load();
            Scene lobbyScene = new Scene(lobbyParent);
            Stage mainStage = (Stage) ((Label) actionEvent.getSource()).getScene().getWindow();
            mainStage.setScene(lobbyScene);
            mainStage.show();

            State.mainController = loader.getController();

            Lobby lobby = new Lobby();
            Timeline roomListTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Platform.runLater(lobby);
                }
            }));
            lobby.setTimerUpdateList(roomListTimer);
            roomListTimer.setCycleCount(Timeline.INDEFINITE);
            roomListTimer.play();

        }
        catch (Exception e){e.printStackTrace();}
    }
    public void enterClick() {
    appendChat(inputChat.getText());
    System.out.println("appended");
    }

    public void onUndoClicked(MouseEvent event)
    {
        try {
            State.roomManager.undoShapeOfRoom(State.roomName);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}