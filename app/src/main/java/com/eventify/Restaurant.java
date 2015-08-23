package com.eventify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav on 22-08-2015.
 */
public class Restaurant {
    public String address, name, image, number, URL;
    public double latitude, longitude;



    public Restaurant(String name, String address, String image, String number, String URL, double latitude, double longitude){
        this.name  = name;
        this.address = address;
        this.image = image;
        this.number = number;
        this.URL = URL;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}


