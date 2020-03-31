package com.example.discgolfapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DiscListAdapter extends RecyclerView.Adapter<DiscListAdapter.ViewHolder>{

    private static final String TAG = "DiscListAdapter";
    private ArrayList<String> discImageNames = new ArrayList<>();
    private ArrayList<String> discImages = new ArrayList<>();
    private Context mContext;

    public DiscListAdapter( Context mContext, ArrayList<String> discImageNames, ArrayList<String> discImages) {
        this.discImageNames = discImageNames;
        this.discImages = discImages;
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
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(discImages.get(position))
                .into(holder.discImage);
        holder.discName.setText(discImageNames.get(position));

        holder.discListLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + discImageNames.get(position));
                Toast.makeText(mContext, discImageNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int getItemCount() {
        return discImageNames.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView discImage;
        TextView discName;
        RelativeLayout discListLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            discImage = itemView.findViewById(R.id.discImage);
            discName = itemView.findViewById(R.id.disc_name);
            discListLayout = itemView.findViewById(R.id.discListLayout);

        }
    }

}
