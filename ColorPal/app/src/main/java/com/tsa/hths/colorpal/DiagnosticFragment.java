package com.tsa.hths.colorpal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;

public class DiagnosticFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button mNextButton;

    private ColorBoxList mColorBoxList;

    private int mRound = 0;
    private int[] mResults = new int[4];

    public static DiagnosticFragment getInstance()
    {
        return new DiagnosticFragment();
    }

    private class ColorBoxViewHolder extends RecyclerView.ViewHolder
    {
        private View mView;
        private int mPos;

        public ColorBoxViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mPos = 0;

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mColorBoxList.handleClick(mPos);
                    mView.setSelected(true);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }

        public void assign(int pos, int color)
        {
            mPos = pos;
            mView.setBackgroundColor(color);
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

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        mColorBoxList = new ColorBoxList(colors);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.diagnostic_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(new ColorBoxAdapter(mColorBoxList));

        mNextButton = (Button) v.findViewById(R.id.diagnostic_test_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResults[mRound++] = mColorBoxList.getScore();
                mColorBoxList.setColors(DiagnosticTestColorData.getColorsForRound(mRound));
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });


        return v;
    }
}
