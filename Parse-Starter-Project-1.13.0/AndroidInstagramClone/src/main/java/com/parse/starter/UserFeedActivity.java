package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.util.BitSet;
import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.ll);

        Intent i = getIntent();
        String activeUserName = i.getStringExtra("username");

        setTitle(activeUserName + "'s feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("images");

        query.whereEqualTo("username", activeUserName);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        for(ParseObject object : objects){
                            ParseFile file = (ParseFile) object.get("image");
                            final String tFile = (String) object.get("text");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e == null){
                                        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);


                                        ImageView imageView = new ImageView(getApplicationContext());
                                        imageView.setImageBitmap(image);
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        imageView.setMaxHeight(image.getHeight()/2);

                                        TextView textView = new TextView(getApplicationContext());
                                        textView.setText(tFile);
                                        textView.setPadding(25,25,25,25);
                                        textView.setTextColor(Color.BLACK);

                                        linearLayout.addView(imageView);
                                        linearLayout.addView(textView);

                                        android.view.ViewGroup.LayoutParams params = imageView.getLayoutParams();
                                        params.width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
                                        params.height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
                                        imageView.setLayoutParams(params);
                                    }
                                }
                            });



















                        }
                    }
                }
            }
        });






















        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
