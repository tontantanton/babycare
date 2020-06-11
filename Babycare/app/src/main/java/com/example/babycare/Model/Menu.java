package com.example.babycare.Model;

public class Menu {

    private String name;
    private String calories;
    private String id;


    public Menu(String id,String name, String calories) {
        this.id = id;
       this.name = name;
       this.calories = calories;
    }

    public Menu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
