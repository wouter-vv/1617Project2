package com.odisee.project2game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odisee.project2game.states.GameStateManager;
import com.odisee.project2game.states.LoginState;

public class Game extends ApplicationAdapter {
    private static Preferences preferences;
    SpriteBatch batch;
	Texture img;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    private GameStateManager gsm;
	private Music music;

	// interface to call the activity to log in
	public interface MyGameCallback {
		void onStartActivityLogin();
    }

    // ** Additional **
    // Setter for the callback
    public void setMyGameCallback(MyGameCallback callback) {
        myGameCallback = callback;
    }

    // Local variable to hold the callback implementation
    public static MyGameCallback myGameCallback;


    @Override
	public void create () {
        batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		gsm = new GameStateManager();
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1); // wipes the screen and redraws everything
		gsm.push(new LoginState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		music.dispose();
	}

    public static Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences("com.project2.prefs");
        }
        return preferences;
    }

}
