package com.example.monika.smartstreetassign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by monika on 9/18/17.
 */

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
    }

    /* Method to handle scanning */

    public void scan(View view)
    {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        zXingScannerView.stopCamera();

    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(), Toast.LENGTH_SHORT).show();
        zXingScannerView.resumeCameraPreview(this);
    }
}
