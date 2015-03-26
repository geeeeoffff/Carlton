package com.geoffrathbone.geoff.carlton;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.internal.widget.ActivityChooserModel;

/**
 * Created by Geoff on 3/26/2015.
 */
public class Commander {
    /*
        launch the dialer activity
    * */
    public static void launchDialer(Activity activity){
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Setting this to my phone number so it's easy to call me in :-)
        // I'd comment this out if it were important to not pre-set a number here.
        dialIntent.setData((Uri.parse(Commander.TelPrefix + Commander.GeoffsPhoneNumber )));
        activity.startActivity(dialIntent);
    }

    /*
        launch the texting activity
    * */
    public static void launchSMS(Activity activity){
        Intent textIntent = new Intent(Intent.ACTION_VIEW);
        textIntent.setData(Uri.parse(Commander.SmsPrefix + Commander.GeoffsPhoneNumber));
        activity.startActivity(textIntent);
    }

    /*
        Launch my weather activity
    * */
    public static void launchWeatherActivity(Activity activity){
        Intent weatherIntent = new Intent(activity, WeatherActivity.class);
        activity.startActivity(weatherIntent);
    }
    final private static String GeoffsPhoneNumber = "2067887398";
    final private static String SmsPrefix = "sms:";
    final private static String TelPrefix = "tel:";
 }
