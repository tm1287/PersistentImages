package com.maraligat.persistentimages;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button b_red;
    private Button b_yellow;
    private Button b_green;
    private Button b_blue;
    private Button b_plus;
    private Button b_minus;
    private Button b_reset;

    private TextView tv_dot;

    private PaintPotView drawingPad;

    private static final int DOT_SIZE_INCREMENT = 5;

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
        b_blue = findViewById(R.id.b_blue);
        b_plus = findViewById(R.id.b_plus);
        b_minus = findViewById(R.id.b_minus);
        b_reset = findViewById(R.id.b_reset);
        tv_dot = findViewById(R.id.tv_dot);
        drawingPad = findViewById(R.id.drawingPad);

        b_red.setOnClickListener(this);
        b_yellow.setOnClickListener(this);
        b_green.setOnClickListener(this);
        b_blue.setOnClickListener(this);
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
            case R.id.b_blue:
                Log.d("TAG","Button Pressed:" + _b.getText().toString());
                drawingPad.setPenColor(Color.BLUE);
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
}
