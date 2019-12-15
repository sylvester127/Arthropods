package com.sylvester.ams.controller.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sylvester.ams.R;
import com.sylvester.ams.entity.ScientificName;
import com.sylvester.ams.service.DetailService;
import com.sylvester.ams.service.realm.RealmDetailService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailHabitatInfo extends Fragment {
    @BindView(R.id.et_temperature_low) EditText etTemperatureLow;
    @BindView(R.id.et_temperature_high) EditText etTemperatureHigh;
    @BindView(R.id.et_humidity_low) EditText etHumidityLow;
    @BindView(R.id.et_humidity_high) EditText etHumidityHigh;

    private DetailService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_habitat_info, container, false);
        ButterKnife.bind(this, view);

        //        ====================================================================
        service = new RealmDetailService();

        bindModel();

        return view;
    }

    private void bindModel() {
        ScientificName scientificName = service.getScientificName(DetailContext.arthropod.getId());
        String temperature = scientificName.getHabitats().first().getTemperature();
        String humidity = scientificName.getHabitats().first().getHumidity();

        etTemperatureLow.setText(temperature.split("-")[0]);
        etTemperatureHigh.setText(temperature.split("-")[1]);
        etHumidityLow.setText(humidity.split("-")[0]);
        etHumidityHigh.setText(humidity.split("-")[1]);
    }
}
