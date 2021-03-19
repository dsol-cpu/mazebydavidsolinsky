
//@Author: DAVID SOLINSKY
package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.CardinalDirection;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Maze;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.Constants.UserInput;

//import generation.CardinalDirection;
//import generation.Maze;
//import gui.Constants.UserInput;

public class BasicRobot implements Robot {

	public Controller controller;
	private Maze mazeConfiguration;
	private Direction dir;
	private float batteryLevel = 2000;
	private int odometer = 0;
	private boolean RoomSensor, OperationalSensor, isStopped, manual;
	private int width, height;

	/*
	 * This is for if you want to start the robot without any parameters
	 * 
	 */
	public BasicRobot() {
		this.RoomSensor = true;
		this.OperationalSensor = true;
		this.manual = false;
	}

	/*
	 * This is if you want to manually do the robot
	 */
	public BasicRobot(boolean manual) {
		this.RoomSensor = true;
		this.OperationalSensor = true;
		this.manual = manual;
	}

	/*
	 * returns current position
	 */

	@Override
	public int[] getCurrentPosition() throws Exception {
		int[] pos = new int[2];
		pos[0] = controller.getCurrentPosition()[0];
		pos[1] = controller.getCurrentPosition()[1];

		return pos;
	}

	/*
	 * returns controller's cardinal direction
	 */
	@Override
	public CardinalDirection getCurrentDirection() {
		return controller.getCurrentDirection();
	}

	@Override
	public void setMaze(Controller controller) {
		this.controller = controller;
		mazeConfiguration = controller.getMazeConfiguration();
		
		width = this.mazeConfiguration.getWidth();
		height = this.mazeConfiguration.getHeight();
	}

	@Override
	public float getBatteryLevel() {
		return batteryLevel;
	}

	@Override
	public void setBatteryLevel(float level) {
		if (level >= 0)
			batteryLevel = level;
	}

	@Override
	public int getOdometerReading() {
		return odometer;
	}

	@Override
	public void resetOdometer() {
		odometer = 0;
	}

	/*
	 * reads out the amount of energy if we were to do a full rotation (two turns)
	 */
	@Override
	public float getEnergyForFullRotation() {
		float level = batteryLevel - 12;
		return level;
	}

	/*
	 * reads out the amount of energy if we were to step forward
	 */
	@Override
	public float getEnergyForStepForward() {
		float level = batteryLevel - 4;
		return level;
	}

	/*
	 * tests if robot is facing exit and is in the cell adjacent to the exit
	 */
	@Override
	public boolean isAtExit() {
		try {
			if (/* this.dir == Direction.FORWARD && */ mazeConfiguration.getDistanceToExit(this.getCurrentPosition()[0],
					this.getCurrentPosition()[1]) <= 0)
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Tells if a sensor can identify the exit in the given direction relative to
	 * the robot's current forward direction from the current position.
	 * 
	 * @return true if the exit of the maze is visible in a straight line of sight
	 * @throws UnsupportedOperationException if robot has no sensor in this
	 *                                       direction
	 */
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		// detecting something forward of the robot is an exit

		int posX = 0, posY = 0;
		try {
			posX = this.getCurrentPosition()[0];
			posY = (height - 1) - this.getCurrentPosition()[1];
		} catch (Exception e) {
		}
		CardinalDirection cd = alignDirection(this.getCurrentDirection(), direction);

		while (true) {
			if (this.mazeConfiguration.getDistanceToExit(posX, posY) == 0)
				return true;
			if (this.mazeConfiguration.getFloorplan().hasWall(posX, posY, cd))
				return false;
			switch (cd) {
			case North:
				posY--;
				break;
			case South:
				posY++;
				break;
			case West:
				posX--;
				break;
			case East:
				posX++;
				break;
			}
			
		}
	}

	/**
	 * Tells if current position is inside a room.
	 * 
	 * @return true if robot is inside a room, false otherwise
	 * @throws UnsupportedOperationException if not supported by robot
	 */
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		try {
			if (this.mazeConfiguration.getFloorplan().isInRoom(this.getCurrentPosition()[0],
					this.getCurrentPosition()[1]))
				return true;
		} catch (Exception e) {
		}
		return false;

	}

