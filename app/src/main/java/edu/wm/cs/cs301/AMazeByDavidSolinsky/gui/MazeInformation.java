package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Maze;

public class MazeInformation {
    private static int staticSeed = -1;
    private static String staticDriver = "", staticMazeGen = "";
    private static Maze maze;
    private static MazePanel mazePanel;

    public static void setSeed(int previousSeed) {
        staticSeed = previousSeed;
    }

    public static int getSeed() {
        return staticSeed;
    }

    public static void setDriver(String driver) {
        staticDriver = driver;
    }

    public static String getDriver() {
        return staticDriver;
    }

    public static void setMazeGen(String mazeGen) {
        staticMazeGen = mazeGen;
    }

    public static String getMazeGen() {
        return staticMazeGen;
    }

    public static Maze getMaze(){
        return maze;
    }
    public static void setMaze(Maze inputMaze){
        maze = inputMaze;
    }
    public static MazePanel getMazePanel(){ return mazePanel;}
    public static void setMazePanel(MazePanel inputMazePanel){
        mazePanel = inputMazePanel;
    }
}
