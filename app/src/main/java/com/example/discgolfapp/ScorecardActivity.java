package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

// public class ScorecardActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scorecard);
//
//        // get the intent that started this activity and extract the string
//        Intent intent = getIntent();
//        String p1Name = intent.getStringExtra(GamesActivity.NAME1_MESSAGE);
//        String p2Name = intent.getStringExtra(GamesActivity.NAME2_MESSAGE);
//        String p3Name = intent.getStringExtra(GamesActivity.NAME3_MESSAGE);
//        int players = intent.getIntExtra(GamesActivity.NUMPLAY_MESSAGE, 0);
//        int holes = intent.getIntExtra(GamesActivity.NUMHOLE_MESSAGE, 0);
//
//        if(players == 0){
//            //hole1
//            findViewById(R.id.hole1p2).setVisibility(View.GONE);
//            findViewById(R.id.hole1p3).setVisibility(View.GONE);
//            findViewById(R.id.hole1p4).setVisibility(View.GONE);
//            //hole2
//            findViewById(R.id.hole2p2).setVisibility(View.GONE);
//            findViewById(R.id.hole2p3).setVisibility(View.GONE);
//            findViewById(R.id.hole2p4).setVisibility(View.GONE);
//            //hole3
//            findViewById(R.id.hole3p2).setVisibility(View.GONE);
//            findViewById(R.id.hole3p3).setVisibility(View.GONE);
//            findViewById(R.id.hole3p4).setVisibility(View.GONE);
//            //hole4
//            findViewById(R.id.hole4p2).setVisibility(View.GONE);
//            findViewById(R.id.hole4p3).setVisibility(View.GONE);
//            findViewById(R.id.hole4p4).setVisibility(View.GONE);
//            //hole5
//            findViewById(R.id.hole5p2).setVisibility(View.GONE);
//            findViewById(R.id.hole5p3).setVisibility(View.GONE);
//            findViewById(R.id.hole5p4).setVisibility(View.GONE);
//            //hole6
//            findViewById(R.id.hole6p2).setVisibility(View.GONE);
//            findViewById(R.id.hole6p3).setVisibility(View.GONE);
//            findViewById(R.id.hole6p4).setVisibility(View.GONE);
//            //hole7
//            findViewById(R.id.hole7p2).setVisibility(View.GONE);
//            findViewById(R.id.hole7p3).setVisibility(View.GONE);
//            findViewById(R.id.hole7p4).setVisibility(View.GONE);
//            //hole8
//            findViewById(R.id.hole8p2).setVisibility(View.GONE);
//            findViewById(R.id.hole8p3).setVisibility(View.GONE);
//            findViewById(R.id.hole8p4).setVisibility(View.GONE);
//            //hole9
//            findViewById(R.id.hole9p2).setVisibility(View.GONE);
//            findViewById(R.id.hole9p3).setVisibility(View.GONE);
//            findViewById(R.id.hole9p4).setVisibility(View.GONE);
//            //hole10
//            findViewById(R.id.hole10p2).setVisibility(View.GONE);
//            findViewById(R.id.hole10p3).setVisibility(View.GONE);
//            findViewById(R.id.hole10p4).setVisibility(View.GONE);
//            //hole11
//            findViewById(R.id.hole11p2).setVisibility(View.GONE);
//            findViewById(R.id.hole11p3).setVisibility(View.GONE);
//            findViewById(R.id.hole11p4).setVisibility(View.GONE);
//            //hole12
//            findViewById(R.id.hole12p2).setVisibility(View.GONE);
//            findViewById(R.id.hole12p3).setVisibility(View.GONE);
//            findViewById(R.id.hole12p4).setVisibility(View.GONE);
//            //hole13
//            findViewById(R.id.hole13p2).setVisibility(View.GONE);
//            findViewById(R.id.hole13p3).setVisibility(View.GONE);
//            findViewById(R.id.hole13p4).setVisibility(View.GONE);
//            //hole14
//            findViewById(R.id.hole14p2).setVisibility(View.GONE);
//            findViewById(R.id.hole14p3).setVisibility(View.GONE);
//            findViewById(R.id.hole14p4).setVisibility(View.GONE);
//            //hole15
//            findViewById(R.id.hole15p2).setVisibility(View.GONE);
//            findViewById(R.id.hole15p3).setVisibility(View.GONE);
//            findViewById(R.id.hole15p4).setVisibility(View.GONE);
//            //hole16
//            findViewById(R.id.hole16p2).setVisibility(View.GONE);
//            findViewById(R.id.hole16p3).setVisibility(View.GONE);
//            findViewById(R.id.hole16p4).setVisibility(View.GONE);
//            //hole17
//            findViewById(R.id.hole17p2).setVisibility(View.GONE);
//            findViewById(R.id.hole17p3).setVisibility(View.GONE);
//            findViewById(R.id.hole17p4).setVisibility(View.GONE);
//            //hole18
//            findViewById(R.id.hole18p2).setVisibility(View.GONE);
//            findViewById(R.id.hole18p3).setVisibility(View.GONE);
//            findViewById(R.id.hole18p4).setVisibility(View.GONE);
//
//        } else if(players == 1){
//            //hole1
//            findViewById(R.id.hole1p3).setVisibility(View.GONE);
//            findViewById(R.id.hole1p4).setVisibility(View.GONE);
//            //hole2
//            findViewById(R.id.hole2p3).setVisibility(View.GONE);
//            findViewById(R.id.hole2p4).setVisibility(View.GONE);
//            //hole3
//            findViewById(R.id.hole3p3).setVisibility(View.GONE);
//            findViewById(R.id.hole3p4).setVisibility(View.GONE);
//            //hole4
//            findViewById(R.id.hole4p3).setVisibility(View.GONE);
//            findViewById(R.id.hole4p4).setVisibility(View.GONE);
//            //hole5
//            findViewById(R.id.hole5p3).setVisibility(View.GONE);
//            findViewById(R.id.hole5p4).setVisibility(View.GONE);
//            //hole6
//            findViewById(R.id.hole6p3).setVisibility(View.GONE);
//            findViewById(R.id.hole6p4).setVisibility(View.GONE);
//            //hole7
//            findViewById(R.id.hole7p3).setVisibility(View.GONE);
//            findViewById(R.id.hole7p4).setVisibility(View.GONE);
//            //hole8
//            findViewById(R.id.hole8p3).setVisibility(View.GONE);
//            findViewById(R.id.hole8p4).setVisibility(View.GONE);
//            //hole9
//            findViewById(R.id.hole9p3).setVisibility(View.GONE);
//            findViewById(R.id.hole9p4).setVisibility(View.GONE);
//            //hole10
//            findViewById(R.id.hole10p3).setVisibility(View.GONE);
//            findViewById(R.id.hole10p4).setVisibility(View.GONE);
//            //hole11
//            findViewById(R.id.hole11p3).setVisibility(View.GONE);
//            findViewById(R.id.hole11p4).setVisibility(View.GONE);
//            //hole12
//            findViewById(R.id.hole12p3).setVisibility(View.GONE);
//            findViewById(R.id.hole12p4).setVisibility(View.GONE);
//            //hole13
//            findViewById(R.id.hole13p3).setVisibility(View.GONE);
//            findViewById(R.id.hole13p4).setVisibility(View.GONE);
//            //hole14
//            findViewById(R.id.hole14p3).setVisibility(View.GONE);
//            findViewById(R.id.hole14p4).setVisibility(View.GONE);
//            //hole15
//            findViewById(R.id.hole15p3).setVisibility(View.GONE);
//            findViewById(R.id.hole15p4).setVisibility(View.GONE);
//            //hole16
//            findViewById(R.id.hole16p3).setVisibility(View.GONE);
//            findViewById(R.id.hole16p4).setVisibility(View.GONE);
//            //hole17
//            findViewById(R.id.hole17p3).setVisibility(View.GONE);
//            findViewById(R.id.hole17p4).setVisibility(View.GONE);
//            //hole18
//            findViewById(R.id.hole18p3).setVisibility(View.GONE);
//            findViewById(R.id.hole18p4).setVisibility(View.GONE);
//        } else if(players == 2){
//            //hole1
//            findViewById(R.id.hole1p4).setVisibility(View.GONE);
//            //hole2
//            findViewById(R.id.hole2p4).setVisibility(View.GONE);
//            //hole3
//            findViewById(R.id.hole3p4).setVisibility(View.GONE);
//            //hole4
//            findViewById(R.id.hole4p4).setVisibility(View.GONE);
//            //hole5
//            findViewById(R.id.hole5p4).setVisibility(View.GONE);
//            //hole6
//            findViewById(R.id.hole6p4).setVisibility(View.GONE);
//            //hole7
//            findViewById(R.id.hole7p4).setVisibility(View.GONE);
//            //hole8
//            findViewById(R.id.hole8p4).setVisibility(View.GONE);
//            //hole9
//            findViewById(R.id.hole9p4).setVisibility(View.GONE);
//            //hole10
//            findViewById(R.id.hole10p4).setVisibility(View.GONE);
//            //hole11
//            findViewById(R.id.hole11p4).setVisibility(View.GONE);
//            //hole12
//            findViewById(R.id.hole12p4).setVisibility(View.GONE);
//            //hole13
//            findViewById(R.id.hole13p4).setVisibility(View.GONE);
//            //hole14
//            findViewById(R.id.hole14p4).setVisibility(View.GONE);
//            //hole15
//            findViewById(R.id.hole15p4).setVisibility(View.GONE);
//            //hole16
//            findViewById(R.id.hole16p4).setVisibility(View.GONE);
//            //hole17
//            findViewById(R.id.hole17p4).setVisibility(View.GONE);
//            //hole18
//            findViewById(R.id.hole18p4).setVisibility(View.GONE);
//
//        }
//        if(holes == 9){
//            findViewById(R.id.row19).setVisibility(View.GONE);
//            findViewById(R.id.row20).setVisibility(View.GONE);
//            findViewById(R.id.row21).setVisibility(View.GONE);
//            findViewById(R.id.row22).setVisibility(View.GONE);
//            findViewById(R.id.row23).setVisibility(View.GONE);
//            findViewById(R.id.row24).setVisibility(View.GONE);
//            findViewById(R.id.row25).setVisibility(View.GONE);
//            findViewById(R.id.row26).setVisibility(View.GONE);
//            findViewById(R.id.row27).setVisibility(View.GONE);
//            findViewById(R.id.row28).setVisibility(View.GONE);
//            findViewById(R.id.row29).setVisibility(View.GONE);
//            findViewById(R.id.row30).setVisibility(View.GONE);
//            findViewById(R.id.row31).setVisibility(View.GONE);
//            findViewById(R.id.row32).setVisibility(View.GONE);
//            findViewById(R.id.row33).setVisibility(View.GONE);
//            findViewById(R.id.row34).setVisibility(View.GONE);
//            findViewById(R.id.row35).setVisibility(View.GONE);
//            findViewById(R.id.row36).setVisibility(View.GONE);
//        }
//
//        // capture the layouts textview and set the string
//        TextView p1Nametxt = findViewById(R.id.p1Name);
//        p1Nametxt.setText(p1Name);
//        TextView p2Nametxt = findViewById(R.id.p2Name);
//        p2Nametxt.setText(p2Name);
//        TextView p3Nametxt = findViewById(R.id.p3Name);
//        p3Nametxt.setText(p3Name);
//    }
// }



