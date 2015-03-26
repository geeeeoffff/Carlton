package com.geoffrathbone.geoff.carlton;

/**
 * Created by Geoff on 3/26/2015.
 */
public class UpdateWeatherActivtyRunnable implements Runnable {

    public UpdateWeatherActivtyRunnable(WeatherActivity activity,YahooWeatherData weatherData)
    {
        this.weatherActivity = activity;
        this.yahooWeatherData = weatherData;
    }

    @Override
    public void run()
    {
        weatherActivity.updateWeatherData(yahooWeatherData);
    }

    YahooWeatherData yahooWeatherData = null;
    WeatherActivity weatherActivity = null;
}
