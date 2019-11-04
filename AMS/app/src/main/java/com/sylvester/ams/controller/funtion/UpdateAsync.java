package com.sylvester.ams.controller.funtion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.sylvester.ams.controller.service.realm.ArthropodInfoService;
import com.sylvester.ams.model.ArthropodInfo;
import com.sylvester.ams.controller.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class UpdateAsync extends AsyncTask<String, Void, ArrayList<ArthropodInfo>> {
    private Context context;
    private ProgressDialog asyncDialog;
    private String address;
    private ArrayList<ArthropodInfo> tempList;

    public UpdateAsync(Context context) {
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
        tempList = new ArrayList<>();
    }

    // background 스레드로 일처리를 한다.
    @Override
    protected ArrayList<ArthropodInfo> doInBackground(String... strings) {
        try {
            URL url = new URL(address); // URL화 한다.

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));  // 문자열 셋 세팅

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
                        ArthropodInfo arthropodInfo = new ArthropodInfo();
                        arthropodInfo.setScientificName(tempS);
                        arthropodInfo.setDistribution(tempD);
                        tempList.add(arthropodInfo);
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
        return tempList;
    }

    @Override
    protected void onPostExecute(ArrayList<ArthropodInfo> result) {
        super.onPostExecute(result);

        ArthropodInfoService service = new ArthropodInfoService();
        service.setArthropodInfos(tempList);

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