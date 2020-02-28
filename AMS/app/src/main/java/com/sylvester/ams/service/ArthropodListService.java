package com.sylvester.ams.service;

import android.graphics.Bitmap;

import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;

import java.util.List;

public interface ArthropodListService {
    List<Arthropod> getArthropods();
    Bitmap getArthropodImg(String path);
    ScientificName getScientificName(int arthropodId);
    void insertSample();
}
