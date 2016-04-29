package com.example.michaelvaldez.photosharing;
import android.util.Log;

import com.loopj.android.http.*;
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by michaelvaldez on 4/21/16.
 */
public class PhotoSharingRestClient {

    // Location of API's used to upload photos as well request JSON of photos
    private static final String BASE_URL = "http://kickbakapp.com/PhotoSharing/";

    private static final String UPLOAD_API = BASE_URL + "uploadPhoto.php";

    private static final String FETCH_PHOTOS_API = BASE_URL + "fetchPhotos.php";

    // Location of photos, use this when you have gathered the names of photos
    public static final String PHOTO_DIR = BASE_URL + "ALLPHOTOS/";

    // Object used to access backend - process uploads and request...
    private static AsyncHttpClient client = new AsyncHttpClient();

    // Used to upload CaptionFrag in params to server API where it is decoded and put in filesystem
    public static void uploadPhoto(String encodedImage, JsonHttpResponseHandler responseHandler, String caption) {
        RequestParams params = new RequestParams();
            params.put("img", encodedImage);
//            if(!caption.isEmpty())params.put("caption", caption);
        client.post(UPLOAD_API, params, responseHandler);
    }

    // Used to request photos from the backend which returns
    // a JSON of all the names relative to BASE_PHOTO_URL
    public static void requestPhotos(JsonHttpResponseHandler responseHandler) {
        // fill photoArray and return photos
        client.post(FETCH_PHOTOS_API, responseHandler);
    }

    public static void testConnection() {
        client.post(BASE_URL + "test.php", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String result = response.get("result").toString();
                    Log.e("TEST CONNECTION:", result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

         });
    }

    // HELPER FUNCTION - to keep files and base locations abstract
    private static String getAbsoluteUrl(String relativeUrl) {
        return PHOTO_DIR + relativeUrl;
    }

}
