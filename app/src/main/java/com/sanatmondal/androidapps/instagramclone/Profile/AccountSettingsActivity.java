package com.sanatmondal.androidapps.instagramclone.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sanatmondal.androidapps.instagramclone.R;
import com.sanatmondal.androidapps.instagramclone.Utils.BottomNavigationViewHelper;
import com.sanatmondal.androidapps.instagramclone.Utils.SectionStatePageAdapter;

import java.util.ArrayList;

/**
 * Created by Sanat on 8/17/2017.
 */

public class AccountSettingsActivity extends AppCompatActivity {

    private static final String TAG = "AccountSettingsActivity";
    private Context mContext;
    private SectionStatePageAdapter pageAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;
    private static final int ACTIVITY_NUM = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);
        mContext = AccountSettingsActivity.this;
        Log.d(TAG, "onCreate: started");

        mViewPager = (ViewPager) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);

        setupSettingsList();
        setupBottomNavigationView();
        setupFragments();

        // setup the backarrow for navigation back to to ProfileActivity
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating back to profileActivity");
                finish();   // since this is an activity we can do finish to go back.
            }
        });
    }

    private void setupFragments(){
        pageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile_fragment));   // fragment 0
        pageAdapter.addFragment(new SignOutFragment(), getString(R.string.sing_out_fragment));           // fragment 1
    }

    private void setupViewPager(int fragmentNumber){
        Log.d(TAG, "setupViewPager: navigating to fragment number");
        mRelativeLayout.setVisibility(View.GONE);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }

    private void setupSettingsList(){
        Log.d(TAG, "setupSettingsList: initializing Account settings");
        ListView listview = (ListView) findViewById(R.id.lvAccountSettings);

        ArrayList<String> options  = new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment));      // fragment 0
        options.add(getString(R.string.sing_out_fragment));          // fragment 1

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemLongClick: navigating to fragment#: " + position);
                setupViewPager(position);
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

        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
