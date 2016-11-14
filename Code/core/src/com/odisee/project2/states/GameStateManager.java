package com.odisee.project2.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odisee.project2.ActionResolver;

import java.util.Stack;

/**
 * Created by Walter on 26/10/2016.
 */

public class GameStateManager {
    private Stack<State> states;
    ActionResolver actionResolver;

    public GameStateManager(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
        states = new Stack<State>();

    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();

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
