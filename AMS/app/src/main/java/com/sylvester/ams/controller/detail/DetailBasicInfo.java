package com.sylvester.ams.controller.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.service.realm.RealmDetailService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBasicInfo extends Fragment {
    @BindView(R.id.et_name) EditText etName;                               // 개체이름
    @BindView(R.id.et_genus) EditText etGenus;                             // 종
    @BindView(R.id.et_species) EditText etSpecies;                         // 속
    @BindView(R.id.et_gender) EditText etGender;                           // 성별
    @BindView(R.id.et_habit) EditText etHabit;                             // 사육 타입, 활동 방식
    @BindView(R.id.et_status) EditText etStatus;                           // 개체 상태
    @BindView(R.id.et_receiveDate) EditText etReceiveDate;                 // 입양, 브리딩 날짜
    @BindView(R.id.et_lastRehousingDate) EditText etLastRehousingDate;     // 마지막 집갈이 날짜
    @BindView(R.id.et_moltCount) EditText etMoltCount;                     // 탈피 횟수
    @BindView(R.id.et_moltHistory) EditText etMoltHistory;                 // 탈피 기록
    @BindView(R.id.et_memo) EditText etMemo;                               // 메모
    @BindView(R.id.btn_molt_history) Button btnMoltHistory;                // 탈피 기록 추가

    private RealmDetailService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_basic_info, container, false);
        ButterKnife.bind(this, view);

        //        ====================================================================
        // 리스트에서 받아온 개체정보를 받아온다.
        service = new RealmDetailService();

        // DetailBasicInfo 의 각 content 에 모델을 바인드한다.
        bindModel();

        addListener(view);

        return view;
    }

    private void bindModel() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);

        etName.setText(DetailContext.arthropod.getName());
        etGenus.setText(DetailContext.getGenus());
        etSpecies.setText(DetailContext.getSpecies());
        etGender.setText(DetailContext.arthropod.getGender());
        etHabit.setText(DetailContext.arthropod.getHabit());
        etStatus.setText(DetailContext.arthropod.getStatus());
        etReceiveDate.setText(format.format(DetailContext.arthropod.getReceiveDate()));
        etLastRehousingDate.setText(format.format(DetailContext.arthropod.getLastRehousingDate()));
        etMoltCount.setText(DetailContext.arthropod.getMoltCount());
        etMoltHistory.setText(DetailContext.arthropod.getMoltHistory());
        etMemo.setText(DetailContext.arthropod.getMemo());
    }

    private void addListener(View view) {
        Detail activity = (Detail) getActivity();
        activity.setBasicListener(new DetailMenuListener() {
            @Override
            public void onClickSave(Arthropod arthropod) {
                arthropod.setName(etName.getText().toString());
                DetailContext.setGenus(etGenus.getText().toString());
                DetailContext.setSpecies(etSpecies.getText().toString());
                arthropod.setGender(etGender.getText().toString());
                arthropod.setHabit(etHabit.getText().toString());
                arthropod.setStatus(etStatus.getText().toString());
                DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
                try {
                    arthropod.setReceiveDate(format.parse(etReceiveDate.getText().toString()));
                    arthropod.setLastRehousingDate(format.parse(etLastRehousingDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                arthropod.setMoltCount(etMoltCount.getText().toString());
                arthropod.setMoltHistory(etMoltHistory.getText().toString());
                arthropod.setMemo(etMemo.getText().toString());
            }
        });

        etGenus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Genus";

                    showDialog(title, service.getGenus());
                }
                return false;
            }
        });

        etSpecies.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String title = "Species";

                    if (DetailContext.getGenus().equals(""))
                        return false;

                    showDialog(title, service.getSpecies(DetailContext.getGenus()));
                }
                return false;
            }
        });
    }

    private void showDialog(final String title, final List<String> scientificName) {
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

        final AlertDialog alertDialog = builder.create();

        builder.setTitle(title);

        alertDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (title.equals("Genus")) {
                    etGenus.setText(scientificName.get(i));
                    DetailContext.setGenus(etGenus.getText().toString());
                    etSpecies.setText("");
                } else {
                    etSpecies.setText(scientificName.get(i));
                    DetailContext.setSpecies(etSpecies.getText().toString());
                }
                alertDialog.dismiss();
            }
        });
    }
}