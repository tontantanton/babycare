package com.example.babycare;


public class Model_1 {

    private String name;
    private String calories;
    private String id;
    private String quantity;
    private String date;
    private String protein;
    private String calcium;
    private String iodine;
    private String iron;
    private String vitamina;
    private String vitaminb6;
    private String vitaminb12;
    private String vitaminc;


    public Model_1(String name, String calories, String id, String quantity, String date, String protein, String calcium, String iodine, String iron, String vitamina, String vitaminb6, String vitaminb12, String vitaminc) {
        this.name = name;
        this.calories = calories;
        this.id = id;
        this.quantity = quantity;
        this.date = date;
        this.protein = protein;
        this.calcium = calcium;
        this.iodine = iodine;
        this.iron = iron;
        this.vitamina = vitamina;
        this.vitaminb6 = vitaminb6;
        this.vitaminb12 = vitaminb12;
        this.vitaminc = vitaminc;
    }

    public Model_1() {
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


    public String getCalcium() {
        return calcium;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public String getIodine() {
        return iodine;
    }

    public void setIodine(String iodine) {
        this.iodine = iodine;
    }


    public String getIron() {
        return iron;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }




    public String getVitamina() {
        return vitamina;
    }

    public void setVitamina(String vitamina) {
        this.vitamina = vitamina;
    }

    public String getVitaminb6() {
        return vitaminb6;
    }

    public void setVitaminb6(String vitaminb6) {
        this.vitaminb6 = vitaminb6;
    }


    public String getVitaminb12() {
        return vitaminb12;
    }

    public void setVitaminb12(String vitaminb12) {
        this.vitaminb12 = vitaminb12;
    }

    public String getVitaminc() {
        return vitaminc;
    }

    public void setVitaminc(String vitaminc) {
        this.vitaminc = vitaminc;
    }

}