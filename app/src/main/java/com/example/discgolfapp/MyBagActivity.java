package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Disc;

public class MyBagActivity extends AppCompatActivity{

    private static final String TAG = "MyBagActivity";
    List<Disc> fetchedDiscs = new ArrayList<>();

    Button addDiscBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bag);

        addDiscBtn = findViewById(R.id.addDiscBtn);

        addDiscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDisc();
            }
        });



        initImageBitmaps();
    }

    public void addDisc() {

        Intent intent = new Intent(this, AddDiscActivity.class);

        startActivity(intent);
        this.finish();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    private void initImageBitmaps() {

        getData();

//        discImgUrls.add("https://www.discraft.com/product/image/small/zflxringergt_max-dk_1.jpg");
//        discImgUrls.add("https://www.discraft.com/product/image/small/zflxpunisher_max-dk_1.jpg");
//        discImgUrls.add("https://www.discraft.com/product/image/small/zflxnukess_max-dk_1.jpg");
//        discImgUrls.add("https://www.discraft.com/product/image/small/zflxnukeos_max-dk_1.jpg");
//        discImgUrls.add("https://www.discraft.com/product/image/small/zflxheat_max-dk_1.jpg");
//        discImgUrls.add("https://www.discraft.com/product/image/small/zflxundertaker_max-dk_1.jpg");

    }

    private void getData() {
        db.collection("discs").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshots) {
                        if (querySnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {

                            Log.d(TAG, "retrieved -----> "+querySnapshots.size());
                            for (DocumentSnapshot documentSnapshot : querySnapshots.getDocuments()) {
                                fetchedDiscs.add(documentSnapshot.toObject(Disc.class));
                            }

                            initRecyclerView();
                        }
                    }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                        }
                    });
    }

    private void initRecyclerView() {
        RecyclerView discListView = findViewById(R.id.discList);
        DiscListAdapter adapter = new DiscListAdapter(this, fetchedDiscs);
        discListView.setAdapter(adapter);
        discListView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
