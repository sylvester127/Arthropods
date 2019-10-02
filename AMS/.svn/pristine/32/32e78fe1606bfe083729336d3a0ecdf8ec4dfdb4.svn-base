package com.sylvester.ams.funtion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.sylvester.ams.model.TarantulaInfo;
import com.sylvester.ams.realm.RealmController;
import com.sylvester.ams.ui.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<String, Void, ArrayList<TarantulaInfo>> {
    Context context;
    ProgressDialog asyncDialog;
    String address;
    ArrayList<TarantulaInfo> tempArrList;

    public MyAsyncTask(Context context) {
        this.context = context;
    }

    // doInBackground가 실행되기 이전에 수행할 동작들을 구현한다.
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // 다이얼로그를 띄운다.
        asyncDialog = new ProgressDialog(context);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("데이터를 받아오는 중 입니다...");
        asyncDialog.setCanceledOnTouchOutside(false);

        // show dialog
        asyncDialog.show();

        // 변수 초기화
        address = "https://www.tarantupedia.com/list";

        // realm에 넘겨줄 임시 리스트
        tempArrList = new ArrayList<>();
    }

    // background 스레드로 일처리를 한다.
    @Override
    protected ArrayList<TarantulaInfo> doInBackground(String... strings) {
        try {
            URL url = new URL(address); // URL화 한다.

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "euc-kr"));  // 문자열 셋 세팅

            String line;

            boolean lineCheckfir = false, lineCheckSec = false;
            boolean startSaveCheck = false;
            int saveCheck = 0;
            String tempS = "", tempD = "";

            while ((line = reader.readLine()) != null) {
                if(line.contains("Species List")) {
                    lineCheckfir = true;
                }
                else if(line.contains("</article")) {
                    lineCheckfir = false;
                }

                if(line.contains("<h4 class=\"uk-h5 uk-margin-remove\">")) { lineCheckSec = true; }
                else if(line.contains("</h4")) { lineCheckSec = false; }

                // 필요한 부분만 파싱한다.
                if(lineCheckfir && lineCheckSec) {
                    //System.out.println(line);
                    if(startSaveCheck)
                        saveCheck++;

                    // 학명 파싱
                    if(line.contains("<i> <a title=")) {
                        tempS = line.split(">")[2].split("<")[0];
                        tempS = tempS.trim();
                        startSaveCheck = true;
                        //System.out.println(temp);
                    }

                    // 서식지
                    if(line.contains("<span class=\"uk-article-meta uk-margin-left\">")) {
                        tempD = line.split(">")[1].split("<")[0];
                        tempD = tempD.trim();
                        //System.out.println(temp);
                    }

                    // 임시 리스트에 저장
                    if(saveCheck == 2)
                    {
                        TarantulaInfo tarantulaInfo = new TarantulaInfo();
                        tarantulaInfo.setScientific_name(tempS);
                        tarantulaInfo.setDistribution(tempD);
                        tempArrList.add(tarantulaInfo);
                        startSaveCheck = false;
                        saveCheck = 0;
                    }
                }
            }
            reader.close();
        }
        catch (Exception e) {
            System.out.print("Err: " + e.getMessage());
        }
        return tempArrList;
    }

    @Override
    protected void onPostExecute(ArrayList<TarantulaInfo> result) {
        super.onPostExecute(result);

        for(int i = 0; i<tempArrList.size(); i++)
        RealmController.getInstance().setTarantulaInfo(tempArrList.get(i));

        asyncDialog.dismiss();;
        Toast.makeText(context, "데이터 받아오기 완료", Toast.LENGTH_SHORT).show();

        double sec = 1.5;   // 지연 시간
        Handler hand = new Handler();   // Handler 생성

        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, List.class);  // 다음 화면으로 넘어갈 클래스 지정한다.
                context.startActivity(intent);  // 다음 액티비티로 이동한다.
            }
        }, (long)sec * 1000);   // 1초 뒤에 핸들러가 실행한다.
    }
}