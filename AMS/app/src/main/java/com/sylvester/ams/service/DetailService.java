package com.sylvester.ams.service;

import android.graphics.Bitmap;

import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;

import java.util.List;

public interface DetailService {
    Arthropod getArthropod(int id);
    Bitmap getArthropodImage(String path);
    List<String> getGenus();
    List<String> getSpecies(String genus);
    ScientificName getScientificName(int arthropodId);
    void insertArthropod(Arthropod arthropod, String genus, String species);
    void updateArthropod(int id);
    void deleteArthropod(int id);
}
