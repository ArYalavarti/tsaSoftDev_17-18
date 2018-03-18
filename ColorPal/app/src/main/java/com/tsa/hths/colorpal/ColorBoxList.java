package com.tsa.hths.colorpal;

import java.util.ArrayList;
import java.util.Collections;

public class ColorBoxList {
    private ArrayList<Integer> mOrigColors;
    private ArrayList<Integer> mColors;
    private int mLastClicked;

    public ColorBoxList(ArrayList<Integer> colors)
    {
        mOrigColors = new ArrayList<Integer>();
        mColors = new ArrayList<Integer>();
        mLastClicked = -1;

        for(Integer i : colors)
        {
            mOrigColors.add(i);
            mColors.add(i);
        }
    }

    public void setColors(ArrayList<Integer> colors)
    {
        mOrigColors.clear();
        mColors.clear();

        for(Integer i : colors)
        {
            mOrigColors.add(i);
            mColors.add(i);
        }
    }

    public void handleClick(int pos)
    {
        if(pos == 0 || pos == mColors.size() - 1)
        {
            mLastClicked = -1; // reset
            return;
        }
        else if(mLastClicked == -1)
        {
            mLastClicked = pos;
        }
        else
        {
            swap(mLastClicked, pos);
            mLastClicked = -1;
        }
    }

    public void shuffleColors()
    {
        Collections.shuffle(mColors.subList(1, mColors.size() - 1)); // do not include first and last elements in shuffle
    }

    public void swap(int a, int b)
    {
        int temp = mColors.get(a);
        mColors.set(a, mColors.get(b));
        mColors.set(b, temp);
    }

    public int getScore()
    {
        int score = 0;
        for(Integer i : mColors)
        {
            score += Math.pow(mColors.indexOf(i) - mOrigColors.indexOf(i), 2);
        }
        return score;
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
