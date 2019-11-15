package com.sylvester.ams.controller.title;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.sylvester.ams.controller.list.ArthropodList;

public class TitleContext {
    public static Context context;
    public static SharedPreferences preferences;
    public static long lastCon;
    public static final int FIRST_CON = -1;

    public static void activityHandler() {
        double sec = 1.5;   // 지연 시간
        Handler hand = new Handler();   // Handler 생성

        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 다음 화면으로 넘어갈 클래스 지정한다.
                Intent intent = new Intent(context, ArthropodList.class);
                // 다음 액티비티로 이동한다.
                context.startActivity(intent);
            }
        }, (long) sec * 1000);   // 1.5초 뒤에 Handler 가 실행한다.
    }

    public static boolean getEqual(){
        if (lastCon == FIRST_CON)
            return true;
        return false;
    }
}
