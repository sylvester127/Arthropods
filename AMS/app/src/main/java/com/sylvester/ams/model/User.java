package com.sylvester.ams.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

public class User extends RealmObject {
    private String scrapingDate;
    private boolean initData = false;

    public boolean updateCheck(long updateCycle) {
        boolean result = false;

        if(scrapingDate == null) {  // 널값 체크
            return result;
        }

        // 디바이스에서 현재시간을 받아온다.
        Date now = new Date(System.currentTimeMillis());

        // 날짜 형식을 설정
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // parse()를 통해 String형을 Date형으로 변환.
            Date past = dateFormat.parse(scrapingDate);
            long duration = Math.abs(now.getTime() - past.getTime());
            duration = duration / (24 * 60 * 60 * 1000);

            // 정해진 기간보다 업데이트를 안한 기간이 더 길면
            if(updateCycle < duration) {
                result = true;
            }
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    // Standard getters & setters generated
    public String getScrapingDate() {
        return scrapingDate;
    }

    public void setScrapingDate(long scrapingDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.scrapingDate = dateFormat.format(scrapingDate);
    }

    public boolean getInitData() {
        return initData;
    }

    public void setInitData(boolean initData) {
        this.initData = initData;
    }
}
