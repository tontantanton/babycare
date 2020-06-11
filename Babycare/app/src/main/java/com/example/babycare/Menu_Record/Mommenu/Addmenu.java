package com.example.babycare.Menu_Record.Mommenu;

public class Addmenu {
    private String name,cal,protein,amount, id;

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }


    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }








    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Addmenu(String name, String cal, String amount, String id,  String protein) {
        this.name = name;
        this.cal = cal;
        this.amount = amount;
        this.id = id;
        this.protein =protein;
    }

    public Addmenu() {
    }

    public Addmenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
