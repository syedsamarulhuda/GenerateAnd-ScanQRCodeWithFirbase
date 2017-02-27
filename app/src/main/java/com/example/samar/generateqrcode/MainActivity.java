package com.example.samar.generateqrcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private EditText etAmount;
    private Button btnSubmit, btnScan, btnGenerateQr, btnTripHistory,btnLogout;

    private TextView tvAmount, tvName, tvId;

    int bikerId ;
    String bikerName;
    boolean isManager;

    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        bikerName=SharedPrefUtil.getString(this,"biker_name");
        bikerId=SharedPrefUtil.getInt(this,"biker_id");
        isManager=SharedPrefUtil.getBoolean(this,"is_manager");

        Log.d("##BIKER-ID",bikerId+"");

        amount = FireBaseRealTimeDb.getInstance(this).getBikerTrip(bikerId, "biker_trip");

        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnScan = (Button) findViewById(R.id.btnScan);
        btnGenerateQr = (Button) findViewById(R.id.btnGenerateQr);
        btnTripHistory = (Button) findViewById(R.id.btnHistory);
        btnLogout=(Button)findViewById(R.id.btnLogout);

        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvName = (TextView) findViewById(R.id.tvName);
        tvId = (TextView) findViewById(R.id.tvId);

        tvAmount.setText("Amount : " + amount);
        tvName.setText("Name : "+bikerName);
        tvId.setText("Id : "+bikerId);

        if(isManager)
        {
            btnGenerateQr.setVisibility(View.GONE);
            btnTripHistory.setVisibility(View.GONE);
            tvAmount.setVisibility(View.GONE);
            findViewById(R.id.etAmount).setVisibility(View.GONE);
            findViewById(R.id.btnSubmit).setVisibility(View.GONE);
        }else{
           btnScan.setVisibility(View.GONE);
            btnTripHistory.setVisibility(View.GONE);
        }


        btnSubmit.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        btnGenerateQr.setOnClickListener(this);
        btnTripHistory.setOnClickListener(this);
        btnLogout.setOnClickListener(this);



    }


    //product qr code mode
    public void scanQR() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                Log.d("##Scan-Result", contents);

                Intent i = new Intent(MainActivity.this, MangerAmountActivity.class);
                i.putExtra("qr_content", contents);
                startActivity(i);
                //  convertToJsonFromXml(contents);

                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                collectedTripAmount();
                break;
            case R.id.btnScan:
                scanQR();
                break;
            case R.id.btnGenerateQr:
                genQr();
                break;
            case R.id.btnHistory:
                navHistory();
                break;
            case R.id.btnLogout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPrefUtil.putBoolean(this, "logged_in", false);
        SharedPrefUtil.removeSharedPref(this,"biker_id");
        SharedPrefUtil.removeSharedPref(this,"manager_id");
        SharedPrefUtil.removeSharedPref(this,"biker_name");
        SharedPrefUtil.removeAll(this);
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void navHistory() {
        Intent i = new Intent(MainActivity.this, TripHistory.class);
        startActivity(i);
    }


    private void genQr() {
        if(amount==0)
        {
            Toast.makeText(this, "Cannot Genrate QR Code. Amount Is Zero.", Toast.LENGTH_LONG).show();

        }else {
            int amountSaved = amount;
            Intent i = new Intent(MainActivity.this, GenerateQRCodeActivity.class);
            i.putExtra("amount", amountSaved);
            startActivity(i);
        }
    }

    private void collectedTripAmount() {
        if (etAmount.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter Amount.", Toast.LENGTH_LONG).show();

        } else {
            int amountSaved = amount;
            int amountCollected = Integer.parseInt(etAmount.getText().toString());
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            FireBaseRealTimeDb.getInstance(MainActivity.this).writeToBikerTrip(bikerId, date, amountCollected + amountSaved, "biker_trip");

            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            i.putExtra("amount", amountCollected + amountSaved);
            finish();
            Toast.makeText(this, "Amount Saved Successfully!", Toast.LENGTH_LONG).show();
        }
    }


}