	/**
	 * Tells if the robot has a room sensor.
	 */
	@Override
	public boolean hasRoomSensor() {
		return this.RoomSensor;
	}

	@Override
	public boolean hasStopped() { // have to stop first
		try {
			if (batteryLevel <= 0 || this.mazeConfiguration.getFloorplan().hasWall(this.getCurrentPosition()[0],
					this.getCurrentPosition()[1], this.getCurrentDirection())) {
				isStopped = true;
				// fail and switch to title
			}
		} catch (Exception e) {
		}
		return isStopped;
	}

	/*
	 *  find distance to next wall
	 *  align CardinalDirection to 
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		CardinalDirection cd = this.getCurrentDirection();
		
		
		int distance = 0;
		int posX = 0, posY = 0;
		try {
			posX = this.getCurrentPosition()[0];
//			posY = (height-1) - this.getCurrentPosition()[1];
			posY = this.getCurrentPosition()[1];

			

		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}	
		
		
		System.out.println("DistanceToObstable function, Position: (" + posX  +  ", " + posY + ")");
		System.out.println("Direction: " + cd + "  Heading: " + direction);
		
		cd = alignDirection(cd, direction);
		System.out.println("Aligned Direction: " + cd);

		if (this.canSeeThroughTheExitIntoEternity(Direction.FORWARD))
			return Integer.MAX_VALUE;

		while (posX < width && posY < height && posY >= 0
				&& posX >= 0 && !this.mazeConfiguration.getFloorplan().hasWall(posX, posY, cd)) {

			switch (cd) {
			case North:
				posY--;
				break;
			case South:
				posY++;
				break;
			case West:
				posX--;
				break;
			case East:
				posX++;
				break;
			}
			System.out.println("PosX: (" + posX + ", " + posY + ")");
			distance++;
		}
		
		System.out.println("Distance to obstacle " + distance);
		return distance;
			
	}

	// returns direction facing to check for wall in that direction i.e. check left
	// wall, right wall, etc of robot
	public CardinalDirection alignDirection(CardinalDirection cd, Direction direction) {
		CardinalDirection newDir = cd;
		switch (cd) {
		case North:
			switch (direction) {
			case LEFT:
				newDir = CardinalDirection.East;
				break;
			case RIGHT:
				newDir = CardinalDirection.West;
				break;
			case BACKWARD:
				newDir = CardinalDirection.South;
				break;
			default:
				break;
			}
			break;
		case South:
			switch (direction) {
			case LEFT:
				newDir = CardinalDirection.West;
				break;
			case RIGHT:
				newDir = CardinalDirection.East;
				break;
			case BACKWARD:
				newDir = CardinalDirection.North;
				break;
			default:
				break;
			}
			break;
		case West:
			switch (direction) {
			case LEFT:	// North South switched
				newDir = CardinalDirection.North;
				break;
			case RIGHT:
				newDir = CardinalDirection.South;
				break;
			case BACKWARD:
				newDir = CardinalDirection.East;
				break;
			default:
				break;
			}
			break;
		case East:
			switch (direction) {
			case LEFT:
				newDir = CardinalDirection.South;
				break;
			case RIGHT:
				newDir = CardinalDirection.North;
				break;
			case BACKWARD:
				newDir = CardinalDirection.West;
				break;
			default:
				break;
			}
			break;
		}
		return newDir;
	}

	/*
	 * breaks the CardinalDirections down into their vector pairs and returns their
	 * values
	 */
	public int[] getVectorDirection(CardinalDirection cd) {
		int[] vec = new int[2];
		switch (cd) {
		case North:
			vec[0] = 0;
			vec[1] = -1;
			break;
		case South:
			vec[0] = 0;
			vec[1] = 1;
			break;
		case West:
			vec[0] = -1;
			vec[1] = 0;
			break;
		case East:
			vec[0] = 1;
			vec[1] = 0;
			break;
		}
		return vec;
	}

