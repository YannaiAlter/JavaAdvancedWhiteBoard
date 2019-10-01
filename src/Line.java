import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;

public class Line extends Shape implements Serializable {
	Point from;
	Point to;

	Line(Point from, Point to, Color color)
	{
		super(color,Shape.Type.LINE);
		this.from=from;
		this.to=to;
	}
	@Override
		public void draw()
		{
			RoomController roomController = (RoomController)State.mainController;
			roomController.drawLine(from,to,Shape.awtToFx(super.color));
		}
}
