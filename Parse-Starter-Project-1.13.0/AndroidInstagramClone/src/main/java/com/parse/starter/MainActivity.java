/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends ActionBarActivity {

  EditText userName, userPassword;

  public void signUp(View v){
    Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
    startActivity(i);

  }
  public void logIn(View v){

    ParseUser.logInInBackground(String.valueOf(userName.getText()), String.valueOf(userPassword.getText()), new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {

        if (user != null) {

          //success
          showUserList();

        } else {

          Toast.makeText(getApplicationContext(), "please login or signup", Toast.LENGTH_LONG).show();


        }

      }
    });
  }




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (ParseUser.getCurrentUser() != null) {
      //check login
      showUserList();

    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());




    userName = (EditText) findViewById(R.id.loginUserName);
    userPassword = (EditText) findViewById(R.id.loginPassword);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void showUserList() {

    //takes you to login place
    Intent i = new Intent(getApplicationContext(), UserListActivity.class);
    startActivity(i);

  }
}
