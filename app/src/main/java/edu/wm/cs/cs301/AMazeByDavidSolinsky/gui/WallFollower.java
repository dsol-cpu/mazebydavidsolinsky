//@Author DAVID SOLINSKY

package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Distance;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.Robot.*;
//import generation.Distance;
//import gui.Robot.Direction;
//import gui.Robot.Turn;

public class WallFollower implements RobotDriver {

	private Robot robot;
	private Distance distance;
	private int height, width;

	public WallFollower() {
		super();
	}

	// run drive2exit
	public void run() {
		try {
			this.drive2Exit();
		} catch (Exception e) {
			// StateLosing lost = new StateLosing();

		}
	}

	/**
	 * Assigns a robot platform to the driver. The driver uses a robot to perform,
	 * this method provides it with this necessary information.
	 * 
	 * @param r robot to operate
	 */
	@Override
	public void setRobot(Robot r) {
		this.robot = (BasicRobot) r;
	}

	/**
	 * Provides the robot driver with information on the dimensions of the 2D maze
	 * measured in the number of cells in each direction.
	 * 
	 * @param width  of the maze
	 * @param height of the maze
	 * @precondition 0 <= width, 0 <= height of the maze.
	 */
	@Override
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Drives the robot towards the exit given it exists and given the robot's
	 * energy supply lasts long enough.
	 * 
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		System.out.println("Original robot cardinal direction: " + this.robot.getCurrentDirection());
		while (true) {
			// if the robot is at the exit, success!
			if (robot.isAtExit()) {
				return true;
			}
			// if the robot is out of battery, it fails
			if (robot.getBatteryLevel() <= 0) {
				throw new RuntimeException();
			}

			// if the robot sees no left wall, rotate counterclockwise 90 degrees and step
			// forward
			if (robot.distanceToObstacle(Direction.LEFT) != 0) {
				System.out.println("Check left before: " + this.robot.getCurrentDirection());
				robot.rotate(Turn.LEFT);
				System.out.println("Check left after: " + this.robot.getCurrentDirection());
				robot.move(1, false);
				continue;
			}
			// if the robot sees no wall in front, step forward

			if (robot.distanceToObstacle(Direction.FORWARD) != 0) {
				robot.move(1, false);
				continue;
			}
			// else, rotate right
			System.out.println("Check right before: " + this.robot.getCurrentDirection());
			robot.rotate(Turn.RIGHT);
			System.out.println("Check right after: " + this.robot.getCurrentDirection());

			continue;
		}
	}

	/**
	 * Returns the total energy consumption of the journey, i.e., the difference
	 * between the robot's initial energy level at the starting position and its
	 * energy level at the exit position. This is used as a measure of efficiency
	 * for a robot driver.
	 */
	@Override
	public float getEnergyConsumption() {
		return 2000 - robot.getBatteryLevel();
	}

	/**
	 * Returns the total length of the journey in number of cells traversed. Being
	 * at the initial position counts as 0. This is used as a measure of efficiency
	 * for a robot driver.
	 */
	@Override
	public int getPathLength() {
		return robot.getOdometerReading();
	}

	@Override
	public void setDistance(Distance distance) {
		this.distance = distance;

	}
}
