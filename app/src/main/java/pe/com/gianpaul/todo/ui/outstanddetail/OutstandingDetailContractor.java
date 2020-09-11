package pe.com.gianpaul.todo.ui.outstanddetail;

import io.realm.RealmList;
import pe.com.gianpaul.todo.model.OutstandModelRealm;

public class OutstandingDetailContractor {
    interface View {
        void updateAdapter();
    }

    interface Presenter {
        void editOutstanding(String id, String title);

        void checkOutstanding(String id, boolean active);

        void deleteOutstanding(String id);

        RealmList<OutstandModelRealm> getOutstandDetailList(String id);

        void addOutstanding(String text, String id);

        void onViewDettached();

        void onViewAttached(OutstandingDetailContractor.View view);
    }
}
