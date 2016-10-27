package com.odisee.project2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odisee.project2.states.GameStateManager;
import com.odisee.project2.states.MenuState;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Game";

    private GameStateManager gsm;
    private SpriteBatch batch;
	Texture img;

	private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
        Gdx.gl.glClearColor(1, 0, 0, 1); // wipes the screen and redraws everything
	    gsm.push(new MenuState(gsm));
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
