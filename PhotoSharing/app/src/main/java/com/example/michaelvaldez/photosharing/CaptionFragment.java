package com.example.michaelvaldez.photosharing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/*
public class CaptionFragment extends Fragment {

    String TAG = "debug";
    Bitmap image = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG,
                "******\n" +
                        "*******\n" +
                        "*IN Fragment*\n" +
                        "*******\n" +
                        "******\n");
        // end log

  //      View rootView = inflater.inflate(R.layout.fragment_caption, container, false);
    //    ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView2);
      //  imageView.setImageBitmap(image);
        //container.addView(imageView);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caption, container, false);
    }

    // return a CaptionFragment with the ImageView member image set
    // with picture just taken.
    public CaptionFragment newInstance(Bundle bundle)
    {
        CaptionFragment capFrag = new CaptionFragment();
        image = bundle.getParcelable("BitmapImage");
        return capFrag;
    };

     public CaptionFragment()
    {
        // required constructor, can leave blank
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
*/