package pe.com.gianpaul.todo.ui.outstandgroup;

import android.content.Context;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;
import pe.com.gianpaul.todo.model.OutstandModelRealm;

public class OutstandingGroupPresenter implements OutstandingGroupContractor.Presenter {
    private OutstandingGroupContractor.View view;
    private Context mContext;
    private Realm realm;

    public OutstandingGroupPresenter(Context mContext) {
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

    public OutstandingGroupContractor.View getView() {
        return view;
    }

    public boolean isAttached() {
        return getView() != null;
    }

    @Override
    public RealmList<OutstandGroupModelRealm> getOutstandGroupList() {
        RealmList<OutstandGroupModelRealm> outstandGroupModelRealmArrayList = new RealmList<>();
        if (isAttached()) {
            RealmResults<OutstandGroupModelRealm> outstandGroupModelRealms = realm.where(OutstandGroupModelRealm.class).sort("name").findAll();
            for (OutstandGroupModelRealm outstandGroupModelRealm : outstandGroupModelRealms) {
                if (outstandGroupModelRealm.getOutstandModelRealmList().size() != 0) {
                    int actives = 0;
                    for (OutstandModelRealm outstandModelRealm : outstandGroupModelRealm.getOutstandModelRealmList()) {
                        if (outstandModelRealm.isActive()) actives++;
                    }
                    if (actives != outstandGroupModelRealm.getOutstandModelRealmList().size())
                        outstandGroupModelRealmArrayList.add(outstandGroupModelRealm);
                } else outstandGroupModelRealmArrayList.add(outstandGroupModelRealm);
            }
        }
        return outstandGroupModelRealmArrayList;
    }

    @Override
    public void editOutstanding(final String id, final String title) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<OutstandGroupModelRealm> outstandGroupModelRealms = realm.where(OutstandGroupModelRealm.class).equalTo("idkey", id).findAll();
                for (OutstandGroupModelRealm outstandGroupModelRealm : outstandGroupModelRealms) {
                    outstandGroupModelRealm.setName(title);
                }
                realm.copyToRealmOrUpdate(outstandGroupModelRealms);
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void deleteOutstanding(final String id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<OutstandGroupModelRealm> outstandGroupModelRealms = realm.where(OutstandGroupModelRealm.class).equalTo("idkey", id).findAll();
                outstandGroupModelRealms.deleteAllFromRealm();
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void addOutstanding(final String text) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                OutstandGroupModelRealm outstandGroupModelRealmNew = new OutstandGroupModelRealm();
                outstandGroupModelRealmNew.setIdkey(UUID.randomUUID().toString());
                outstandGroupModelRealmNew.setName(text);
                realm.copyToRealmOrUpdate(outstandGroupModelRealmNew);
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Override
    public void onViewAttached(OutstandingGroupContractor.View view) {
        this.view = view;
    }

}
