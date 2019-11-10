package com.sylvester.ams.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.*;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.controller.service.realm.RealmContext;

import java.util.Date;

public class Title extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // contentView activity_title 로 설정
        setContentView(R.layout.activity_title);

        TitleContext.context = this;

        // realm 을 초기화하고 RealmContext 개체를 생성한다.
        RealmContext.initRealm(this);
        RealmContext.with(this.getApplication());

        tweenTextAlpha();

        // SharedPreferences 선언
        TitleContext.preferences = getSharedPreferences("pref", MODE_PRIVATE);

        // 1번째 인자는 키, 2번째는 기본값
        // lastCon 라는 키값으로 값을 받아온다.
        TitleContext.lastCon = TitleContext.preferences.getLong("lastCon", TitleContext.FIRST_CON);


        // 처음 실행했거나 업데이트 주기가 지났으면 update 다이얼로그를 띄워준다.
        if (checkUpdate(30))
            popupUpdateDialog();
        else
            TitleContext.activityHandler();
    }

    private void tweenTextAlpha() {
        TextView tv_wait = (TextView) findViewById(R.id.tv_wait);

        // 애니메이션을 이용하여 알파값을 조절한다.
        Animation animation = AnimationUtils.loadAnimation(TitleContext.context, R.anim.alpha);
        tv_wait.startAnimation(animation);
    }

    public boolean checkUpdate(long updateCycle) {
        boolean result = false;

        // 처음 실행했으면 true 를 리턴한다.
        if (TitleContext.getEqual())
            result = true;
        else {
            // 현재시간을 받아온다.
            Date now = new Date(System.currentTimeMillis());

            // 현재와 과거 시간 차이를 구하고 일수로 변환한다.
            long duration = Math.abs(now.getTime() - TitleContext.lastCon);
            duration = duration / (24 * 60 * 60 * 1000);

            // 업데이트 주기가 지났으면 true 를 리턴한다.
            if (updateCycle < duration) {
                result = true;
            }
        }
        return result;
    }

    // 다이얼로그를 띄우는 함수
    private void popupUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TitleContext.context);
        String message;
        String button;

        // 처음 실행 했을 때와 업데이트 주기마다 다른 메시지와 버튼의 문구를 띄워준다.
        if (TitleContext.getEqual()) {
            message = "추가 데이터를 받아옵니다.\n" +
                    "\nWi-Fi가 아닐 경우 데이터 요금이 발생할 수 있습니다.";
            button = "확인";
        } else {
            message = "최신 버전이 등록되었습니다.\n" +
                    "지금 최신 버전으로 업데이트 하시겠습니까\n?" +
                    "\nWi-Fi가 아닐 경우 데이터 요금이 발생할 수 있습니다.";
            button = "업데이트 하기";
        }

        builder.setMessage(message);
        builder.setPositiveButton(button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 데이터를 관리하는 일이 끝나기 전에 진행상황을 보여준다.
                        UpdateAsync task = new UpdateAsync();
                        task.execute();
                    }
                });
        builder.setNegativeButton("나중에",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (TitleContext.getEqual()) {  // 처음 실행인 경우 앱 종료
                            finish();
                        } else {    // 처음 실행이 아닌경우 기존 데이터만으로 실행
//                            activityHandler();
                            TitleContext.activityHandler();
                        }
                    }
                });

        AlertDialog alertDialog = builder.show();

        alertDialog.setCanceledOnTouchOutside(false);   // alertDialog 외 화면을 터치해도 취소버튼을 누르지않게 한다.
        TextView messTextView = (TextView) alertDialog.findViewById(android.R.id.message);
        messTextView.setTextSize(14);   // 메세지의 텍스트 크기를 조절한다.

        alertDialog.show();
    }
}
