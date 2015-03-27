package com.geoffrathbone.geoff.carlton;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by Geoff on 3/27/2015.
 */
public class YahooWeatherDataDeserializer implements JsonDeserializer<YahooWeatherData>{
    @Override
    public YahooWeatherData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jdc){
        try{
            final JsonObject jsonObj = jsonElement.getAsJsonObject();
            final JsonObject queryObj = jsonObj.get("query").getAsJsonObject();
            // Not going to grab all the elements, all we need are those that will be displayed later
            final JsonObject resultsObj = queryObj.get("results").getAsJsonObject();
            final JsonObject channelObj = resultsObj.get("channel").getAsJsonObject();
            final JsonObject itemObj = channelObj.get("item").getAsJsonObject();
            final JsonObject conditionObj = itemObj.get("condition").getAsJsonObject();
            final String title = itemObj.get("title").getAsString();
            final String temp = conditionObj.get("temp").getAsString();
            final String condition = conditionObj.get("text").getAsString();
            return new YahooWeatherData(title,temp,condition);

        }catch(Exception ex)
        {
            // cutting corners for this example app
            return YahooWeatherData.GenerateErrorData();
        }
    }

}
