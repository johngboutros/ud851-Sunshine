package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.sunshine.data.WeatherContract.WeatherEntry;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

// (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {

    // JB: TAG
    private final static String TAG = "SunshineSyncTask";

    // (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    synchronized public static void syncWeather(Context context) {
        // (3) Within syncWeather, fetch new weather data
        URL url = NetworkUtils.getUrl(context);
        String responseFromHttpUrl = null;
        try {
            responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(url);


            // (4) If we have valid results, delete the old data and insert the new
            if (responseFromHttpUrl == null) {
                Log.e(TAG, "null response!");
                return;
            }

            ContentValues[] values =
                    OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, responseFromHttpUrl);

            if (values == null || values.length == 0) {
                Log.e(TAG, "null values!");
                return;
            }

            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.delete(WeatherEntry.CONTENT_URI, null, null);
            contentResolver.bulkInsert(WeatherEntry.CONTENT_URI, values);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}