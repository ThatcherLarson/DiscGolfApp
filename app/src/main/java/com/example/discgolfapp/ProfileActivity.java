package com.example.discgolfapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import controllers.ProfileController;
import models.ProfileModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ProfileController controller;
    private ProfileModel model;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            //go to login;
        }
        else {

            setContentView(R.layout.activity_profile);
            controller = new ProfileController(auth);
            model = new ProfileModel();
            btnSignOut = findViewById(R.id.signOutBtn);
            btnSignOut.setOnClickListener(this);

            load();
        }


        disableInput((EditText)findViewById(R.id.fullName));
        EditText fullName = (EditText) findViewById(R.id.fullName);

        disableInput((EditText)findViewById(R.id.userEmail));
        EditText userEmail = (EditText) findViewById(R.id.userEmail);

    }

    private void load() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //load saved data
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        final String UID = user.getUid();

        final EditText full_name = (EditText) findViewById(R.id.fullName);
        final EditText email = (EditText) findViewById(R.id.userEmail);

        //final Map<String, TextView> data_map = new HashMap<>();
        //data_map.put("full_name", full_name);

        final DocumentReference userData = db.collection("users").document(UID);

        userData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String firstName = "" ;
                String lastName = "";
                String emailName = "";
                if (documentSnapshot.getData() != null) {
                    for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                        if (entry.getKey().equals("firstName")){
                            firstName = entry.getValue().toString();
                        }
                        if (entry.getKey().equals("lastName")){
                            lastName = entry.getValue().toString();
                        }
                        if (entry.getKey().equals("email")){
                            emailName = entry.getValue().toString();
                        }
                    }
                    full_name.setText(firstName + " " +lastName);
                    email.setText(emailName);
                    }
                }


        });


    }

    public void disableInput(EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signOutBtn) {
            controller.signOutUser();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }
}
