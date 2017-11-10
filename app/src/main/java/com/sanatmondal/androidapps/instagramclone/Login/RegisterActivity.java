package com.sanatmondal.androidapps.instagramclone.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanatmondal.androidapps.instagramclone.R;
import com.sanatmondal.androidapps.instagramclone.Utils.FirebaseMethods;

/**
 * Created by Sanat on 9/14/2017.
 */

public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = "RegisterActivity";

    private Context mContext = RegisterActivity.this;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private EditText mEmail, mUserName, mPassword;
    private String email, userName, password;
    private TextView mPleaseWait;
    Button btnRegister;

    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseMethods = new FirebaseMethods(mContext);

        Log.d(TAG, "onCreate: started");

        initWidgets();
        setupFirebaseAuth();
        init();

    }

    private void init(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                userName = mUserName.getText().toString();

                if(checkInputs(email, userName, password)){
                    mProgressBar.setVisibility(View.VISIBLE);
                    mPleaseWait.setVisibility(View.VISIBLE);

                    firebaseMethods.registerNewEmail(email, password, userName);
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password){
        Log.d(TAG, "checkInputs: Checking input for null value");
        if (email.equals("") && username.equals("") && password.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    /**
     * Initializing the activity widgets
     */
    private void initWidgets(){
        Log.d(TAG, "initWidgets: initializing widgets");

        mProgressBar = (ProgressBar) findViewById(R.id.Progressbar);
        mPleaseWait = (TextView) findViewById(R.id.please_wait);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mUserName = (EditText) findViewById(R.id.input_username);
        btnRegister = (Button) findViewById(R.id.btn_register);

        mProgressBar.setVisibility(View.GONE);
        mPleaseWait.setVisibility(View.GONE);

    }


    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null");
        if(string.equals("")){
            return true;
        }else {
            return false;
        }
    }



    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth");
        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                // this will run when the authentication is changed
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        // success method
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // 1st check make sure the user name is not already in use
                            if(firebaseMethods.checkIfUsernameExits(userName, dataSnapshot)){
                                append = myRef.push().getKey().substring(3,7);
                                Log.d(TAG, "onDataChange: Username already exists appending randon string to name " + append);
                            }
                            userName = userName + append;
                            // add new user(table) to the database
                            firebaseMethods.addNewUser(email, userName, "", "", "");
                            Toast.makeText(mContext, "Singup successfully. Sending verification email", Toast.LENGTH_LONG).show();
                        }

                        // Error method
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: Database Error");
                        }
                    });

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
