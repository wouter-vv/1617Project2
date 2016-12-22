package com.odisee.project2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodSession;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.games.Games;



public class GoogleLoginActivity extends FragmentActivity implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    public static GoogleApiClient mGoogleApiClient;
    private TextView hello, who;
    private Button continueToGame, guest_button, sign_out_button;
    private SignInButton sign_in_button;

    private String currPlayer;
    private static SharedPreferences prefs;
    private static int start;
    private Button retry;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;
    private ConnectionResult mConnectionResult;

    private static InputMethodSession.EventCallback ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();

        sign_in_button = (SignInButton)findViewById(R.id.sign_in_button);
        guest_button = (Button)findViewById(R.id.guest_button);
        continueToGame = (Button)findViewById(R.id.continueToGame);
        sign_out_button = (Button)findViewById(R.id.sign_out_button);
        retry = (Button)findViewById(R.id.retry);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.guest_button).setOnClickListener(this);
        findViewById(R.id.continueToGame).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.retry).setOnClickListener(this);
        currPlayer="notInitialized";

        hello = (TextView)findViewById(R.id.hello);
        who = (TextView)findViewById(R.id.who);
        if(prefs != null ) {
            if (start == 1 && prefs.getString("action", "test").equals("submit")) {
                mGoogleApiClient.connect();
                switchToLeaderboard();
                submit();
            }

            if (start == 1 && prefs.getString("action", "test").equals("showLeaderboard")) {
                mGoogleApiClient.connect();
                switchToLeaderboard();
                showLeaderboard ();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
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
            case R.id.retry:
                retry();
        }
    }

    public void switchToLeaderboard () {
        sign_in_button.setVisibility(View.GONE);
        continueToGame.setVisibility(View.GONE);
        guest_button.setVisibility(View.GONE);
        sign_out_button.setVisibility(View.GONE);
        hello.setVisibility(View.GONE);
        who.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
        
    }
    private void signOut() {
        sign_in_button.setVisibility(View.VISIBLE);
        continueToGame.setVisibility(View.GONE);
        guest_button.setVisibility(View.VISIBLE);
        sign_out_button.setVisibility(View.GONE);
    }

    private void playAsGuest() {
        currPlayer = "Guest";
        hello.setText("Hello Guest!");
        endLogin();
    }

    private void endLogin() {
        who.setText("Welcome!");
        sign_in_button.setVisibility(View.GONE);
        continueToGame.setVisibility(View.VISIBLE);
        guest_button.setVisibility(View.GONE);
        sign_out_button.setVisibility(View.VISIBLE);
    }


    private void signIn() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            currPlayer = Games.Players.getCurrentPlayer(mGoogleApiClient).getDisplayName();
            hello.setText("hello " + currPlayer + "!");
            endLogin();
        } else {
            hello.setText("Error logging in, try again or play as guest.");
        }
    }



    public void finishAct() {
        start = 1;
        prefs = this.getSharedPreferences(
                "com.project2.prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("currPlayer", currPlayer);
        editor.putString("action", "nothing");
        editor.putInt("currScore", 0);
        if(prefs.getInt("highscore",0)==0) {
            editor.putInt("highscore", 1);
        }
        if(prefs.getString("highscoreOwner","notCreated").equals("notCreated")) {
            editor.putString("highscoreOwner", "no owner");
        }
        editor.commit();
        this.finish();
    }



    public void submit() {
        if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_id), prefs.getInt("currScore", prefs.getInt("currScore",0)));
            this.finish();
        }

    }
    public void showLeaderboard () {
        if (mGoogleApiClient.isConnected()) {
            final int BOARD_REQUEST_CODE = 1;
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_id)), BOARD_REQUEST_CODE);
            this.finish();
        }

    }

    public void retry() {
        mGoogleApiClient.connect();
        if(mGoogleApiClient.isConnected()) {
            retry.setVisibility(View.GONE);
            if(prefs.getString("action","test").equals("submit")) {
                submit();
            }

            if(prefs.getString("action","test").equals("showLeaderboard")) {
                showLeaderboard ();
            }
            //this.finish();
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        hello.setText("welkom");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    if (!mGoogleApiClient.isConnecting()
                            && !mGoogleApiClient.isConnected())
                        mGoogleApiClient.connect();
                }
                break;
        }
    }
}
