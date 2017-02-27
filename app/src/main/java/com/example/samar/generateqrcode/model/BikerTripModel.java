package com.example.samar.generateqrcode.model;

/**
 * Created by samar on 25/02/17.
 */

public class BikerTripModel {

    int bikerId;
    String date;
    int amount;

    public BikerTripModel() {
    }

    public BikerTripModel(int bikerId, String date, int amount) {
        this.bikerId = bikerId;
        this.date = date;
        this.amount = amount;
    }

    public int getBikerId() {
        return bikerId;
    }

    public void setBikerId(int bikerId) {
        this.bikerId = bikerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
