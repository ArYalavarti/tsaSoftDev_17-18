package com.tsa.hths.colorpal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.widget.TextView;

public class DiagnosticResultsActivity extends FragmentActivity {

    private TextView mResultsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diag_results_layout);

        mResultsTextView = (TextView) findViewById(R.id.diagnostic_test_results_text_view);
        mResultsTextView.setText(getResultsString());
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