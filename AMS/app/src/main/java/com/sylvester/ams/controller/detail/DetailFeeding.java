package com.sylvester.ams.controller.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.ScientificName;
import com.sylvester.ams.service.realm.RealmArthropodInfoService;
import com.sylvester.ams.service.realm.RealmArthropodService;

import java.text.DateFormat;
import java.util.Date;

public class DetailFeeding extends Fragment {
    private RealmArthropodInfoService infoService;
    private RealmArthropodService service;
    private Arthropod arthropod;
    private ScientificName scientificName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_feeding, container, false);

        // 리스트에서 받아온 개체정보를 받아온다.
        infoService = DetailContext.getInfoService();
        service = DetailContext.getService();
        arthropod = DetailContext.arthropod;

        // DetailBasicInfo의 각 content에 모델을 바인드한다.
        if (arthropod.getScientificName() != null) {
            scientificName = service.getScientificName(arthropod);

            bindModel(view);
        }

        Detail activity = (Detail) getActivity();
        activity.setFeedListener(new DetailMenuListener() {
            @Override
            public void onClickSave(Arthropod arthropod) {
                Log.d("debug", "feeding");
            }
        });

        return view;
    }

    private void bindModel(View view) {
        TextView tv_lastFeedDate = view.findViewById(R.id.tv_lastFeedDate);              // 마지막 피딩일
        TextView tv_hungry = view.findViewById(R.id.tv_hungry);                          // 굶은 기간
        Button ib_feed = view.findViewById(R.id.ib_feed);                                  // 피딩 버튼
        CheckBox cb_postponeFeed = view.findViewById(R.id.cb_postponeFeed);              // 피딩을 중지
        EditText et_feedingCycle = view.findViewById(R.id.et_feedingCycle);              // 피딩주기

        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);

        if (arthropod.getLastFeedDate() != null) {
            tv_lastFeedDate.setText(format.format(arthropod.getLastFeedDate()));

            long days = new Date().getTime() - arthropod.getLastFeedDate().getTime();
            days /= (24 * 60 * 60 * 1000);
            tv_hungry.setText(String.valueOf(days));
        }

        cb_postponeFeed.setChecked(arthropod.isPostponeFeed());

        et_feedingCycle.setText(String.valueOf(arthropod.getFeedingCycle()));

        arthropod.getMoltCount();
//        et_afterMoltPostponeFeed.setText();
    }
}
