package com.odisee.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.odisee.project2.Game;

public class AndroidLauncher extends AndroidApplication  {
	ActionResolver actionResolverAndroid;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionResolverAndroid= new ActionResolverAndroid(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Game(actionResolverAndroid), config);
	}
	public void startLogingActivity(Context context) {
		/*Intent myIntent = new Intent(AndroidLauncher.this, LoginActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(myIntent);*/
	}
}
