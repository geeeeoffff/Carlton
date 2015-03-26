package com.geoffrathbone.geoff.carlton;

import android.app.Application;

/**
 * Created by Geoff on 3/26/2015.
 */
public class CarltonApplication extends Application {
    // Extending the application class to hold the instance of Commander, basically
    @Override
    public void onCreate()
    {
        super.onCreate();

        // creating this here, I don't feel that it belongs in an activity class

    }

    /*
        Method to be used for loading weather data from Yahoo
    * */
    public void loadWeatherData(WeatherActivity weatherActivity)
    {
        WeatherFetchRunnable weatherCallable = new WeatherFetchRunnable(weatherActivity);
        // It should be okay to destroy any existing thread.  The page was re-opened and we'll refresh the data
        // IN a published app it would be better to cache some of this data.
        this.threadHandle = new Thread(weatherCallable);
        threadHandle.start();
    }

    private Thread threadHandle = null;
    private Commander commander = null;
}
