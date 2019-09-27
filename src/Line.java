import java.awt.*;
import java.io.Serializable;

public class Line extends Shape implements Serializable {
    Point from;
    Point to;
    Line(Point from, Point to)
    {
        super(Shape.Type.LINE);
        this.from=from;
        this.to=to;
    }
}
