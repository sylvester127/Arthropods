package com.sylvester.ams.model.realm;

import android.app.Activity;
import android.app.Application;

import com.sylvester.ams.model.TarantulaInfo;
import com.sylvester.ams.model.TarantulaObject;
import com.sylvester.ams.model.User;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController implements Serializable {
    private static RealmController instance;
    private Realm realm;

    // 생성자
    public RealmController(Application application) {
        // 이 스레드의 Realm의 인스턴스를 얻는다.
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Activity activity) {
        if(instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if(instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public static void initRealm(Activity activity){
        // Realm을 초기화한다.
        Realm.init(activity);

        // Realm 데이터베이스 파일의 위치, 이름, 스키마버전 등을 설정한다.
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("tarantulaDB.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        System.out.println(config.getPath());
    }

    // Refresh the realm istance
    public void refreshRealm() {
        realm.refresh();
    }

    public void closeRealm() {
        realm.close();
    }







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

    // TarantulaInfo 저장 쿼리
    public void setTarantulaInfo(final TarantulaInfo tarantulaInfo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(tarantulaInfo);
            }
        });
    }

    public void setTarantulaInfos(final ArrayList<TarantulaInfo> tarantulaInfo){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(tarantulaInfo);
            }
        });
    }

    // query a single item with the given scientific name
    public TarantulaInfo getTarantulaInfo(String scientific_name) {
        return realm.where(TarantulaInfo.class).equalTo("scientific_name", scientific_name).findFirst();
    }

    public ArrayList<TarantulaInfo> getTarantulaInfos() {
        ArrayList<TarantulaInfo> tarantulaInfoArrayList = new ArrayList<>();
        RealmResults<TarantulaInfo> realmResults = realm.where(TarantulaInfo.class).findAll();

        for (TarantulaInfo tarantulaInfo : realmResults) {
            tarantulaInfoArrayList.add(tarantulaInfo);
        }

        return tarantulaInfoArrayList;
    }

    //query example
    public TarantulaInfo sampleObject() {
        return realm.copyFromRealm(realm.where(TarantulaInfo.class)
                .equalTo("scientific_name", "Acanthoscurria geniculata").findFirst());
    }

    // TarantulaObject 쿼리
    public void setTarantulaObject(final TarantulaObject tarantulaObject) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(tarantulaObject);
            }
        });
    }

    public TarantulaObject getTarantulaObject(String key) {
        return realm.copyFromRealm(realm.where(TarantulaObject.class).
                equalTo("key", key).findFirst());
    }

    // find all objects in the TarantulaObject.class
    public RealmResults<TarantulaObject> getTarantulaObjects() {
        return realm.where(TarantulaObject.class).findAll();
    }

    public ArrayList<TarantulaObject> searchTarantulaObject() {
        ArrayList<TarantulaObject> tarantulaObjectArrayList = new ArrayList<>();
        RealmResults<TarantulaObject> realmResults = realm.where(TarantulaObject.class).findAll();

        for (TarantulaObject tInfo : realmResults) {
            tarantulaObjectArrayList.add(tInfo);
        }

        return tarantulaObjectArrayList;
    }

    // 갱신

    // 삭제
}