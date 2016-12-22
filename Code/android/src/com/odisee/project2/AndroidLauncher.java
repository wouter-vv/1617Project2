package com.odisee.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.odisee.project2.Game;

public class AndroidLauncher extends AndroidApplication implements Game.MyGameCallback  {

	private boolean show;
	private int score;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// create an instance of MyGame, and set the callback
		Game myGame = new Game();
		// Since AndroidLauncher implements MyGame.MyGameCallback, we can just pass 'this' to the callback setter.
		myGame.setMyGameCallback(this);

		initialize(new Game(), config);
	}

	@Override
	public void onStartActivityLogin() {

		Intent intent = new Intent(this, GoogleLoginActivity.class);
		startActivity(intent);
	}
	public void onStartActivityHighscore(boolean show, int score) {
		this.show = show;
		this.score = score;
	}

	public boolean isShow() {
		return show;
	}

	public int getScore() {
		return score;
	}
}
