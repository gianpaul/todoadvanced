package pe.com.gianpaul.todo.ui.outstanddetail;

import android.content.Context;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;
import pe.com.gianpaul.todo.model.OutstandModelRealm;

public class OutstandingDetailPresenter implements OutstandingDetailContractor.Presenter {
    private OutstandingDetailContractor.View view;
    private Context mContext;
    private Realm realm;

    public OutstandingDetailPresenter(Context mContext) {
        this.mContext = mContext;
        initRealmConfiguration();
        realm = Realm.getDefaultInstance();
    }

    public OutstandingDetailContractor.View getView() {
        return view;
    }

    public boolean isAttached() {
        return getView() != null;
    }

    private void initRealmConfiguration() {
        Realm.init(mContext);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void editOutstanding(final String id, final String title) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<OutstandModelRealm> outstandModelRealms = realm.where(OutstandModelRealm.class).equalTo("idkey", id).findAll();
                for (OutstandModelRealm outstandModelRealm : outstandModelRealms) {
                    outstandModelRealm.setName(title);
                }
                realm.copyToRealmOrUpdate(outstandModelRealms);
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void checkOutstanding(final String id, final boolean active) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<OutstandModelRealm> outstandModelRealms = realm.where(OutstandModelRealm.class).equalTo("idkey", id).findAll();
                for (OutstandModelRealm outstandModelRealm : outstandModelRealms) {
                    outstandModelRealm.setActive(active);
                }
                realm.copyToRealmOrUpdate(outstandModelRealms);
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void deleteOutstanding(final String id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<OutstandModelRealm> outstandModelRealms = realm.where(OutstandModelRealm.class).equalTo("idkey", id).findAll();
                outstandModelRealms.deleteAllFromRealm();
                getView().updateAdapter();
            }
        });
    }

    @Override
    public RealmList<OutstandModelRealm> getOutstandDetailList(String id) {
        RealmList<OutstandModelRealm> outstandModelRealmRealmList = new RealmList<>();
        if (isAttached()) {
            RealmResults<OutstandGroupModelRealm> outstandGroupModelRealms = realm.where(OutstandGroupModelRealm.class).equalTo("idkey", id).findAll();
            for (OutstandGroupModelRealm outstandGroupModelRealm : outstandGroupModelRealms) {
                outstandModelRealmRealmList = outstandGroupModelRealm.getOutstandModelRealmList();
            }
        }
        return outstandModelRealmRealmList;
    }

    @Override
    public void addOutstanding(final String text, final String id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmList<OutstandModelRealm> outstandModelRealmRealmList = new RealmList<>();
                RealmResults<OutstandGroupModelRealm> outstandGroupModelRealms = realm.where(OutstandGroupModelRealm.class).equalTo("idkey", id).findAll();
                for (OutstandGroupModelRealm outstandGroupModelRealm : outstandGroupModelRealms) {
                    outstandModelRealmRealmList = outstandGroupModelRealm.getOutstandModelRealmList();
                    OutstandModelRealm outstandModelRealm = new OutstandModelRealm();
                    outstandModelRealm.setIdkey(UUID.randomUUID().toString());
                    outstandModelRealm.setName(text);
                    outstandModelRealmRealmList.add(outstandModelRealm);
                    outstandGroupModelRealm.setOutstandModelRealmList(outstandModelRealmRealmList);
                }
                realm.copyToRealmOrUpdate(outstandGroupModelRealms);
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Override
    public void onViewAttached(OutstandingDetailContractor.View view) {
        this.view = view;
    }
}
