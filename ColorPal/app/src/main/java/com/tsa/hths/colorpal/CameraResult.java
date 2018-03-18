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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CameraResult extends AppCompatActivity {

    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 101;
    private static final int CAMERA_PIC_REQUEST = 000;
    private static final int RESULT_LOAD_IMAGE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            image = processImage(image);
            ImageView imageview = (ImageView) findViewById(R.id.camera_result_image); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        } else if (requestCode == RESULT_LOAD_IMAGE) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.camera_result_image);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Bitmap image = BitmapFactory.decodeFile(picturePath);
//            int nh = (int) (image.getHeight() * (512.0 / image.getWidth()));
//            Bitmap scaledImage = Bitmap.createScaledBitmap(image, 512, nh, true);
            image = processImage(image);
            imageView.setImageBitmap(image);
        }
    }

    private Bitmap processImage(Bitmap originalImage) {
        int nh = (int) (originalImage.getHeight() * (100.0 / originalImage.getWidth()));
        Bitmap image = Bitmap.createScaledBitmap(originalImage, 100, nh, true);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getPixel(x,y);
//                Log.i("color", color + "");
                color *= 2;
                image.setPixel(x,y,color);
            }
        }
        return image;
    }
}
