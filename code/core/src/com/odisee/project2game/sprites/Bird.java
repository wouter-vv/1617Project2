package com.odisee.project2game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Walter on 26/10/2016.
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
        //texture = new Texture("birdanimation.png");
        //birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        bounds = new Rectangle(x,y,texture.getWidth(),texture.getHeight());
    }

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
        bounds.setPosition(position.x,position.y);
    }

    public void jump() {
        velocity.y=250;
        flap.play(0.5f);
    }

    public void ceiling() {
        velocity.y=0;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose () {
        texture.dispose();
        flap.dispose();
    }
}
