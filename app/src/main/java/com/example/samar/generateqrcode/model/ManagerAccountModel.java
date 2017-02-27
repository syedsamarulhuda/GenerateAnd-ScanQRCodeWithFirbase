package com.example.samar.generateqrcode.model;

/**
 * Created by samar on 25/02/17.
 */

public class ManagerAccountModel {

    int managerId;
    int bikerId;
    String date;
    double amount;

    public ManagerAccountModel() {
    }

    public ManagerAccountModel(int managerId, int bikerId, String date, double amount) {
        this.managerId = managerId;
        this.bikerId = bikerId;
        this.date = date;
        this.amount = amount;
    }


    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
