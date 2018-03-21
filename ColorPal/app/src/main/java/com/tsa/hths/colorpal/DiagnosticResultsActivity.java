package com.tsa.hths.colorpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        String result = ColorBlindnessCalc.getColorBlindness(this);

        String out = "";

        if(result.equals(ColorBlindnessCalc.RED))
        {
            out += "Protanomaly (RED)";
        }
        else if(result.equals(ColorBlindnessCalc.GREEN))
        {
            out += "Deuteranomaly (GREEN)";
        }
        else if(result.equals(ColorBlindnessCalc.BLUE))
        {
            out += "Tritanomaly (BLUE)";
        }
        else if(result.equals(ColorBlindnessCalc.FULL))
        {
            out += "Fully color blind";
        }
        else
        {
            out += "Not color blind";
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        int redError = sp.getInt(getResources().getString(R.string.red_error), 0);
        int greenError = sp.getInt(getResources().getString(R.string.green_error), 0);
        int blueError = sp.getInt(getResources().getString(R.string.blue_error), 0);

        Log.e("COLOR_ERRORS", "RED: " + redError + " GREEN: " + greenError + " BLUE: " + blueError);

        //double redPercent = 100.0 * redError / (redError + greenError + blueError);
        //double greenPercent = 100.0 * greenError / (redError + greenError + blueError);
        //double bluePercent = 100.0 * blueError / (redError + greenError + blueError);

        //out += ("\nR: " + redPercent + "%, G: " + greenPercent + "%, B: " + bluePercent + "%");
        return out;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, TitlePageActivity.class);
        startActivity(i);
    }
}