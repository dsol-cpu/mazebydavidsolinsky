package edu.wm.cs.cs301.AMazeByDavidSolinsky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.SingleRandom;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazeInformation;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.gui.MazePanel;

public class AMazeActivity extends AppCompatActivity {
    private int skillLevel = 0, seed;
    private EditText fileTextBox;
    private TextView mazeSize;
    private SeekBar sizeBar;
    private Button revisit, explore;
    private Spinner spinner, spinner2;
    private String mazeGenType="", robotDriverType="", LogTag = "AMazeActivity", buttonType = "";
    private Intent menuIntent;
    private File f;
    private MazePanel panel;
    private SingleRandom randSeed = SingleRandom.getRandom();
    //private MazeFileReader reader;

    /*
     *main function adds references to gui
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_layout);

        fileTextBox = (EditText) findViewById(R.id.fileTextBox);
        mazeSize = (TextView) findViewById(R.id.mazeSize);
        mazeSize.setText(String.valueOf(skillLevel));
        spinner = (Spinner) findViewById(R.id.mazeAlgorithmList);
        spinner2 = (Spinner) findViewById(R.id.robotDriverList);
        explore = (Button) findViewById(R.id.explore);

        setSkillLevel();
    }

    /*
    adds button functionality for explore and revisit
     */
    public void buttonListeners() {
        /*
         *explore button takes in spinners' Maze Algorithm and Robot Algorithm choices
         */

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonType = "explore";
                seed = randSeed.nextInt()*(Integer.MAX_VALUE);

                mazeGenType = spinner.getSelectedItem().toString();
                robotDriverType = spinner2.getSelectedItem().toString();
                Log.v(LogTag, "Maze Algorithm: " + mazeGenType);
                Log.v(LogTag, "Robot Driver Algorithm: " + robotDriverType);

                //initial seed creation
                if(MazeInformation.getSeed()==-1) {
                    seed = randSeed.nextInt()*(Integer.MAX_VALUE);
                    MazeInformation.setSeed(seed);
                    Log.v(LogTag, " random seed generated");
                }

                switchFromTitleToGenerating();
            }
        });

        /*
         *revisit button is supposed to find a maze and load from it
         */
        revisit = (Button) findViewById(R.id.revisit);
        revisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonType = "revisit";
                Log.v(LogTag, "Revisit button clicked");
                //load a maze from file
                Bundle menuSelections = getIntent().getExtras();

                if(menuSelections!=null) {
                    seed = MazeInformation.getSeed();
                    mazeGenType = MazeInformation.getMazeGen();
                    robotDriverType = MazeInformation.getDriver();
                    switchFromTitleToGenerating();
                }
//                if (isFilePathValid()) {
//
//                }
            }
        });
    }

    public void setSkillLevel() {
        buttonListeners();
        sizeBar = (SeekBar) findViewById(R.id.seekBar);
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                skillLevel = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.v(LogTag, "Started touching bar");
            }

            //tell user what maze size is
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mazeSize.setText(String.valueOf(skillLevel));
                Log.v(LogTag, "SkillLevel set to" + skillLevel);

            }
        });
    }

    public boolean isFilePathValid() {
        try {
            Paths.get(String.valueOf(fileTextBox.getText()));
        } catch (InvalidPathException e) {
            return false;
        }
        return true;
    }

    /*
     * placeholder for loading from a file
     */
    public void loadMap() {
        //reader.load(String.valueOf(fileTextBox.getText()));
    }


    public void switchFromTitleToGenerating() {
        menuIntent = new Intent(this, GeneratingActivity.class);
        menuIntent.putExtra("robotDriverType", robotDriverType);
        menuIntent.putExtra("mazeGenType", mazeGenType);
        menuIntent.putExtra("skillLevel", skillLevel);
        menuIntent.putExtra("seed", seed);
        menuIntent.putExtra("buttonType", buttonType);
        startActivity(menuIntent);
    }
}