package com.sylvester.ams.controller.detail;

import android.content.Context;

import com.sylvester.ams.entity.Arthropod;

public class DetailContext {
    public static Context context;
    public static Arthropod arthropod;
    private static String genus;
    private static String species;

    public static String getGenus() {
        return genus;
    }

    public static void setGenus(String genus) {
        DetailContext.genus = genus;
    }

    public static void setGenus(Arthropod arthropod) {
        if (arthropod.getScientificName() == null)
            DetailContext.genus = "";
        else
            DetailContext.genus = arthropod.getScientificName().first().getGenus();
    }

    public static String getSpecies() {
        return species;
    }

    public static void setSpecies(String species) {
        DetailContext.species = species;
    }

    public static void setSpecies(Arthropod arthropod) {
        if (arthropod.getScientificName() == null)
            DetailContext.species = "";
        else
            DetailContext.species = arthropod.getScientificName().first().getSpecies();
    }
}
