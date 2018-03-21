package com.tsa.hths.colorpal;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {


    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.result_image);

        byte[] byteArray = getArguments().getByteArray("image"); //get byte[] of image
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); //decode byte[] to image

        imageView.setImageBitmap(bmp); //display image

        return v;
    }

}
