package com.geoffrathbone.geoff.carlton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Geoff on 3/26/2015.
 */
public class YahooWeatherSource {
    // Since the request is already happening in a background thread, I'm doing this synchronously
    // For the purposes of this app, Location is hard coded
    public YahooWeatherData getWeatherData()
    {
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(this.requestUrl));
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200){
                // return error data
                return YahooWeatherData.GenerateErrorData();
            }

            // At this point we should be green!
            // Borrowed this snippet from the internet
            StringBuilder builder = new StringBuilder();
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String resultString = builder.toString();
            return this.deserialize(resultString);
        }catch(Exception ex)
        {
            // Swallowing this exception.  If this were to be published, I might add some
            // retry logic.  You can retry by backing out and re-opening the page
            // for now, just pass back an error message in the weather object
            return YahooWeatherData.GenerateErrorData();
        }
    }

    private YahooWeatherData deserialize(String jsonString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(YahooWeatherData.class, new YahooWeatherDataDeserializer());
        Gson gson = gsonBuilder.create();
        YahooWeatherData result = gson.fromJson(jsonString,YahooWeatherData.class);
        return result;
    }

    final private String requestUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20item%20from%20weather.forecast%20where%20location%3D%2211206%22&format=json";
}
