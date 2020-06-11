package com.example.babycare.Menu_recommand;

public class Menureccommand {

    private String name;
    private String image;
    private String images;


    public Menureccommand(){

    }

    public Menureccommand(String mName, String mImage, String mImages) {
        name = mName;
        image = mImage;
        images = mImages;

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public String toString() {
        return "Menureccommand{" +
                "mName='" + name + '\'' +
                ", mImage='" + image + '\'' +
                ", mImages='" +images + '\'' +
                '}';
    }
}
