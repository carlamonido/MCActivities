package com.mlabs.bbm.firstandroidapp_morningclass;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TouchImageMid extends AppCompatActivity {
    float x1,x2,y1,y2;
    TextView txtI,txtF,txtD,txtQ,txtDiff;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_touch_image_mid);

        txtI = (TextView) findViewById(R.id.txtI);
        txtF = (TextView) findViewById(R.id.txtF);
        txtD = (TextView) findViewById(R.id.txtD);
        txtQ = (TextView) findViewById(R.id.txtQ);
        txtDiff = (TextView) findViewById(R.id.txtDiff);
        img = (ImageView) findViewById(R.id.imageView2);
        img.setOnTouchListener(TouchListener);


    }

    View.OnTouchListener TouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch(e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x1 = e.getX();
                    y1 = e.getY();
                    Log.d("ACTION","Pressed DOWN");
                    return true;
                case MotionEvent.ACTION_UP:
                    x2 = e.getX();
                    y2 = e.getY();
                    Log.d("ACTION","Released press");

                    int x = 0;
                    int y = 0;
                    String pos = null;
                    String quad = null;
                    if(x1>x2)
                        x =1;
                        //x = "LEFT";
                    else if(x1<x2)
                        x=0;
                        //x = "RIGHT";

                    if(y1>y2)
                        y=0;
                        //y = "UP";
                    else if(y1<y2)
                        y=2;
                        //y = "DOWN";

                    int q = x+y;
                    switch(q) {
                        case 0:
                            pos = "UP-RIGHT";
                            quad = "1st";
                            break;
                        case 1:
                            pos = "UP-LEFT";
                            quad ="2nd";
                            break;
                        case 3:
                            pos = "DOWN-LEFT";
                            quad ="3rd";
                            break;
                        case 2:
                            pos = "DOWN-RIGHT";
                            quad ="4th";
                    }
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(4);

                    txtI.setText    ("Initial Position:     "+df.format(x1)+" , "+df.format(y1));
                    txtF.setText    ("Final Position:       "+df.format(x2)+" , "+df.format(y2));
                    txtD.setText    ("Direction:            "+ pos);
                    txtQ.setText    ("Quadrant:             "+ quad);
                    txtDiff.setText ("Difference:           "+df.format(Math.abs(x1-x2))+" , "+df.format(Math.abs(y1-y2)));
            }

            Log.d("onTouch","Exit onTouch");
            return false;
        }
    };

}
