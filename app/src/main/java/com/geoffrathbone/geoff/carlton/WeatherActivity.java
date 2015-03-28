package com.geoffrathbone.geoff.carlton;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class WeatherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ((CarltonApplication)this.getApplicationContext()).loadWeatherData(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Callback for updating the UI once the weather data is received from the Yahoo Backend
    * */
    public void updateWeatherData(final YahooWeatherData weatherData)
    {
        TextView weatherTitle = (TextView)findViewById(R.id.weatherActivityTitleTextView);
        weatherTitle.setText(weatherData.getTitle());
        TextView degrees = (TextView)findViewById(R.id.weatherActivitydegreesView);
        degrees.setText(weatherData.getTemp());
        TextView condition = (TextView)findViewById(R.id.weatherActivityconditionView);
        condition.setText(weatherData.getCond());
    }
}
