package com.mlabs.bbm.firstandroidapp_morningclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchImage extends AppCompatActivity {
    float x1,x2,y1,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_touch_image);
        findViewById(R.id.imageView).setOnTouchListener(TouchListener);
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

                    String x="";
                    String y ="";
                        if(x1>x2)
                            x = "LEFT";
                        else if(x1<x2)
                            x = "RIGHT";

                        if(y1>y2)
                            y = "UP";
                        else if(y1<y2)
                            y = "DOWN";
                        Toast.makeText(getApplicationContext(), x + " " +y + " from ("+x1+","+y1+") to ("+x2+","+y2+")", Toast.LENGTH_SHORT).show();
                                }
            Log.d("onTouch","Exit onTouch");
            return false;
        }
    };
}
