package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText sUser, sPass;

    public void signUserUp(View v){

        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(sUser.getText()));
        user.setPassword(String.valueOf(sPass.getText()));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){

                    //redirect
                    Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(SignUpActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sUser = (EditText) findViewById(R.id.sUserName);
        sPass = (EditText) findViewById(R.id.sPassword);


























        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
