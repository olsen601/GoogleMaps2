package com.RyanOlsen;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here

        String key = null;
        //Read key from file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Key.txt"));
            key = reader.readLine();
            System.out.println(key);   //Just checking...
        } catch (Exception ioe) {
            System.out.println("No key file found, or could not read key. Please verify key.txt present");
            System.exit(-1);   //Quit program - need to fix before continuing.
        }


        //Create a context - this authenticates you to Google using your key and verifies which map services you are allowed to use
        //Do this one time at the start of your code, once you've read your key
        GeoApiContext context = new GeoApiContext().setApiKey(key);

        //According to the ElevationAPI docs, to get the elevation of a particular point, you need to know it's latitude and longitude
        //http://googlemaps.github.io/google-maps-services-java/v0.1.15/javadoc/
        //I looked up the lat and long of MCTC
        //Which are 44.973074, -93.283356
        //Could have also used another Google service to look up the lat and long of a place... that will be your job in lab

        //Create a LatLng object, to represent a point on the earth by latitude and longitude
        //We'll use ElevationAPI to request the elevation of this LatLng.
        LatLng mctcLatLng = new LatLng(44.973074, -93.283356);

        //Use ElevationApi class to request Elevation data
        //The await() method makes your code pause and wait for the results to come back - a synchronous method call
        //Can also use these methods asynchronously - your code keeps running while waiting for results to come back, can multitask.
        //Advanced reading... async vs. synchronous calls - we'll keep it simple and use synchronous
        ElevationResult[] results = ElevationApi.getByPoints(context, mctcLatLng).await();

        // All of the APIs seem to return an array of results. We only expect one result - the elevation of this point
        // So we'll expect an array with 1 element (the elevation of MCTC) or 0 elements (if the location is not found or invalid)

        if (results.length >= 1) {
            //Get first ElevationResult object
            ElevationResult mctcElevation = results[0];
            System.out.println("The elevation of MCTC above sea level is " + mctcElevation.elevation + " meters");
            //Let's do some rounding :)
            System.out.println(String.format("The elevation of MCTC above sea level is %.2f meters.", mctcElevation.elevation));

        }


    }
}