package com.sanatmondal.androidapps.instagramclone.Utils;

/**
 * Created by Sanat on 9/15/2017.
 */

public class StringManipulation {
    public static String expandUsername(String username){
        return username.replace(",", " ");
    }
    public static String condenseUsername(String username){
        return username.replace(" ", ",");
    }
}
