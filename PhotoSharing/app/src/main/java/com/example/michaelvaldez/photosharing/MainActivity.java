package com.example.michaelvaldez.photosharing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Drawable drawImages;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String TAG = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create floating action button listener here,
        // or in it's own function, giving it an onCall thing in XML file.
        // have all of this use the code in Camera project
        // or maybe this should be in it's own activity file..

        loadImages();
    }

    public void loadImages() {
        setContentView(R.layout.activity_main);
        ViewGroup.LayoutParams imageViewParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);                        // creating new ViewGroup
        ImageView newImage2 = new ImageView(this);
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
        ImageView newImage = new ImageView(this);
        Picasso.with(this).load("http://kickbakapp.com/profile_pic/ballershotcaller/6a4f092125c193a.jpeg")
                .resize(700,700)
                .into(newImage);
        Picasso.with(this).load("http://kickbakapp.com/profile_pic/mjvald3z/818d95dab1bf0d7.jpeg")
                .resize(700,700)
                .into(newImage2);

        newImage2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        newImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        newImage.setLayoutParams(imageViewParams);                           // fitting imageView to ViewGroup obj imageViewParams
        newImage2.setLayoutParams(imageViewParams);
        newImage2.setAdjustViewBounds(true);
        newImage.setAdjustViewBounds(true);
        tl.addView(newImage);                                                // adds it to TableLayout tl
        tl.addView(newImage2);
        tl.requestLayout();
        }


        // all I'm trying to do is take a picture and add it to
        // a table layout that already has a couple imageView children..

        // listens for Floating Action Button, initiates photo process
        public void takingPhoto(View CallingView) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   // create intent for taking pics

            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);     // launches photo appliations of Android
            }                                                                         // calls onActivityResult automatically

        } // takingPhoto

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            ImageView cameraPic = new ImageView(this);               // Declare it dynamically like this.
            Bundle extras = data.getExtras();                        // works
            Bitmap imageBitmap = (Bitmap) extras.get("data");        // works
            Log.d(TAG, "******************************************************************BitMap made with extras.get('data')");
            cameraPic.setImageBitmap(imageBitmap);                   // works with dynamically declared ImageView (duh)

            ViewGroup.LayoutParams imageViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);                        // creating new ViewGroup


        }

    } // onActivityResult



}

