package com.sylvester.ams.controller.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (DetailContext.editData != 0)
                DetailContext.editData = 2;
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

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

        tvLastFeedDate.addTextChangedListener(textWatcher);
        tvHungry.addTextChangedListener(textWatcher);
        cbPostponeFeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (DetailContext.editData != 0)
                    DetailContext.editData = 2;
            }
        });
        etFeedingCycle.addTextChangedListener(textWatcher);

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

        cbPostponeFeed.setChecked(DetailContext.arthropod.getPostponeFeed());
        etFeedingCycle.setText(String.valueOf(DetailContext.arthropod.getFeedingCycle()));
    }
}
