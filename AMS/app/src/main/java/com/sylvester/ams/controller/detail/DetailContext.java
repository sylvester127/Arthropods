package com.sylvester.ams.controller.detail;

import com.sylvester.ams.service.realm.RealmArthropodInfoService;
import com.sylvester.ams.service.realm.RealmArthropodService;

public class DetailContext {
    private static DetailContext instance;
    public static int id;
    public static String ScientificName;
    private static RealmArthropodInfoService infoService;
    private static RealmArthropodService service;

    public static DetailContext getInstance() {
        if (instance == null)
            instance = new DetailContext();
        return instance;
    }

    public RealmArthropodInfoService getInfoService() {
        if (infoService == null)
            infoService = new RealmArthropodInfoService();
        return infoService;
    }

    public RealmArthropodService getService() {
        if (service == null)
            service = new RealmArthropodService();
        return service;
    }
}
