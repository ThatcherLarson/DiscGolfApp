package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discgolfapp.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    private String data1[], data2[];
    private int images[];
    private Context context;

    public RVAdapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_list_row, parent, false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        holder.name.setText(data1[position]);
        holder.description.setText(data2[position]);
        holder.image.setImageResource(images[0]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class RVViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description;
        private ImageView image;
        private CheckBox favorite;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvCourseName);
            description = itemView.findViewById(R.id.tvCourseDescription);
            image = itemView.findViewById(R.id.ivCourseImage);
            favorite = itemView.findViewById(R.id.cbFavorite);
        }
    }
}
