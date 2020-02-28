package com.sylvester.ams.service.realm;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sylvester.ams.controller.list.ArthropodListContext;
import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;
import com.sylvester.ams.service.ArthropodListService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmArthropodListService implements ArthropodListService {
    Realm realm;

    public RealmArthropodListService() {
        realm = RealmContext.getInstance().getRealm();
    }

    @Override
    public List<Arthropod> getArthropods() {
        List<Arthropod> arthropods = new ArrayList<>();

        RealmResults<Arthropod> results = realm.where(Arthropod.class).findAll();
        arthropods.addAll(realm.copyFromRealm(results));

        return arthropods;
    }

    @Override
    public Bitmap getArthropodImg(String path) {
        Context context = ArthropodListContext.context;
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
    public ScientificName getScientificName(int arthropodId) {
        return realm.where(Arthropod.class).equalTo("id", arthropodId).findFirst().
                getScientificName().first();
    }

    @Override
    public void insertSample() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxValue = realm.where(Arthropod.class).max("id");

                int pk;
                if (maxValue == null)
                    pk = 0;
                else
                    pk = maxValue.intValue() + 1;

                Arthropod arthropod = new Arthropod(pk, "tarantulaImg.jpg", "따따라니");

                ScientificName scientificName = realm.where(ScientificName.class).equalTo("genus",
                        "Acanthoscurria").equalTo("species", "geniculata").findFirst();

                scientificName.getArthropods().add(arthropod);
            }
        });
    }
}
