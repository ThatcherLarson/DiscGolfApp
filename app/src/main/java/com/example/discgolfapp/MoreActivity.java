package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        setTitle("More");

        Button rulesBtn = (Button) findViewById(R.id.rulesBtn);
        Button tutorialsBtn = (Button) findViewById(R.id.tutorialsBtn);
        Button measureThrowBtn = (Button) findViewById(R.id.measureThrowBtn);

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rulesIntent = new Intent(getApplicationContext(), RulesActivity.class);
                startActivity(rulesIntent);
            }
        });

        tutorialsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tutorialsIntent = new Intent(getApplicationContext(), TutorialsActivity.class);
                startActivity(tutorialsIntent);
            }
        });

        measureThrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measureThrowIntent = new Intent(getApplicationContext(), MeasureThrowActivity.class);
                startActivity(measureThrowIntent);
            }
        });

    }
}
