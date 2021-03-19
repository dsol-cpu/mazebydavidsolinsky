package edu.wm.cs.cs301.AMazeByDavidSolinsky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazePanel;

public class PlayAnimationActivity extends AppCompatActivity {

    private Thread robotDriverThread;
    private Button shortcut;
    private String PlayActivityType = "robot";
    private MazePanel panel;


    /*
    currently adds the shortcut button functionality
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_animation_layout);
        shortcut = findViewById(R.id.robotShortcutBtn);
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
    public void onBackPressed(){
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
    public void shortcutToFinish(){
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
