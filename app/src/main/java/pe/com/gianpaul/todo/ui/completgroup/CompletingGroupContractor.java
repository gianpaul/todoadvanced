package pe.com.gianpaul.todo.ui.completgroup;

import io.realm.RealmList;
import pe.com.gianpaul.todo.model.OutstandGroupModelRealm;

public class CompletingGroupContractor {

    interface View {

    }

    interface Presenter {

        RealmList<OutstandGroupModelRealm> getCompletingGroupList();

        void onViewDettached();

        void onViewAttached(CompletingGroupContractor.View view);
    }
}
