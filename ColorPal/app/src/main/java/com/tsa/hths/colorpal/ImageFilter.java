package com.tsa.hths.colorpal;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageFilter {
    public ImageFilter() {}

    public Bitmap filterImage(Bitmap image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getPixel(x, y);
//                Log.i("color", color + "");
                color *= 2;
                image.setPixel(x, y, color);
            }
        }
        return image;
    }
}
