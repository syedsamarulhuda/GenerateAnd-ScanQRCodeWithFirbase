package com.example.samar.generateqrcode.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samar on 25/02/17.
 */

public class QRContentModel {


    public static class QrData {

        @SerializedName("amount")
        int amount;
        @SerializedName("biker_id")
        int bikerId;
        @SerializedName("manager_id")
        int managerId;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getBikerId() {
            return bikerId;
        }

        public void setBikerId(int bikerId) {
            this.bikerId = bikerId;
        }

        public int getManagerId() {
            return managerId;
        }

        public void setManagerId(int managerId) {
            this.managerId = managerId;
        }
    }
}
