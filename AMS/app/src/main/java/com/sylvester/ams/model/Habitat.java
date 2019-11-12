package com.sylvester.ams.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Habitat extends RealmObject {
    @PrimaryKey
    private int id;   // 아이디
    private String continentType;   // 뉴월드 or 올드월드
    private String habitatName;     // 서식지명
    private String environment;     // 서식환경
    private String temperature;     // 온도
    private String humidity;        // 습도

    public Habitat() {

    }

    public Habitat(int id, String habitatName) {
        this(id, "", habitatName, "", "", "");
    }

    public Habitat(int id, String continentType, String habitatName, String environment, String temperature, String humidity) {
        this.id = id;
        this.continentType = continentType;
        this.habitatName = habitatName;
        this.environment = environment;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContinentType() {
        return continentType;
    }

    public void setContinentType(String continentType) {
        this.continentType = continentType;
    }

    public String getHabitatName() {
        return habitatName;
    }

    public void setHabitatName(String habitatName) {
        this.habitatName = habitatName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
