package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Transition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transition);

        findViewById(R.id.btnTouch).setOnClickListener(btnClickListener);
        findViewById(R.id.btnTouchMid).setOnClickListener(btnClickListener);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btnTouch:
                    intent = new Intent(Transition.this, TouchImage.class);
                    startActivity(intent);
                    break;
                case R.id.btnTouchMid:
                    intent = new Intent(Transition.this, TouchImageMid.class);
                    startActivity(intent);
                                }

        }
    };
}
