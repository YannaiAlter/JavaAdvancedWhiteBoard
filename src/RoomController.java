import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

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

    GraphicsContext graphicsContext;



    @FXML
public void initialize() {
            graphicsContext = canvasWhiteBoard.getGraphicsContext2D();
            initDraw(graphicsContext);
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

    void drawLine(Point p1, Point p2)
    {
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
    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

    }

    public void enterClick(ActionEvent actionEvent) {
    appendChat(inputChat.getText());
    System.out.println("appended");
    }
}