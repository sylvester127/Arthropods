package com.sylvester.ams.controller.funtion;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sylvester.ams.controller.TitleContext;
import com.sylvester.ams.controller.service.realm.ArthropodInfoService;
import com.sylvester.ams.model.ArthropodInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class UpdateAsync extends AsyncTask<String, Void, ArrayList<ArthropodInfo>> {
    private ProgressDialog asyncDialog;
    private String address;
    private ArrayList<ArthropodInfo> tempList;

    // doInBackground가 실행되기 이전에 수행할 동작들을 구현한다.
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // ProgressDialog 를 띄운다.
        asyncDialog = new ProgressDialog(TitleContext.context);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("데이터를 받아오는 중 입니다...");
        asyncDialog.setCanceledOnTouchOutside(false);

        // show dialog
        asyncDialog.show();

        // 데이터를 받아올 url 주소 설정
        address = "https://www.tarantupedia.com/list";

        // 데이터를 받아올 임시 리스트
        tempList = new ArrayList<>();
    }

    // background 쓰레드로 일처리를 한다.
    @Override
    protected ArrayList<ArthropodInfo> doInBackground(String... strings) {
        try {
            URL url = new URL(address); // String 의 주소를 URL 화 한다.

            // 받아올 문자를 UTF-8 타입으로 받아온다
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

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

        SharedPreferences.Editor editor = TitleContext.preferences.edit();
        editor.putLong("lastCon",System.currentTimeMillis());
        editor.commit();

        asyncDialog.dismiss();
        Toast.makeText(TitleContext.context, "데이터 받아오기 완료", Toast.LENGTH_SHORT).show();

        TitleContext.activityHandler();
    }
}