package com.odisee.project2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.odisee.project2.states.GameStateManager;
import com.odisee.project2.states.LoginState;
import com.odisee.project2.states.MenuState;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Game";

	// interface to call the activity to log in
	public interface MyGameCallback {
		void onStartActivityLogin();
		void onStartActivityHighscore();
	}

	// ** Additional **
	// Setter for the callback
	public void setMyGameCallback(MyGameCallback callback) {
		myGameCallback = callback;
	}

	// Local variable to hold the callback implementation
	public static MyGameCallback myGameCallback;


	private GameStateManager gsm;
    private SpriteBatch batch;
	Texture img;

	private Music music;

	public Game() {

	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
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
	public void dispose() {
		music.dispose();
	}
}
