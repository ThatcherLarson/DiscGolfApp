package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScorecardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        // get the intent that started this activity and extract the string
        Intent intent = getIntent();
        String p1Name = intent.getStringExtra(GamesActivity.NAME1_MESSAGE);
        String p2Name = intent.getStringExtra(GamesActivity.NAME2_MESSAGE);
        String p3Name = intent.getStringExtra(GamesActivity.NAME3_MESSAGE);
        int players = intent.getIntExtra(GamesActivity.NUMPLAY_MESSAGE, 0);
        int holes = intent.getIntExtra(GamesActivity.NUMHOLE_MESSAGE, 0);

        if(players == 0){
            //hole1
            findViewById(R.id.hole1p2).setVisibility(View.GONE);
            findViewById(R.id.hole1p3).setVisibility(View.GONE);
            findViewById(R.id.hole1p4).setVisibility(View.GONE);
            //hole2
            findViewById(R.id.hole2p2).setVisibility(View.GONE);
            findViewById(R.id.hole2p3).setVisibility(View.GONE);
            findViewById(R.id.hole2p4).setVisibility(View.GONE);
            //hole3
            findViewById(R.id.hole3p2).setVisibility(View.GONE);
            findViewById(R.id.hole3p3).setVisibility(View.GONE);
            findViewById(R.id.hole3p4).setVisibility(View.GONE);
            //hole4
            findViewById(R.id.hole4p2).setVisibility(View.GONE);
            findViewById(R.id.hole4p3).setVisibility(View.GONE);
            findViewById(R.id.hole4p4).setVisibility(View.GONE);
            //hole5
            findViewById(R.id.hole5p2).setVisibility(View.GONE);
            findViewById(R.id.hole5p3).setVisibility(View.GONE);
            findViewById(R.id.hole5p4).setVisibility(View.GONE);
            //hole6
            findViewById(R.id.hole6p2).setVisibility(View.GONE);
            findViewById(R.id.hole6p3).setVisibility(View.GONE);
            findViewById(R.id.hole6p4).setVisibility(View.GONE);
            //hole7
            findViewById(R.id.hole7p2).setVisibility(View.GONE);
            findViewById(R.id.hole7p3).setVisibility(View.GONE);
            findViewById(R.id.hole7p4).setVisibility(View.GONE);
            //hole8
            findViewById(R.id.hole8p2).setVisibility(View.GONE);
            findViewById(R.id.hole8p3).setVisibility(View.GONE);
            findViewById(R.id.hole8p4).setVisibility(View.GONE);
            //hole9
            findViewById(R.id.hole9p2).setVisibility(View.GONE);
            findViewById(R.id.hole9p3).setVisibility(View.GONE);
            findViewById(R.id.hole9p4).setVisibility(View.GONE);
            //hole10
            findViewById(R.id.hole10p2).setVisibility(View.GONE);
            findViewById(R.id.hole10p3).setVisibility(View.GONE);
            findViewById(R.id.hole10p4).setVisibility(View.GONE);
            //hole11
            findViewById(R.id.hole11p2).setVisibility(View.GONE);
            findViewById(R.id.hole11p3).setVisibility(View.GONE);
            findViewById(R.id.hole11p4).setVisibility(View.GONE);
            //hole12
            findViewById(R.id.hole12p2).setVisibility(View.GONE);
            findViewById(R.id.hole12p3).setVisibility(View.GONE);
            findViewById(R.id.hole12p4).setVisibility(View.GONE);
            //hole13
            findViewById(R.id.hole13p2).setVisibility(View.GONE);
            findViewById(R.id.hole13p3).setVisibility(View.GONE);
            findViewById(R.id.hole13p4).setVisibility(View.GONE);
            //hole14
            findViewById(R.id.hole14p2).setVisibility(View.GONE);
            findViewById(R.id.hole14p3).setVisibility(View.GONE);
            findViewById(R.id.hole14p4).setVisibility(View.GONE);
            //hole15
            findViewById(R.id.hole15p2).setVisibility(View.GONE);
            findViewById(R.id.hole15p3).setVisibility(View.GONE);
            findViewById(R.id.hole15p4).setVisibility(View.GONE);
            //hole16
            findViewById(R.id.hole16p2).setVisibility(View.GONE);
            findViewById(R.id.hole16p3).setVisibility(View.GONE);
            findViewById(R.id.hole16p4).setVisibility(View.GONE);
            //hole17
            findViewById(R.id.hole17p2).setVisibility(View.GONE);
            findViewById(R.id.hole17p3).setVisibility(View.GONE);
            findViewById(R.id.hole17p4).setVisibility(View.GONE);
            //hole18
            findViewById(R.id.hole18p2).setVisibility(View.GONE);
            findViewById(R.id.hole18p3).setVisibility(View.GONE);
            findViewById(R.id.hole18p4).setVisibility(View.GONE);

        } else if(players == 1){
            //hole1
            findViewById(R.id.hole1p3).setVisibility(View.GONE);
            findViewById(R.id.hole1p4).setVisibility(View.GONE);
            //hole2
            findViewById(R.id.hole2p3).setVisibility(View.GONE);
            findViewById(R.id.hole2p4).setVisibility(View.GONE);
            //hole3
            findViewById(R.id.hole3p3).setVisibility(View.GONE);
            findViewById(R.id.hole3p4).setVisibility(View.GONE);
            //hole4
            findViewById(R.id.hole4p3).setVisibility(View.GONE);
            findViewById(R.id.hole4p4).setVisibility(View.GONE);
            //hole5
            findViewById(R.id.hole5p3).setVisibility(View.GONE);
            findViewById(R.id.hole5p4).setVisibility(View.GONE);
            //hole6
            findViewById(R.id.hole6p3).setVisibility(View.GONE);
            findViewById(R.id.hole6p4).setVisibility(View.GONE);
            //hole7
            findViewById(R.id.hole7p3).setVisibility(View.GONE);
            findViewById(R.id.hole7p4).setVisibility(View.GONE);
            //hole8
            findViewById(R.id.hole8p3).setVisibility(View.GONE);
            findViewById(R.id.hole8p4).setVisibility(View.GONE);
            //hole9
            findViewById(R.id.hole9p3).setVisibility(View.GONE);
            findViewById(R.id.hole9p4).setVisibility(View.GONE);
            //hole10
            findViewById(R.id.hole10p3).setVisibility(View.GONE);
            findViewById(R.id.hole10p4).setVisibility(View.GONE);
            //hole11
            findViewById(R.id.hole11p3).setVisibility(View.GONE);
            findViewById(R.id.hole11p4).setVisibility(View.GONE);
            //hole12
            findViewById(R.id.hole12p3).setVisibility(View.GONE);
            findViewById(R.id.hole12p4).setVisibility(View.GONE);
            //hole13
            findViewById(R.id.hole13p3).setVisibility(View.GONE);
            findViewById(R.id.hole13p4).setVisibility(View.GONE);
            //hole14
            findViewById(R.id.hole14p3).setVisibility(View.GONE);
            findViewById(R.id.hole14p4).setVisibility(View.GONE);
            //hole15
            findViewById(R.id.hole15p3).setVisibility(View.GONE);
            findViewById(R.id.hole15p4).setVisibility(View.GONE);
            //hole16
            findViewById(R.id.hole16p3).setVisibility(View.GONE);
            findViewById(R.id.hole16p4).setVisibility(View.GONE);
            //hole17
            findViewById(R.id.hole17p3).setVisibility(View.GONE);
            findViewById(R.id.hole17p4).setVisibility(View.GONE);
            //hole18
            findViewById(R.id.hole18p3).setVisibility(View.GONE);
            findViewById(R.id.hole18p4).setVisibility(View.GONE);
        } else if(players == 2){
            //hole1
            findViewById(R.id.hole1p4).setVisibility(View.GONE);
            //hole2
            findViewById(R.id.hole2p4).setVisibility(View.GONE);
            //hole3
            findViewById(R.id.hole3p4).setVisibility(View.GONE);
            //hole4
            findViewById(R.id.hole4p4).setVisibility(View.GONE);
            //hole5
            findViewById(R.id.hole5p4).setVisibility(View.GONE);
            //hole6
            findViewById(R.id.hole6p4).setVisibility(View.GONE);
            //hole7
            findViewById(R.id.hole7p4).setVisibility(View.GONE);
            //hole8
            findViewById(R.id.hole8p4).setVisibility(View.GONE);
            //hole9
            findViewById(R.id.hole9p4).setVisibility(View.GONE);
            //hole10
            findViewById(R.id.hole10p4).setVisibility(View.GONE);
            //hole11
            findViewById(R.id.hole11p4).setVisibility(View.GONE);
            //hole12
            findViewById(R.id.hole12p4).setVisibility(View.GONE);
            //hole13
            findViewById(R.id.hole13p4).setVisibility(View.GONE);
            //hole14
            findViewById(R.id.hole14p4).setVisibility(View.GONE);
            //hole15
            findViewById(R.id.hole15p4).setVisibility(View.GONE);
            //hole16
            findViewById(R.id.hole16p4).setVisibility(View.GONE);
            //hole17
            findViewById(R.id.hole17p4).setVisibility(View.GONE);
            //hole18
            findViewById(R.id.hole18p4).setVisibility(View.GONE);

        }
        if(holes == 9){
            findViewById(R.id.row19).setVisibility(View.GONE);
            findViewById(R.id.row20).setVisibility(View.GONE);
            findViewById(R.id.row21).setVisibility(View.GONE);
            findViewById(R.id.row22).setVisibility(View.GONE);
            findViewById(R.id.row23).setVisibility(View.GONE);
            findViewById(R.id.row24).setVisibility(View.GONE);
            findViewById(R.id.row25).setVisibility(View.GONE);
            findViewById(R.id.row26).setVisibility(View.GONE);
            findViewById(R.id.row27).setVisibility(View.GONE);
            findViewById(R.id.row28).setVisibility(View.GONE);
            findViewById(R.id.row29).setVisibility(View.GONE);
            findViewById(R.id.row30).setVisibility(View.GONE);
            findViewById(R.id.row31).setVisibility(View.GONE);
            findViewById(R.id.row32).setVisibility(View.GONE);
            findViewById(R.id.row33).setVisibility(View.GONE);
            findViewById(R.id.row34).setVisibility(View.GONE);
            findViewById(R.id.row35).setVisibility(View.GONE);
            findViewById(R.id.row36).setVisibility(View.GONE);
        }

        // capture the layouts textview and set the string
        TextView p1Nametxt = findViewById(R.id.p1Name);
        p1Nametxt.setText(p1Name);
        TextView p2Nametxt = findViewById(R.id.p2Name);
        p2Nametxt.setText(p2Name);
        TextView p3Nametxt = findViewById(R.id.p3Name);
        p3Nametxt.setText(p3Name);
    }
}







/**
public class ScorecardActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
    }
}
**/