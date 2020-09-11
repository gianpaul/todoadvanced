package pe.com.gianpaul.todo.ui.completgroup;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;
import pe.com.gianpaul.todo.model.OutstandModelRealm;

public class CompletingGroupPresenter implements CompletingGroupContractor.Presenter {
    private CompletingGroupContractor.View view;
    private Context mContext;
    private Realm realm;

    public CompletingGroupPresenter(Context mContext) {
        this.mContext = mContext;
        initRealmConfiguration();
        realm = Realm.getDefaultInstance();
    }

    private void initRealmConfiguration() {
        Realm.init(mContext);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public CompletingGroupContractor.View getView() {
        return view;
    }

    public boolean isAttached() {
        return getView() != null;
    }

    @Override
    public RealmList<OutstandGroupModelRealm> getCompletingGroupList() {
        RealmList<OutstandGroupModelRealm> outstandGroupModelRealmArrayList = new RealmList<>();
        if (isAttached()) {
            RealmResults<OutstandGroupModelRealm> outstandGroupModelRealms = realm.where(OutstandGroupModelRealm.class).sort("name").findAll();
            for (OutstandGroupModelRealm outstandGroupModelRealm : outstandGroupModelRealms) {
                int actives = 0;
                for (OutstandModelRealm outstandModelRealm : outstandGroupModelRealm.getOutstandModelRealmList()) {
                    if (outstandModelRealm.isActive()) actives++;
                }
                if (actives == outstandGroupModelRealm.getOutstandModelRealmList().size())
                    outstandGroupModelRealmArrayList.add(outstandGroupModelRealm);
            }
        }
        return outstandGroupModelRealmArrayList;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Override
    public void onViewAttached(CompletingGroupContractor.View view) {
        this.view = view;
    }
}
