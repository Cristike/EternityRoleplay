package me.cristike.eternityroleplay.objects;

import me.cristike.eternityroleplay.Main;

public class Character {
    private String name;
    private int age;
    private String gender;
    private String nation;
    private String religion;

    public Character(String name, int age, String gender, String nation, String religion) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nation = nation;
        this.religion = religion;
    }

    public Character() {

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Nation getNation() {
        return Main.nations.get(nation);
    }

    public String getNationName() {
        return nation;
    }

    public String getReligion() {
        return religion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }


    public void setReligion(String religion) {
        this.religion = religion;
    }
}
