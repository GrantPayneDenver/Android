package com.example.michaelvaldez.photosharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PopCaption extends Activity {

    private Bitmap photo;
    private String caption;
    private String TAG = "DEBUG";
    private EditText capField;
    boolean Tclicked = false;
    boolean fbclicked = false;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_caption);

        FrameLayout layout = (FrameLayout) findViewById(R.id.frame);

        // stuff to make softkeypad dissapear if user touches outside of EditText field
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });


        Intent activityThatCalled = getIntent();

        photo = (Bitmap)activityThatCalled.getParcelableExtra("BitmapImage");
        ImageView picPortrait = (ImageView) findViewById(R.id.imageView2);
        picPortrait.setImageBitmap(photo);
        capField = (EditText)findViewById(R.id.capField);

    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    // clicked by Image Button with Plus Sign. Exits activity, returns string in EditText field
    public void returnToMain(View view) {

       // EditText capField = (EditText)findViewById(R.id.editText);
        caption = capField.getText().toString();

        Intent goingBack = new Intent();
        goingBack.putExtra("caption", caption);
        setResult(RESULT_OK, goingBack);

                        Log.d(TAG,
                        "******\n" +
                        "*******\n" +
                        "**back button pressed**\n" + " " + caption +
                        "******\n");

        // close this activity
        finish();

    }

    // Tints Twitter button
    public void clickTwitter(View view)
    {
        if(!Tclicked)// hasn't been clicked yet. Tint
        {
            ImageButton button = (ImageButton) this.findViewById(R.id.twitter);
            button.setColorFilter(Color.argb(150, 150, 150, 150)); // White Tint
            Tclicked=true;
        }
        else         // has been clicked, untint
        {
            ImageButton button = (ImageButton) this.findViewById(R.id.twitter);
            button.setColorFilter(null);                           // No Tint
            Tclicked=false;
        }
    }

    // Tints fb button
    public void clickFB(View view)
    {
        if(!fbclicked)// hasn't been clicked yet. Tint
        {
            ImageButton button = (ImageButton) this.findViewById(R.id.fb);
            button.setColorFilter(Color.argb(150, 150, 150, 150)); // White Tint
            fbclicked=true;
        }
        else         // has been clicked, untint
        {
            ImageButton button = (ImageButton) this.findViewById(R.id.fb);
            button.setColorFilter(null);                           // No Tint
            fbclicked=false;
        }
    }

}
