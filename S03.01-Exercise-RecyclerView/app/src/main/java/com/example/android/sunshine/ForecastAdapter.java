package com.example.android.sunshine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by john on 19/11/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
    // (15) Add a class file called ForecastAdapter
    // (22) Extend RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>

    // (23) Create a private string array called mWeatherData
    String[] mWeatherData;

    // (47) Create the default constructor (we will pass in parameters in a later lesson)
    public ForecastAdapter() {
    }

    // (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // (17) Extend RecyclerView.ViewHolder
    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////
        // (18) Create a public final TextView variable called mWeatherTextView
        public final TextView mWeatherTextView;

        // (19) Create a constructor for this class that accepts a View as a parameter
        // (20) Call super(view) within the constructor for ForecastAdapterViewHolder
        // (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
        }
    }

    // (24) Override onCreateViewHolder
    // (25) Within onCreateViewHolder, inflate the list item xml into a view
    // (26) Within onCreateViewHolder, return a new ForecastAdapterViewHolder with the above view passed in as a parameter
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View forecastListItem = inflater.inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastAdapterViewHolder(forecastListItem);
    }


    // (27) Override onBindViewHolder
    // (28) Set the text of the TextView to the weather for this list item's position
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
       holder.mWeatherTextView.setText(mWeatherData[position]);
    }


    // (29) Override getItemCount
    // (30) Return 0 if mWeatherData is null, or the size of mWeatherData if it is not null
    @Override
    public int getItemCount() {
        if (mWeatherData != null) {
            return mWeatherData.length;
        }
        return 0;
    }


    // (31) Create a setWeatherData method that saves the weatherData to mWeatherData
    // (32) After you save mWeatherData, call notifyDataSetChanged
    public void setWeatherData(String[] mWeatherData) {
        this.mWeatherData = mWeatherData;
        notifyDataSetChanged();
    }

    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
}
