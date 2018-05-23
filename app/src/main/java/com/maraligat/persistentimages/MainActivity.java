package com.maraligat.persistentimages;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button b_red;
    private Button b_yellow;
    private Button b_green;
    private Button b_photo;
    private Button b_plus;
    private Button b_minus;
    private Button b_reset;
    private TinyDB tinydb = new TinyDB(getApplicationContext());

    private TextView tv_dot;

    private PaintPotView drawingPad;

    private static final int DOT_SIZE_INCREMENT = 5;

    private Uri mImageURI;
    private static final int TAKE_PICTURE = 1;

    private static final int REQUEST_EXTERNAL_STORAGE =1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init(){
        b_red = findViewById(R.id.b_red);
        b_yellow = findViewById(R.id.b_yellow);
        b_green = findViewById(R.id.b_green);
        b_photo = findViewById(R.id.b_photo);
        b_plus = findViewById(R.id.b_plus);
        b_minus = findViewById(R.id.b_minus);
        b_reset = findViewById(R.id.b_reset);
        tv_dot = findViewById(R.id.tv_dot);
        drawingPad = findViewById(R.id.drawingPad);

        b_red.setOnClickListener(this);
        b_yellow.setOnClickListener(this);
        b_green.setOnClickListener(this);
        b_photo.setOnClickListener(this);
        b_plus.setOnClickListener(this);
        b_minus.setOnClickListener(this);
        b_reset.setOnClickListener(this);

        tv_dot.setText("Dot Size: " + drawingPad.getDotSize());
    }

    @Override
    public void onClick(View view) {

        Button _b = findViewById(view.getId());

        switch(view.getId()){
            case R.id.b_red:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.setPenColor(Color.RED);
                break;
            case R.id.b_yellow:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.setPenColor(Color.YELLOW);
                break;
            case R.id.b_green:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.setPenColor(Color.GREEN);
                break;
            case R.id.b_photo:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                this.takePicture();
                break;
            case R.id.b_plus:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.changeDotSize(+DOT_SIZE_INCREMENT);
                tv_dot.setText("Dot Size: " + drawingPad.getDotSize());
                break;
            case R.id.b_minus:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.changeDotSize(-DOT_SIZE_INCREMENT);
                tv_dot.setText("Dot Size: " + drawingPad.getDotSize());
                break;
            case R.id.b_reset:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.reset();
                tv_dot.setText("Dot Size: " + drawingPad.getDotSize());
                break;
        }
    }

    private void takePicture(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "picture.jpg");
        tinydb.putImage("Pictures/PaintPot", "picture.png", bitmap);
        mImageURI = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI);
        startActivityForResult(intent, TAKE_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            verifyStoragePermissions(this);
            Uri selectedImage = mImageURI;
            getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            Drawable drawable;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                drawable = new BitmapDrawable(getResources(), bitmap);
                // set view background here
                drawingPad.setBackground(tinydb.getImage("Picutres/PaintPot/picture.png"));
                Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
