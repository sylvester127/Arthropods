package com.sylvester.ams.service;

import android.graphics.Bitmap;

import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;

import java.util.List;

public interface ArthropodListService {
    List<Arthropod> getArthropods();
    ScientificName getScientificName(Arthropod arthropod);
    Bitmap getArthropodImg(Arthropod arthropod);
    void insertSample();
    Arthropod getArthropod(int id);

}
