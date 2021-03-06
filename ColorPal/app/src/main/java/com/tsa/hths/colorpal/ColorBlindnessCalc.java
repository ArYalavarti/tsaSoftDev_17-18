package com.tsa.hths.colorpal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ColorBlindnessCalc {
    public static final String RED = "RED";
    public static final String GREEN = "GREEN";
    public static final String BLUE = "BLUE";
    public static final String FULL = "FULL";
    public static final String NONE = "NONE";

    public static String getColorBlindness(Context context) // accesses data from saved preferences; gets type of color blindness
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        int redError = sp.getInt(context.getResources().getString(R.string.red_error), 0);
        int greenError = sp.getInt(context.getResources().getString(R.string.green_error), 0);
        int blueError = sp.getInt(context.getResources().getString(R.string.blue_error), 0);

        if(redError + greenError + blueError <= 100) // accounts for low errors and prevents division by 0 error
        {
            return NONE;
        }

        double redPercent = 100.0 * redError / (redError + greenError + blueError);
        double greenPercent = 100.0 * greenError / (redError + greenError + blueError);
        double bluePercent = 100.0 * blueError / (redError + greenError + blueError);

        if(isBetween(redPercent, 28, 38) && isBetween(greenPercent, 28, 38) && isBetween(bluePercent, 28, 38))
        {
            return FULL;
        }
        else if(redPercent >= bluePercent && redPercent >= greenPercent)
        {
            return RED;
        }
        else if(greenPercent >= redPercent && greenPercent >= bluePercent)
        {
            return GREEN;
        }
        else if(bluePercent >= greenPercent && bluePercent >= redPercent)
        {
            return BLUE;
        }
        else
        {
            return NONE;
        }
    }

    public static boolean diagnosticHasBeenTaken(Context context)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        int redError = sp.getInt(context.getResources().getString(R.string.red_error), -1);
        int greenError = sp.getInt(context.getResources().getString(R.string.green_error), -1);
        int blueError = sp.getInt(context.getResources().getString(R.string.blue_error), -1);

        if(redError == -1 || greenError == -1 || blueError == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private static boolean isBetween(double val, int min, int max)
    {
        if(val >= min && val <= max)
        {
            return true;
        }
        return false;
    }
}
