package com.odisee.project2game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * class to start and initialize the game for android
 *
 * @author Wouter Vande Velde
 */
public class AndroidLauncher extends AndroidApplication implements Game.MyGameCallback {
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

	/**
	 * start the GoogleApiActivity
	 */
	@Override
	public void onStartActivityLogin() {
		Intent intent = new Intent(this, GoogleApiActivity.class);
		startActivity(intent);
	}
}
