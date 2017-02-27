package com.example.samar.generateqrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.samar.generateqrcode.model.ManagerAccountModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripHistory extends AppCompatActivity {


    int managerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        managerId=SharedPrefUtil.getInt(this,"manager_id");

        Log.d("##mId",managerId+"");

        initView();
    }

    private void initView() {

        List<ManagerAccountModel>managerAccountModelList=FireBaseRealTimeDb.getInstance(this).getSelectedManagerAccountDetails(managerId,"manager_account");

         for(int i=0; i<managerAccountModelList.size();i++)
         {
             Log.d("##LIST-DATA",managerAccountModelList.get(i).getAmount()+"");
         }


    }


}
