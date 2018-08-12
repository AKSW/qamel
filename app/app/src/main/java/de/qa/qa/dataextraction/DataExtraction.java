package de.qa.qa.dataextraction;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.MapView;

import org.json.JSONObject;

import java.util.ArrayList;



public class DataExtraction {
    private final LocationExtraction locationExtraction;
    private final OrientationExtraction orientationExtraction;
    private final ContactsExtraction contactsExtraction;
    private final CalendarExtraction calendarExtraction;
    Location location;

    Context context;

    public DataExtraction(Context context) {
        this.context = context;
        // Initialize extraction of location + orientation
        locationExtraction = new LocationExtraction();
        orientationExtraction = new OrientationExtraction();
        contactsExtraction = new ContactsExtraction();
        // Initialize calendar extraction
        calendarExtraction = new CalendarExtraction();

    }

    void start() {
        locationExtraction.onLocationChanged(location);
        orientationExtraction.startOrientationUpdates();
    }

    void stop() {
        orientationExtraction.stopOrientationUpdates();
    }

    JSONObject getData() {
        Double orientation = orientationExtraction.getHeading();
        String iCalendar = calendarExtraction.getCalendar();
        ArrayList<String> contacts = contactsExtraction.getContacts();

        // Building the JSON
        JSONObject json = new JSONObject();
        try {
            json.put("lat", String.valueOf(location.getLatitude()));
            json.put("long", String.valueOf(location.getLongitude()));
            json.put("orient", String.valueOf(orientation));
            json.put("vcards", contacts);
            json.put("ical", iCalendar);
        } catch (Exception e) {
        }
        // Returning the JSON String
        return json;
    }
}