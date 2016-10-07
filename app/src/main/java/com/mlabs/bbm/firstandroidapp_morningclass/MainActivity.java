package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPW;
    Pattern emailP;
    DbHandler helper = new DbHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPW = (EditText) findViewById(R.id.etPW);
        TextView txtSU = (TextView) findViewById(R.id.txtSignUp);
        txtSU.setPaintFlags(txtSU.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG); //puts some underline on 'Sign Up'
        //set listeners
        findViewById(R.id.btnLogin).setOnClickListener(btnLoginClickListener);
        findViewById(R.id.txtSP).setOnTouchListener(txtSPTouchListener);
        txtSU.setOnClickListener(txtSUClickListener);

        emailP = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    View.OnTouchListener txtSPTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch(e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    etPW.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Log.d("ACTION","Pressed DOWN");
                    return true;
                case MotionEvent.ACTION_UP:
                    etPW.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Log.d("ACTION","Pressed UP");
            }
            Log.d("onTouch","Exit onTouch");
            return false;
        }
    };


    View.OnClickListener btnLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailStrLog = etEmail.getText().toString().toLowerCase();
            String pwStrLog = etPW.getText().toString();
            //login by email
            if (validate(emailStrLog)) {
                String password = helper.matchPassEA(emailStrLog);

                if(pwStrLog.equals(password)){
                    Intent intent = new Intent(MainActivity.this,Transition.class );
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Invalid Email Address/Password", Toast.LENGTH_SHORT).show();
                }
            //login by username
            else {
                String password = helper.matchPassUN(emailStrLog);
                if(pwStrLog.equals(password)){
                    Intent intent = new Intent(MainActivity.this,Transition.class );
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener txtSUClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,SignUp.class );
            startActivity(intent);
        }
    };


    Boolean validate(String email)
    {   Matcher m;
        m = emailP.matcher(email);
        if (m.matches()){
            return true;
        }
        else
            return false;
    }

    @Override
    protected void onPause() {
    super.onPause();
    finish();
    }
}
