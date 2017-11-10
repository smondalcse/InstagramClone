package com.sanatmondal.androidapps.instagramclone.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanatmondal.androidapps.instagramclone.Models.User;
import com.sanatmondal.androidapps.instagramclone.Models.UserAccountSettings;
import com.sanatmondal.androidapps.instagramclone.R;

/**
 * Created by Sanat on 9/15/2017.
 */

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private Context mContext;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    public FirebaseMethods(Context mContext){
        mAuth = FirebaseAuth.getInstance();
        this.mContext = mContext;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkIfUsernameExits(String username, DataSnapshot dataSnapshot){
        Log.d(TAG, "checkIfUsernameExits: cheking if " + username  + " already exists");
        User user = new User();

        // Datasnapshot contains every node inside the DB
        for(DataSnapshot ds: dataSnapshot.child(userID).getChildren()){
            Log.d(TAG, "checkIfUsernameExits: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExits: checkIfUsenameExits: username: " + user.getUsername());

            if(StringManipulation.expandUsername(user.getUsername()).equals(username)){
                Log.d(TAG, "checkIfUsernameExits: Found a match" + user.getUsername());
                return true;
            }
        }
        return false;
    }

    /**
     * Register a new email and password to firebase authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, final String username){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else if (task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed" + userID);
                        }

                        // ...
                    }
                });
    }

    public void addNewUser(String email, String username, String description, String website, String profile_photo){
        User user = new User(userID, 1, email, StringManipulation.condenseUsername(username));
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        UserAccountSettings userAccountSettings = new UserAccountSettings(
                description, username, 0,
                0, 0, profile_photo, StringManipulation.condenseUsername(username), website);
        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(userAccountSettings);
    }

}
