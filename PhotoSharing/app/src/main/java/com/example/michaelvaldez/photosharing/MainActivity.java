package com.example.michaelvaldez.photosharing;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private Drawable drawImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImages();
    }

    public void loadImages() {
        setContentView(R.layout.activity_main);
        ViewGroup.LayoutParams imageViewParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView newImage2 = new ImageView(this);
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
        ImageView newImage = new ImageView(this);
        Picasso.with(this).load("http://kickbakapp.com/profile_pic/ballershotcaller/6a4f092125c193a.jpeg")
                .resize(700,700)
                .into(newImage);
        Picasso.with(this).load("http://kickbakapp.com/profile_pic/mjvald3z/818d95dab1bf0d7.jpeg")
                .resize(700,700)
                .into(newImage2);
        newImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        newImage2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        newImage.setLayoutParams(imageViewParams);
        newImage2.setLayoutParams(imageViewParams);
        newImage2.setAdjustViewBounds(true);
        newImage.setAdjustViewBounds(true);
        tl.addView(newImage);
        tl.addView(newImage2);
        tl.requestLayout();
        }

}
