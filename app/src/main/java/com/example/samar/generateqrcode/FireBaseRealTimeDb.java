package com.example.samar.generateqrcode;

import android.content.Context;
import android.util.Log;

import com.example.samar.generateqrcode.model.BikerProfileModel;
import com.example.samar.generateqrcode.model.BikerTripModel;
import com.example.samar.generateqrcode.model.ManagerAccountModel;
import com.example.samar.generateqrcode.model.ManagerProfileModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samar on 25/02/17.
 */

public class FireBaseRealTimeDb implements ValueEventListener {


    private DatabaseReference mDatabase;

    int bikeramount;
    int managerId;


    private static FireBaseRealTimeDb fireBaseRealTimeDb;
    Context context;


    public FireBaseRealTimeDb(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public synchronized static FireBaseRealTimeDb getInstance(Context context) {
        if (fireBaseRealTimeDb == null) {
            fireBaseRealTimeDb = new FireBaseRealTimeDb(context);
        }
        return fireBaseRealTimeDb;

    }

    public void writeToBikerProfile(int bikerId, String bikerName, int bikerWareHouse, String tableName) {
        BikerProfileModel bikerProfileModel = new BikerProfileModel(bikerName, bikerWareHouse);
        bikerProfileModel.setBikerId(bikerId);
        mDatabase.child(tableName).child(bikerId + "").setValue(bikerProfileModel);
    }

    public void writeToManagerProfile(int id, String name, int wareHouse, String tableName) {
        ManagerProfileModel managerProfileModel = new ManagerProfileModel(wareHouse, name);
        managerProfileModel.setManagerId(id);
        mDatabase.child(tableName).child(id + "").setValue(managerProfileModel);
    }

    public void writeToBikerTrip(int id, String date, int amount, String tableName) {

        BikerTripModel bikerTripModel = new BikerTripModel(id, date, amount);
        mDatabase.child(tableName).child(id + "").setValue(bikerTripModel);
    }

    public void writeToManagerAccount(int id, int bikerId, String date, double amount, String tableName) {

        ManagerAccountModel managerAccountModel = new ManagerAccountModel(id, bikerId, date, amount);
        mDatabase.child(tableName).child(id + "").child(bikerId+"").setValue(managerAccountModel);
    }

    public void writeToSelectedManager(int mId, String mName,int warehouse, String tableName) {

        ManagerProfileModel managerProfileModel = new ManagerProfileModel(mId,mName,warehouse);
        mDatabase.child(tableName).setValue(managerProfileModel);
    }


    public int getBikerTrip(int bikerId, String tableName) {

        mDatabase.child(tableName).child(bikerId + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BikerTripModel bikerTripModel = dataSnapshot.getValue(BikerTripModel.class);
                if(!(bikerTripModel == null))
                {
                    bikeramount = bikerTripModel.getAmount();
                    Log.d("##BIKER-DATA", " Amount " + bikerTripModel.getAmount());
                }else
                {
                    bikeramount=0;
                }

                Log.d("##BIKER-DATA", " Amount " + bikeramount);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

        return bikeramount;
    }

    public List<ManagerAccountModel>  getSelectedManagerAccountDetails( int mId,String tableName) {

        final List<ManagerAccountModel> managerAccountModelListt = new ArrayList<>();

        Log.d("##Manager-Id", " " + mId);

        mDatabase.child(tableName).child(mId+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ManagerAccountModel managerAccountModel = postSnapshot.getValue(ManagerAccountModel.class);
                    managerAccountModelListt.add(managerAccountModel);

                    }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

        return managerAccountModelListt;
    }

    public void getBikerDetail(int bikerId,String tableName) {

        mDatabase.child(tableName).child(bikerId+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BikerProfileModel bikerProfileModel = dataSnapshot.getValue(BikerProfileModel.class);

                Log.d("##BIKER-DATA", " Name"+bikerProfileModel.getBikername());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
