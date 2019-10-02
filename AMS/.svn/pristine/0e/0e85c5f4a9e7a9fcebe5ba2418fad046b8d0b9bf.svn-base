package com.sylvester.ams.model;

import io.realm.RealmObject;

public class TarantulaObject extends RealmObject {
   private String key;                    // 키
   private int drawableId;                // 사진
   private String name;                   // 개체명
   private TarantulaInfo tarantulaInfo;   // 종의 정보
   private String last_fed;               // 마지막 피딩 날짜
   private int hungry;                    // 굶은 날짜
   private boolean postpone_fed;          // 피딩을 중지
   private int fed_cycle;                 // 피딩 사이클
   private int postpone_fed_date;
   private int sex;                       // 성별
   private String recive_date;
   private String last_rehouse;           // 마지막 집갈이
   private String status;                 // 개체 상태
   private String life_stages;            // 탈피횟수
   private String molt_history;
   private String memo;

   public TarantulaObject() {
      key = String.valueOf(System.currentTimeMillis());
   }

   public TarantulaObject(int drawableId, TarantulaInfo tarantulaInfo) {
      key = String.valueOf(System.currentTimeMillis());
      this.drawableId = drawableId;
      this.tarantulaInfo = tarantulaInfo;
      hungry = -1;
   }

   // getter
   public String getKey() {
      return key;
   }

   public int getDrawableId() {
      return drawableId;
   }

   public String getName() {
      return name;
   }

   public TarantulaInfo getTarantulaInfo() {
      return tarantulaInfo;
   }

   public String getLast_fed() {
      return last_fed;
   }

   public int getHungry() {
      return hungry;
   }

   public boolean isPostpone_fed() {
      return postpone_fed;
   }

   public int getFed_cycle() {
      return fed_cycle;
   }

   public int getPostpone_fed_date() {
      return postpone_fed_date;
   }

   public int getSex() {
      return sex;
   }

   public String getRecive_date() {
      return recive_date;
   }

   public String getLast_rehouse() {
      return last_rehouse;
   }

   public String getStatus() {
      return status;
   }

   public String getLife_stages() {
      return life_stages;
   }

   public String getMolt_history() {
      return molt_history;
   }

   public String getMemo() {
      return memo;
   }

   // setter
   public void setKey(String key) {
      this.key = key;
   }

   public void setName(String name) {
      this.name = name;
   }
}
