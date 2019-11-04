package com.sylvester.ams.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class ArthropodInfo extends RealmObject{
    @Required
    private String scientificName; // 학명
    private String common_name;     // 커먼네임
    private String type;            // 뉴월드 or 올드월드
    private String behavior;        // 활동방식
    private String distribution;    // 서식지
    private String habitat;         // 서식환경
    private float temperature_low;  // 적정 온도
    private float temperature_high;
    private float humidity_low;     // 적정 습도
    private float humidity_high;

    public ArthropodInfo() {

    }

    public ArthropodInfo(String scientificName) {
        this.scientificName = scientificName;
    }

    // getter
    public String getScientificName() { return scientificName; }

    public String getBehavior() { return behavior; }

    // setter
    public void setScientificName(String scientificName) { this.scientificName = scientificName; }

    public void setDistribution(String distribution) { this.distribution = distribution; }
}