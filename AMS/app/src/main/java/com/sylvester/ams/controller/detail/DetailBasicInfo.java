package com.sylvester.ams.controller.detail;

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
import com.sylvester.ams.model.ScientificName;
import com.sylvester.ams.service.realm.RealmArthropodInfoService;
import com.sylvester.ams.service.realm.RealmArthropodService;
import com.sylvester.ams.model.Arthropod;

import java.text.DateFormat;
import java.util.List;

public class DetailBasicInfo extends Fragment {
    private RealmArthropodInfoService infoService;
    private RealmArthropodService service;
    private Arthropod arthropod;
    private ScientificName scientificName;
    private String genus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_basic_info, container, false);

        // 리스트에서 받아온 개체정보를 받아온다.
        infoService = DetailContext.getInstance().getInfoService();
        service = DetailContext.getInstance().getService();
        arthropod = service.getArthropod(DetailContext.id);

        // DetailBasicInfo 의 각 content 에 모델을 바인드한다.
        if (arthropod != null) {
            scientificName = service.getScientificName(arthropod);
            genus = scientificName.getGenus();
        } else {
            arthropod = new Arthropod();
            genus = "";
        }

        bindModel(view);

        addListener(view);

        return view;
    }

    private void bindModel(View view) {
        // xml에 있는 gui와 모델을 바인드한다.
        EditText et_name = view.findViewById(R.id.et_name);                                 // 개체이름
        EditText et_genus = view.findViewById(R.id.et_genus);                               // 종
        EditText et_species = view.findViewById(R.id.et_species);                           // 속
        EditText et_sex = view.findViewById(R.id.et_sex);                                   // 성별
        EditText et_habit = view.findViewById(R.id.et_habit);                               // 사육 타입, 활동 방식
        EditText et_status = view.findViewById(R.id.et_status);                             // 개체 상태
        EditText et_receiveDate = view.findViewById(R.id.et_receiveDate);                   // 입양, 브리딩 날짜
        EditText et_lastRehousingDate = view.findViewById(R.id.et_lastRehousingDate);       // 마지막 집갈이 날짜
        EditText et_moltCount = view.findViewById(R.id.et_moltCount);                       // 탈피 횟수
        EditText et_moltHistory = view.findViewById(R.id.et_moltHistory);                   // 탈피 기록
        EditText et_memo = view.findViewById(R.id.et_memo);                                 // 메모

        // 개체의 이름
        et_name.setText(arthropod.getName());

        if (scientificName != null) {
            // 개체의 종
            et_genus.setText(scientificName.getGenus());
            // 개체의 속
            et_species.setText(scientificName.getSpecies());
        } else {
            et_genus.setText("");
            et_species.setText("");
        }

        // 개체의 성별
        et_sex.setText(arthropod.getSex());
        // 개체의 사육 타입, 활동 방식
        et_habit.setText(arthropod.getHabit());
        // 개체 상태
        et_status.setText(arthropod.getStatus());
        // 입양, 브리딩 날짜
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
        et_receiveDate.setText(format.format(arthropod.getReceiveDate()));
        // 마지막 집갈이 날짜
        et_lastRehousingDate.setText(format.format(arthropod.getLastRehousingDate()));
        // 탈피 횟수
        et_moltCount.setText(arthropod.getMoltCount());
        // 탈피 기록
        et_moltHistory.setText(arthropod.getMoltHistory());
        // 메모
        et_memo.setText(arthropod.getMemo());
    }

    private void addListener(View view) {
        EditText et_genus = view.findViewById(R.id.et_genus);       // 종
        EditText et_species = view.findViewById(R.id.et_species);   // 속
        Button btn_molt_history = view.findViewById(R.id.btn_molt_history);     // 탈피 기록 추가


        et_genus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Genus";

                    showDialog(title, infoService.getGenus());
                }
                return false;
            }
        });

        et_species.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Species";

                    showDialog(title, infoService.getSpecies(genus));
                }
                return false;
            }
        });
    }

    private void showDialog(String title, List<String> scientificName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // dialog view 설정
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_detail_alertdialog, null);
        builder.setView(view);

        // 리스트 추가버튼
        TextView textView = view.findViewById(R.id.txt_alertdialog_create_row);
        textView.setText("목록에 " + title + " 추가");

        // 리스트를 ArrayAdapter를 써서 채운다.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext()
                , R.layout.custom_detail_alertdialog_row, R.id.txt_alertdialog_item, scientificName);

        ListView listView = (ListView) view.findViewById(R.id.lv_alertdialog_list);
        listView.setAdapter(arrayAdapter);

        AlertDialog alertDialog = builder.create();

        builder.setTitle(title);

        alertDialog.show();
    }
}