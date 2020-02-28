package com.sylvester.ams.service.realm;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sylvester.ams.controller.detail.DetailContext;
import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;
import com.sylvester.ams.service.DetailService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDetailService implements DetailService {
    Realm realm;

    public RealmDetailService() {
        realm = RealmContext.getInstance().getRealm();
    }

    @Override
    public Arthropod getArthropod(int id) {
        Arthropod arthropod = realm.where(Arthropod.class).equalTo("id", id).findFirst();

        return arthropod;
    }

    @Override
    public Bitmap getArthropodImage(String path) {
        Context context = DetailContext.context;
        AssetManager am = context.getResources().getAssets();
        Bitmap bm = null;

        try {
            InputStream is = am.open(path);
            bm = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
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

    @Override
    public ScientificName getScientificName(final int arthropodId) {
        return realm.where(Arthropod.class).equalTo("id", arthropodId).findFirst().
                getScientificName().first();
    }

    @Override
    public void insertArthropod(final Arthropod arthropod, final String genus, final String species) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxValue = realm.where(Arthropod.class).max("id");

                int pk;
                if (maxValue == null)
                    pk = 0;
                else
                    pk = maxValue.intValue() + 1;

                arthropod.setId(pk);

                ScientificName scientificName = realm.where(ScientificName.class).equalTo("genus",
                        genus).equalTo("species", species).findFirst();

                scientificName.getArthropods().add(arthropod);
            }
        });
    }

    @Override
    public void updateArthropod(final Arthropod arthropod, final String genus, final String species) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Arthropod currentArthropod = realm.where(Arthropod.class).equalTo("id", arthropod.getId()).
                        findFirst();

                currentArthropod.setGender(arthropod.getGender());
                currentArthropod.setFeedingCycle(arthropod.getFeedingCycle());
                currentArthropod.setHabit(arthropod.getHabit());
                currentArthropod.setImgDir(arthropod.getImgDir());
                currentArthropod.setLastFeedDate(arthropod.getLastFeedDate());
                currentArthropod.setLastRehousingDate(arthropod.getLastRehousingDate());
                currentArthropod.setMemo(arthropod.getMemo());
                currentArthropod.setMolt(arthropod.getMolt());
                currentArthropod.setMoltCount(arthropod.getMoltCount());
                currentArthropod.setMoltHistory(arthropod.getMoltHistory());
                currentArthropod.setName(arthropod.getName());
                currentArthropod.setPostponeFeed(arthropod.getPostponeFeed());
                currentArthropod.setReceiveDate(arthropod.getReceiveDate());
                currentArthropod.setReceivePlace(arthropod.getReceivePlace());
                currentArthropod.setRegdate(arthropod.getRegdate());
                currentArthropod.setSize(arthropod.getSize());
                currentArthropod.setStatus(arthropod.getStatus());

                ScientificName scientificName = realm.where(Arthropod.class).equalTo("id", arthropod.getId()).
                        findFirst().getScientificName().first();

                scientificName.getArthropods().remove(currentArthropod);
                scientificName = realm.where(ScientificName.class).equalTo("genus", genus).
                        equalTo("species", species).findFirst();
                scientificName.getArthropods().add(currentArthropod);
            }
        });
    }

    @Override
    public void deleteArthropod(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Arthropod.class).equalTo("id", id).findFirst().deleteFromRealm();
            }
        });
    }
}
