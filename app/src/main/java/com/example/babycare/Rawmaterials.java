package com.example.babycare;

public class Rawmaterials {


    private String name;
    private String calories;




    public Rawmaterials(String name, String calories) {
        this.name = name;
        this.calories = calories;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }


}
