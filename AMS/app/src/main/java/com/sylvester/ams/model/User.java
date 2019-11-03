package com.sylvester.ams.model;

import java.util.Date;

import io.realm.RealmObject;

public class User extends RealmObject {
    private Date updateDate;
    private boolean firstOn;

    public User() {
        this(new Date(),true);
    }

    public User(Date updateDate, boolean firstOn) {
        this.updateDate = updateDate;
        this.firstOn = firstOn;
    }

    // Standard getters & setters generated
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isFirstOn() {
        return firstOn;
    }

    public void setFirstOn(boolean firstOn) {
        this.firstOn = firstOn;
    }
}
