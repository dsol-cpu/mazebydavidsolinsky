//@Author: DAVID SOLINSKY

package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.CardinalDirection;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Distance;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.Robot.Direction;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.Robot.Turn;

//import generation.CardinalDirection;
//import generation.Distance;
//import gui.Robot.Direction;
//import gui.Robot.Turn;

public class Wizard implements RobotDriver {
	private static final int INT_MAX = Integer.MAX_VALUE;
	private BasicRobot robot;
	private Distance distance;
	private int width, height;
	
	//run drive2exit
		public void run() {
			try {
				this.drive2Exit();
			} catch (Exception e) {
				//StateLosing lost = new StateLosing();
				
			}
		}
	@Override
	public void setRobot(Robot r) {
		this.robot = (BasicRobot) r;
	}

	@Override
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setDistance(Distance distance) {
		this.distance = distance;		
	}

	// check 4 adjacent cells
	// move to whichever one has the shortest distance to the exit
	// rinse, repeat
	@Override
	public boolean drive2Exit() throws Exception {
		
		while (true) {
			// if the robot is at the exit, success!
			if (robot.isAtExit()) {
				return true;
			}
			// if the robot is out of battery, it fails
			if (robot.getBatteryLevel() <= 0) {
				throw new RuntimeException();
			}

			int posX = this.robot.getCurrentPosition()[0];
			int posY = this.robot.getCurrentPosition()[1];
			//compute adjacent cells' positions, distance away form them, and minimum distance
			int neighborX, neighborY, distance, minDistance = INT_MAX;
			//loop through every direction in order to check tile with minimum distance
			CardinalDirection currentDir, minCardinalDir = null;
			for (CardinalDirection cd : CardinalDirection.values()) {
				if (!this.robot.controller.getMazeConfiguration().getFloorplan().hasWall(posX, posY, cd)) {
					
					int[] v = this.robot.getVectorDirection(cd);
					
					neighborX = posX + v[0];
					neighborY = posY + v[1];
	
					distance = this.robot.controller.getMazeConfiguration().getDistanceToExit(neighborX, neighborY);
					if (distance < minDistance) {
						minDistance = distance;
						minCardinalDir = cd;
					}
				}
			}
			// Move after checking all 4 neighbors
			// forward or a side case, we already check the behind case so it is unnecessary
			if (minCardinalDir != this.robot.getCurrentDirection()) {
				int[] v = this.robot.getVectorDirection(minCardinalDir);
				int[] v2 = this.robot.getVectorDirection(this.robot.getCurrentDirection());
				// rotating ccw, left
				if (v[0] == v2[1] && v[1] == -v2[0]) {
					this.robot.rotate(Turn.RIGHT);
					// rotating cw, right
				} else if (v[0] == -v2[1] && v[1] == v2[0]) {
					this.robot.rotate(Turn.LEFT);
				} else if (v[0] == -v2[0] && v[1] == -v2[1]) {
					this.robot.rotate(Turn.AROUND);
				} else {
					throw new Exception("something went wrong with vector direction comparison!");
				}
			}
			this.robot.move(1, false);
		
		}
	}

	@Override
	public float getEnergyConsumption() {
		return 2000 - this.robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		return this.robot.getOdometerReading();
	}

}
