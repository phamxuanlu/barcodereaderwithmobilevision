package com.lupx.barcode.reader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class DecodeActivity extends AppCompatActivity {
    private Button btn;
    private Bitmap bitmap;
    private TextView txtView;
    private BarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detect();
            }
        });

        ImageView myImageView = (ImageView) findViewById(R.id.imgview);
        bitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.we_make_it_awesome_aztec);
        myImageView.setImageBitmap(bitmap);
        txtView = findViewById(R.id.txtContent);
    }

    private void detect() {
        if (detector == null) {
            detector = new BarcodeDetector.Builder(getApplicationContext())
                    .setBarcodeFormats(Barcode.ALL_FORMATS)
                    .build();
        }
        if (!detector.isOperational()) {
            txtView.setText("Could not set up the detector!");
            return;
        }
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);
        Barcode thisCode = barcodes.valueAt(0);
        txtView.setText("Result: " + thisCode.rawValue);
    }
}
