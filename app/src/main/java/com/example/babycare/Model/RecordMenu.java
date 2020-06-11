package com.example.babycare.Model;


public class RecordMenu {
    private String name;
    private String calories;

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

    public RecordMenu(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "DessertMenu{" +
                "name='" + name + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }

    public RecordMenu() {
        //empty constructor needed
    }
}

