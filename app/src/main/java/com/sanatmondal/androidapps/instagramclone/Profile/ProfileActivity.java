package com.sanatmondal.androidapps.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sanatmondal.androidapps.instagramclone.R;
import com.sanatmondal.androidapps.instagramclone.Utils.BottomNavigationViewHelper;
import com.sanatmondal.androidapps.instagramclone.Utils.GridImageAdapter;
import com.sanatmondal.androidapps.instagramclone.Utils.UniversalImageLoder;

import java.util.ArrayList;

/**
 * Created by Sanat on 8/15/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private Context mcontext = ProfileActivity.this;
    private ProgressBar mProgressBar;
    private ImageView profilePhoto;
    private static final int NUM_GRID_COLUMN = 3;

    // press alt+insert. it will show a pop up a view where u can select for override the method

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");

        setupBottomNavigationView();
        setupToolbar();
        setupActivityWidgets();
        setProfileImage();

        tempGridSetup();
    }


    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("http://www.desibucket.com/db2/01/28680/28680.jpg");
        imgURLs.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJvFmW4_s6oWQYT8ebE6X1it7u_X7aee-J120Oo3gXUxeQ2-P2");
        imgURLs.add("https://static01.nyt.com/images/2017/06/05/business/05RUTENEBERG-web-add/05RUTENEBERG-web-add-master768.jpg");
        imgURLs.add("https://media.vanityfair.com/photos/56abbecd74ddc1821611aeea/master/w_768,c_limit/hollywood-portfolio-2016-annie-leibovitz-02.jpg");
        imgURLs.add("http://blog.styyo.com/wp-content/uploads/2016/06/Sunny-Leone1-1024x776.jpg");
        imgURLs.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9P6bHUXclZtZ-C6gYSFCILyGu6Z7njxdSLvQsaqMMxQfyu0qYLg");
        imgURLs.add("http://www.masala.com/sites/default/files/styles/gallery_slideshow_cache_734/public/images/2014/12/17/Priyanka.jpg?itok=iX60dE4y");
        imgURLs.add("https://wallpapersite.com/images/pages/pic_w/5905.jpg");
        imgURLs.add("https://i.pinimg.com/236x/c7/49/d5/c749d5bedd4349760fc568caf0ef66e1--bollywood-actress.jpg");
        imgURLs.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5zY8LZ0ThJ1zf021MUzEXKVh6KZ29X18TvFaDCJNz-AJApTKk");

        setupImageGrid(imgURLs);
    }

    public void setupImageGrid(ArrayList<String> imgURLs){
        GridView gridView = (GridView) findViewById(R.id.gridView);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMN;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mcontext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);
    }

    /**
     * set the profile image in profile activity
     */
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: Setting profile image");
        //UniversalImageLoder.setImage();
        String imgURL = "media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAfeAAAAJDA4ODA4MTNjLTNiNjgtNGMzOS1iMTBjLTgwNzc5OGRiOWJjMA.jpg";
        UniversalImageLoder.setImage(imgURL, profilePhoto, mProgressBar, "https://");
    }

    /**
     * Initialize all the widgets
     */
    private void setupActivityWidgets(){
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
    }


    /**
     * Responsible for setting up the profile toolbar
     */
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigate to account settings.");
                Intent intent = new Intent(mcontext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView started");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        BottomNavigationViewHelper.enableNavigation(mcontext, bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
