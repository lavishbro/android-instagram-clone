package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SelectImageActivity extends AppCompatActivity {

    EditText content;
    ImageView imagePreview;
    Button cancel, post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);




        imagePreview = (ImageView) findViewById(R.id.imageViewPreview);
        content = (EditText) findViewById(R.id.contentEditText);
        cancel = (Button) findViewById(R.id.buttonCancel);
        post = (Button) findViewById(R.id.buttonPost);

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);








        //cancel

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finishedPost();


            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);


                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                final byte[] byteArray = stream.toByteArray();



                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


                //scale image
                int imageWidth = bitmap.getWidth()/10;
                int imageHeight = bitmap.getHeight()/10;

                //Toast.makeText(getApplicationContext(), String.valueOf(imageWidth) + String.valueOf(imageHeight), Toast.LENGTH_LONG).show();
                Bitmap bm = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, true);

                imagePreview.setImageBitmap(bm);


                post.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        finishedPost();




                ParseFile file = new ParseFile("image.png", byteArray);

                ParseObject object = new ParseObject("images");

                object.put("username", ParseUser.getCurrentUser().getUsername());
                object.put("text", String.valueOf(content.getText()));

                object.put("image", file);

                ParseACL parseACL = new ParseACL();
                parseACL.setPublicReadAccess(true);
                object.setACL(parseACL);

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(getApplication().getBaseContext(), "Your image has been posted!", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }

                    }
                });

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();

                Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();


            }


        }

    }







    public void finishedPost(){
        Intent c = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(c);
    }
}
