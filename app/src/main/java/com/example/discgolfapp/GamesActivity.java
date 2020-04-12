package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discgolfapp.R;

import java.util.ArrayList;
import java.util.List;

public class GamesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    // Arrays for spinner
    String[] player = { "0", "1", "2", "3"};
    String[] holes = {"9","18"};

    // xml objects
    private TextView player1;
    private TextView player2;
    private TextView player3;
    private EditText player1Name;
    private EditText player2Name;
    private EditText player3Name;
    private Button invite1;
    private Button invite2;
    private Button invite3;

    public static final String NAME1_MESSAGE = "com.example.game.P1NAME";
    public static final String NAME2_MESSAGE = "com.example.game.P2NAME";
    public static final String NAME3_MESSAGE = "com.example.game.P3NAME";
    public static final String NUMPLAY_MESSAGE = "com.example.game.NUMPLAY";
    public static final String NUMHOLE_MESSAGE = "com.example.game.NUMHOLE";

    public int players = 0;
    public int hole = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        // setup xml objects
        player1 = findViewById(R.id.Player1);
        player2 = findViewById(R.id.Player2);
        player3 = findViewById(R.id.Player3);
        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        player3Name = findViewById(R.id.player3Name);
        invite1 = findViewById(R.id.inviteP1);
        invite2 = findViewById(R.id.inviteP2);
        invite3 = findViewById(R.id.inviteP3);

        // hide extra players
        player1.setVisibility(View.INVISIBLE);
        player2.setVisibility(View.INVISIBLE);
        player3.setVisibility(View.INVISIBLE);
        player1Name.setVisibility(View.INVISIBLE);
        player2Name.setVisibility(View.INVISIBLE);
        player3Name.setVisibility(View.INVISIBLE);
        invite1.setVisibility(View.INVISIBLE);
        invite2.setVisibility(View.INVISIBLE);
        invite3.setVisibility(View.INVISIBLE);

        // Spinner Number of players
        final Spinner playSpin = findViewById(R.id.numPlay);
        final Spinner holeSpin = findViewById(R.id.holePlay);
        Button button = findViewById(R.id.button);

        // Spinner/click listener
        playSpin.setOnItemSelectedListener(this);
        holeSpin.setOnItemSelectedListener(this);

        // setup arrays
        // Spinner Drop down elements
        List<Integer> numPlayers = new ArrayList<Integer>();
        numPlayers.add(0);
        numPlayers.add(1);
        numPlayers.add(2);
        numPlayers.add(3);

        List<Integer> numHoles = new ArrayList<Integer>();
        numHoles.add(9);
        numHoles.add(18);

        // Creating adapter for spinner
        ArrayAdapter<Integer> playerDataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numPlayers);
        ArrayAdapter<Integer> holeDataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numHoles);
        // Drop down layout style - list view with radio button
        playerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        playSpin.setAdapter(playerDataAdapter);
        holeSpin.setAdapter(holeDataAdapter);

        // start game button handler
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GamesActivity.this, ScorecardActivity.class);
                String p1name = player1Name.getText().toString();
                String p2name = player2Name.getText().toString();
                String p3name = player3Name.getText().toString();

                intent.putExtra(NAME1_MESSAGE, p1name);
                intent.putExtra(NAME2_MESSAGE, p2name);
                intent.putExtra(NAME3_MESSAGE, p3name);
                intent.putExtra(NUMPLAY_MESSAGE, players);
                intent.putExtra(NUMHOLE_MESSAGE, hole);
                //intent.putExtra("data", String.valueOf(playSpin.getSelectedItem()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch(arg0.getId()) {
            case R.id.numPlay:
                Toast.makeText(getApplicationContext(), player[position], Toast.LENGTH_SHORT).show();
                players = (int) arg0.getItemAtPosition(position);
                if (players == 1){
                    player1.setVisibility(View.VISIBLE);
                    player1Name.setVisibility(View.VISIBLE);
                    invite1.setVisibility(View.VISIBLE);

                    player2.setVisibility(View.INVISIBLE);
                    player2Name.setVisibility(View.INVISIBLE);
                    invite2.setVisibility(View.INVISIBLE);

                    player3.setVisibility(View.INVISIBLE);
                    player3Name.setVisibility(View.INVISIBLE);
                    invite3.setVisibility(View.INVISIBLE);

                }
                else if(players == 2){
                    player1.setVisibility(View.VISIBLE);
                    player1Name.setVisibility(View.VISIBLE);
                    invite1.setVisibility(View.VISIBLE);

                    player2.setVisibility(View.VISIBLE);
                    player2Name.setVisibility(View.VISIBLE);
                    invite2.setVisibility(View.VISIBLE);

                    player3.setVisibility(View.INVISIBLE);
                    player3Name.setVisibility(View.INVISIBLE);
                    invite3.setVisibility(View.INVISIBLE);

                }
                else if(players == 3){
                    player1.setVisibility(View.VISIBLE);
                    player1Name.setVisibility(View.VISIBLE);
                    invite1.setVisibility(View.VISIBLE);

                    player2.setVisibility(View.VISIBLE);
                    player2Name.setVisibility(View.VISIBLE);
                    invite2.setVisibility(View.VISIBLE);

                    player3.setVisibility(View.VISIBLE);
                    player3Name.setVisibility(View.VISIBLE);
                    invite3.setVisibility(View.VISIBLE);
                }
                else {
                    player1.setVisibility(View.INVISIBLE);
                    player1Name.setVisibility(View.INVISIBLE);
                    invite1.setVisibility(View.INVISIBLE);

                    player2.setVisibility(View.INVISIBLE);
                    player2Name.setVisibility(View.INVISIBLE);
                    invite2.setVisibility(View.INVISIBLE);

                    player3.setVisibility(View.INVISIBLE);
                    player3Name.setVisibility(View.INVISIBLE);
                    invite3.setVisibility(View.INVISIBLE);
                }

                break;
            case R.id.holePlay:
                Toast.makeText(getApplicationContext(), holes[position], Toast.LENGTH_SHORT).show();
                hole = (int) arg0.getItemAtPosition(position);
                break;
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}







/*
public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
    }


}*/
