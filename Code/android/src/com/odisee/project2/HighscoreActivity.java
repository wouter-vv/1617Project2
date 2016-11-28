package com.odisee.project2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;

public class HighscoreActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private int score;
    private GoogleApiClient mGoogleApiClient;
    private Button test;
    private Button test2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        test = (Button)findViewById(R.id.test);
        test2 = (Button)findViewById(R.id.test2);
        initialise();
    }

    public void initialise () {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        mGoogleApiClient.connect();
        test.setVisibility(View.VISIBLE);
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if(mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_id), 50);
            final int BOARD_REQUEST_CODE = 1;
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                    mGoogleApiClient, getString(R.string.leaderboard_id)), BOARD_REQUEST_CODE);


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        test2.setVisibility(View.VISIBLE);
    }
}
