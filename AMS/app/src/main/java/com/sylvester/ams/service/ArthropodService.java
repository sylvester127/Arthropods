package com.sylvester.ams.service;

import android.graphics.Bitmap;

import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;

import java.util.List;

public interface ArthropodService {
    List<Arthropod> getArthropods();
    ScientificName getScientificName(Arthropod arthropod);
    Bitmap getArthropodImg();
    Bitmap getArthropodImg(int id);
    Bitmap getArthropodImg(Arthropod arthropod);
    void insertSample();
    void insertArthropod();
    Arthropod getArthropod(int id);

}
