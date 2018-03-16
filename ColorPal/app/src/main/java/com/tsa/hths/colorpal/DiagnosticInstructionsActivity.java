package com.tsa.hths.colorpal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class DiagnosticInstructionsActivity extends AppCompatActivity {

    private Button mStartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnostic_instructions_layout);

        mStartButton = (Button) findViewById(R.id.diagnostic_instructions_start_test_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DiagnosticInstructionsActivity.this, DiagnosticActivity.class);
                startActivity(i);
            }
        });
    }
}
