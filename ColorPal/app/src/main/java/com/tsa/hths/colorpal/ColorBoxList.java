package com.tsa.hths.colorpal;

import java.util.ArrayList;
import java.util.Collections;

public class ColorBoxList {
    private ArrayList<Integer> mOrigColors;
    private ArrayList<Integer> mColors;
    private ArrayList<Integer> mErrors;
    private int mLastClicked;

    public ColorBoxList(ArrayList<Integer> colors)
    {
        mOrigColors = new ArrayList<Integer>();
        mColors = new ArrayList<Integer>();
        mErrors = new ArrayList<Integer>();
        mLastClicked = -1;

        for(Integer i : colors)
        {
            mOrigColors.add(i);
            mColors.add(i);
        }
    }

    public void setColors(ArrayList<Integer> colors) // change dataset of colors
    {
        mOrigColors.clear();
        mColors.clear();
        mLastClicked = -1;

        for(Integer i : colors)
        {
            mOrigColors.add(i);
            mColors.add(i);
        }
    }

    public void handleClick(int pos) // select color, swap colors, or do nothing
    {
        if(pos == 0 || pos == mColors.size() - 1) // do nothing (selected an anchor value)
        {
            mLastClicked = -1; // reset
            return;
        }
        else if(mLastClicked == -1) // select color
        {
            mLastClicked = pos;
        }
        else // swap colors
        {
            swap(mLastClicked, pos);
            mLastClicked = -1;
        }
    }

    public void shuffleColors() // shuffle all colors (except first and last ones)
    {
        Collections.shuffle(mColors.subList(1, mColors.size() - 1)); // do not include first and last elements in shuffle
    }

    public void swap(int a, int b) // swap colors at pos a and pos b
    {
        int temp = mColors.get(a);
        mColors.set(a, mColors.get(b));
        mColors.set(b, temp);
    }

    public void addErrors() // add errors to list of errors
    {
        int score = 0;
        for(Integer i : mOrigColors)
        {
            mErrors.add((int) Math.pow(mColors.indexOf(i) - mOrigColors.indexOf(i), 2));
        }
    }

    public int getRedError() // calculate red error
    {
        if(mErrors.size() < 88)
        {
            return -1;
        }
        else
        {
            int red1 = 0;
            for(int i = 63; i <= 71; i++)
            {
                red1 += mErrors.get(i);
            }
            red1 /= 9; // average from red sector 1

            int red2 = 0;
            for(int i = 15; i <= 24; i++)
            {
                red2 += mErrors.get(i);
            }
            red2 /= 10; // average from red sector 2

            return red1 * red2; // find product
        }
    }

    public int getGreenError() // calculate green error
    {
        if(mErrors.size() < 88)
        {
            return -1;
        }
        else
        {
            int green1 = 0;
            for(int i = 56; i <= 62; i++)
            {
                green1 += mErrors.get(i);
            }
            green1 /= 7;  // average from green sector 1

            int green2 = 0;
            for(int i = 11; i <= 19; i++)
            {
                green2 += mErrors.get(i);
            }
            green2 /= 9;  // average from green sector 2

            return green1 * green2;  // find product
        }
    }

    public int getBlueError()  // calculate blue error
    {
        if(mErrors.size() < 88)
        {
            return -1;
        }
        else
        {
            int blue1 = 0;
            for(int i = 45; i <= 53; i++)
            {
                blue1 += mErrors.get(i);
            }
            blue1 /= 9;  // average from blue sector 1

            int blue2 = 0;
            for(int i = 1; i <= 6; i++)
            {
                blue2 += mErrors.get(i);
            }
            blue2 /= 6;  // average from blue sector 2

            return blue1 * blue2;  // find product
        }
    }


    public int getColorAtPos(int pos)
    {
        return mColors.get(pos);
    }

    public int getLastClicked()
    {
        return mLastClicked;
    }

    public int size()
    {
        return mColors.size();
    }
}