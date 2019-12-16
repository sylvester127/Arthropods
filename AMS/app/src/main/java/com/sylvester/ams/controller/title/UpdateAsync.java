package com.sylvester.ams.controller.title;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.sylvester.ams.service.TitleService;
import com.sylvester.ams.service.realm.RealmTitleService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UpdateAsync extends AsyncTask<Void, Void, Boolean> {
    private ProgressDialog updateDialog;
    private String addressURL;
    private List<String> scientificNames;
    private List<String> habitats;

    // doInBackground 가 실행되기 이전에 수행할 동작들을 구현한다.
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // ProgressDialog 를 띄운다.
        updateDialog = new ProgressDialog(TitleContext.context);
        updateDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        updateDialog.setMessage("데이터를 받아오는 중 입니다...");
        updateDialog.setCanceledOnTouchOutside(false);

        // show dialog
        updateDialog.show();

        // 데이터를 받아올 url 주소 설정
        addressURL = "https://wsc.nmbe.ch/family/100/Theraphosidae";

        // 데이터를 받아올 임시 리스트
        scientificNames = new ArrayList<>();
        habitats = new ArrayList<>();
    }

    // background 쓰레드로 일처리를 한다.
    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean updateComplete = true;

        try {
            Document doc = Jsoup.connect(addressURL).maxBodySize(0).timeout(15 * 1000).get();

            // article 부분만 뽑아온다.
            Elements elem = doc.select("div.speciesTitle");
            Iterator<Element> iterator1 = elem.select("strong i").iterator();
            Iterator<Element> iterator2 = elem.select("span").iterator();

            while (iterator1.hasNext()) {
                String scientificName = iterator1.next().text();
                String habitat = iterator2.next().text();
                habitat = habitat.replaceAll("\\|\\s(\\?|j)?\\s?\\|", "").split("\\[")[0];
                scientificNames.add(scientificName);
                habitats.add(habitat);
            }

        } catch (Exception e) {
            updateComplete = false;
        }
        return updateComplete;
    }

    @Override
    protected void onPostExecute(Boolean updateComplete) {
        super.onPostExecute(updateComplete);

        TitleService service= new RealmTitleService();
        service.insertArthropodInfo(habitats, scientificNames);

        updateDialog.dismiss();

        if (updateComplete)
            Toast.makeText(TitleContext.context, "데이터 받아오기 완료", Toast.LENGTH_SHORT).show();
        else {
            if (TitleContext.getEqual()) {
                Toast.makeText(TitleContext.context, "데이터 받아오기 실패. 잠시후 재시도 해주세요", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);

                    }
                }, 1500);
            }
            Toast.makeText(TitleContext.context, "데이터 받아오기 실패", Toast.LENGTH_SHORT).show();
        }

        TitleContext.activityHandler();
    }
}