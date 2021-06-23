package com.softech.bipldirect.Models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Util.Preferences;

import net.orange_box.storebox.StoreBox;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Developed by Hasham.Tahir on 1/29/2016.
 */

public class Event {

    @SerializedName("dateTime")
    @Expose
    private Long dateTime;
    @SerializedName("body")
    @Expose
    private String body;

    /**
     * No args constructor for use in serialization
     */
    public Event() {
    }

    /**
     * @param body
     * @param dateTime
     */
    public Event(Long dateTime, String body) {
        this.dateTime = dateTime;
        this.body = body;
    }

    public static void add(Context context, Event event) {

        Gson gson = new Gson();
        Preferences preferences = StoreBox.create(context, Preferences.class);
        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        ArrayList<Event> eventArrayList = new ArrayList<>();

        eventArrayList.add(event);

        String serialized = gson.toJson(eventArrayList, listType);

//        Log.d("add", "serialized: " + serialized);

        String events = preferences.getEvents();

        if (events == null) {

            preferences.setEvents(serialized);
        } else {


            JsonParser parser = new JsonParser();
            JsonElement eventsElement = parser.parse(events);
            JsonArray eventArray = eventsElement.getAsJsonArray();


//            Log.d("add", "eventArray: " + eventArray);


            List<Event> yourList = gson.fromJson(eventArray, listType);


            yourList.add(event);

//            Log.d("add", "yourList: " + gson.toJson(yourList, listType));

            preferences.setEvents(gson.toJson(yourList, listType));
        }


    }


    public static List<Event> getAllEvents(Context context) {

        Gson gson = new Gson();
        Preferences preferences = StoreBox.create(context, Preferences.class);
        Type listType = new TypeToken<List<Event>>() {
        }.getType();

        List<Event> evs = gson.fromJson(preferences.getEvents(), listType);

        Collections.sort(evs, new EventComparatorByTime());

        return evs;

    }

    /**
     * @return The dateTime
     */
    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @param dateTime The dateTime
     */
    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    public String getDateTimeString() {
        return new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a", Locale.UK).format(new Date(dateTime));
    }

    static class EventComparatorByTime implements Comparator<Event> {
        @Override
        public int compare(Event o1, Event o2) {
            return o2.getDateTime().compareTo(o1.getDateTime());
        }
    }

}
