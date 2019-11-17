package com.sylvester.ams.controller.detail;

import com.sylvester.ams.service.realm.RealmArthropodInfoService;
import com.sylvester.ams.service.realm.RealmArthropodService;

public class DetailContext {
    public static int id;
    private static RealmArthropodInfoService infoService;
    private static RealmArthropodService service;

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
