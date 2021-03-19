package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Floorplan;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Maze;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Order;

public class OrderStub implements Order {
	private Maze m;
	private Builder builder;
	private int skillLevel;
	private boolean perfect;
	private int percentdone;

	public OrderStub() {
	}
	
	public OrderStub(Builder builder, int skillLevel, boolean perfect){
		this.builder = builder;
		this.skillLevel = skillLevel;
		this.perfect = perfect;
	}
	/**
	 * Gives the required skill level, range of values 0,1,2,...,15
	 */
	public int getSkillLevel() {
		return skillLevel;
	}

	/**
	 * Gives the requested builder algorithm, possible values are listed in the
	 * Builder enum type.
	 */
	public Builder getBuilder() {
		return builder;
	}

	/**
	 * Describes if the ordered maze should be perfect, i.e. there are no loops and
	 * no isolated areas, which also implies that there are no rooms as rooms can
	 * imply loops
	 */
	public boolean isPerfect() {
		return perfect;
	}

	/**
	 * Delivers the produced maze. This method is called by the factory to provide
	 * the resulting maze as a MazeConfiguration.
	 * 
	 * @param mazeConfig maze
	 * @return 
	 */
	public void deliver(Maze mazeConfig) {
		// WARNING: DO NOT REMOVE, USED FOR GRADING PROJECT ASSIGNMENT
		if (Floorplan.deepdebugWall)
		{   // for debugging: dump the sequence of all deleted walls to a log file
			// This reveals how the maze was generated
			mazeConfig.getFloorplan().saveLogFile(Floorplan.deepedebugWallFileName);
		}
	}

	/**
	 * Provides an update on the progress being made on the maze production. This
	 * method is called occasionally during production, there is no guarantee on
	 * particular values. Percentage will be delivered in monotonously increasing
	 * order, the last call is with a value of 100 after delivery of product.
	 * 
	 * @param percentage percentage of job completion
	 */
	public void updateProgress(int percentage) {
		if (this.percentdone < percentage && percentage <= 100) {
			this.percentdone = percentage;
		}
	}
	public Maze getMaze() {
		return m;
	}
}

