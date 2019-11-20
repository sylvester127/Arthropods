package com.sylvester.ams.controller.detail;

import android.content.Context;

import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.service.realm.RealmArthropodInfoService;
import com.sylvester.ams.service.realm.RealmArthropodService;

public class DetailContext {
    private static DetailContext instance;
    private static RealmArthropodInfoService infoService;
    private static RealmArthropodService service;
    public static Arthropod arthropod;
    public static String scientificName;
    public static Context context;

    public static DetailContext getInstance() {
        if (instance == null)
            instance = new DetailContext();
        return instance;
    }

    public static RealmArthropodInfoService getInfoService() {
        if (infoService == null)
            infoService = new RealmArthropodInfoService();
        return infoService;
    }

    public static RealmArthropodService getService() {
        if (service == null)
            service = new RealmArthropodService();
        return service;
    }
}
