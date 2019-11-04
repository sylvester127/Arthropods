package com.sylvester.ams.controller.service.realm;

import android.app.Activity;
import android.app.Application;

import com.sylvester.ams.model.ArthropodInfo;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmContext implements Serializable {
    private static RealmContext instance;
    private Realm realm;

    // 생성자
    public RealmContext(Application application) {
        // 이 스레드의 Realm의 인스턴스를 얻는다.
        realm = Realm.getDefaultInstance();
    }

    public static RealmContext with(Activity activity) {
        if (instance == null) {
            instance = new RealmContext(activity.getApplication());
        }
        return instance;
    }

    public static RealmContext with(Application application) {
        if (instance == null) {
            instance = new RealmContext(application);
        }
        return instance;
    }

    public static RealmContext getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public static void initRealm(Activity activity) {
        // Realm을 초기화한다.
        Realm.init(activity);

        // Realm 데이터베이스 파일의 위치, 이름, 스키마버전 등을 설정한다.
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("arthropodsDB.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
//        Realm.deleteRealm(config);
    }

    // Refresh the realm istance
    public void refreshRealm() {
        realm.refresh();
    }

    public void closeRealm() {
        realm.close();
    }
}