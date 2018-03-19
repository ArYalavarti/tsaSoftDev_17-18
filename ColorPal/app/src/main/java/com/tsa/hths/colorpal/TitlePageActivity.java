package com.tsa.hths.colorpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TitlePageActivity extends FragmentActivity {

    private Button mDiagnosticTestButton;
    private Button mCamera;
    private Button mPhoto;
    private ImageButton mHelpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_page_layout);

        //ImageView app_logo = (ImageView) findViewById(R.id.app_logo);
        //app_logo.setImageResource(R.drawable.app_logo);

        mDiagnosticTestButton = (Button) findViewById(R.id.title_page_diagnostic_button);
        mDiagnosticTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent diagnosticIntent = new Intent(TitlePageActivity.this, DiagnosticInstructionsActivity.class);
                startActivity(diagnosticIntent);
            }
        });

        mCamera = (Button) findViewById(R.id.title_page_camera_button);
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TitlePageActivity.this, ResultImagesActivity.class);
                i.putExtra("type","camera");
                startActivity(i);
            }
        });

        mPhoto = (Button) findViewById(R.id.title_page_photos_button);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TitlePageActivity.this, ResultImagesActivity.class);
                i.putExtra("type","gallery");
                startActivity(i);
            }
        });

        mHelpButton = (ImageButton) findViewById(R.id.help_button);
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructionsDialog dialog = new InstructionsDialog();
                dialog.show(getSupportFragmentManager(), "DIALOG");
            }
        });
        mHelpButton.setOnLongClickListener(new View.OnLongClickListener() { //this is just for testing we should probably take this out
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(TitlePageActivity.this, FilterScreen.class));
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {}
}