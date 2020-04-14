package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import models.Disc;

public class AddDiscActivity extends AppCompatActivity {

    Button submitDisk;
    EditText discName;
    EditText discType;
    EditText discMan;
    EditText discColor;
    EditText discDist;
    EditText discID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disc);

        submitDisk = findViewById(R.id.submit_new_disk);
        discName = findViewById(R.id.new_disc_name);
        discType =  findViewById(R.id.new_disc_type);
        discMan = findViewById(R.id.new_disc_manufacturer);
        discColor = findViewById(R.id.new_disc_color);
        discDist = findViewById(R.id.new_disc_distance);
        discID = findViewById(R.id.new_disc_id);

        submitDisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNewDisk();
            }
        });
    }

    public void submitNewDisk() {

        final Disc newDisc = new Disc(discDist.getText().toString(), discColor.getText().toString(), discMan.getText().toString(),
                discName.getText().toString(), discType.getText().toString(), discID.getText().toString());

        Random rand = new Random(System.currentTimeMillis());
        int a = rand.nextInt();
        String b = "Disc-" + a;

        db.collection("discs").document(b).set(newDisc);

        startActivity(new Intent(this, MyBagActivity.class));

    }
}

