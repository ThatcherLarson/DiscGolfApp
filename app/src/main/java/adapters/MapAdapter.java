package adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discgolfapp.R;
import com.example.discgolfapp.StartGameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.DiscMap;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {

    private ArrayList<DiscMap> mMaps;

    public MapAdapter(ArrayList<DiscMap> discMaps) {
        this.mMaps = discMaps;
    }

    public void update(ArrayList<DiscMap> discMaps) {
        this.mMaps = discMaps;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_course_map, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder) holder).description.setText(mMaps.get(position).getDescription());
        ((ViewHolder) holder).title.setText(mMaps.get(position).getTitle());
        //((ViewHolder)holder).image.setImageDrawable(mMaps.get(position).getImage());
        ((ViewHolder) holder).numPars.setText("Holes: "+Integer.toString(mMaps.get(position).getNumPars()));
        ((ViewHolder) holder).milesAway.setText("Miles Away: "+String.format("%.2f", (mMaps.get(position).getMilesAway())));
        ((ViewHolder) holder).favorite.setChecked(mMaps.get(position).getFavorite());
    }

    @Override
    public int getItemCount() {
        return mMaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView description;
        TextView numPars;
        TextView milesAway;
        Button newGame;
        CheckBox favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCourseNameMapList);
            description = itemView.findViewById(R.id.tvCourseDescriptionMapList);
            numPars = itemView.findViewById(R.id.tvParsMapList);
            milesAway = itemView.findViewById(R.id.tvMilesAway);
            favorite = itemView.findViewById(R.id.tvFavorite);
            newGame = itemView.findViewById(R.id.playGame);

            newGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.playGame) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("Map", mMaps.get(getAdapterPosition()));
                        Intent intent = new Intent(v.getContext(), StartGameActivity.class);
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                    }
                }
            });

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.tvFavorite) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final DocumentReference userDoc = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<String> ids = (ArrayList<String>) task.getResult().get("fav_courses");
                                    if (ids == null) {
                                        ids = new ArrayList<String>();
                                    }
                                    String id = mMaps.get(getAdapterPosition()).getId();
                                    if (favorite.isChecked()) {
                                        if (id != null) {
                                            ids.add(id);
                                        }
                                    } else {
                                        if (id != null) {
                                            ids.remove(id);
                                        }
                                    }
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("fav_courses", ids);
                                    userDoc.set(data, SetOptions.merge());
                                }
                            }
                        });
                    }
                }
            });
        }


    }
}