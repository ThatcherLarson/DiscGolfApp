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
import com.example.discgolfapp.ScorecardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import models.CourseThrows;
import models.DiscMap;
import models.Throw;
import models.UserCourse;

public class RecentGameAdapter extends RecyclerView.Adapter<RecentGameAdapter.ViewHolder> {
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<DiscMap> mapsToGames = new ArrayList<>();
    ArrayList<String> gamesID = new ArrayList<>();

    String gameID = "";

    FirebaseFirestore db;
    private FirebaseAuth auth;


    @NonNull
    @Override
    public RecentGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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
        return gamesID.size();
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
                public void onClick(final View v) {
                    if (v.getId() == R.id.scoreCard) {
                            gameID = gamesID.get(getAdapterPosition());

                            loadDataOnCourse(new RecentGameAdapter.FirestoreCallBack() {
                                @Override
                                public void onCallback(Map<Integer, UserCourse> courseThrows,ArrayList<String> names) {
                                    Bundle bundle = new Bundle();
                                    bundle.putIntegerArrayList("Pars", mapsToGames.get(getAdapterPosition()).getPars());

                                    int count = 0;
                                    for (String name:names) {
                                        bundle.putIntegerArrayList(name + "scores", courseThrows.get(count).getParResults());
                                        count++;
                                    }
                                    bundle.putStringArrayList("Names", names);

                                    Intent intent = new Intent(v.getContext(), ScorecardActivity.class);
                                    intent.putExtras(bundle);
                                    v.getContext().startActivity(intent);
                                }
                            });
                        }


                }
            });

        }


    }
    private interface FirestoreCallBack{
        void onCallback(Map<Integer, UserCourse> courseThrows,ArrayList<String> names);
    }

    private void loadDataOnCourse(final RecentGameAdapter.FirestoreCallBack firestoreCallBack) {
        loadGames(firestoreCallBack);
    }

    private void loadGames(final RecentGameAdapter.FirestoreCallBack callback) {
        String courseId = gameID;
        db.collection("users").document(auth.getUid()).collection("games").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String,Object> loadGame = task.getResult().getData();
                ArrayList<String> names = (ArrayList<String>)loadGame.get("Names");

                Map<Integer, UserCourse> gameData = new HashMap<>();
                for (int i = 0; i < names.size(); i++){

                    ArrayList<Integer> strokes = (ArrayList<Integer>) ((Map<String,Object>)loadGame.get("User"+i)).get("Pars");
                    Map<Integer, CourseThrows> courseThrows = new HashMap<>();
                    for(int j = 0; j < strokes.size(); j++){
                        CourseThrows ct = new CourseThrows();
                        ArrayList<Object> parThrows = (ArrayList<Object>) ((Map<String,Object>)loadGame.get("User"+i)).get("Location"+Integer.toString(j+1));

                        for(Object parThrow: parThrows){
                            Map<String, GeoPoint> geoStartEnd = (Map<String, GeoPoint>) parThrow;
                            GeoPoint startGeo = geoStartEnd.get("Start");
                            GeoPoint endGeo = geoStartEnd.get("End");
                            Throw singleThrow = new Throw(startGeo,endGeo);
                            ct.addThrowEnd(singleThrow);
                        }


                        courseThrows.put(j,ct);
                    }

                    UserCourse myCourse = new UserCourse(strokes,courseThrows, names.get(i));
                    gameData.put(i,myCourse);


                }
                callback.onCallback(gameData,names);

            }

        });

    }
}
