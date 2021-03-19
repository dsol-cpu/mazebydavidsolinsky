package edu.wm.cs.cs301.AMazeByDavidSolinsky;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Maze;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.Constants;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazeInformation;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazePanel;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.StatePlaying;

public class PlayManuallyActivity extends AppCompatActivity {
    private Maze maze = MazeInformation.getMaze();

    private Button shortcut, upBtn, downBtn, leftBtn, rightBtn;
    private String PlayActivityType = "manual";
    private static final String TAG = "PlayManuallyActivity";
    private MazePanel panelView = MazeInformation.getMazePanel();
    private Bitmap bitmap;
    private Canvas canvas;
    private StatePlaying statePlaying = new StatePlaying();

    /*
    currently adds the shortcut button functionality
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "state created");

        setContentView(R.layout.manual_animation_layout);
        upBtn = findViewById(R.id.upBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panelView.update();
            }
        });

        downBtn = findViewById(R.id.downBtn);
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown()
                panelView.update();
            }
        });

        leftBtn = findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panelView.update();
            }
        });

        rightBtn = findViewById(R.id.rightBtn);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panelView.update();
            }
        });

        Log.v(TAG, "content set");
//        panelView = (MazePanel)findViewById(R.id.mazePanel);
//        if(panelView==null)
//            Log.v(TAG, "maze panel doesn't exist ");
//        panelView.commit();
//        canvas = new Canvas(bitmap);

//        panelView.setColor(Color.RED);
//        panelView.addFilledRectangle(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
//        panelView.commit();

        shortcut = findViewById(R.id.manualShortcutBtn);
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shortcutToFinish();
            }
        });


    }


    /*
    when back button pressed, returns to main menu
     */
    @Override
    public void onBackPressed() {
        //robotDriverThread.interrupt();
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        /*
         *Finish this activity
         */
        this.finish();
    }

    /*
    method skips maze and brings user to finish screen
     */
    public void shortcutToFinish() {
        //robotDriverThread.interrupt();
        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra("PlayActivityType", PlayActivityType);
        startActivity(intent);
        /*
         *Finish this activity
         */
        this.finish();
    }
}
