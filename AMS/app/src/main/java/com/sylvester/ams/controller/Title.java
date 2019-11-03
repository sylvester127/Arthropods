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
import com.sylvester.ams.controller.service.realm.UserService;
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

        tweenTextAlpha();

        bindModel();
    }

    // User 정보가 없으면 초기화를 하고 있으면 갱신하는 함수
    private void bindModel() {
        UserService service = new UserService();
        User user = service.getUser();

        if (user == null) {    // 설치한 후 실행을 한 번도 안했다면
            user = new User(new Date(), true);

            popupUpdateDialog(user);    // 업데이트 다이얼로그를 띄운다.

            user.setFirstOn(false);
            service.setUser(user);
        } else {
            // 한 달에 한번 절지류 데이터를 받아온다.
            if (service.checkUpdate(30)) {
                user.setUpdateDate(new Date());
                service.setUser(user);

                popupUpdateDialog(user);
            } else
                activityHandler();
        }
    }

    // 다이얼로그를 띄우는 함수
    private void popupUpdateDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message, button;
        final boolean firstOn = user.isFirstOn();
        if (firstOn) {  // 앱을 처음 실행 했을 때
            message = "추가 데이터를 받아옵니다.\n" +
                    "\nWi-Fi가 아닐 경우 데이터 요금이 발생할 수 있습니다.";
            button = "확인";
        } else {  // 앱을 2번 이상 실행 했을 때
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
                        MyAsyncTask task = new MyAsyncTask(context);
                        task.execute();
                    }
                });
        builder.setNegativeButton("나중에",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (firstOn) {  // 처음 실행인 경우 앱 종료
                            finish();
                        } else {    // 처음 실행이 아닌경우 기존 데이터만으로 실행
                            activityHandler();
                        }
                    }
                });

        AlertDialog alertDialog = builder.show();
        TextView messTextView = (TextView) alertDialog.findViewById(android.R.id.message);
        messTextView.setTextSize(14);   // 메세지 텍스트 크기를 조절한다.
        alertDialog.setCanceledOnTouchOutside(false);   // alertDialog 외 화면을 터치해도 취소버튼을 누르지않게 한다.

        alertDialog.show();
    }

    // TextView의 알파값을 조절하여 깜박이게하는 함수
    private void tweenTextAlpha() {
        TextView tv_wait = (TextView) findViewById(R.id.tv_wait);

        // 애니메이션을 이용하여 알파값을 조절한다.
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        tv_wait.startAnimation(animation);
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