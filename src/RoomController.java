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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.Optional;


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
    @FXML
    Label circleLabel;
    @FXML
    Label rectangleLabel;
    @FXML
    Label lineLabel;
    @FXML
    Label textLabel;
    @FXML
    Label redoLabel;

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
                    else if(State.drawState == Shape.Type.RECTANGLE)
                    {
                        Point clickedPoint = new Point((int) event.getX(), (int) event.getY());
                        drawRectangle(clickedPoint,50,50);
                        try {
                            State.roomManager.addShapeToRoom(State.roomName, new Rectangle(clickedPoint));
                            State.roomManager.updateGraphicsTime(State.roomName);
                        } catch (Exception e) { e.printStackTrace(); }

                    }
                    else if(State.drawState == Shape.Type.CIRCLE)
                    {
                        Point clickedPoint = new Point((int) event.getX(), (int) event.getY());
                        try{
                            drawCircle(clickedPoint,50,50);
                            State.roomManager.addShapeToRoom(State.roomName,new Circle(clickedPoint,50,50));
                            State.roomManager.updateGraphicsTime(State.roomName);
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                    else if(State.drawState == Shape.Type.TEXT)
                    {
                        Point clickedPoint = new Point((int) event.getX(), (int) event.getY());
                        TextInputDialog dialog = new TextInputDialog("walter");
                        dialog.setTitle("Room's Name");
                        dialog.setContentText("Please enter text:");

                        Optional<String> result = dialog.showAndWait();
                        if (!result.isPresent()) return;

                        drawText(result.get(),clickedPoint);
                        try {
                            State.roomManager.addShapeToRoom(State.roomName, new Text(result.get(), clickedPoint));
                            State.roomManager.updateGraphicsTime(State.roomName);
                        } catch (Exception e) { e.printStackTrace(); }



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
    void drawRectangle(Point p1,int width,int height)
    {
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(p1.getX(),p1.getY(),width,height);
    }
    void drawCircle(Point p1,int radiusX,int radiusY)
    {
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeOval(p1.getX(),p1.getY(),radiusX,radiusY);
    }
    void drawText(String text, Point p1)
    {
        graphicsContext.setFont(new Font("Arial", 20));
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(text,p1.getX(),p1.getY());
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
/*
        graphicsContext.setFill(Color.RED);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setLineWidth(1);
*/
    }

    public void onCircleClick()
    {
        String blueBorder = circleLabel.getStyle() + "-fx-border-color: blue;";
        State.drawState = Shape.Type.CIRCLE;
        if(State.currentToolBoxItemClicked != null) State.currentToolBoxItemClicked.setStyle(null);
        State.currentToolBoxItemClicked = this.circleLabel;
        circleLabel.setStyle(blueBorder);
    }
    public void onTextClick()
    {
        String blueBorder = textLabel.getStyle() + "-fx-border-color: blue;";
        State.drawState = Shape.Type.TEXT;
        if(State.currentToolBoxItemClicked != null) State.currentToolBoxItemClicked.setStyle(null);
        State.currentToolBoxItemClicked = this.textLabel;
        textLabel.setStyle(blueBorder);
    }
    public void onRectangleClick()
    {
        String blueBorder = rectangleLabel.getStyle() + "-fx-border-color: blue;";
        State.drawState = Shape.Type.RECTANGLE;
        if(State.currentToolBoxItemClicked != null) State.currentToolBoxItemClicked.setStyle(null);
        State.currentToolBoxItemClicked = this.rectangleLabel;
        rectangleLabel.setStyle(blueBorder);
    }
    public void onLineClick()
    {
        String blueBorder = this.lineLabel.getStyle() + "-fx-border-color: blue;";
        State.drawState = Shape.Type.LINE;
        if(State.currentToolBoxItemClicked != null) State.currentToolBoxItemClicked.setStyle(null);
        State.currentToolBoxItemClicked = this.lineLabel;
        lineLabel.setStyle(blueBorder);
    }
    public void logoutClicked(MouseEvent actionEvent)
    {
        try {
            State.roomManager.setRoomConversation(State.roomName,"[Server]: User " + State.username + " has left the room. \r\n");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyDesign.fxml"));
            Parent lobbyParent = loader.load();
            Scene lobbyScene = new Scene(lobbyParent);
            Stage mainStage = (Stage) ((Label) actionEvent.getSource()).getScene().getWindow();
            mainStage.setScene(lobbyScene);
            mainStage.show();

            State.mainController = loader.getController();

            Lobby lobby = new Lobby();
            Timeline roomListTimer = new Timeline(new KeyFrame(Duration.millis(DBFinals.ROOMLIST_UPDATES_INTERVAL_TIME), event -> Platform.runLater(lobby)));
            lobby.setTimerUpdateList(roomListTimer);
            roomListTimer.setCycleCount(Timeline.INDEFINITE);
            roomListTimer.play();

        }
        catch (Exception e){e.printStackTrace();}
    }
    public void enterClick() {
    appendChat(inputChat.getText());
    inputChat.setText(null);
    }

    public void onUndoClicked(MouseEvent event)
    {
        try {
            State.roomManager.undoShapeOfRoom(State.roomName);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void onRedoClick(MouseEvent event)
    {
        try {
            State.roomManager.redoShapeOfRoom(State.roomName);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}