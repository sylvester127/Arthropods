package com.sylvester.ams.service.realm;

import android.content.SharedPreferences;

import com.sylvester.ams.controller.title.TitleContext;
import com.sylvester.ams.entity.Habitat;
import com.sylvester.ams.entity.ScientificName;
import com.sylvester.ams.service.TitleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmTitleService implements TitleService {
    private Realm realm;

    public RealmTitleService() {
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
}
