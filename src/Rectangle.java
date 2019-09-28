import java.awt.*;
import java.io.Serializable;

public class Rectangle extends Shape implements Serializable {
    Point topLeftPoint;
    private int width,height;
    Rectangle(Point p1)
    {
        super(Type.RECTANGLE);
        topLeftPoint=p1;
        width=50;
        height=50;
    }
    Rectangle(Point p1,int width,int height)
    {
        super(Type.RECTANGLE);
        topLeftPoint=p1;
        this.width=width;
        this.height=height;
    }

    @Override
    public void draw() {
        RoomController roomController = (RoomController)State.mainController;
        roomController.drawRectangle(topLeftPoint,this.width,this.height);
    }
}
