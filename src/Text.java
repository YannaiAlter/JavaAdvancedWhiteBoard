import java.awt.*;
import java.io.Serializable;

public class Text extends Shape implements Serializable {
    Point textStartPoint;
    String text;
    Text(String text, Point clickedPoint)
    {
        this.textStartPoint = clickedPoint;
        this.text = text;
    }

    @Override
    public void draw()
    {
        RoomController roomController = (RoomController)State.mainController;
        roomController.drawText(text,textStartPoint);
    }
}
