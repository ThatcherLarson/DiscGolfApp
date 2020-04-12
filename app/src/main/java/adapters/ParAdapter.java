package adapters;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discgolfapp.R;

import java.util.ArrayList;


public class ParAdapter extends RecyclerView.Adapter<ParAdapter.ViewHolder>{
    private ArrayList<String> pars = new ArrayList<>();
    private ArrayList<String> yards = new ArrayList<>();

    public ArrayList<String> get_pars(){
        return pars;
    }
    public ArrayList<String> getYards(){
        return yards;
    }

    public ParAdapter(int parNums){
        for(int i = 0; i< parNums; i++) {
            pars.add("");
            yards.add("");
        }
    }
    public void update(int newVal,int oldVal){
        if (newVal>oldVal) {
            for (int i = 0; i < newVal-oldVal; i++) {
                pars.add("");
                yards.add("");
            }
        }

        if (newVal<oldVal) {
            for (int i = 0; i < oldVal-newVal; i++) {
                pars.remove(pars.size()-1);
                yards.remove(pars.size()-1);
            }
        }

    }

    @NonNull
    @Override
    public ParAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_par_list_item, parent, false);
        final ParAdapter.ViewHolder holder = new ParAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParAdapter.ViewHolder holder, int position) {

        ((ViewHolder)holder).num.setText(Integer.toString(position+1));
        ((ViewHolder)holder).par.setText(pars.get(position));
        ((ViewHolder)holder).yards.setText(yards.get(position));

        disableInput((EditText) ((ViewHolder)holder).num);

    }

    @Override
    public int getItemCount() {
        return pars.size();
    }

    public void disableInput(EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        EditText num;
        EditText par;
        EditText yards;


        public ViewHolder(View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.parNum);
            par = itemView.findViewById(R.id.parVal);
            yards = itemView.findViewById(R.id.parYards);
        }


    }
}
