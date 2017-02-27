package com.example.samar.generateqrcode;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samar.generateqrcode.model.QRContentModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MangerAmountActivity extends AppCompatActivity {

    String data;
    TextView tvAmount;
    ImageView ivsuccess;
    int bikerId;
    String managerName;
    int managerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_amount);

        data=getIntent().getStringExtra("qr_content");

        bikerId=SharedPrefUtil.getInt(this,"biker_id");
     //   managerId=FireBaseRealTimeDb.getInstance(this).getSelectedManagerId("selected_manager");
        managerId=SharedPrefUtil.getInt(this,"manager_id");
        managerName=SharedPrefUtil.getString(this,"manager_name");

        initdata();
    }

    private void initdata() {

        tvAmount=(TextView)findViewById(R.id.tvAmount);
        ivsuccess=(ImageView)findViewById(R.id.ivSuccessIcon);
        ivsuccess.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
        QRContentModel.QrData qrData = new Gson().fromJson(data, QRContentModel.QrData.class);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        FireBaseRealTimeDb.getInstance(this).writeToBikerTrip(qrData.getBikerId(),date,0,"biker_trip");
        FireBaseRealTimeDb.getInstance(this).writeToManagerAccount(qrData.getManagerId(),qrData.getBikerId(),date,qrData.getAmount(),"manager_account");
        tvAmount.setText("Rs. "+qrData.getAmount()+"");


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
