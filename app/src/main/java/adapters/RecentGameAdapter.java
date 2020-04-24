package adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discgolfapp.HoleActivity;
import com.example.discgolfapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import models.DiscMap;

public class RecentGameAdapter extends RecyclerView.Adapter<RecentGameAdapter.ViewHolder> {
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<DiscMap> mapsToGames = new ArrayList<>();
    ArrayList<String> gamesID = new ArrayList<>();


    @NonNull
    @Override
    public RecentGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recent_games, parent, false);
        final RecentGameAdapter.ViewHolder holder = new RecentGameAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder) holder).title.setText(mapsToGames.get(position).getTitle());
        ((ViewHolder) holder).description.setText(mapsToGames.get(position).getDescription());
        ((ViewHolder) holder).pars.setText(Integer.toString(mapsToGames.get(position).getNumPars()));
        ((ViewHolder) holder).dateCreated.setText(dates.get(position));
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public void update(ArrayList<String> users, ArrayList<DiscMap> discMaps) {
        gamesID = users;
        for(String user: users){
            String courseID = user.substring(0,user.indexOf("-"));
            String sec = user.substring(user.indexOf("-") + 1);
            System.out.println(courseID);
            System.out.println(sec);
            Date date = new Date(Long.parseLong(sec)*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String formattedDate = sdf.format(date);
            dates.add(formattedDate);
            for (DiscMap dm: discMaps){
                if(dm.getId().equals(courseID)){
                    mapsToGames.add(dm);
                    break;
                }
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button return_to_game;
        Button scoreCard;
        TextView title;
        TextView description;
        TextView pars;
        TextView dateCreated;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleReturnToGame);
            description = itemView.findViewById(R.id.descriptionReturnToGame);
            pars = itemView.findViewById(R.id.parsReturnToGame);
            dateCreated = itemView.findViewById(R.id.createDate);
            return_to_game = itemView.findViewById(R.id.return_to_game);
            scoreCard = itemView.findViewById(R.id.scoreCard);

            return_to_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.return_to_game) {
                        Bundle bundle = new Bundle();
                        bundle.putString("CourseId", gamesID.get(getAdapterPosition()));
                        bundle.putParcelable("Map", mapsToGames.get(getAdapterPosition()));
                        bundle.putBoolean("LoadDB", true);
                        Intent intent = new Intent(v.getContext(), HoleActivity.class);
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                    }
                }
            });


            scoreCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.scoreCard) {


                    }
                }
            });

        }


    }
}
