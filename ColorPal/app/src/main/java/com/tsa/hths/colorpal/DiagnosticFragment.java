package com.tsa.hths.colorpal;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class DiagnosticFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button mNextButton;

    private ColorBoxList mColorBoxList;

    private int mRound = 0;

    public static DiagnosticFragment getInstance()
    {
        return new DiagnosticFragment();
    }

    private class ColorBoxViewHolder extends RecyclerView.ViewHolder
    {
        private View mView;
        private LinearLayout mLinLayout;
        private int mPos;

        public ColorBoxViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.color_box_view);
            mLinLayout = itemView.findViewById(R.id.color_box_linear_layout);
            mPos = 0;

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mColorBoxList.handleClick(mPos);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }

        public void assign(int pos, int color)
        {
            mPos = pos;
            mView.setBackgroundColor(color);
            if(mColorBoxList.getLastClicked() != -1 && mPos == mColorBoxList.getLastClicked())
            {
                mLinLayout.setBackgroundColor(Color.WHITE);
            }
            else
            {
                mLinLayout.setBackgroundColor(Color.BLACK);
            }
        }
    }

    private class ColorBoxAdapter extends RecyclerView.Adapter<ColorBoxViewHolder>
    {
        ColorBoxList mColors;

        public ColorBoxAdapter(ColorBoxList colors)
        {
            mColors = colors;
        }

        @Override
        public ColorBoxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.color_box_layout, parent, false);
            return new ColorBoxViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ColorBoxViewHolder holder, int position) {
            holder.assign(position, mColors.getColorAtPos(position));
        }

        @Override
        public int getItemCount() {
            return mColors.size();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diagnostic_test_fragment_layout, container, false);

        mColorBoxList = new ColorBoxList(DiagnosticTestColorData.getColorsForRound(mRound, getActivity()));
        mColorBoxList.shuffleColors();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.diagnostic_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(new ColorBoxAdapter(mColorBoxList));

        mNextButton = (Button) v.findViewById(R.id.diagnostic_test_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mColorBoxList.addErrors();
                mRound++;

                if(mRound > 3) {
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putInt(getResources().getString(R.string.red_error), mColorBoxList.getRedError()).apply();
                    sp.edit().putInt(getResources().getString(R.string.green_error), mColorBoxList.getGreenError()).apply();
                    sp.edit().putInt(getResources().getString(R.string.blue_error), mColorBoxList.getBlueError()).apply();

                    Log.e("COLOR ERRORS", "RED: " + mColorBoxList.getRedError());

                    Intent i = new Intent(getActivity(), DiagnosticResultsActivity.class);
                    startActivity(i);
                }
                else {
                    mColorBoxList.setColors(DiagnosticTestColorData.getColorsForRound(mRound, getActivity()));
                    mColorBoxList.shuffleColors();
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });


        return v;
    }
}
