package edu.wm.cs.cs301.AMazeByDavidSolinsky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class    FinishActivity extends AppCompatActivity {
    private Button homeBtn;

    /*
     *uses intent called from last play activity and fishes for which one, manual or robotanimation, were called
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle menuSelections = getIntent().getExtras();
        assert menuSelections != null;
        String finishType = menuSelections.getString("PlayActivityType");
        if (finishType.equals("robot")) {
            setContentView(R.layout.robot_finish_layout);
            homeBtn = findViewById(R.id.robotHomeBtn);
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goHome();
                }
            });
        }
        if (finishType.equals("manual")) {
            setContentView(R.layout.manual_finish_layout);
            homeBtn = findViewById(R.id.manualHomeBtn);
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goHome();
                }
            });
        }

        /*
         *place holder for when energy is gone before reaching the end
         */
        //if(energy<=0)
        //gameThread.interrupt
        //switchToLoseScreen();

    }
    /*
     *home button takes you to AMazeActivity.java, the main menu
     */

    public void goHome() {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        this.finish();
    }

//    public void switchToLoseScreen(){
//
//    }
}
