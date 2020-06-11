package com.example.babycare.Calculator;

public class Calofweek {

    private String name;
    private String calories;
    private String id;
    private String quantity;
    private String date;

    public Calofweek(String name, String calories, String id, String quantity, String date) {
        this.name = name;
        this.calories = calories;
        this.id = id;
        this.quantity = quantity;
        this.date = date;
    }

    public Calofweek() {
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
}
