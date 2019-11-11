package com.sylvester.ams.service.realm;

import com.sylvester.ams.service.ArthropodInfoService;

public class RealmArthropodInfoService implements ArthropodInfoService {
    @Override
    public void insertArthropodInfos() {

    }


//    // ScientificName 저장 쿼리
//    public void setArthropodInfo(final ScientificName scientificName) {
//        Realm realm = RealmContext.getInstance().getRealm();
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(scientificName);
//            }
//        });
//    }
//
//    public void setArthropodInfos(final ArrayList<ScientificName> scientificName) {
//        Realm realm = RealmContext.getInstance().getRealm();
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(scientificName);
//            }
//        });
//    }
//
//    // query a single item with the given scientific name
//    public ScientificName getArthropodInfo(String scientificName) {
//        Realm realm = RealmContext.getInstance().getRealm();
//        return realm.where(ScientificName.class).equalTo("scientificName", scientificName).findFirst();
//    }
//
//    public ArrayList<ScientificName> getArthropodInfos() {
//        Realm realm = RealmContext.getInstance().getRealm();
//
//        ArrayList<ScientificName> scientificNameArrayList = new ArrayList<>();
//        RealmResults<ScientificName> realmResults = realm.where(ScientificName.class).findAll();
//
//        for (ScientificName scientificName : realmResults)
//            scientificNameArrayList.add(scientificName);
//
//        return scientificNameArrayList;
//    }
}
