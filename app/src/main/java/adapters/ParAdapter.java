package adapters;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
    public ArrayList<String> get_yards(){
        return yards;
    }

    public ParAdapter(int parNums){
        for(int i = 0; i< parNums; i++) {
            pars.add("");
            yards.add("");
        }
        notifyDataSetChanged();
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
        ((ViewHolder)holder).yard.setText(yards.get(position));
        ((ViewHolder)holder).par.setTag(position);
        ((ViewHolder)holder).yard.setTag(position);

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
        private EditText num;
        private EditText par;
        private EditText yard;
        public ViewHolder(View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.parNum);
            par = itemView.findViewById(R.id.parVal);
            yard = itemView.findViewById(R.id.parYards);
            par.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                public void afterTextChanged(Editable editable) {}
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(par.getTag()!=null){
                        pars.set((int)par.getTag(),charSequence.toString());
                    }
                }
            });
            yard.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                public void afterTextChanged(Editable editable) {}
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(yard.getTag()!=null){
                        yards.set((int)yard.getTag(),charSequence.toString());
                    }
                }
            });
        }
    }
}
