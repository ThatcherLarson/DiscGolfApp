package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discgolfapp.R;

import java.util.ArrayList;

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
        CheckBox favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCourseNameMapList);
            //image = itemView.findViewById(R.id.ivCourseImageMap);
            description = itemView.findViewById(R.id.tvCourseDescriptionMapList);
            numPars = itemView.findViewById(R.id.tvParsMapList);
            milesAway = itemView.findViewById(R.id.tvMilesAway);
            favorite = itemView.findViewById(R.id.tvFavorite);
        }


    }


}