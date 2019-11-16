package com.sylvester.ams.service.realm;

import android.content.SharedPreferences;

import com.sylvester.ams.controller.title.TitleContext;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.Habitat;
import com.sylvester.ams.model.ScientificName;
import com.sylvester.ams.service.ArthropodInfoService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmArthropodInfoService implements ArthropodInfoService {
    private Realm realm;

    public RealmArthropodInfoService() {
        realm = RealmContext.getInstance().getRealm();
    }

    @Override
    public void insertArthropodInfo(final List<String> habitats, final List<String> scientificNameList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // 중복제거
                Set<String> tempSet = new HashSet<>();

                for (String s : habitats) {
                    String[] temp = s.split(",");
                    if (temp.length > 1)
                        for (String ts : temp)
                            tempSet.add(ts);
                    else
                        tempSet.add(s);
                }

                List<String> tempList = new ArrayList<>(tempSet);

                // 서식지 리스트를 저장
                for (int i = 0; i < tempList.size(); i++) {
                    Habitat habitat = new Habitat(i, tempList.get(i));
                    realm.copyToRealm(habitat);
                }


                // 학명 리스트를 저장
                for (int i = 0; i < scientificNameList.size(); i++) {
                    String genus = scientificNameList.get(i).split(" ")[0];
                    String species = scientificNameList.get(i).split(" ")[1];

                    String[] temp = habitats.get(i).split(",");
                    RealmResults<Habitat> results = realm.where(Habitat.class)
                            .in("habitatName", temp).findAll();
                    RealmList<Habitat> list = new RealmList<>();
                    list.addAll(results.subList(0, results.size()));

                    ScientificName scientificName = new ScientificName(i, genus, species, list);
                    realm.copyToRealm(scientificName);
                }

                SharedPreferences.Editor editor = TitleContext.preferences.edit();
                editor.putLong("lastCon", System.currentTimeMillis());
                editor.commit();
            }
        });
    }

    @Override
    public List<String> getGenus() {
        List<ScientificName> scientificNames = new ArrayList<>();
        List<String> genus = new ArrayList<>();
        Set<String> tempList = new LinkedHashSet<>();

        RealmResults<ScientificName> results = realm.where(ScientificName.class).findAll();
        scientificNames.addAll(realm.copyFromRealm(results));

        for (ScientificName s : scientificNames)
            tempList.add(s.getGenus());

        genus.addAll(tempList);

        return genus;
    }

    @Override
    public List<String> getSpecies(String genus) {
        List<ScientificName> scientificNames = new ArrayList<>();
        List<String> species = new ArrayList<>();

        RealmResults<ScientificName> results = realm.where(ScientificName.class)
                .equalTo("genus", genus).findAll();
        scientificNames.addAll(realm.copyFromRealm(results));

        for (ScientificName s : scientificNames)
            species.add(s.getSpecies());

        return species;
    }
}
