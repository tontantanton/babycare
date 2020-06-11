package com.example.babycare.Menu_recommand;

public class Benner {
    private String name,image;

    public Benner(String name, String image) {

        this.name = name;
        this.image = image;
    }

    public Benner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
