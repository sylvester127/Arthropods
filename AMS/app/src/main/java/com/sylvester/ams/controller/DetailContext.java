package com.sylvester.ams.controller;

public class DetailContext {
    private static DetailContext instence;
    public static String key;

    public static DetailContext getInstance() {
        if (instence == null)
            return new DetailContext();
        else
            return instence;
    }
}
