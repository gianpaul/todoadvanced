package pe.com.gianpaul.todo.ui.outstanddetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.RealmList;
import pe.com.gianpaul.todo.MainActivity;
import pe.com.gianpaul.todo.R;
import pe.com.gianpaul.todo.model.OutstandModelRealm;
import pe.com.gianpaul.todo.ui.outstanddetail.adapter.OutstandingDetailAdapter;
import pe.com.gianpaul.todo.ui.outstandgroup.dialog.AddDialog;
import pe.com.gianpaul.todo.ui.outstandgroup.dialog.DeleteDialog;
import pe.com.gianpaul.todo.ui.outstandgroup.dialog.EditDialog;

public class OutstandingDetailFragment extends Fragment implements OutstandingDetailContractor.View, AddDialog.Callback, DeleteDialog.Callback, EditDialog.Callback {
    private OutstandingDetailContractor.Presenter presenter;
    private Context mContext;
    private RecyclerView outstandDetailRecyclerView;
    private FloatingActionButton floatingActionButton;
    private OutstandingDetailAdapter mAdapter;
    private TextView titleTextView;
    private ImageButton backImageButton;
    private String id, title, deleteId, editId;
    private RealmList<OutstandModelRealm> outstandModelRealmRealmList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outstanding_detail, container, false);
        mContext = getActivity();
        initializeViews(view);
        if (presenter == null) {
            presenter = new OutstandingDetailPresenter(mContext);
        }
        getPresenter().onViewAttached(OutstandingDetailFragment.this);
        setUpRecycler();
        return view;
    }

    private void initializeViews(View view) {
        outstandDetailRecyclerView = view.findViewById(R.id.outstandDetailRecyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        titleTextView = view.findViewById(R.id.titleTextView);
        backImageButton = view.findViewById(R.id.backImageButton);

        id = getArguments().getString("id");
        title = getArguments().getString("title");
        titleTextView.setText(title);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                AddDialog newFragment = new AddDialog();
                newFragment.setTargetFragment(OutstandingDetailFragment.this, 0);
                newFragment.show(ft, "dialog");
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


    }

    private void setUpRecycler() {
        mAdapter = new OutstandingDetailAdapter(outstandModelRealmRealmList = getPresenter().getOutstandDetailList(id), mContext);
        outstandDetailRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        outstandDetailRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new OutstandingDetailAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(View view, String id, String title) {
                editId = id;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                EditDialog newFragment = new EditDialog(title);
                newFragment.setTargetFragment(OutstandingDetailFragment.this, 0);
                newFragment.show(ft, "dialog");
            }

            @Override
            public void onCheckClick(View view, String id, boolean active) {
                getPresenter().checkOutstanding(id, active);

            }

            @Override
            public void onDeleteClick(View view, String id, String title) {
                deleteId = id;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                DeleteDialog newFragment = new DeleteDialog(title);
                newFragment.setTargetFragment(OutstandingDetailFragment.this, 0);
                newFragment.show(ft, "dialog");
            }
        });
    }

    private OutstandingDetailContractor.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(OutstandingDetailFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    @Override
    public void acceptNew(String text) {
        getPresenter().addOutstanding(text, id);
        ((MainActivity) getActivity()).message(getResources().getString(R.string.add_message));
    }

    @Override
    public void acceptDelete() {
        getPresenter().deleteOutstanding(deleteId);
        ((MainActivity) getActivity()).message(getResources().getString(R.string.delete_message));
    }

    @Override
    public void acceptEdit(String text) {
        getPresenter().editOutstanding(editId, text);
        ((MainActivity) getActivity()).message(getResources().getString(R.string.edit_message));
    }

    @Override
    public void updateAdapter() {
        setUpRecycler();
    }
}