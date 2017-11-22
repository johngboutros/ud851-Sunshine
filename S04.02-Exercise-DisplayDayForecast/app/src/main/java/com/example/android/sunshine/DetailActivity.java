package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    TextView detailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDisplay = (TextView) findViewById(R.id.tv_detail_display);

        // (2) Display the weather forecast that was passed from MainActivity
        String stringExtra = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (stringExtra != null) {
            detailDisplay.setText(stringExtra);
        }
    }
}