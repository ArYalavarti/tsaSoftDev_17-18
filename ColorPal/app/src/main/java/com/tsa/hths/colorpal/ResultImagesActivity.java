package com.tsa.hths.colorpal;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ResultImagesActivity extends FragmentActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private byte[] mOriginalImage;
    private byte[] mProcessedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_images);

        mOriginalImage = getIntent().getByteArrayExtra("originalImage");
        mProcessedImage = getIntent().getByteArrayExtra("processedImage");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =null;
            switch (position) {
                case 0:

                    Bundle args0 = new Bundle();
                    args0.putByteArray("image",mOriginalImage);

                    fragment = Fragment.instantiate(ResultImagesActivity.this, ImageFragment.class.getName(), args0);
                    break;
                case 1:

                    Bundle args1 = new Bundle();
                    args1.putByteArray("image",mProcessedImage);

                    fragment = Fragment.instantiate(ResultImagesActivity.this, ImageFragment.class.getName(), args1);
                    break; }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}


//    @Override
//    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
//    }



