package com.odisee.project2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;


public class GoogleLoginActivity extends FragmentActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    public static GoogleApiClient mGoogleApiClient;
    private TextView hello, who;
    private Button continueToGame, guest_button, sign_out_button;
    private SignInButton sign_in_button;

    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        sign_in_button = (SignInButton)findViewById(R.id.sign_in_button);
        guest_button = (Button)findViewById(R.id.guest_button);
        continueToGame = (Button)findViewById(R.id.continueToGame);
        sign_out_button = (Button)findViewById(R.id.sign_out_button);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.guest_button).setOnClickListener(this);
        findViewById(R.id.continueToGame).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        hello = (TextView)findViewById(R.id.hello);
        who = (TextView)findViewById(R.id.who);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.guest_button:
                playAsGuest();
                break;
            case R.id.continueToGame:
                finishAct();
                break;
            case R.id.sign_out_button:
                signOut();
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        sign_in_button.setVisibility(View.VISIBLE);
        continueToGame.setVisibility(View.GONE);
        guest_button.setVisibility(View.VISIBLE);
        sign_out_button.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            hello.setText("hello " + acct.getDisplayName() + "!");
            endLogin();
        } else {
            hello.setText("Error logging in, try again.");
        }
    }
    private void playAsGuest() {
        hello.setText("Hello Guest!");
        endLogin();

    }

    private void endLogin() {
        who.setText("Welcome!");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        sign_in_button.setVisibility(View.GONE);
        continueToGame.setVisibility(View.VISIBLE);
        guest_button.setVisibility(View.GONE);
        sign_out_button.setVisibility(View.VISIBLE);
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void finishAct() {
        mGoogleApiClient = null;
        this.finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
