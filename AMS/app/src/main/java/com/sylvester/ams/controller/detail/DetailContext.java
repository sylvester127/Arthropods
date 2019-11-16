package com.sylvester.ams.controller.detail;

public class DetailContext {
    private static DetailContext instence;
    public static int id;

    public static DetailContext getInstance() {
        if (instence == null)
            return new DetailContext();
        else
            return instence;
    }
}
