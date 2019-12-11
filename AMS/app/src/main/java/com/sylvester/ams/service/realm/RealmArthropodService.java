package com.sylvester.ams.service.realm;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sylvester.ams.controller.list.ArthropodListContext;
import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.entity.ScientificName;
import com.sylvester.ams.service.ArthropodService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmArthropodService implements ArthropodService {
    Realm realm;

    public RealmArthropodService() {
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
    public ScientificName getScientificName(Arthropod arthropod) {
        RealmResults<ScientificName> scientificNames = realm.where(Arthropod.class)
                .equalTo("id", arthropod.getId()).findFirst().getScientificName();
        return scientificNames.get(0);
    }

    @Override
    public Bitmap getArthropodImg() {
        Context context = ArthropodListContext.context;

        AssetManager am = context.getResources().getAssets();
        String path = "tarantulaImg.jpg";

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
    public Bitmap getArthropodImg(int id) {
        Arthropod arthropod = realm.where(Arthropod.class).equalTo("id", id).findFirst();
        Context context = ArthropodListContext.context;

        AssetManager am = context.getResources().getAssets();
        String path = arthropod.getImgDir();

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
    public Bitmap getArthropodImg(Arthropod arthropod) {
        Context context = ArthropodListContext.context;

        AssetManager am = context.getResources().getAssets();
        String path = arthropod.getImgDir();

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

    @Override
    public void insertArthropod() {
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

    @Override
    public Arthropod getArthropod(int id) {
        Arthropod arthropod = realm.where(Arthropod.class).equalTo("id", id).findFirst();

        return arthropod;
    }

    // ArthropodDao 쿼리
    public void setArthropod(final Arthropod arthropod) {
        Realm realm = RealmContext.getInstance().getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(arthropod);
            }
        });
    }

    public Arthropod getArthropod(String key) {
        Realm realm = RealmContext.getInstance().getRealm();

        return realm.copyFromRealm(realm.where(Arthropod.class).
                equalTo("key", key).findFirst());
    }

    public ArrayList<Arthropod> searchTarantulaObject() {
        Realm realm = RealmContext.getInstance().getRealm();

        ArrayList<Arthropod> arthropodArrayList = new ArrayList<>();
        RealmResults<Arthropod> realmResults = realm.where(Arthropod.class).findAll();

        for (Arthropod tInfo : realmResults) {
            arthropodArrayList.add(tInfo);
        }

        return arthropodArrayList;
    }
}
