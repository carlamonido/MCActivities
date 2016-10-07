package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText email, uname, fname, lname, pass, cPass;
    Button reg, viewAll;
    DbHandler helper = new DbHandler(this);
    Pattern emailP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up);

        email = (EditText) findViewById(R.id.emailReg);
        uname = (EditText) findViewById(R.id.etUname);
        fname = (EditText) findViewById(R.id.etFname);
        lname = (EditText) findViewById(R.id.etLname);
        pass = (EditText) findViewById(R.id.passReg);
        cPass = (EditText) findViewById(R.id.cPassReg);
        reg = (Button) findViewById(R.id.reg);
        viewAll = (Button) findViewById(R.id.viewAll);
        emailP = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        findViewById(R.id.reg).setOnClickListener(regClickListener);
        viewAll();
    }

        View.OnClickListener regClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String emailStr = email.getText().toString().toLowerCase();
                String unameStr = uname.getText().toString().toLowerCase();
                String fnameStr = fname.getText().toString().toLowerCase();
                String lnameStr = lname.getText().toString().toLowerCase();
                String pwStr = pass.getText().toString();
                String cPWStr = cPass.getText().toString();
                String eMsg;

                eMsg = validate(emailStr,unameStr,pwStr,cPWStr,fnameStr,lnameStr);
                if(eMsg == null){
                    Accounts a = new Accounts();
                    a.setEmail(emailStr);
                    a.setUname(unameStr);
                    a.setFname(fnameStr);
                    a.setLname(lnameStr);
                    a.setPass(pwStr);
                    a.setDate();

                    helper.regAccount(a);
                    //pop up dialog box
                    AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).create();
                    alertDialog.setMessage("Account Successfully Created");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //back to loginScreen
                                    //MainActivity is already gone, start new one
                                    finish();
                                    Intent intent = new Intent(SignUp.this,MainActivity.class );
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                }else
                    Toast.makeText(getApplicationContext(), eMsg, Toast.LENGTH_SHORT).show();
            }
        };

    String validate(String email, String uname, String pw, String cpw, String fname, String lname) {
        Matcher m;
        m = emailP.matcher(email);
        if (m.matches() && pw.length()>=8){
            Log.d("TEST","valid input");
            if (pw.equals(cpw)){
                    Log.d("TEST", "password match");
                    if(helper.emailUnused(email))
                        if(helper.unameUnused(uname))
                            if(isAlpha(fname)&&isAlpha(lname))
                                return null;
                                else
                                    return "Invalid name";
                        else
                            return "Username already used.";
                    else
                        return "Email already used";
                }
            else
                return "Password does not match";
        }
        else {
            return "Invalid Email/Password";
        }
    }

    public boolean isAlpha(String name){
        return name.matches("^[\\p{L} .'-]+$"); //letter and spaces only
    }

    //back button goes back to loginScreen
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(SignUp.this,MainActivity.class );
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void viewAll() {
        viewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = helper.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("id :"+ res.getString(0)+"\n");
                            buffer.append("UN :"+ res.getString(1)+"\n");
                            buffer.append("email :"+ res.getString(2)+"\n");
                            buffer.append("firstname :"+ res.getString(3)+"\n");
                            buffer.append("lastname :"+ res.getString(4)+"\n");
                            buffer.append("PW :"+ res.getString(5)+"\n");
                            buffer.append("DC :"+ res.getString(6)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
