package com.sanatmondal.androidapps.instagramclone.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sanatmondal.androidapps.instagramclone.R;
import com.sanatmondal.androidapps.instagramclone.Utils.UniversalImageLoder;

/**
 * Created by Sanat on 8/17/2017.
 */

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";

    private ImageView mProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);

        mProfilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

        setProfileImage();

        // Back arrow for navigating to "ProfileActivity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        return  view;
    }



    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile images");
        String imgURL = "media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAfeAAAAJDA4ODA4MTNjLTNiNjgtNGMzOS1iMTBjLTgwNzc5OGRiOWJjMA.jpg";
        UniversalImageLoder.setImage(imgURL, mProfilePhoto, null, "https://");
    }

}
