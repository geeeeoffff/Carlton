package com.geoffrathbone.geoff.carlton;

/**
 * Created by Geoff on 3/26/2015.
 */
public class YahooWeatherData {


    public YahooWeatherData(String title, String temp, String cond) {
        this.mTitle = title;
        this.mTemperature = temp;
        this.mCondition = cond;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getTemp() {
        return this.mTemperature;
    }

    public String getCond() {
        return this.mCondition;
    }

    // In case we hit some kind of error.  So the scenario isn't broken.
    // Better error handling would be needed for a 'real' application
    public static YahooWeatherData GenerateErrorData() {
        return new YahooWeatherData("Error", "??", "mysterious");
    }

    private String mTitle = null;
    private String mTemperature = null;
    private String mCondition = null;
}
