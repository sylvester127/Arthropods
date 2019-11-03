package com.sylvester.ams.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.*;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.controller.funtion.MyAsyncTask;
import com.sylvester.ams.controller.service.realm.RealmContext;
import com.sylvester.ams.controller.service.realm.UserSerivece;
import com.sylvester.ams.model.User;

import java.util.Date;

public class Title extends AppCompatActivity {
    RealmContext realmContext;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // contentView activity_title 로 설정
        setContentView(R.layout.activity_title);

        // realm 을 초기화하고 RealmContext 인스턴스를 얻는다.
        RealmContext.initRealm(this);
        RealmContext.with(this);
        realmContext = RealmContext.getInstance();
//        Realm.deleteRealm(config);

        bindModel();

        tweenTextAlpha();
    }

    // User 정보가 없으면 초기화를 하고 있으면 갱신하는 함수
    private void bindModel() {
        UserSerivece userSerivece = new UserSerivece();
        User user = userSerivece.getUser();

        if (user != null) {    // User에 정보가 있으면 업데이트 체크를 한다.
            boolean updateData = user.updateCheck(60);

            if (updateData) {
                popupAlertDialog();

                user.setUpdateDate(new Date());
                userSerivece.setUser(user);
            }
        } else {  // User에 정보가 없으면 초기화를 한 후 업데이트를 한다.
            userSerivece.setUser(user);

            popupAlertDialog();    // 다이얼로그를 띄운다.
            user.setInitData(true);
            realmContext.setUser(user);
        }
//        activityHandler();
    }

    // TextView의 알파값을 조절하여 깜박이게하는 함수
    private void tweenTextAlpha() {
        TextView tv_wait = (TextView) findViewById(R.id.tv_wait);

        // 애니메이션을 이용하여 알파값을 조절한다.
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        tv_wait.startAnimation(animation);
    }

    // 다이얼로그를 띄우는 함수
    private void popupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message, button;

        if (realmContext.getUser().getInitData()) {  // 앱을 처음 실행하지 않았을 때
            message = "최신 버전이 등록되었습니다.\n" +
                    "지금 최신 버전으로 업데이트 하시겠습니까\n?" +
                    "\nWi-Fi가 아닐 경우 데이터 요금이 발생할 수 있습니다.";
            button = "업데이트 하기";
        } else {  // 앱을 처음 실행했을 때
            message = "추가 데이터를 받아옵니다.\n" +
                    "\nWi-Fi가 아닐 경우 데이터 요금이 발생할 수 있습니다.";
            button = "확인";
        }

        builder.setMessage(message);
        builder.setPositiveButton(button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 데이터를 관리하는 일이 끝나기 전에 진행상황을 보여준다.
                        MyAsyncTask task = new MyAsyncTask(context);
                        task.execute();
                    }
                });
        builder.setNegativeButton("나중에",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (realmContext.getUser().getInitData()) {  // 처음 실행이 아닌경우 기존 데이터만으로 실행
                            activityHandler();
                        } else {
                            finish();
                        }
                    }
                });

        AlertDialog alertDialog = builder.show();
        TextView messTextView = (TextView) alertDialog.findViewById(android.R.id.message);
        messTextView.setTextSize(14);   // 메세지 텍스트 크기를 조절한다.
        alertDialog.setCanceledOnTouchOutside(false);   // alertDialog 외 화면을 터치해도 취소버튼을 누르지않게 한다.

        alertDialog.show();
    }

    private void activityHandler() {
        double sec = 1.5;   // 지연 시간
        Handler hand = new Handler();   // Handler 생성

        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, List.class);  // 다음 화면으로 넘어갈 클래스 지정한다.
                context.startActivity(intent);  // 다음 액티비티로 이동한다.
            }
        }, (long) sec * 1000);   // 1초 뒤에 핸들러가 실행한다.
    }
}