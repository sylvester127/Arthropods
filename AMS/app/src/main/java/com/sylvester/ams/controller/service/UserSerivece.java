package com.sylvester.ams.controller.service;

import com.sylvester.ams.model.User;

import io.realm.Realm;

public class UserSerivece {
    // User 쿼리
    public User getUser() {
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
