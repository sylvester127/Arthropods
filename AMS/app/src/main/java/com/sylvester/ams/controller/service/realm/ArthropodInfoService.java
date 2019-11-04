package com.sylvester.ams.controller.service.realm;

import com.sylvester.ams.model.ArthropodInfo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ArthropodInfoService {
    // ArthropodInfo 저장 쿼리
    public void setArthropodInfo(final ArthropodInfo arthropodInfo) {
        Realm realm = RealmContext.getInstance().getRealm();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(arthropodInfo);
            }
        });
    }

    public void setArthropodInfos(final ArrayList<ArthropodInfo> arthropodInfo) {
        Realm realm = RealmContext.getInstance().getRealm();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(arthropodInfo);
            }
        });
    }

    // query a single item with the given scientific name
    public ArthropodInfo getArthropodInfo(String scientificName) {
        Realm realm = RealmContext.getInstance().getRealm();
        return realm.where(ArthropodInfo.class).equalTo("scientificName", scientificName).findFirst();
    }

    public ArrayList<ArthropodInfo> getArthropodInfos() {
        Realm realm = RealmContext.getInstance().getRealm();

        ArrayList<ArthropodInfo> arthropodInfoArrayList = new ArrayList<>();
        RealmResults<ArthropodInfo> realmResults = realm.where(ArthropodInfo.class).findAll();

        for (ArthropodInfo arthropodInfo : realmResults)
            arthropodInfoArrayList.add(arthropodInfo);

        return arthropodInfoArrayList;
    }
}