	/**
	 * Tells if the robot has an operational distance sensor for the given
	 * direction. The interface is generic and may be implemented with robots that
	 * are more or less equipped with sensor or have sensors that are subject to
	 * failures and repairs. The purpose is to allow for a flexible robot driver to
	 * adapt its driving strategy according the features it finds supported by a
	 * robot.
	 * 
	 * @param direction specifies the direction of the sensor
	 * @return true if robot has operational sensor, false otherwise
	 */
	@Override
	public boolean hasOperationalSensor(Direction direction) {
		return OperationalSensor;
	}

	/**
	 * Turn robot on the spot for amount of degrees. If robot runs out of energy, it
	 * stops, which can be checked by hasStopped() == true and by checking the
	 * battery level.
	 * 
	 * @param direction to turn and relative to current forward direction.
	 */
	@Override
	public void rotate(Turn turn) {
		switch (turn) {
		case LEFT:
			controller.keyDown(UserInput.Left, 10);
			this.setBatteryLevel(batteryLevel - 4);
			break;
		case RIGHT:
			controller.keyDown(UserInput.Right, 10);
			this.setBatteryLevel(batteryLevel - 4);
			break;
		case AROUND:
			controller.keyDown(UserInput.Left, 10);
			controller.keyDown(UserInput.Left, 10);
			this.setBatteryLevel(batteryLevel - 8);
			break;
		}
	}

	/**
	 * Moves robot forward a given number of steps. A step matches a single cell. If
	 * the robot runs out of energy somewhere on its way, it stops, which can be
	 * checked by hasStopped() == true and by checking the battery level. If the
	 * robot hits an obstacle like a wall, it depends on the mode of operation what
	 * happens. If an algorithm drives the robot, it remains at the position in
	 * front of the obstacle and also hasStopped() == true as this is not supposed
	 * to happen. This is also helpful to recognize if the robot implementation and
	 * the actual maze do not share a consistent view on where walls are and where
	 * not. If a user manually operates the robot, this behavior is inconvenient for
	 * a user, such that in case of a manual operation the robot remains at the
	 * position in front of the obstacle but hasStopped() == false and the game can
	 * continue.
	 * 
	 * @param distance is the number of cells to move in the robot's current forward
	 *                 direction
	 * @param manual   is true if robot is operated manually by user, false
	 *                 otherwise
	 * @precondition distance >= 0
	 */
	@Override
	public void move(int distance, boolean manual) {
		int posX = 0, posY = 0;
		try {
			posX = this.getCurrentPosition()[0];
			posY = this.getCurrentPosition()[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (batteryLevel != 0)
			if (distance >= 0) {
				if (manual) {
					if (this.mazeConfiguration.getFloorplan().hasWall(posX, posY, controller.getCurrentDirection())) {
						batteryLevel--;
						if (this.isAtExit())
							controller.switchToTitle();
						controller.keyDown(UserInput.Up, 10);
						this.odometer++;

					}
				} else {
					batteryLevel--;
					if (this.isAtExit())
						controller.switchToTitle();
					controller.keyDown(UserInput.Up, 10);
					this.odometer++;
				}
			}
	}

	/**
	 * Makes robot move in a forward direction even if there is a wall in front of
	 * it. In this sense, the robot jumps over the wall if necessary. The distance
	 * is always 1 step and the direction is always forward.
	 * 
	 * @throws Exception is thrown if the chosen wall is an exterior wall and the
	 *                   robot would land outside of the maze that way. The current
	 *                   location remains set at the last position, same for
	 *                   direction but the game is supposed to end with a failure.
	 */
	@Override
	public void jump() throws Exception {
		batteryLevel--;
		if (this.isAtExit())
			controller.switchToTitle();
		try {
		}catch(Exception e) {
			
		}
	}
}
