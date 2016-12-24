package com.odisee.project2game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.odisee.project2game.Game;

import java.util.Random;

/**
 * Created by Walter on 24/12/2016.
 */

public class Coin {
    private Texture coin;
    private Vector2 posCoin;
    private Random rand;
    private static final int VALUE_BETWEEN_MINANDMAX = 150;
    public static final int WIDTH = 30;

    private Rectangle boundsCoin;
    private boolean up;


    /**
     * constructor of the coin
     *
     * @param x x-axis position of the tube
     */
    public Coin( float x ) {
        coin = new Texture("coin.png");
        rand = new Random();
        int upOrDown = rand.nextInt(2);
        if(upOrDown == 1) {
            posCoin = new Vector2(x, 100);
        } else {
            posCoin = new Vector2(x, Game.HEIGHT - 100);
        }

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

    public void setCoin(Texture coin) { this.coin = coin; }


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
        posCoin.set(x, 100);

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
}
