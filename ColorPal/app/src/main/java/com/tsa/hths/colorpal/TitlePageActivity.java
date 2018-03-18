package com.tsa.hths.colorpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TitlePageActivity extends FragmentActivity {

    private Button mDiagnosticTestButton;
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

        mHelpButton = (ImageButton) findViewById(R.id.help_button);
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructionsDialog dialog = new InstructionsDialog();
                dialog.show(getSupportFragmentManager(), "DIALOG");
            }
        });
    }
}