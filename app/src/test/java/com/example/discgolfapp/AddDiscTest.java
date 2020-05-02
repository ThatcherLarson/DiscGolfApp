package com.example.discgolfapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class AddDiscTest {

    private AddDiscActivity activity;
    private FirebaseAuth auth;
    private Context appContext;

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp fapp = FirebaseApp.initializeApp(appContext);
        auth = FirebaseAuth.getInstance(fapp);
        activity = Robolectric.buildActivity(AddDiscActivity.class).create().get();
    }

    @Test
    public void validIdTest() {

        final EditText newDiscID = activity.findViewById(R.id.new_disc_id);
        newDiscID.setText("3");

        FirebaseApp.initializeApp(appContext);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("discs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                final String currentID = document.getData().get("uid").toString();
                                Log.d("TAG -----------", "current Disc: " + currentID);
                                assertNotEquals("ID clashed with existing discs' ID", currentID, "3");
                            }
                        }
                    }
                });
    }
}
