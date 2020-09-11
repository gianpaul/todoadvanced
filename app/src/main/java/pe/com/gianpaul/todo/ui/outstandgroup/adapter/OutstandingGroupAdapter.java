package pe.com.gianpaul.todo.ui.outstandgroup.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmList;
import pe.com.gianpaul.todo.R;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;
import pe.com.gianpaul.todo.ui.outstanddetail.OutstandingDetailFragment;

public class OutstandingGroupAdapter extends RecyclerView.Adapter<OutstandingGroupAdapter.ViewHolder> {
    private RealmList<OutstandGroupModelRealm> outstandingList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public OutstandingGroupAdapter(RealmList<OutstandGroupModelRealm> outstandingList, Context mContext) {
        this.outstandingList = outstandingList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onEditClick(View view, String id, String title);

        void onDeleteClick(View view, String id, String title);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
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
            holder.id = outstanding.getIdkey();
            holder.text = outstanding.getName();
            holder.title.setText(outstanding.getName() + " - (" + outstanding.getOutstandModelRealmList().size() + " items)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return outstandingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String id, text;
        private TextView title;
        private ImageButton editImageButton;
        private ImageButton deleteImageButton;

        public ViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.titleTextView);
            editImageButton = v.findViewById(R.id.editImageButton);
            deleteImageButton = v.findViewById(R.id.deleteImageButton);

            editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onEditClick(view, id, text);
                    }
                }
            });

            deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onDeleteClick(view, id, text);
                    }
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new OutstandingDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("title", text);
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
