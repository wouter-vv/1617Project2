package com.odisee.project2game;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GoogleApiActivity extends FragmentActivity implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_RESOLVE_ERR = 9001;
    private GoogleApiClient mGoogleApiClient;
    private TextView hello, who;

    private Button continueToGame, guest_button, sign_out_button;
    private SignInButton sign_in_button;

    private String currPlayer;
    private static SharedPreferences prefs;
    private static int start;

    private Button retry;

    /**
     * create googleapiclient to connect to leaderboard
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_api);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, 0 /* clientId */, this)
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

        hello = (TextView)findViewById(R.id.hello);
        who = (TextView)findViewById(R.id.who);
    }

    /**
     * automatically called after onCreate
     */
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     * check if button is clicked
     *
     * @param view current view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void signOut() {
       // Games.signOut(mGoogleApiClient);
        sign_in_button.setVisibility(View.VISIBLE);
        continueToGame.setVisibility(View.GONE);
        guest_button.setVisibility(View.VISIBLE);
        sign_out_button.setVisibility(View.GONE);
    }

    /**
     * finish the activity and save the shared preferences
     * initialize the shared prefs if first time
     */
    private void finishAct() {
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
        /**
         * releasing an official apk on the play store requires the achievements to be implemented, dummy implementation
         */
        if(mGoogleApiClient.isConnected()) {
            Games.Achievements.unlock(mGoogleApiClient, "CgkI2Y6BmvQZEAIQAg");
            Games.Achievements.unlock(mGoogleApiClient, "CgkI2Y6BmvQZEAIQAw");
            Games.Achievements.unlock(mGoogleApiClient, "CgkI2Y6BmvQZEAIQBA");
            Games.Achievements.unlock(mGoogleApiClient, "CgkI2Y6BmvQZEAIQBQ");
            Games.Achievements.unlock(mGoogleApiClient, "CgkI2Y6BmvQZEAIQBg");
        }

        this.finish();
    }

    /**
     * play as a guest, set current player to guest
     */
    private void playAsGuest() {
        currPlayer = "Guest";
        hello.setText("Hello Guest!");
        endLogin();
    }

    /**
     * sign in to the google api and get display name
     */
    private void signIn() {
        if (mGoogleApiClient.isConnected()) {
            currPlayer = Games.Players.getCurrentPlayer(mGoogleApiClient).getDisplayName();
            hello.setText("hello " + currPlayer + "!");
            endLogin();
        } else {
            hello.setText("Error logging in, try again or play as guest.");
        }
    }

    /**
     * end the login by changing the buttons
     */
    private void endLogin() {
        who.setText("Welcome!");
        sign_in_button.setVisibility(View.GONE);
        continueToGame.setVisibility(View.VISIBLE);
        guest_button.setVisibility(View.GONE);
        sign_out_button.setVisibility(View.VISIBLE);
    }

    /**
     * method is called when the api is connected
     *
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(prefs != null ) {
            if (start == 1 && prefs.getString("action", "test").equals("submit")) {
                switchToLeaderboard();
                submit();
            }

            if (start == 1 && prefs.getString("action", "test").equals("showLeaderboard")) {
                switchToLeaderboard();
                showLeaderboard ();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

// ...

    /**
     * method called when the connection to the api failed
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // retry connecting by showing the intent again
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERR);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
                e.printStackTrace();
            }
        }
        // call to remove the other buttons, even if failed
        if(prefs != null ) {
            if (start == 1 && prefs.getString("action", "test").equals("submit")) {
                switchToLeaderboard();
            }

            if (start == 1 && prefs.getString("action", "test").equals("showLeaderboard")) {
                switchToLeaderboard();
            }
        }
    }

    /**
     * get the result of the activity that is called if the connection failed
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_RESOLVE_ERR:
                if (resultCode == RESULT_OK) {
                    if (!mGoogleApiClient.isConnecting()
                            && !mGoogleApiClient.isConnected())
                        mGoogleApiClient.connect();
                }
                break;
        }
    }

    /**
     * remove login buttons and show retry button
     */
    public void switchToLeaderboard () {
        sign_in_button.setVisibility(View.GONE);
        continueToGame.setVisibility(View.GONE);
        guest_button.setVisibility(View.GONE);
        sign_out_button.setVisibility(View.GONE);
        who.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
        if(!mGoogleApiClient.isConnected()) {
            hello.setText("An error occured connecting to the leaderboard, retry connecting.");
        }
    }

    /**
     * submit the highscore
     */
    public void submit() {
        if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_id), prefs.getInt("currScore", prefs.getInt("currScore",0)));
            this.finish();
        }

    }

    /**
     * show the leaderboard
     */
    public void showLeaderboard () {
        if (mGoogleApiClient.isConnected()) {
            final int BOARD_REQUEST_CODE = 1;
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_id)), BOARD_REQUEST_CODE);
            this.finish();
        }

    }

    /**
     * retry connection to the leaderboard manually
     */
    public void retry() {
        if(!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            if (mGoogleApiClient.isConnected()) {
                retry.setVisibility(View.GONE);
                if (prefs.getString("action", "test").equals("submit")) {
                    submit();
                }

                if (prefs.getString("action", "test").equals("showLeaderboard")) {
                    showLeaderboard();
                }
            } else {
                this.finish();
            }
        }


    }
}
