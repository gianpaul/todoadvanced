package pe.com.gianpaul.todo.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OutstandModelRealm extends RealmObject {

    @PrimaryKey
    private String idkey;
    private String name;
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
