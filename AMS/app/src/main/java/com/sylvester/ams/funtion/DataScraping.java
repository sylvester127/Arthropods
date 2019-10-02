package com.sylvester.ams.funtion;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DataScraping {
    private String address = "https://www.tarantupedia.com/list";
    ArrayList<String> scientificName = new ArrayList<>();
    ArrayList<String> distribution = new ArrayList<>();

    public DataScraping(Context context) {
        try {
            URL url = new URL(address);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "euc-kr"));

            String line;
            int lineCheck = 0;

            while ((line = reader.readLine()) != null) {
                if(line.contains("<i> <a title=")) { lineCheck = 1; }
                else if(line.contains("</h4")) { lineCheck = 0; }

                // 필요한 부분만 파싱한다.
                if(lineCheck == 1) {
                    //System.out.println(line);

                    // 학명
                    if(line.contains("<i> <a title=")) {

                        String temp = line.split(">")[2].split("<")[0];
                        temp = temp.trim();
                        //System.out.println(temp);
                        scientificName.add(temp);
                    }

                    // 서식지
                    if(line.contains("<span class=\"uk-article-meta uk-margin-left\">")) {
                        String temp = line.split(">")[1].split("<")[0];
                        temp = temp.trim();
                        //System.out.println(temp);
                        distribution.add(temp);
                    }
                }
            }
            Toast.makeText(context,"데이터 스크레핑 성공", Toast.LENGTH_SHORT);
            reader.close();
        }
        catch (Exception e) {
            System.out.print("Err: " + e.getMessage());
        }
    }

    public ArrayList<String> getScientificName() { return scientificName; }

    public ArrayList<String> getDistribution() { return distribution; }
}
