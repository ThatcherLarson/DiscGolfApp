package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MyBagActivity extends AppCompatActivity {

    private ArrayList<String> discNames = new ArrayList<>();
    private ArrayList<String> discImgUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bag);

        initImageBitmaps();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    private void initImageBitmaps() {
        discImgUrls.add("https://www.discraft.com/product/image/small/zflxringergt_max-dk_1.jpg");
        discNames.add("Ringer GT Max");

        discImgUrls.add("https://www.discraft.com/product/image/small/zflxpunisher_max-dk_1.jpg");
        discNames.add("Punisher Max");

        discImgUrls.add("https://www.discraft.com/product/image/small/zflxnukess_max-dk_1.jpg");
        discNames.add("Nukess Max");

        discImgUrls.add("https://www.discraft.com/product/image/small/zflxnukeos_max-dk_1.jpg");
        discNames.add("Nukeos Max");

        discImgUrls.add("https://www.discraft.com/product/image/small/zflxheat_max-dk_1.jpg");
        discNames.add("Heat Max");

        discImgUrls.add("https://www.discraft.com/product/image/small/zflxundertaker_max-dk_1.jpg");
        discNames.add("Undertaker Max");

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView discListView = findViewById(R.id.discList);
        DiscListAdapter adapter = new DiscListAdapter(this, discNames, discImgUrls);
        discListView.setAdapter(adapter);
        discListView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void addDisc(View view) {

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
