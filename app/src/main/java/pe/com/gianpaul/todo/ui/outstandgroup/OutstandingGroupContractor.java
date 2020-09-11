package pe.com.gianpaul.todo.ui.outstandgroup;

import io.realm.RealmList;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;

public class OutstandingGroupContractor {

    interface View {
        void updateAdapter();
    }

    interface Presenter {
        void editOutstanding(String id, String title);

        void deleteOutstanding(String id);

        RealmList<OutstandGroupModelRealm> getOutstandGroupList();

        void addOutstanding(String text);

        void onViewDettached();

        void onViewAttached(OutstandingGroupContractor.View view);
    }
}
