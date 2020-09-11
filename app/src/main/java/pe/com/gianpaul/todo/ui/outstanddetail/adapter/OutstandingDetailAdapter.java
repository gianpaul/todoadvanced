package pe.com.gianpaul.todo.ui.outstanddetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmList;
import pe.com.gianpaul.todo.R;
import pe.com.gianpaul.todo.model.OutstandModelRealm;

public class OutstandingDetailAdapter extends RecyclerView.Adapter<OutstandingDetailAdapter.ViewHolder> {
    private RealmList<OutstandModelRealm> outstandModelRealmRealmList;
    private Context mContext;
    private OutstandingDetailAdapter.OnItemClickListener mOnItemClickListener;

    public OutstandingDetailAdapter(RealmList<OutstandModelRealm> outstandModelRealmRealmList, Context mContext) {
        this.outstandModelRealmRealmList = outstandModelRealmRealmList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onEditClick(View view, String id, String title);

        void onCheckClick(View view, String id, boolean active);

        void onDeleteClick(View view, String id, String title);
    }

    public void setOnItemClickListener(final OutstandingDetailAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public OutstandingDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_detail, parent, false);
        return new OutstandingDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutstandingDetailAdapter.ViewHolder holder, int position) {
        try {
            final OutstandModelRealm outstanding = outstandModelRealmRealmList.get(position);
            holder.id = outstanding.getIdkey();
            holder.title.setText(outstanding.getName());
            holder.activeCheckBox.setChecked(outstanding.isActive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return outstandModelRealmRealmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String id;
        private TextView title;
        private ImageButton editImageButton;
        private ImageButton deleteImageButton;
        private CheckBox activeCheckBox;

        public ViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.titleTextView);
            editImageButton = v.findViewById(R.id.editImageButton);
            deleteImageButton = v.findViewById(R.id.deleteImageButton);
            activeCheckBox = v.findViewById(R.id.activeCheckBox);

            editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onEditClick(view, id, title.getText().toString());
                    }
                }
            });

            deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onDeleteClick(view, id, title.getText().toString());
                    }
                }
            });

            activeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onCheckClick(view, id, activeCheckBox.isChecked());
                    }
                }
            });
        }
    }

}
