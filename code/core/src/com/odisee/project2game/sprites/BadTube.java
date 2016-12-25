package com.odisee.project2game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Walter on 24/12/2016.
 */

public class BadTube {
    private Texture badTube;
    private Vector2 posBadTube;
    private Random rand;
    private static final int VALUE_BETWEEN_MINANDMAX = 200;
    public static final int WIDTH = 30;

    private Rectangle boundsBadTube;
    private boolean up;


    /**
     * constructor of the coin
     *
     * @param x x-axis position of the tube
     */
    public BadTube(float x ) {
        badTube = new Texture("tubeBlue.png");
        rand = new Random();
        int distBetween = rand.nextInt(8);
        distBetween +=4;
        posBadTube = new Vector2(x*distBetween, rand.nextInt(VALUE_BETWEEN_MINANDMAX));


        // invisible rectangles for collision detection
        boundsBadTube = new Rectangle(posBadTube.x,posBadTube.y, badTube.getWidth(),badTube.getHeight());
    }

    /**
     * return the texture coin
     *
     * @return texture topTube
     */
    public Texture getBadTube() {
        return badTube;
    }

    public void setbadTube(Texture coin) { this.badTube = badTube; }


    /**
     * return the position of coin
     *
     * @return x and y of TopTube
     */
    public Vector2 getPosBadTube() {
        return posBadTube;
    }

    /**
     * method to reposition the coin on the screen on the x-axis
     *
     * @param x x-position of the tube on the screen
     */
    public void repositionX(float x) {
        int distBetween = rand.nextInt(8);
        distBetween +=4;
        posBadTube = new Vector2(x*distBetween, rand.nextInt(VALUE_BETWEEN_MINANDMAX));

        boundsBadTube.setPosition(posBadTube.x, posBadTube.y);
    }


    /**
     * checks if there is a collision between bird and coin
     *
     * @param player the bird
     * @return boolean if a collision has happened
     */
    public boolean collides(Rectangle player) {
        return player.overlaps(boundsBadTube);
    }

    /**
     * method to dispose the textures
     */
    public void dispose () {
        badTube.dispose();
    }

    public Rectangle getBounds() {
        return boundsBadTube;
    }
}
