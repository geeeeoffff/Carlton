/*
by Geoff Rathbone
This code is an exercise for the mysterious Insensi company.  Here's the prompt for external folk:

I want you to create an Android app that will use the CMUSphinx speech recognition C library and
recognize 3 different actions (words) : “call”, “text”, “weather”.

The app will include a button to start/stop recording an action.
The weather action will trigger a view that will display the weather of the day based on the user
location.

Instructions:
- You must compile the C library for either arm or x86
(http://cmusphinx.sourceforge.net/wiki/tutorialpocketsphinx)
- You will communicate with a 3rd party API to get the local weather
(https://developer.yahoo.com/weather/)
* */

package com.geoffrathbone.geoff.carlton;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
