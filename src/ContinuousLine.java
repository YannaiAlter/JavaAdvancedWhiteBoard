import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ContinuousLine extends Shape implements Serializable {
    ArrayList<Point> points;

    ContinuousLine()
    {
        points = new ArrayList<>();
    }
    public void addPoint(Point p ) { points.add(p); }

    public void clear()
    {
        points.clear();
    }
    @Override
    public void draw()
    {
        RoomController roomController = (RoomController)State.mainController;
        roomController.drawContinuousLine(points);
        clear();
    }
}
