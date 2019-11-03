package com.sylvester.ams.controller.service.realm;

import com.sylvester.ams.model.User;

import java.util.Date;

import io.realm.Realm;

public class UserService {
    private Realm realm;

    public UserService() {
        this.realm = RealmContext.getInstance().getRealm();
    }

    public boolean checkUpdate(long updateCycle) {
        boolean result = false;

        // DB에 저장한 시간을 받아온다.
        Date past = realm.where(User.class).findFirst().getUpdateDate();

        if (past == null) {  // 널값 체크
            return result;
        }

        // 디바이스에서 현재시간을 받아온다.
        Date now = new Date(System.currentTimeMillis());

        // 현재와 과거 시간 차이를 구한다.
        long duration = Math.abs(now.getTime() - past.getTime());
        duration = duration / (24 * 60 * 60 * 1000);

        // 정해진 기간보다 업데이트를 안한 기간이 더 길면
        if (updateCycle < duration) {
            result = true;
        }
        return result;
    }

    public User getUser() {
        if (realm.where(User.class).count() == 0)
            return null;
        return realm.where(User.class).findFirst();
    }

    public void setUser(final User user) {
        if (user == null) {
            return;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user);
            }
        });
    }
}
