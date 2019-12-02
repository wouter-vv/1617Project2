package com.odisee.project2game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * entity of the bird
 *
 * Created by Wouter Vande Velde on 26/10/2016.
 */

public class Bird {
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Texture texture;

    private Sound flap;

    private static final int GRAVITY  = -15;
    private static final int MOVEMENT  = 100;

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public Bird(int x, int y) {
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("bird2.png");

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        bounds = new Rectangle(x,y,texture.getWidth()-12,texture.getHeight()-8);
    }

    /**
     * get the height of the bird
     *
     * @return height of bird
     */
    public float getHeight() {
        return texture.getHeight();
    }

    public void update(float dt) {
        //birdAnimation.update(dt);
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt,velocity.y, 0);
        if (position.y < 0 ) {
            position.y = 0;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x+6,position.y+4);
    }

    /**
     * set the speed to 250 if the bird has to jump
     */
    public void jump() {
        velocity.y=250;
        flap.play(0.5f);
    }

    /**
     * if the bird hits the ceiling, set speed to 0
     */
    public void ceiling() {
        velocity.y=0;
    }

    /**
     * get bounds of bird
     *
     * @return bounds of bird
     */
    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose () {
        texture.dispose();
        flap.dispose();
    }
}
