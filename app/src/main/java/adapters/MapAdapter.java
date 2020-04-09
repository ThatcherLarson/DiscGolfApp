package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discgolfapp.R;

import java.util.ArrayList;

import models.DiscMap;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder>{

    private ArrayList<DiscMap> mMaps = new ArrayList<>();


    public MapAdapter(ArrayList<DiscMap> discMaps) {
        this.mMaps = discMaps;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_disc_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder)holder).description.setText(mMaps.get(position).getDescription());
        ((ViewHolder)holder).title.setText(mMaps.get(position).getTitle());
        ((ViewHolder)holder).image.setImageDrawable(mMaps.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return mMaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView image;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCourseName);
            image = itemView.findViewById(R.id.ivCourseImage);
            description = itemView.findViewById(R.id.tvCourseDescription);
        }


    }

}