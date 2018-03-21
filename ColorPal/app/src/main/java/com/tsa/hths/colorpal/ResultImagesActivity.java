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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

import java.io.ByteArrayOutputStream;

public class ResultImagesActivity extends AppCompatActivity {

    public final static int RESOLUTION = 200; //resolution of displayed images

    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 101;
    private static final int CAMERA_PIC_REQUEST = 000;
    private static final int RESULT_LOAD_IMAGE = 111;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Bitmap mProcessedImage;

    private byte[] mOriginalImageByteArray;
    private byte[] mProcessedImageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_images);

        getImages(); //either from camera or gallery and display


    }

    private void getImages() {
        String type = getIntent().getExtras().getString("type");
        if (type.equals("camera")) { //launch camera
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } else if (type.equals("gallery")) { //select from gallery
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //get permissions to access gallery
                int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
                }
            }
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) { // return to TitlePageActivity if they do not take/select an image
            this.onBackPressed();
            return;
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data"); //get image
            processResults(image); //filter and display image
        } else if (requestCode == RESULT_LOAD_IMAGE) {
            Uri selectedImage = data.getData(); //get image data
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(picturePath); //get image
            processResults(image); //filter and display image
        }
    }

    private Bitmap processImage(Bitmap originalImage, Boolean filter) {
        int nh = (int) (originalImage.getHeight() * (((double) RESOLUTION) / originalImage.getWidth()));
        Bitmap image = Bitmap.createScaledBitmap(originalImage, RESOLUTION, nh, true); //lower image resolution
        if (filter) {
            ImageFilter filterer = new ImageFilter();
            image = filterer.filterImage(image, ColorBlindnessCalc.getColorBlindness(this));
        }
        return image;
    }

    private String RGBInterpreter(int pixel) {
        String trueColor = "";

        final double S_LOWER = 0.06;
        final double S_MID = 0.09;

        final double L_HIGHER = .97;
        final double L_LOWER = .08;
        final double L_MID = .35;


        int r = Color.red(pixel);
        int b = Color.blue(pixel);
        int g = Color.green(pixel);

        float[] hsl = new float[3];
        ColorUtils.colorToHSL(pixel, hsl);

        float hue = hsl[0];
        float sat = hsl[1];
        float light = hsl[2];

        if (light > L_LOWER) {

            if (light > L_HIGHER) {
                trueColor = "WHITE";
            } else {
                if (sat < S_LOWER || (sat < S_MID && light < L_MID)) {
                    trueColor = "GRAY";
                } else {

                    if (hue >= 20 && hue < 50) {
                        trueColor = "ORANGE";
                    } else if (hue >= 50 && hue < 70) {
                        trueColor = "YELLOW";
                    } else if (hue >= 70 && hue < 150) {
                        trueColor = "GREEN";
                    } else if (hue >= 150 && hue < 170) {
                        trueColor = "TEAL";
                    } else if (hue >= 170 && hue < 220) {
                        trueColor = "BLUE";
                    } else if (hue >= 220 && hue < 265) {
                        trueColor = "PURPLE";
                    } else if (hue >= 265 && hue < 285) {
                        trueColor = "MAGENTA";
                    } else if (hue >= 285 && hue < 335) {
                        trueColor = "PINK";
                    } else if (hue >= 335 && hue < 356) {
                        trueColor = "CORAL";
                    } else {
                        trueColor = "RED";
                    }

                }
            }

        } else {
            trueColor = "BLACK";
        }

        return (trueColor);
    }

    private void processResults(Bitmap image) {
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        processImage(image, false).compress(Bitmap.CompressFormat.PNG, 100, stream1); //resize image and put in byte[]
        byte[] byteArray1 = stream1.toByteArray();

        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        mProcessedImage = processImage(image, true);
        mProcessedImage.compress(Bitmap.CompressFormat.PNG, 100, stream2); //resize and filter image and put in byte[]
        byte[] byteArray2 = stream2.toByteArray();

        mOriginalImageByteArray = byteArray1; //save original image to be used in fragment
        mProcessedImageByteArray = byteArray2; //save filtered image to be used in fragment

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter); //set screen to slider page
        mPager.setCurrentItem(1, true); //launch on filtered image

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    mPager.setDrawingCacheEnabled(true);
                    mPager.buildDrawingCache();

                    Bitmap bitmap = mPager.getDrawingCache();
                    int pixel = bitmap.getPixel(((int) x), ((int) y));
                    String output;
                    if (pixel != 0) {
                        output = RGBInterpreter(pixel);
                    } else {
                        output = "WHITE";
                    }

                    Toast.makeText(ResultImagesActivity.this, "True Color:" + output, Toast.LENGTH_SHORT).show();
                    //(output[0]) + " " + output[1] + " " + output[2]
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultImagesActivity.this, TitlePageActivity.class)); //return to home page
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_result_images, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //get permissions to write to storage
                    int hasReadPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
                    }
                }

                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), mProcessedImage,null, null);
                Uri bitmapUri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                startActivity(Intent.createChooser(intent, "Share"));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: //display original image on left page
                    Bundle args0 = new Bundle();
                    args0.putByteArray("image", mOriginalImageByteArray);

                    fragment = Fragment.instantiate(ResultImagesActivity.this, ImageFragment.class.getName(), args0);
                    break;
                case 1: //display filtered image on right page
                    Bundle args1 = new Bundle();
                    args1.putByteArray("image", mProcessedImageByteArray);

                    fragment = Fragment.instantiate(ResultImagesActivity.this, ImageFragment.class.getName(), args1);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = new String[]{"Original", "Filtered"};
            return titles[position];
        }
    }
}



