package com.odisee.project2game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.odisee.project2game.Game;

import java.util.Random;

/**
 * entity of the coin
 *
 * Created by Wouter Vande Velde on 24/12/2016.
 */

public class Coin {
    private Texture coin;
    private Vector2 posCoin;
    private Random rand;
    private static final int VALUE_BETWEEN_MINANDMAX = 200;
    public static final int WIDTH = 30;

    private Rectangle boundsCoin;


    /**
     * constructor of the coin
     *
     * @param x x-axis position of the tube
     */
    public Coin( float x ) {
        coin = new Texture("coin.png");
        rand = new Random();
        int distBetween = rand.nextInt(8);
        distBetween +=4;
        posCoin = new Vector2(x*distBetween, rand.nextInt(VALUE_BETWEEN_MINANDMAX));


        // invisible rectangles for collision detection
        boundsCoin = new Rectangle(posCoin.x,posCoin.y, coin.getWidth(),coin.getHeight());
    }

    /**
     * return the texture coin
     *
     * @return texture topTube
     */
    public Texture getCoin() {
        return coin;
    }

    /**
     * return the position of coin
     *
     * @return x and y of TopTube
     */
    public Vector2 getPosCoin() {
        return posCoin;
    }

    /**
     * method to reposition the coin on the screen on the x-axis
     *
     * @param x x-position of the tube on the screen
     */
    public void repositionX(float x) {
        int distBetween = rand.nextInt(8);
        distBetween +=4;
        posCoin = new Vector2(x*distBetween, rand.nextInt(VALUE_BETWEEN_MINANDMAX));

        boundsCoin.setPosition(posCoin.x, posCoin.y);
    }

    /**
     * checks if there is a collision between bird and coin
     *
     * @param player the bird
     * @return boolean if a collision has happened
     */
    public boolean collides(Rectangle player) {
        return player.overlaps(boundsCoin);
    }

    /**
     * method to dispose the textures
     */
    public void dispose () {
        coin.dispose();
    }

    /**
     * method to move the coin 25 pixels if needed
     */
    public void moveCoin() {
        posCoin.x += 25;
    }

    /**
     * get the bounds of the coin to detect collision
     *
     * @return bounds coin
     */
    public Rectangle getBounds() {
        return boundsCoin;
    }
}
