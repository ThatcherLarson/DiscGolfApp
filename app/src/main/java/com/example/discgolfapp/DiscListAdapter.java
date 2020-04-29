package com.example.discgolfapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Disc;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.discName.setText(fetchedDiscs.get(position).getName());
        holder.discType.setText(fetchedDiscs.get(position).getType());
        holder.discMan.setText(fetchedDiscs.get(position).getManufacturer());
        holder.discColor.setText(fetchedDiscs.get(position).getColor());
        holder.discDist.setText(fetchedDiscs.get(position).getUid());

        holder.editDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Disc disc = fetchedDiscs.get(position);

                Intent intent = new Intent(mContext, AddDiscActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("disc", disc);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
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
        TextView discName;
        TextView discType;
        TextView discMan;
        TextView discColor;
        TextView discDist;
        Button editDisc;
        RelativeLayout discListLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            discImage = itemView.findViewById(R.id.discImage);
            discName = itemView.findViewById(R.id.disc_name_text);
            discType = itemView.findViewById(R.id.disc_type_text);
            discMan = itemView.findViewById(R.id.disc_manufacturer_text);
            discColor = itemView.findViewById(R.id.disc_color_text);
            discDist = itemView.findViewById(R.id.disc_distance_text);
            editDisc = itemView.findViewById(R.id.edit_disc);
            discListLayout = itemView.findViewById(R.id.discListLayout);

        }

    }

}