package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.find:
                startActivity(new Intent(this, FindCourseActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.more:
                startActivity(new Intent(this, MoreActivity.class));
                return true;
            case R.id.bag:
                startActivity(new Intent(this, MyBagActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
