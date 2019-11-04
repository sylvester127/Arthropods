package com.sylvester.ams.controller.service.realm;

import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.ArthropodInfo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ArthropodService {
    //query example
    public ArthropodInfo setArthropod() {
        Realm realm = RealmContext.getInstance().getRealm();
        return realm.copyFromRealm(realm.where(ArthropodInfo.class)
                .equalTo("scientificName", "Acanthoscurria geniculata").findFirst());
    }

    // Arthropod 쿼리
    public void setArthropod(final Arthropod arthropod) {
        Realm realm = RealmContext.getInstance().getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(arthropod);
            }
        });
    }

    public Arthropod getArthropod(String key) {
        Realm realm = RealmContext.getInstance().getRealm();

        return realm.copyFromRealm(realm.where(Arthropod.class).
                equalTo("key", key).findFirst());
    }

    // find all objects in the Arthropod.class
    public ArrayList<Arthropod> getArthropods() {
        Realm realm = RealmContext.getInstance().getRealm();
        ArrayList<Arthropod> list = new ArrayList<>();
        RealmResults<Arthropod> results = realm.where(Arthropod.class).findAll();

        list.addAll(realm.copyFromRealm(results));

        return list;
    }

    public ArrayList<Arthropod> searchTarantulaObject() {
        Realm realm = RealmContext.getInstance().getRealm();

        ArrayList<Arthropod> arthropodArrayList = new ArrayList<>();
        RealmResults<Arthropod> realmResults = realm.where(Arthropod.class).findAll();

        for (Arthropod tInfo : realmResults) {
            arthropodArrayList.add(tInfo);
        }

        return arthropodArrayList;
    }
}
