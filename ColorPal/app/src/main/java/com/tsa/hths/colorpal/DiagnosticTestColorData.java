package com.tsa.hths.colorpal;

import android.graphics.Color;

import java.util.ArrayList;

public class DiagnosticTestColorData {
    public static ArrayList<Integer> getColorsForRound(int round)
    {
        ArrayList<Integer> colors = new ArrayList<>();
        if(round == 0)
        {
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);
        }
        else if(round == 1)
        {
            colors.add(Color.CYAN);
            colors.add(Color.YELLOW);
            colors.add(Color.MAGENTA);
        }

        return colors;
    }
}
