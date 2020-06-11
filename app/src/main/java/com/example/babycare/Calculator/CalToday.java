package com.example.babycare.Calculator;

public class CalToday {

    private String name;
    private String calories;
    private String id;
    private String quantity;
    private String date;
    private String protein;

    public CalToday(String name, String calories, String id, String quantity, String date,String protein) {
        this.name = name;
        this.calories = calories;
        this.id = id;
        this.quantity = quantity;
        this.date = date;
        this.protein = protein;
    }

    public CalToday() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }




}
