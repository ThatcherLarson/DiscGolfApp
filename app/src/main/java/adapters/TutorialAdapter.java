package adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.discgolfapp.R;

import java.util.ArrayList;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.MyViewHolder> {
    private ArrayList<String> tutorialThumbnails = new ArrayList<>();
    private ArrayList<String> tutorialText = new ArrayList<>();
    private ArrayList<String> tutorialURL = new ArrayList<>();
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tutorialTextView;
        public ImageView tutorialImageView;
        public RelativeLayout tutorialListLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            tutorialTextView = (TextView) itemView.findViewById(R.id.tutorialTextView);
            tutorialImageView = (ImageView) itemView.findViewById(R.id.tutorialImageView);
            tutorialListLayout = itemView.findViewById(R.id.tutorialListItem);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TutorialAdapter(Context mContext, ArrayList<String> tutorialText, ArrayList<String> tutorialThumbnails, ArrayList<String> tutorialURL) {
        this.tutorialThumbnails = tutorialThumbnails;
        this.tutorialText = tutorialText;
        this.mContext = mContext;
        this.tutorialURL = tutorialURL;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TutorialAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorial_list_item, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d("TutorialAdapter", "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(tutorialThumbnails.get(position))
                .into(holder.tutorialImageView);
        holder.tutorialTextView.setText(tutorialText.get(position));

        holder.tutorialListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TutorialActivity", "onClick: clicked on: " + tutorialText.get(position));
                //Toast.makeText(mContext, tutorialText.get(position), Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(tutorialURL.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tutorialText.size();
    }

}
