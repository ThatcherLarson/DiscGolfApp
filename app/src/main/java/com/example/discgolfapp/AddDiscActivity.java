package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

import models.Disc;

public class AddDiscActivity extends AppCompatActivity {

    Button submitDisc;
    Button deleteDisc;
    Button cancelDisc;
    EditText discName;
    EditText discType;
    EditText discMan;
    EditText discColor;
    EditText discDist;
    EditText discID;

    private static final String TAG = "AddDiscActivity";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disc);

        submitDisc = findViewById(R.id.submit_new_disk);
        deleteDisc = findViewById(R.id.delete_disk);
        cancelDisc = findViewById(R.id.cancel_disc);
        discName = findViewById(R.id.new_disc_name);
        discType =  findViewById(R.id.new_disc_type);
        discMan = findViewById(R.id.new_disc_manufacturer);
        discColor = findViewById(R.id.new_disc_color);
        discDist = findViewById(R.id.new_disc_distance);
        discID = findViewById(R.id.new_disc_id);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        cancelDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        if (bundle != null) {

            Disc existingDisc = (Disc) bundle.getSerializable("disc");

            // populate the EditText fields
            discName.setText(existingDisc.getName());
            discType.setText(existingDisc.getType());
            discMan.setText(existingDisc.getManufacturer());
            discColor.setText(existingDisc.getColor());
            discDist.setText(existingDisc.getAvg_distance());
            discID.setText(existingDisc.getUid());

            // get correct target name from firebase
            getDocName(existingDisc);
        }

        else {
            Random rand = new Random(System.currentTimeMillis());
            int a = rand.nextInt();
            final String target = "Disc-" + a;

            submitDisc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitNewDisk(target);
                }
            });
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void cancel() {
        startActivity(new Intent(this, MyBagActivity.class));
        this.finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void getDocName(final Disc existingDisc) {

        final String[] documentName = new String[1];
        final String[] target = new String[1];

        db.collection("discs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("uid").equals(existingDisc.getUid())) {

                                    // submit edited disc
                                    submitDisc.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            submitNewDisk(document.getId());
                                        }
                                    });

                                    // delete this disc
                                    deleteDisc.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteThisDisk(document.getId());
                                        }
                                    });

                                }
                            }
                        } else {

                        }
                    }
                });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteThisDisk(final String target) {

        if (target != null) {

            db.collection("discs").document(target)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Disc " + target + " deleted");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting Disc " + target, e);
                        }
                    });
        }

        startActivity(new Intent(this, MyBagActivity.class));
        this.finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void submitNewDisk(final String target) {

        // get new disc details
        final Disc newDisc = new Disc(discDist.getText().toString(), discColor.getText().toString(), discMan.getText().toString(),
                discName.getText().toString(), discType.getText().toString(), discID.getText().toString());

        db.collection("discs").document(target).set(newDisc);

        startActivity(new Intent(this, MyBagActivity.class));
        this.finish();
    }
}

