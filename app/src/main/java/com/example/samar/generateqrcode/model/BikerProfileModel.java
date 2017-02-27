package com.example.samar.generateqrcode.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by samar on 25/02/17.
 */
@IgnoreExtraProperties
public class BikerProfileModel {

    int bikerId;
    String bikername;
    int bikerWarehouse;

    public BikerProfileModel() {
    }

    public BikerProfileModel( String bikername, int bikerWarehouse) {
        this.bikername = bikername;
        this.bikerWarehouse = bikerWarehouse;
    }


    public int getBikerId() {
        return bikerId;
    }

    public void setBikerId(int bikerId) {
        this.bikerId = bikerId;
    }

    public String getBikername() {
        return bikername;
    }

    public void setBikername(String bikername) {
        this.bikername = bikername;
    }

    public int getBikerWarehouse() {
        return bikerWarehouse;
    }

    public void setBikerWarehouse(int bikerWarehouse) {
        this.bikerWarehouse = bikerWarehouse;
    }
}
