package com.example.michaelvaldez.photosharing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.extras.Base64;


public class MainActivity extends Activity {

    private Drawable drawImages;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CAPTION = 2;
    String TAG = "debug";
    private JSONArray photos;
    TableLayout overAllTable;
    private Bitmap mImageBitmap = null;                        // globals hold the image and capion just taken
    private String mStringCaption ="";

    private PhotoSharingRestClient photoSharingServer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        photoSharingServer = new PhotoSharingRestClient(); // Instantiate our connection to server

        photoSharingServer.testConnection();

        getPhotos();

        overAllTable = (TableLayout) findViewById(R.id.tableLayout1);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    } // onCreate

    // needed for Fragment plumbing to work, can ignore.


    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void getPhotos() {
        photoSharingServer.requestPhotos(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String result = response.get("result").toString();
                    if (result.equals("1")) {
                        Log.e("PHOTOS RECEIVED", "TRUE");
                        photos = response.getJSONArray("photos");
                        loadImages();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refresh(View callingView) {
        getPhotos();

    }

    public void loadImages() {
        setContentView(R.layout.activity_main);
        ViewGroup.LayoutParams imageViewParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT);                        // creating new ViewGroup
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();


        Log.e("Photos", photos.toString());
        Log.e("Count", String.valueOf(photos.length()));
        for (int i = 0; i < photos.length(); i++) {
            ImageView newImage = new ImageView(this);
            newImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            newImage.setLayoutParams(imageViewParams);
            newImage.setAdjustViewBounds(true);
            try {
                Picasso.with(this).load(photoSharingServer.PHOTO_DIR + photos.getString(i))
                        .resize(width, height)
                        .centerCrop()
                        .into(newImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            tl.addView(newImage);

            // add TextView buffer
            // where the caption should go. Somehow needs to be stored and retrieved in database
            TextView captionBuffer = new TextView(this);
            captionBuffer.setWidth(width);
            CharSequence line = "----------------------------------";
            captionBuffer.setText(line);
            tl.addView(captionBuffer);
            tl.requestLayout();
        }
    }



    // listens for Floating Action Button
    public void takingPhoto(View CallingView) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   // create intent for taking pics
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);     // launches CaptionFrag appliations of Android
                            Log.d(TAG,
                            "******\n" +
                            "*******\n" +
                            "TAKING*\n" +
                            "picture\n" +
                            "*******\n" +
                            "******\n");
        }                                                                         // calls onActivityResult automatically

    } // takingPhoto

    public void captionReq()
    {
        Intent captionIntent = new Intent(this, PopCaption.class);
        captionIntent.putExtra("BitmapImage", mImageBitmap);                      // send the image to the other activity
        startActivityForResult(captionIntent, REQUEST_CAPTION);                   // start the Caption activity, get result in onActivityResult
    }


    // called in takingPhoto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            String caption = "";

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView cameraPic = new ImageView(this);
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
                      Log.d(TAG,
                      "******\n" +
                      "*******\n" +
                      "Picture*\n" +
                      "Taken\n" +
                      "*******\n" + REQUEST_IMAGE_CAPTURE);

                captionReq();                                                      // call for caption Intent to be made
            }

            else if (requestCode == REQUEST_CAPTION && resultCode == RESULT_OK)
            {
                Bundle extras = data.getExtras();
                if(extras!=null) {
                    mStringCaption = data.getStringExtra("caption");               // get caption from data
                }                                                                  // caption stored in mStringCaption
                        Log.d(TAG,
                        "******\n" +
                        "*******\n" +
                        "*******\n" +
                        "**Back in Main from PopCap**\n" + REQUEST_CAPTION + " " + mStringCaption);

                // have caption info in data.
            }

            //

            /* encode image file and upload the string to the server for it to be decoded
               --------*/

               /*


            String encodedImage = encodeToBase64(imageBitmap, Bitmap.CompressFormat.JPEG, 100);
            photoSharingServer.uploadPhoto(encodedImage, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    getPhotos();                                                                        // downloads onto phone
                }
            },

            mStringCaption);                                                                            // caption string argument

            /* -------- */

    } // onActivityResult, called by takingPhoto





    // -- HELPER FUNCTION to encode a BITMAP image to Base64
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.michaelvaldez.photosharing/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.michaelvaldez.photosharing/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

