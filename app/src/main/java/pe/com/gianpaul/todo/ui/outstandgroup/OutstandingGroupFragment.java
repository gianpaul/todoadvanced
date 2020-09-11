package pe.com.gianpaul.todo.ui.outstandgroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.RealmList;
import pe.com.gianpaul.todo.MainActivity;
import pe.com.gianpaul.todo.R;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;
import pe.com.gianpaul.todo.ui.outstandgroup.adapter.OutstandingGroupAdapter;
import pe.com.gianpaul.todo.ui.outstandgroup.dialog.AddDialog;
import pe.com.gianpaul.todo.ui.outstandgroup.dialog.DeleteDialog;
import pe.com.gianpaul.todo.ui.outstandgroup.dialog.EditDialog;

public class OutstandingGroupFragment extends Fragment implements OutstandingGroupContractor.View, AddDialog.Callback, DeleteDialog.Callback, EditDialog.Callback {
    private OutstandingGroupContractor.Presenter presenter;
    private Context mContext;
    private RecyclerView outstandRecyclerView;
    private FloatingActionButton floatingActionButton;
    private OutstandingGroupAdapter mAdapter;
    private String deleteId, editId;
    private RealmList<OutstandGroupModelRealm> outstandGroupModelRealmsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outstanding, container, false);
        mContext = getActivity();
        initializeViews(view);
        if (presenter == null) {
            presenter = new OutstandingGroupPresenter(mContext);
        }
        getPresenter().onViewAttached(OutstandingGroupFragment.this);
        setUpRecycler();
        return view;
    }

    private void initializeViews(View view) {
        outstandRecyclerView = view.findViewById(R.id.outstandRecyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                AddDialog newFragment = new AddDialog();
                newFragment.setTargetFragment(OutstandingGroupFragment.this, 0);
                newFragment.show(ft, "dialog");
            }
        });
    }

    private void setUpRecycler() {
        mAdapter = new OutstandingGroupAdapter(outstandGroupModelRealmsList = getPresenter().getOutstandGroupList(), mContext);
        outstandRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        outstandRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new OutstandingGroupAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(View view, String id, String title) {
                editId = id;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                EditDialog newFragment = new EditDialog(title);
                newFragment.setTargetFragment(OutstandingGroupFragment.this, 0);
                newFragment.show(ft, "dialog");
            }

            @Override
            public void onDeleteClick(View view, String id, String title) {
                deleteId = id;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                DeleteDialog newFragment = new DeleteDialog(title);
                newFragment.setTargetFragment(OutstandingGroupFragment.this, 0);
                newFragment.show(ft, "dialog");
            }
        });
    }

    private OutstandingGroupContractor.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(OutstandingGroupFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    @Override
    public void acceptNew(String text) {
        getPresenter().addOutstanding(text);
        ((MainActivity) getActivity()).message(getResources().getString(R.string.add_message));
    }

    @Override
    public void updateAdapter() {
        setUpRecycler();
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
}