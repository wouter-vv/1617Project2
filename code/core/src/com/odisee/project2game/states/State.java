package com.odisee.project2game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * a class that initializes a state
 *
 * Created by Wouter Vande Velde on 26/10/2016.
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

    /**
     * handle the input
     */
    protected abstract void handleInput();

    /**
     * update values, looped
     * @param dt deltatime, the time between one frame rendered and the next frame rendered
     */
    public abstract void update(float dt);

    /**
     * render the textures
     * @param sb spritebatch: a container for everything that needs to be rendered, sprites etc.
     */
    public abstract void render (SpriteBatch sb);

    /**
     * remove unused data
     */
    public abstract void dispose();
}
