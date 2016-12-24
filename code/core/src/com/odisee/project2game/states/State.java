package com.odisee.project2game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Walter on 26/10/2016.
 */

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt); // dt = delta time -> the time between one frame rendered and the next frame rendered
    public abstract void render (SpriteBatch sb); // SpriteBatch = a container for everything that needs to be rendered, sprites etc.
    public abstract void dispose();
}
