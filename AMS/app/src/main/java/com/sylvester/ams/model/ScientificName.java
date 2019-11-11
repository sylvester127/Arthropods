package com.sylvester.ams.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ScientificName extends RealmObject{
    @PrimaryKey
    private int id;                             // 학명 아이디(PK)

    private String genus;                       // 종명
    private String species;                     // 속명
    private String commonName;                  // 커먼네임
    private RealmList<Arthropod> arthropods;    // 관리하는 절지동물 목록
    private RealmList<Habitat> habitats;                    // 서식지 정보

    // 생성자
    public ScientificName() {

    }

    public ScientificName(int id, String genus, String species) {
        this(id, genus, species, "", null, null);
    }

    public ScientificName(int id, String genus, String species, String commonName,
                          RealmList<Arthropod> arthropods, RealmList<Habitat> habitats) {
        this.id = id;
        this.genus = genus;
        this.species = species;
        this.commonName = commonName;
        this.arthropods = arthropods;
        this.habitats = habitats;
    }

    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public RealmList<Arthropod> getArthropods() {
        return arthropods;
    }

    public void setArthropods(RealmList<Arthropod> arthropods) {
        this.arthropods = arthropods;
    }

    public RealmList<Habitat> getHabitats() {
        return habitats;
    }

    public void setHabitats(RealmList<Habitat> habitats) {
        this.habitats = habitats;
    }
}