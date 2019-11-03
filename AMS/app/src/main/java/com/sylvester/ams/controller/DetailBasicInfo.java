package com.sylvester.ams.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.controller.service.realm.RealmContext;
import com.sylvester.ams.model.TarantulaInfo;
import com.sylvester.ams.model.TarantulaObject;

import java.util.ArrayList;

public class DetailBasicInfo extends Fragment {
    private RealmContext realmContext = RealmContext.getInstance();
    private TarantulaObject tarantulaObject;
    private ArrayList<TarantulaInfo> tarantulaInfoList;
    private ArrayList<String> genus;
    private ArrayList<String> species;

    public DetailBasicInfo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_basic_info, container, false);

        // tarantulaObj를 List에서 레퍼런스를 받아온다.
        setTarantulaObj();

        // tarantulaObj의 데이터를 Detail의 각 content에 바인드한다.
        bindTarantulaObj(view);

        return view;
    }

    private void setTarantulaObj() {
        // Intent 값 받기
        Intent intent = getActivity().getIntent();
        String key = intent.getStringExtra("tarantulaObj");

        // List에서 선택한 TarantulaObject Realm에서 받아오기.
        tarantulaObject = realmContext.getTarantulaObject(key);

        // Dialog에서 보여줄 listview에 들어갈 데이터들
        tarantulaInfoList = new ArrayList<>();
        tarantulaInfoList = realmContext.getTarantulaInfos();
        genus = new ArrayList<>();
        species = new ArrayList<>();
    }

    private void bindTarantulaObj(View view) {
        // xml에 있는 gui와 바인드한다.
        EditText et_name = view.findViewById(R.id.et_name);                     // 개체이름
        TextView tv_genus = view.findViewById(R.id.et_genus);                   // 종
        TextView tv_species = view.findViewById(R.id.et_species);               // 속
        EditText et_sex = view.findViewById(R.id.et_sex);                       // 성별
        EditText et_behavior = view.findViewById(R.id.et_behavior);             // 사육 타입, 활동 방식
        EditText et_status = view.findViewById(R.id.et_status);                 // 개체 상태
        EditText et_receive_date = view.findViewById(R.id.et_receive_date);     // 입양, 브리딩 날짜
        EditText et_last_rehouse = view.findViewById(R.id.et_last_rehouse);     // 마지막 집갈이 날짜
        EditText et_life_stages = view.findViewById(R.id.et_life_stages);       // 탈피 횟수
        Button btn_molt_history = view.findViewById(R.id.btn_molt_history);     // 탈피 기록 추가
        EditText et_molt_history = view.findViewById(R.id.et_molt_history);     // 탈피 기록
        EditText et_memo = view.findViewById(R.id.et_memo);                     // 메모

        // realm에 저장되어있는 상태와 동기화한다.
        et_name.setText(tarantulaObject.getName());

        ArrayList<String> scientific_name = new ArrayList<String>();

        // tarantulaInfoList에서 genus 중복제거
        int j = 1;
        for (int i = 1; i < tarantulaInfoList.size(); i++) {
            String temp = tarantulaInfoList.get(i - 1).getScientificName();
            temp = temp.split(" ")[0];

            if (!temp.equals(genus.get(j - 1))) {
                genus.add(temp);
                j++;
            }
        }

        tv_genus.setText(tarantulaObject.getTarantulaInfo().getScientificName().split(" ")[0]);
        tv_genus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Genus";

//                    showDialog(title, scientific_name);
                }
                return false;
            }
        });

        tv_species.setText(tarantulaObject.getTarantulaInfo().getScientificName().split(" ")[1]);
        tv_species.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Species";
//                    showDialog(title, scientific_name);
                }
                return false;
            }
        });
    }

    private void showDialog(String title, ArrayList<String> scientific_name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_detail_alertdialog,null);
        builder.setView(view);

        TextView textView = view.findViewById(R.id.txt_alertdialog_create_row);
        textView.setText("목록에 " + title + " 추가");

        ListView listView = (ListView)view.findViewById(R.id.lv_alertdialog_list);
        AlertDialog alertDialog = builder.create();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_detail_alertdialog_row, scientific_name);
        listView.setAdapter(arrayAdapter);

        builder.setTitle(title);

        alertDialog.show();
    }
}