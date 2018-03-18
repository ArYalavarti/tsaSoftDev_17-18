package com.tsa.hths.colorpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class DiagnosticResultsActivity extends AppCompatActivity {

    private TextView mResultsTextView;
    private Button mReturnHomeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diag_results_layout);

        mResultsTextView = (TextView) findViewById(R.id.diagnostic_test_results_text_view);
        mResultsTextView.setText(getResultsString());

        mReturnHomeButton = (Button) findViewById(R.id.diagnostic_test_results_return_home_button);
        mReturnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DiagnosticResultsActivity.this, TitlePageActivity.class);
                startActivity(i);
            }
        });
    }

    private String getResultsString()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        int result1 = sp.getInt(getResources().getString(R.string.diagnostic_test_results_1), 0);
        int result2 = sp.getInt(getResources().getString(R.string.diagnostic_test_results_2), 0);
        int result3 = sp.getInt(getResources().getString(R.string.diagnostic_test_results_3), 0);
        int result4 = sp.getInt(getResources().getString(R.string.diagnostic_test_results_4), 0);

        return "" + result1 + "\n" + result2 + "\n" + result3 + "\n" + result4;
    }
}