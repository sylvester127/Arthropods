package com.sylvester.ams.service;

import android.graphics.Bitmap;

import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.ScientificName;

import java.util.List;

public interface ArthropodService {
    List<Arthropod> getArthropods();
    ScientificName getScientificName(Arthropod arthropod);
    Bitmap getArthropodImg(Arthropod arthropod);
    Bitmap getArthropodImg(int id);
    void insertSample();
    Arthropod getArthropod(int id);
}
