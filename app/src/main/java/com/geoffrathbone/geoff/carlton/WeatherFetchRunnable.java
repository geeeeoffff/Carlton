package com.geoffrathbone.geoff.carlton;

/**
 * Created by Geoff on 3/26/2015.
 */
public class WeatherFetchRunnable implements Runnable {

    WeatherActivity activity = null;
    public WeatherFetchRunnable(WeatherActivity weatherActivity)
    {
        this.activity = weatherActivity;
    }

    @Override
    public void run() {
        // I considered using callable, but there's no need to return data, and I don't plan to
        // throw exceptions
        YahooWeatherSource source = new YahooWeatherSource();
        YahooWeatherData data = source.getWeatherData();
        UpdateWeatherActivtyRunnable updateRunnable = new UpdateWeatherActivtyRunnable(this.activity,data );
        // Have to make UI changes on UI thread
        this.activity.runOnUiThread(updateRunnable);

    }
}
