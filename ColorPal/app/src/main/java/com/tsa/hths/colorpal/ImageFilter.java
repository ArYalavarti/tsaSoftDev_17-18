package com.tsa.hths.colorpal;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ImageFilter {
    public ImageFilter() {
    }

    public Bitmap filterImage(Bitmap image, String colorBlindnessType) {

        if (colorBlindnessType.equals("RED") || colorBlindnessType.equals("GREEN")) {

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int colorInitial = image.getPixel(x, y);

                    int a = Color.alpha(colorInitial);
                    int r = Color.red(colorInitial);
                    int g = Color.green(colorInitial);
                    int b = Color.blue(colorInitial);

                    int colorOutput = Color.rgb(b, g, r);
                    image.setPixel(x, y, colorOutput);;
                }
            }
        } else if (colorBlindnessType.equals("BLUE")) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int colorInitial = image.getPixel(x, y);

                    int a = Color.alpha(colorInitial);
                    int r = Color.red(colorInitial);
                    int g = Color.green(colorInitial);
                    int b = Color.blue(colorInitial);

                    int colorOutput = Color.rgb(255-b, 255-g, 255-r);
                    image.setPixel(x, y, colorOutput);;
                }
            }
        }
        return image;
    }
}