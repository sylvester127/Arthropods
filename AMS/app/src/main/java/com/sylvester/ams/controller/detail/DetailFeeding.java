package com.sylvester.ams.controller.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.service.DetailService;
import com.sylvester.ams.service.realm.RealmDetailService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFeeding extends Fragment {
    @BindView(R.id.tv_lastFeedDate) TextView tvLastFeedDate;   // 마지막 피딩일
    @BindView(R.id.tv_hungry) TextView tvHungry;               // 굶은 기간
    @BindView(R.id.cb_postponeFeed) CheckBox cbPostponeFeed;   // 피딩을 중지
    @BindView(R.id.et_feedingCycle) EditText etFeedingCycle;   // 피딩주기
    @BindView(R.id.ib_feed) Button ibFeed;                     // 피딩 버튼

    private DetailService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_feeding, container, false);
        ButterKnife.bind(this, view);

        //        ====================================================================
        service = new RealmDetailService();

        bindModel();

        Detail activity = (Detail) getActivity();
        activity.setFeedListener(new DetailMenuListener() {
            @Override
            public void onClickSave(Arthropod arthropod) {
                DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
                try {
                    arthropod.setLastFeedDate(format.parse(tvLastFeedDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                arthropod.setPostponeFeed(cbPostponeFeed.isChecked());
                arthropod.setFeedingCycle(Integer.parseInt(etFeedingCycle.getText().toString()));
            }
        });

        return view;
    }

    private void bindModel() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);

        if (DetailContext.arthropod.getLastFeedDate() != null) {
            tvLastFeedDate.setText(format.format(DetailContext.arthropod.getLastFeedDate()));

            long days = new Date().getTime() - DetailContext.arthropod.getLastFeedDate().getTime();
            days /= (24 * 60 * 60 * 1000);
            tvHungry.setText(String.valueOf(days));
        }

        cbPostponeFeed.setChecked(DetailContext.arthropod.isPostponeFeed());
        etFeedingCycle.setText(String.valueOf(DetailContext.arthropod.getFeedingCycle()));
    }
}
