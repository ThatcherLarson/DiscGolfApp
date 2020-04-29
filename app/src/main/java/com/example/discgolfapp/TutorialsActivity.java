package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import adapters.TutorialAdapter;

public class TutorialsActivity extends AppCompatActivity {
    private RecyclerView tutorialList;
    private RecyclerView.Adapter tutorialAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> tutorialThumbnails = new ArrayList<>();
    private ArrayList<String> tutorialText = new ArrayList<>();
    private ArrayList<String> tutorialURL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

        initImageBitmaps();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    private void initImageBitmaps() {
        tutorialThumbnails.add("https://img.youtube.com/vi/Sgn6Os4YSW0/hqdefault.jpg");
        tutorialText.add("Discraft Disc Golf Clinic: Throwing Basics");
        tutorialURL.add("https://www.youtube.com/watch?v=Sgn6Os4YSW0");

        tutorialThumbnails.add("https://img.youtube.com/vi/q9e_lEs7ASE/hqdefault.jpg");
        tutorialText.add("FORM CRITIQUE AND DISTANCE TIPS");
        tutorialURL.add("https://www.youtube.com/watch?v=q9e_lEs7ASE");

        tutorialThumbnails.add("https://img.youtube.com/vi/SQ6Wutvn4Mg/hqdefault.jpg");
        tutorialText.add("Nate Sexton Disc Golf Clinic - Sidearm");
        tutorialURL.add("https://www.youtube.com/watch?v=SQ6Wutvn4Mg");

        tutorialThumbnails.add("https://img.youtube.com/vi/g8IdEC99thc/hqdefault.jpg");
        tutorialText.add("Physics of Flight 3.02: Putting w/ Robert McCall | Disc Golf Instructional Video");
        tutorialURL.add("https://www.youtube.com/watch?v=g8IdEC99thc");

        tutorialThumbnails.add("https://img.youtube.com/vi/T7Buk1Hn8jM/hqdefault.jpg");
        tutorialText.add("Disc Golf for Beginners with Nate Sexton");
        tutorialURL.add("https://www.youtube.com/watch?v=T7Buk1Hn8jM");

        initRecyclerView();
    }

    private void initRecyclerView() {
        tutorialList = (RecyclerView) findViewById(R.id.tutorialList);

        layoutManager = new LinearLayoutManager(this);
        tutorialList.setLayoutManager(layoutManager);

        tutorialAdapter = new TutorialAdapter(this, tutorialText, tutorialThumbnails, tutorialURL);
        tutorialList.setAdapter(tutorialAdapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
