package com.example.babycare.Baby;

public class Graph {

    private String height;
    private String weight;

    public Graph(String height, String weight) {
        this.height = height;
        this.weight = weight;
    }

    public Graph() {
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
