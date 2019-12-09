package com.sylvester.ams.service;

import java.util.List;

public interface ArthropodInfoService {
    void insertArthropodInfo(final List<String> habitats, final List<String> scientificNameList);
    List<String> getGenus();
    List<String> getSpecies(String genus);
}
