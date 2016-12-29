package com.odisee.project2game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Wouter Vande Velde on 26/10/2016.
 */

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();

    }

    public void push(State state) {
        states.push(state);
    }

    public void set (State state) {
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
