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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupLongClick();
        this.mListenerStatus = ListenerStatusEnum.NOT_LOADED;
        this.mListener = new BasicListener(this);
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

    @Override
    public void onDestroy() {
        this.mListener.stopListening();
        this.mListener = null;
    }

    public void notifyListenerSetupResult(ListenerStatusEnum updatedStatus) {
        Button listenButton = ((Button) findViewById(R.id.listenButton));
        switch (updatedStatus) {
            case NOT_LOADED:
                listenButton.setText(getResources().getText(R.string.listen_button_initializing_text));
                listenButton.setEnabled(false);
                break;
            case LOADING:
                listenButton.setText(getResources().getText(R.string.listen_button_initializing_text));
                listenButton.setEnabled(false);
                break;
            case LOADED:
                listenButton.setText(getResources().getText(R.string.listen_button_inactive_text));
                listenButton.setEnabled(true);
                break;
            case LISTENING:
                listenButton.setText(getResources().getText(R.string.listen_button_active_text));
                listenButton.setEnabled(true);
                break;
            case FAILED:
            default:
                listenButton.setText(getResources().getText(R.string.listen_button_setup_failed));
                listenButton.setEnabled(false);
        }

        // extra set activated above statements are probably not necessary...
        this.mListenerStatus = updatedStatus;
    }

    public void onListenButtonPress(View view) {
        if (this.mListenerStatus == ListenerStatusEnum.LOADED) {
            mListener.listen();
        } else if (this.mListenerStatus == ListenerStatusEnum.LISTENING) {
            mListener.stopListening();
        }
    }

    public enum ListenerStatusEnum {
        NOT_LOADED, LOADING, LOADED, FAILED, LISTENING
    }

    // a small easter egg
    private void setupLongClick() {
        ImageView carlton = (ImageView) findViewById(R.id.carltonView);
        carlton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.carlton_secret_message, Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private ListenerStatusEnum mListenerStatus = null;
    private BasicListener mListener ;
}