public class ScorecardActivity extends AppCompatActivity implements View.OnClickListener{

    private TableLayout table;
    private Button done;
    private float textSize;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecardold);


        table = findViewById(R.id.scoreTable);
        done = findViewById(R.id.btnDone);
        done.setOnClickListener(this);
        Bundle intentData = getIntent().getExtras();
        if (intentData != null) {
            ArrayList<String> players = intentData.getStringArrayList("Names");
            ArrayList<Integer> pars = intentData.getIntegerArrayList("Pars");
            ArrayList<ArrayList<Integer>> scores = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                scores.add(intentData.getIntegerArrayList(players.get(i) + "scores"));
            }

            textSize = getResources().getDimension(R.dimen.scorecardSize);

            initTable(players, scores, pars);
        }


    }

    public void initTable(ArrayList<String> players, ArrayList<ArrayList<Integer>> scores, ArrayList<Integer> pars) {
        if (players == null || scores == null || pars == null) {
            Intent intent = new Intent(this, FindCourseActivity.class);
            startActivity(intent);
            return;
        }
        TableRow headRow = new TableRow(this);
        headRow.setPadding(0, 10, 0, 10);
        headRow.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));

        TextView hole = new TextView(this);
        hole.setTextSize(textSize);
        hole.setTextColor(Color.WHITE);
        hole.setGravity(Gravity.CENTER);
        hole.setTypeface(null, Typeface.BOLD);
        hole.setText(R.string.hole_number);
        headRow.addView(hole);

        TextView par = new TextView(this);
        par.setTextSize(textSize);
        par.setTextColor(Color.WHITE);
        par.setGravity(Gravity.CENTER);
        par.setTypeface(null, Typeface.BOLD);
        par.setText(R.string.par);
        headRow.addView(par);

        table.addView(headRow, 0);
        table.setStretchAllColumns(true);

        for (int i = 0; i < players.size(); i++) {
            TextView name = new TextView(this);
            name.setTextSize(textSize);
            name.setTextColor(Color.WHITE);
            name.setGravity(Gravity.CENTER);
            name.setTypeface(null, Typeface.BOLD);
            name.setText(players.get(i));
            headRow.addView(name);
        }

        for (int i = 0; i < pars.size(); i++) {
            TableRow tr = new TableRow(this);
            tr.setPadding(0, 10, 0, 10);

            if (i % 2 == 0) {
                tr.setBackgroundColor(getResources().getColor(R.color.TealTrans));
            } else {
                tr.setBackgroundColor(getResources().getColor(R.color.TealMoreTrans));
            }

            TextView holeNum = new TextView(this);
            holeNum.setTextSize(textSize);
            holeNum.setGravity(Gravity.CENTER);
            holeNum.setText(String.valueOf(i + 1));
            tr.addView(holeNum);

            TextView holePar = new TextView(this);
            holePar.setGravity(Gravity.CENTER);
            holePar.setTextSize(textSize);
            holePar.setText(String.valueOf(pars.get(i)));
            tr.addView(holePar);

            for (int j = 0; j < players.size(); j++) {
                TextView playerScore = new TextView(this);
                playerScore.setGravity(Gravity.CENTER);
                playerScore.setTextSize(textSize);
                playerScore.setText(String.valueOf(scores.get(j).get(i)));
                tr.addView(playerScore);
            }

            table.addView(tr, i + 1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDone) {
            Intent intent = new Intent(this, FindCourseActivity.class);
            startActivity(intent);
        }
    }
}