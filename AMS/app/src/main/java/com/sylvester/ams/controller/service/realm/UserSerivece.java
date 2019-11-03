package com.sylvester.ams.controller.service.realm;

import com.sylvester.ams.model.User;

import io.realm.Realm;

public class UserSerivece {
    private Realm realm;

    public UserSerivece() {
        this.realm = RealmContext.getInstance().getRealm();
    }

    // User 쿼리
    public User getUser() {
        if(realm.where(User.class).count() == 0)
            return null;
        return realm.where(User.class).findFirst();
    }

    public void setUser(final User user) {
        if(user == null) {return;}

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user);
            }
        });
    }
}
