package edu.wm.cs.cs301.AMazeByDavidSolinsky;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Flushable;
import java.util.concurrent.atomic.AtomicInteger;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Floorplan;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Maze;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.MazeBuilder;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.MazeFactory;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Order;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.SingleRandom;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.Constants;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazeInformation;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazePanel;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.OrderStub;


public class GeneratingActivity extends AppCompatActivity implements Order {
    final AtomicInteger percentDone = new AtomicInteger(0);
    int progress = 0, seed, difficulty;
    int generateTitleRefresh;
    boolean mazeGenExists = false, robotDriverExists = false, perfect = false;
    String mazeGenType = "", robotDriverType = "", skillLevel = "", LogTag = "GeneratingActivity";

    ProgressBar progressBar;
    TextView progressText, generatingTitle;
    Thread progressThread, mazeGenThread;
    Handler handler = new Handler();
    Maze maze;
    MazePanel panel;
    MazeBuilder mazeBuilder = new MazeBuilder(true);


    MazeFactory factory = new MazeFactory();
    Builder builder = Order.Builder.DFS;

    /*
    reads in maze qualifiers and stores them as user selections for use in determining PlayAnimationActivity or PlayManuallyActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generating_layout);
        panel = new MazePanel(this);

        generatingTitle = (TextView) findViewById(R.id.generatingTitle);
        progressBar = (ProgressBar) findViewById(R.id.generatingBar);
        progressBar.setMax(100);
        progressText = (TextView) findViewById(R.id.progressText);


        Bundle menuSelections = getIntent().getExtras();


        assert menuSelections != null;
        mazeGenType = menuSelections.getString("mazeGenType");
        robotDriverType = menuSelections.getString("robotDriverType");

        if (mazeGenType.equals("Prim") || mazeGenType.equals("Kruskal")) {
            Log.v(LogTag, "Maze generating exists!");
            mazeGenExists = true;

            if (mazeGenType.equals("Prim"))
                builder = Order.Builder.Prim;
            if (mazeGenType.equals("Kruskal"))
                builder = Order.Builder.Kruskal;

            skillLevel = menuSelections.getString("skillLevel");
            assert skillLevel != null;
            difficulty = Integer.parseInt(skillLevel);
        }

        assert robotDriverType != null;
        if (robotDriverType.equals("Wallfollower") || robotDriverType.equals("Wizard")) {
            Log.v(LogTag, "Robot driver exists!");
            robotDriverExists = true;
        } else {
            Log.v(LogTag, "Robot driver doesn't exist...");
        }


        progressThread = incrementProgressThread();
        progressThread.start();
//        factory.order(GeneratingActivity.this);

    }

    /*
     *increments progress bar based on atomicinteger in build thread
     */
    private Thread incrementProgressThread() {
        return new Thread(new Runnable() {
            public void run() {
                progress = percentDone.intValue();
//                    progress++;
                generateTitleRefresh++;

                //use a handler for a long running process
                handler.postDelayed(new Runnable() {
                    public void run() {

                        // Start a new Maze generation thread.
                        mazeGenThread = mazeGenerationThread();
                        mazeGenThread.start();
                        // Wait for the thread to finish.
                        try {
                            mazeGenThread.join();
                        } catch (Exception ex) {
                            Log.e(LogTag, "Maze generation thread interrupted");
                        }
                    }
                }, 1000);

            }

        });
    }

    private Thread mazeGenerationThread() {
        return new Thread(new Runnable() {
            public void run() {
                // Reset the progress bar.
                progressBar.setProgress(0);
                factory.order(GeneratingActivity.this);
                factory.waitTillDelivered();
                MazeInformation.setMaze(maze);
                MazeInformation.setMazePanel(panel);
                //generate new maze with current activity's order specifications

                // Keep looping while the progress bar isn't full.
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                        progressText.setText(progress + "%");
                    }
                });
                if (maze != null)
                    Log.v(LogTag, "Maze exists!");
                // Log if maze generation worked and thread exits
                Log.v(LogTag, "Maze generation finished");
                        switchFromGeneratingToPlaying();

            }
        });
    }


    /*
     *check if using a maze generator or a file and then either go to manual or robot driver playing style
     */
    public void switchFromGeneratingToPlaying() {
        if (robotDriverExists) {
            Log.v(LogTag, "Driver present: " + String.valueOf(robotDriverExists));
            Log.v(LogTag, "Switching to PlayAnimationActivity.java");
            Intent intent = new Intent(this, PlayAnimationActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Log.v(LogTag, "Driver present: " + String.valueOf(robotDriverExists));
            Log.v(LogTag, "Switching to PlayManuallyActivity.java");
            Intent intent = new Intent(this, PlayManuallyActivity.class);
            Log.v(LogTag, "Before Start");
            startActivity(intent);
            Log.v(LogTag, "After Start");
            Log.v(LogTag, "Before Finish");
            this.finish();

            Log.v(LogTag, "After Finish");
        }
    }


    /*
     *go back to the home menu
     */
    @Override
    public void onBackPressed() {
        factory.cancel();
        mazeGenThread.interrupt();
        progressThread.interrupt();
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        /*
         *Finish this activity
         */
        this.finish();
    }

    @Override
    public int getSkillLevel() {
        return difficulty;
    }

    @Override
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean isPerfect() {
        return perfect;
    }

    public Maze getMaze() {
        return maze;
    }


    @Override
    public void deliver(Maze mazeConfig) {
        Log.v(LogTag, "Deliver called!");
        System.out.println(mazeConfig);
        maze = mazeConfig;
    }

    @Override
    public void updateProgress(int percentage) {
        if (this.percentDone.get() < percentage && percentage <= 100) {
            this.percentDone.set(percentage);
            progress = percentDone.intValue();
            //EXTRA: change text in generating to show active progress
            switch (generateTitleRefresh) {
                case 3:
                    generatingTitle.setText("Generating.");
                    break;
                case 6:
                    generatingTitle.setText("Generating..");
                    break;
                case 9:
                    generatingTitle.setText("Generating...");
                    generateTitleRefresh = 0;
                    break;
            }
            Log.v(LogTag, "updating called at percentage: " + percentage);
            draw();
        } else {
            Log.v(LogTag, "updateProgress failed");
        }
    }

    private void draw() {
        // draw the content on the panel
        panel.update();

        // update the screen with the buffer graphics
        Log.v(LogTag, "panel updated!");
    }

}

