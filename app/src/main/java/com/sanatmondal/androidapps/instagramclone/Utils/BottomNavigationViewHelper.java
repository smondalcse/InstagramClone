package com.sanatmondal.androidapps.instagramclone.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sanatmondal.androidapps.instagramclone.Home.HomeActivity;
import com.sanatmondal.androidapps.instagramclone.Likes.LikesActivity;
import com.sanatmondal.androidapps.instagramclone.Profile.ProfileActivity;
import com.sanatmondal.androidapps.instagramclone.R;
import com.sanatmondal.androidapps.instagramclone.Search.SearchActivity;
import com.sanatmondal.androidapps.instagramclone.Share.ShareActivity;

/**
 * Created by Sanat on 8/15/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    /**
     * check out this link for BottomNavigationViewEx
     * https://github.com/ittianyu/BottomNavigationViewEx
     * @param bottomNavigationViewEx
     */
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    /**
     * how gonna be navigate to the activity
     */

    public  static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_house:
                        /**
                         * BottomNavigation doesn't have any context bcoz its an object class and its not an activity.
                         * Context references where(which activity) its comming from. From HomeActivity this params is comming
                         * In regular situation we use like HomeActivity.this
                         */
                        Intent intent1  = new Intent(context, HomeActivity.class);  //ACTIVITY-NUM = 0
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2  = new Intent(context, SearchActivity.class);  //ACTIVITY-NUM = 1
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_circle:
                        Intent intent3  = new Intent(context, ShareActivity.class);  //ACTIVITY-NUM = 2
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_alert:
                        Intent intent4  = new Intent(context, LikesActivity.class);  //ACTIVITY-NUM = 3
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_profile:
                        Intent intent5  = new Intent(context, ProfileActivity.class);  //ACTIVITY-NUM = 4
                        context.startActivity(intent5);
                        break;

                }

                return false;
            }
        });
    }

}
