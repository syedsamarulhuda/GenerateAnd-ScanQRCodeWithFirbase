package com.example.samar.generateqrcode.model;

/**
 * Created by samar on 25/02/17.
 */

public class ManagerProfileModel {

    int managerId;
    String managerName;
    int warehouse;

    public ManagerProfileModel() {
    }

    public ManagerProfileModel(int managerId, String managerName, int warehouse) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.warehouse = warehouse;
    }

    public ManagerProfileModel(int warehouse, String managerName) {
        this.warehouse = warehouse;
        this.managerName = managerName;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }
}
