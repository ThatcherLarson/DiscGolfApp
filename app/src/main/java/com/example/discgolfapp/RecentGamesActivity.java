package com.example.discgolfapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.RecentGameAdapter;
import models.DiscMap;


public class RecentGamesActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private RecyclerView recentGames;
    private RecentGameAdapter myAdapter;
    private String courseId;

    public ArrayList<DiscMap> discMaps;
    public ArrayList<String> gameNames;
    public ArrayList<String> users = new ArrayList<>();

    String TAG = "RecentGameActivity";

    FirebaseFirestore db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_games);
        db = FirebaseFirestore.getInstance();


        auth = FirebaseAuth.getInstance();

        recentGames = findViewById(R.id.recentGames);

        discMaps = getIntent().getParcelableArrayListExtra("discMaps");

        myAdapter = new RecentGameAdapter();

        loadNames(new RecentGamesActivity.FirestoreCallBack() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                users = list;
                myAdapter.update(users,discMaps);
                myAdapter.notifyDataSetChanged();
            }
        });



        recentGames.setAdapter(myAdapter);
        recentGames.setLayoutManager(new LinearLayoutManager(this));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }
    private void getNames(final RecentGamesActivity.FirestoreCallBack callback) {
        db.collection("users").document(auth.getUid()).collection("games").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    ArrayList<String> documentNames = new ArrayList<String>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for (DocumentSnapshot dfCourse : myListOfDocuments) {
                                String documentId = dfCourse.getId();
                                System.out.println(documentId);
                                documentNames.add(documentId);
                            }
                            callback.onCallBack(documentNames);
                        }
                        else{
                            Log.d(TAG,"Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private interface FirestoreCallBack{
        void onCallBack(ArrayList<String> games);
    }


    private void loadNames(final RecentGamesActivity.FirestoreCallBack firestoreCallBack){
        getNames(firestoreCallBack);
    }



}
