package com.odisee.project2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private AndroidLauncher al;
    private TextView testt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        test = (Button)findViewById(R.id.test);
        test2 = (Button)findViewById(R.id.test2);
        initialise();
    }

    public void initialise () {

        SharedPreferences prefs = this.getSharedPreferences(
                "com.project2.prefs", Context.MODE_PRIVATE);
        String currPlayer= prefs.getString("currPlayer", "blabla");

        testt = (TextView)findViewById(R.id.testt);
        testt.setText(currPlayer);

        //showLeaderboard ();


    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


    }
    public void submit() {
        Log.d("t20", "Then Here 2");
        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_id), al.getScore());
        Log.d("t20", "Then Here 2");

    }
    public void showLeaderboard () {
        final int BOARD_REQUEST_CODE = 1;
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_id)), BOARD_REQUEST_CODE);

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
