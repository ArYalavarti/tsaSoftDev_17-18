package com.tsa.hths.colorpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

public class TitlePageActivity extends FragmentActivity {

    private Button mDiagnosticTestButton;
    private Button mAnalyzeImageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_page_layout);

        mDiagnosticTestButton = (Button) findViewById(R.id.title_page_diagnostic_button);
        mDiagnosticTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent diagnosticIntent = new Intent(TitlePageActivity.this, DiagnosticInstructionsActivity.class);
                startActivity(diagnosticIntent);
            }
        });

        mAnalyzeImageButton = (Button) findViewById(R.id.title_page_analysis_button);
    }
}