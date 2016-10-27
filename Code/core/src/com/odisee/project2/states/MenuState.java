package com.odisee.project2.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.odisee.project2.Game;

/**
 * Created by Walter on 26/10/2016.
 */

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        playBtn = new Texture("playBtn.png");
        background = new Texture("bg.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        //first open the spritebatch and add everything in it
        sb.begin();
        sb.draw(background,0,0, Game.WIDTH, Game.HEIGHT);
        sb.draw(playBtn, (Game.WIDTH/2) - (playBtn.getWidth() / 2), Game.HEIGHT / 2  );
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menustate disposed");
    }
}
