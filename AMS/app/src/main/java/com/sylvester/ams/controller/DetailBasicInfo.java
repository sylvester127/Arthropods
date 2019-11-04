package com.sylvester.ams.controller;

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
import com.sylvester.ams.controller.service.realm.ArthropodInfoService;
import com.sylvester.ams.controller.service.realm.ArthropodService;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.ArthropodInfo;

import java.util.ArrayList;

public class DetailBasicInfo extends Fragment {
    private Arthropod arthropod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_basic_info, container, false);

        // 리스트에서 받아온 개체정보를 받아온다.
        ArthropodService service = new ArthropodService();
        arthropod = service.getArthropod(DetailContext.key);

        // DetailBasicInfo의 각 content에 모델을 바인드한다.
        bindModel(view);

        return view;
    }

    private void bindModel(View view) {
        // xml에 있는 gui와 모델을 바인드한다.
        EditText et_name = view.findViewById(R.id.et_name);                     // 개체이름
        EditText et_genus = view.findViewById(R.id.et_genus);                   // 종
        EditText et_species = view.findViewById(R.id.et_species);               // 속
        EditText et_sex = view.findViewById(R.id.et_sex);                       // 성별
        EditText et_behavior = view.findViewById(R.id.et_behavior);             // 사육 타입, 활동 방식
        EditText et_status = view.findViewById(R.id.et_status);                 // 개체 상태
        EditText et_receive_date = view.findViewById(R.id.et_receive_date);     // 입양, 브리딩 날짜
        EditText et_last_rehouse = view.findViewById(R.id.et_last_rehouse);     // 마지막 집갈이 날짜
        EditText et_life_stages = view.findViewById(R.id.et_life_stages);       // 탈피 횟수
        Button btn_molt_history = view.findViewById(R.id.btn_molt_history);     // 탈피 기록 추가
        EditText et_molt_history = view.findViewById(R.id.et_molt_history);     // 탈피 기록
        EditText et_memo = view.findViewById(R.id.et_memo);                     // 메모

        // 개체의 이름
        et_name.setText(arthropod.getName());

        // 개체의 종속
        final ArthropodInfoService service = new ArthropodInfoService();
        String scientificName = arthropod.getArthropodInfo().getScientificName();

        et_genus.setText(scientificName.split(" ")[0]);
        et_genus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Genus";
                    ArrayList<String> genus = new ArrayList<>();

                    for (ArthropodInfo info:service.getArthropodInfos())
                        genus.add(info.getScientificName());

                    showDialog(title, genus);
                }
                return false;
            }
        });

        et_species.setText(scientificName.split(" ")[1]);
        et_species.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Species";
                    ArrayList<String> species = new ArrayList<>();

                    showDialog(title, species);
                }
                return false;
            }
        });

        //
    }

    private void showDialog(String title, ArrayList<String> scientificName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // dialog view 설정
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_detail_alertdialog,null);
        builder.setView(view);

        // 리스트 추가버튼
        TextView textView = view.findViewById(R.id.txt_alertdialog_create_row);
        textView.setText("목록에 " + title + " 추가");

        // 리스트를 ArrayAdapter를 써서 채운다.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext()
                , R.layout.custom_detail_alertdialog_row, R.id.txt_alertdialog_item, scientificName);

        ListView listView = (ListView)view.findViewById(R.id.lv_alertdialog_list);
        listView.setAdapter(arrayAdapter);

        AlertDialog alertDialog = builder.create();

        builder.setTitle(title);

        alertDialog.show();
    }
}