package com.odisee.project2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;

public class HighscoreActivity extends Activity  {
    private int score;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GoogleLoginActivity gla = new GoogleLoginActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        Games.Leaderboards.submitScore( GoogleLoginActivity.mGoogleApiClient, getString(R.string.leaderboard_id), 50 );
        final int BOARD_REQUEST_CODE= 1;
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                GoogleLoginActivity.mGoogleApiClient, getString(R.string.leaderboard_id)), BOARD_REQUEST_CODE );
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
