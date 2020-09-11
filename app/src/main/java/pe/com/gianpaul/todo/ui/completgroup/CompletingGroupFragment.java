package pe.com.gianpaul.todo.ui.completgroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmList;
import pe.com.gianpaul.todo.R;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;
import pe.com.gianpaul.todo.ui.completgroup.adapter.CompletingGroupAdapter;

public class CompletingGroupFragment extends Fragment implements CompletingGroupContractor.View {
    private CompletingGroupContractor.Presenter presenter;
    private Context mContext;
    private CompletingGroupAdapter mAdapter;
    private RecyclerView completRecyclerView;
    private RealmList<OutstandGroupModelRealm> outstandGroupModelRealmsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completing, container, false);
        mContext = getActivity();
        initializeViews(view);
        if (presenter == null) {
            presenter = new CompletingGroupPresenter(mContext);
        }
        getPresenter().onViewAttached(CompletingGroupFragment.this);
        setUpRecycler();
        return view;
    }

    private void initializeViews(View view) {
        completRecyclerView = view.findViewById(R.id.completRecyclerView);
    }

    private void setUpRecycler() {
        mAdapter = new CompletingGroupAdapter(outstandGroupModelRealmsList = getPresenter().getCompletingGroupList(), mContext);
        completRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        completRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
    }

    private CompletingGroupContractor.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(CompletingGroupFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

}