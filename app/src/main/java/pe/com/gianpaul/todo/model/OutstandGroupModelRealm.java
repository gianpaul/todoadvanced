package pe.com.gianpaul.todo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OutstandGroupModelRealm extends RealmObject {

    @PrimaryKey
    private String idkey;
    private String name;
    private RealmList<OutstandModelRealm> outstandModelRealmList;

    public String getIdkey() {
        return idkey;
    }

    public void setIdkey(String idkey) {
        this.idkey = idkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<OutstandModelRealm> getOutstandModelRealmList() {
        return outstandModelRealmList;
    }

    public void setOutstandModelRealmList(RealmList<OutstandModelRealm> outstandModelRealmList) {
        this.outstandModelRealmList = outstandModelRealmList;
    }
}
