package com.odisee.project2.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Walter on 26/10/2016.
 */

public class Tube {
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Random rand;
    private static final int FLUCTUATION = 150;
    private static final int GAP = 100;
    private static final int LOWEST = 30;
    public static final int TUBE_WIDTH = 120;

    private Rectangle boundsTop, boundsBot;


    public Tube( float x ) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION)+ GAP + LOWEST);
        posBotTube = new Vector2(x, posTopTube.y - GAP - bottomTube.getHeight());

        // invisible rectangles for collision detection
        boundsTop = new Rectangle(posTopTube.x,posTopTube.y, topTube.getWidth(),topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x,posBotTube.y, bottomTube.getWidth(),bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public void reposition(float x) {
        posTopTube.set(x, rand.nextInt(FLUCTUATION)+ GAP + LOWEST);
        posBotTube.set(x, posTopTube.y - GAP - bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);

    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose () {
        topTube.dispose();
        bottomTube.dispose();
    }
}
