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

public class UsernameAdapter extends RecyclerView.Adapter<UsernameAdapter.ViewHolder>{
    private ArrayList<String> names = new ArrayList<>();

    public ArrayList<String> get_names(){
        return names;
    }

    public UsernameAdapter(int parNums){
        for(int i = 0; i< parNums; i++) {
            names.add("");
        }
        notifyDataSetChanged();
    }
    public void update(int newVal,int oldVal){
        if (newVal>oldVal) {
            for (int i = 0; i < newVal-oldVal; i++) {
                names.add("");
            }
        }
        if (newVal<oldVal) {
            for (int i = 0; i < oldVal-newVal; i++) {
                names.remove(names.size()-1);
            }
        }
    }

    @NonNull
    @Override
    public UsernameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_name, parent, false);
        final UsernameAdapter.ViewHolder holder = new UsernameAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsernameAdapter.ViewHolder holder, int position) {

        ((ViewHolder)holder).name.setTag(position);

    }

    @Override
    public int getItemCount() {
        return names.size();
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
        private EditText name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name_enter);

            name.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                public void afterTextChanged(Editable editable) {}
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(name.getTag()!=null){
                        names.set((int)name.getTag(),charSequence.toString());
                    }
                }
            });
        }
    }
}
