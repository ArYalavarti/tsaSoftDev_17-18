package com.tsa.hths.colorpal;

import java.util.ArrayList;

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

    public void handleClick(int pos)
    {
        if(mLastClicked == -1)
        {
            mLastClicked = pos;
        }
        else
        {
            swap(mLastClicked, pos);
            mLastClicked = -1;
        }
    }

    public void swap(int a, int b)
    {
        int temp = mColors.get(a);
        mColors.set(a, mColors.get(b));
        mColors.set(b, temp);
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
