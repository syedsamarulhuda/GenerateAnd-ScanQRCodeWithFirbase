package com.example.samar.generateqrcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

public class GenerateQRCodeActivity extends Activity implements OnClickListener {

    private String LOG_TAG = "GenerateQRCode";
    TextView tvAmount;
    int amount;

    Button btnManager1, btnManager2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        tvAmount = (TextView) findViewById(R.id.tvAmount);
        btnManager1 = (Button) findViewById(R.id.btnManager1);
        btnManager1.setOnClickListener(this);
        btnManager2 = (Button) findViewById(R.id.btnManager2);
        btnManager2.setOnClickListener(this);
        amount = getIntent().getIntExtra("amount", 0);

        tvAmount.setText("Rs." + amount);



    }

    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnManager1:
                SharedPrefUtil.putString(GenerateQRCodeActivity.this,"manager_name","Tushar");
                SharedPrefUtil.putInt(GenerateQRCodeActivity.this,"manager_id",101);

                FireBaseRealTimeDb.getInstance(this).writeToSelectedManager(101,"Tushar",1,"selected_manager");
                Toast.makeText(this, "Manager Selected", Toast.LENGTH_LONG).show();
                generateQr();

                break;
            case R.id.btnManager2:
                SharedPrefUtil.putString(GenerateQRCodeActivity.this,"manager_name","Apeksha");
                SharedPrefUtil.putInt(GenerateQRCodeActivity.this,"manager_id",144);

                FireBaseRealTimeDb.getInstance(this).writeToSelectedManager(144,"Apeksha",1,"selected_manager");
                Toast.makeText(this, "Manager Selected", Toast.LENGTH_LONG).show();
                generateQr();
                break;
        }

    }


    private void generateQr() {

        int bikerId = SharedPrefUtil.getInt(this,"biker_id");
        int managerId = SharedPrefUtil.getInt(this,"manager_id");

        Log.d("##DATA",bikerId+"--"+managerId);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amount", amount);
            jsonObject.put("biker_id", bikerId);
            jsonObject.put("manager_id", managerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String qrInputText = jsonObject.toString();

        Log.v(LOG_TAG, qrInputText);

        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
