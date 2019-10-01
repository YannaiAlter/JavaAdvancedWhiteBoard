import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;

public class Circle extends Shape implements Serializable {
	Point centerPoint;
	private int radiusX,radiusY;
	Circle(Point centerPoint)
	{
		super(Type.CIRCLE);
		this.centerPoint = centerPoint;
		this.radiusX=50;
		this.radiusY=50;
	}
	Circle(Point centerPoint,int radiusX,int radiusY, Color color)
	{
		super(color,Type.CIRCLE);
		this.centerPoint = centerPoint;
		this.radiusX=radiusX;
		this.radiusY=radiusY;
	}
	@Override
		public void draw()
		{
			RoomController roomController = (RoomController)State.mainController;
			roomController.drawCircle(centerPoint,radiusX,radiusY,Shape.awtToFx(super.color));
		}
}
