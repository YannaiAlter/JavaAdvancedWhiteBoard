import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ContinuousLine extends Shape implements Serializable {
	ArrayList<Point> points;

	ContinuousLine(Color color)
	{
		super(color,Type.CONTINUOUS_LINE);
		points = new ArrayList<>();
	}
	public void setColor(Color color)
	{
		super.color = fxToAwt(color);
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
			roomController.drawContinuousLine(points,Shape.awtToFx(super.color));
			clear();
		}
}
