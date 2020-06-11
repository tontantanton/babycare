package com.example.babycare;


public class Data_new_1 {
    //String key;

    String name;
    String calories;
    String id;
    String quantity;
    String date;

    public Data_new_1(String name, String calories,String id,String quantity,String date) {
      //  this.key = key;
        this.name = name;
        this.id = id;
        this.calories = calories;
        this.quantity = quantity;
        this.date = date;
    }

    public Data_new_1() {
    }



//    public String getKey() {
//        return key;
//    }

    public String getName() {
        return name;
    }



    public String getCalories() {
        return calories;
    }
    public String getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }
    public String getDate() {
        return date;
    }
}
