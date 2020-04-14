package com.example.discgolfapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Disc;

import java.util.List;

public class DiscListAdapter extends RecyclerView.Adapter<DiscListAdapter.ViewHolder> {

    private static final String TAG = "DiscListAdapter";
    List<Disc> fetchedDiscs;
    private Context mContext;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DiscListAdapter(Context mContext, List<Disc> fetchedDiscs) {
        this.fetchedDiscs = fetchedDiscs;
        this.mContext = mContext;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_disc_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    public void saveDiscs() {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//        Glide.with(mContext)
//                .asBitmap()
//                .load(discImages.get(position))
//                .into(holder.discImage);

        holder.discName.setText(fetchedDiscs.get(position).getName());
        holder.discType.setText(fetchedDiscs.get(position).getType());
        holder.discMan.setText(fetchedDiscs.get(position).getManufacturer());
        holder.discColor.setText(fetchedDiscs.get(position).getColor());
        holder.discDist.setText(fetchedDiscs.get(position).getUid());

        holder.discListLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + fetchedDiscs.get(position).getName());

                // save disc
//                Disc savedDisc = fetchedDiscs.get(position);

                // remove this current disc and add saved disc


                final String b = fetchedDiscs.get(position).getUid();
                db.collection("discs").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshots) {
                                if (querySnapshots.isEmpty()) {
                                    Log.d(TAG, "onSuccess: LIST EMPTY");
                                    return;
                                } else {
                                    Log.d(TAG, "retrieved -----> " + querySnapshots.size());
                                    for (DocumentSnapshot documentSnapshot : querySnapshots.getDocuments()) {
                                        if (documentSnapshot.getData().get("uid").equals(b)) {
                                            String documentName = documentSnapshot.getId();
                                            Log.d("TEST12315166", documentName);
                                            db.collection("discs").document(documentName).set(fetchedDiscs.get(position));
                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {

                            }
                        });

                Toast.makeText(view.getContext(), "Pushed to database", Toast.LENGTH_SHORT);
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int getItemCount() {
        return fetchedDiscs.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView discImage;
        EditText discName;
        EditText discType;
        EditText discMan;
        EditText discColor;
        EditText discDist;
        RelativeLayout discListLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            discImage = itemView.findViewById(R.id.discImage);
            discName = itemView.findViewById(R.id.disc_name_text);
            discType = itemView.findViewById(R.id.disc_type_text);
            discMan = itemView.findViewById(R.id.disc_manufacturer_text);
            discColor = itemView.findViewById(R.id.disc_color_text);
            discDist = itemView.findViewById(R.id.disc_distance_text);
            discListLayout = itemView.findViewById(R.id.discListLayout);

        }
    }

}
