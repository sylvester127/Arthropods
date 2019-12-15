package com.sylvester.ams.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Arthropod extends RealmObject {
    @PrimaryKey
    private int id;                     // 아이디

    private String imgDir;              // 사진
    private String name;                // 개체이름
    private String gender;              // 성별: 미구분, 수, 암
    private float size;                 // 개체 크기
    private String habit;               // 개체의 습성: 배회성, 버로우성, 나무위성
    private Date lastFeedDate;          // 마지막 먹이급여 날짜
    private boolean postponeFeed;       // 피딩을 중지
    private int feedingCycle;           // 피딩 사이클
    private Date receiveDate;           // 입양, 태어난 일
    private String receivePlace;        // 입양 보낸 사람, 브리더
    private Date lastRehousingDate;     // 마지막 집갈이 날짜
    private String status;              // 개체 상태
    private boolean molt;               // 탈기인지 아닌지 체크
    private String moltCount;           // 탈피횟수
    private String molt_history;        // 탈피기록
    private String memo;                // 메모
    private long regdate;               // 등록일자

    @LinkingObjects("arthropods")
    private final RealmResults<ScientificName> scientificName;

    // 생성자
    public Arthropod() {
        this(0, "tarantulaImg.jpg", "", "미구분", 0, "배회성", null,
                false, 0, new Date(), "", new Date(), "소유중",
                false, "N1", "", "", null);
    }

    public Arthropod(int id, String imgDir, String name) {
        this(id, imgDir, name, "미구분", 0, "배회성", null, false,
                0, new Date(), "", new Date(), "소유중",
                false, "N1", "", "", null);
    }

    public Arthropod(int id, String imgDir, String name, String gender, float size, String habit,
                     Date lastFeedDate, boolean postponeFeed, int feedingCycle, Date receiveDate,
                     String receivePlace, Date lastRehousingDate, String status, boolean molt,
                     String moltCount, String molt_history, String memo, RealmResults<ScientificName> scientificName) {
        this.id = id;
        this.imgDir = imgDir;
        this.name = name;
        this.gender = gender;
        this.size = size;
        this.habit = habit;
        this.lastFeedDate = lastFeedDate;
        this.postponeFeed = postponeFeed;
        this.feedingCycle = feedingCycle;
        this.receiveDate = receiveDate;
        this.receivePlace = receivePlace;
        this.lastRehousingDate = lastRehousingDate;
        this.status = status;
        this.molt = molt;
        this.moltCount = moltCount;
        this.molt_history = molt_history;
        this.memo = memo;
        regdate = System.currentTimeMillis();
        this.scientificName = scientificName;
    }

    // getter, setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public Date getLastFeedDate() {
        return lastFeedDate;
    }

    public void setLastFeedDate(Date lastFeedDate) {
        this.lastFeedDate = lastFeedDate;
    }

    public boolean getPostponeFeed() {
        return postponeFeed;
    }

    public void setPostponeFeed(boolean postponeFeed) {
        this.postponeFeed = postponeFeed;
    }

    public int getFeedingCycle() {
        return feedingCycle;
    }

    public void setFeedingCycle(int feedingCycle) {
        this.feedingCycle = feedingCycle;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceivePlace() {
        return receivePlace;
    }

    public void setReceivePlace(String receivePlace) {
        this.receivePlace = receivePlace;
    }

    public Date getLastRehousingDate() {
        return lastRehousingDate;
    }

    public void setLastRehousingDate(Date lastRehousingDate) {
        this.lastRehousingDate = lastRehousingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getMolt() {
        return molt;
    }

    public void setMolt(boolean molt) {
        this.molt = molt;
    }

    public String getMoltCount() {
        return moltCount;
    }

    public void setMoltCount(String moltCount) {
        this.moltCount = moltCount;
    }

    public String getMoltHistory() {
        return molt_history;
    }

    public void setMoltHistory(String molt_history) {
        this.molt_history = molt_history;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }

    public RealmResults<ScientificName> getScientificName() {
        return scientificName;
    }
}
