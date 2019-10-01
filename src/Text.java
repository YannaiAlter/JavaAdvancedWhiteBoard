import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;

public class Text extends Shape implements Serializable {
	Point textStartPoint;
	String text;
	Text(String text, Point clickedPoint, Color color)
	{
		super(color,Type.TEXT);
		this.textStartPoint = clickedPoint;
		this.text = text;
	}

	@Override
		public void draw()
		{
			RoomController roomController = (RoomController)State.mainController;
			roomController.drawText(text,textStartPoint,Shape.awtToFx(super.color));
		}
}
