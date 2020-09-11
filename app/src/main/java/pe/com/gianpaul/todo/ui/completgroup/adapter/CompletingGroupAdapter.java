package pe.com.gianpaul.todo.ui.completgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmList;
import pe.com.gianpaul.todo.R;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;

public class CompletingGroupAdapter extends RecyclerView.Adapter<CompletingGroupAdapter.ViewHolder> {
    private RealmList<OutstandGroupModelRealm> outstandingList;
    private Context mContext;

    public CompletingGroupAdapter(RealmList<OutstandGroupModelRealm> outstandingList, Context mContext) {
        this.outstandingList = outstandingList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_complet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final OutstandGroupModelRealm outstanding = outstandingList.get(position);
            holder.title.setText(outstanding.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return outstandingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageButton editImageButton;
        private ImageButton deleteImageButton;

        public ViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.titleTextView);
            editImageButton = v.findViewById(R.id.editImageButton);
            deleteImageButton = v.findViewById(R.id.deleteImageButton);
            editImageButton.setVisibility(View.INVISIBLE);
            deleteImageButton.setVisibility(View.INVISIBLE);
        }
    }
}
