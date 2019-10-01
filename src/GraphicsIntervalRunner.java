import javafx.animation.Timeline;

import java.util.ArrayList;

public class GraphicsIntervalRunner implements Runnable {
	Timeline timerUpdateList;

	public void setTimerUpdateList(Timeline timerUpdateList){
		this.timerUpdateList=timerUpdateList;
	}

	public void run() {
		try {
			if(! (State.mainController instanceof RoomController))
			{
				timerUpdateList.stop();
				return;
			}

			RoomController roomController=(RoomController) State.mainController;
			if(!State.roomManager.isBoardUpdated(State.roomName,State.lastTimeUpdatedGraphics))
			{
				System.out.println("Updating room");
				State.lastTimeUpdatedGraphics = State.roomManager.getWhiteBoardUpdateTimeOfRoom(State.roomName);
				ArrayList<Shape> shapes = State.roomManager.getAllShapesOfRoom(State.roomName);
				roomController.clearWhiteBoard();
				roomController.initDraw();
				Shape.draw(shapes);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
