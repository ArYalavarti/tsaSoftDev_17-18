package com.tsa.hths.colorpal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ResultImagesActivity extends FragmentActivity {

    public final static int RESOLUTION = 200;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private byte[] mOriginalImage;
    private byte[] mProcessedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_images);

        getImages();


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

    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 101;
    private static final int CAMERA_PIC_REQUEST = 000;
    private static final int RESULT_LOAD_IMAGE = 111;

    private void getImages() {
        String type = getIntent().getExtras().getString("type");
        if (type.equals("camera")) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } else if (type.equals("gallery")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                List<String> permissions = new ArrayList<String>();
                if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

                if (!permissions.isEmpty()) {
                    requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
                }
            }
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            this.onBackPressed();
            return;
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            processResults(image);
        } else if (requestCode == RESULT_LOAD_IMAGE) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(picturePath);
            processResults(image);
        }
    }

    private Bitmap processImage(Bitmap originalImage, Boolean filter) {
        int nh = (int) (originalImage.getHeight() * ( ((double) RESOLUTION) / originalImage.getWidth()));
        Bitmap image = Bitmap.createScaledBitmap(originalImage, RESOLUTION, nh, true);
        if (filter) {
            ImageFilter filterer = new ImageFilter();
            image = filterer.filterImage(image, ColorBlindnessCalc.getColorBlindness(this));
        }
        return image;
    }

    private void processResults(Bitmap image) {
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        processImage(image, false).compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] byteArray1 = stream1.toByteArray();

        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        processImage(image, true).compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] byteArray2 = stream2.toByteArray();

        mOriginalImage = byteArray1;
        mProcessedImage = byteArray2;

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    Toast.makeText(ResultImagesActivity.this, "" + x + ","+ y,Toast.LENGTH_LONG).show();

                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultImagesActivity.this, TitlePageActivity.class));
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



