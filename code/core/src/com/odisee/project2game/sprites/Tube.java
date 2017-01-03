package com.odisee.project2game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.odisee.project2game.Game;

import java.util.Random;

/**
 * Tube entity
 *
 * Created by Wouter Vande Velde on 26/10/2016.
 */

public class Tube {
    private boolean moving;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Random rand;
    private static final int VALUE_BETWEEN_MINANDMAX = 150;
    private static final int GAP = 100;
    private static final int LOWEST = 30;
    public static final int TUBEWIDTH = 52;

    private Rectangle boundsTop, boundsBot;
    private boolean up;


    /**
     * constructor of the tube
     *
     * @param x x-axis position of the tube
     */
    public Tube( float x ) {
        topTube = new Texture("toptubeGreen.png");
        bottomTube = new Texture("bottomtubeGreen.png");
        rand = new Random();
        moving = false;
        up = true;

        posTopTube = new Vector2(x, rand.nextInt(VALUE_BETWEEN_MINANDMAX)+ GAP + LOWEST);
        posBotTube = new Vector2(x, posTopTube.y - GAP - bottomTube.getHeight());

        // invisible rectangles for collision detection
        boundsTop = new Rectangle(posTopTube.x,posTopTube.y, topTube.getWidth(),topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x,posBotTube.y, bottomTube.getWidth(),bottomTube.getHeight());
    }

    /**
     * return the texture topTube
     *
     * @return texture topTube
     */
    public Texture getTopTube() {
        return topTube;
    }
    /**
     * return the texture bottomtube
     *
     * @return texture bottomtube
     */
    public Texture getBottomTube() {
        return bottomTube;
    }

    public void setTopTube(Texture topTube) { this.topTube = topTube; }

    public void setBottomTube(Texture bottomTube) { this.bottomTube = bottomTube; }

    /**
     * return the texture TopTube
     *
     * @return x and y of TopTube
     */
    public Vector2 getPosBotTube() {
        return posBotTube;
    }
    /**
     * return the position of toptube
     *
     * @return x and y of TopTube
     */
    public Vector2 getPosTopTube() {
        return posTopTube;
    }
    /**
     * get if tubes should move
     *
     * @return true if tubes are moving
     */
    public boolean isMoving() { return moving; }
    /**
     * set the moving variable of the tubes to see if they should move
     *
     * @param moving true if the tubes should move
     */
    public void setMoving(boolean moving) { this.moving = moving; }







    /**
     * method to reposition the tube on the screen on the x-axis
     *
     * @param x x-position of the tube on the screen
     */
    public void repositionX(float x) {
        posTopTube.set(x, rand.nextInt(VALUE_BETWEEN_MINANDMAX) + GAP + LOWEST);
        posBotTube.set(x, posTopTube.y - GAP - bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    /**
     * method to repostion the tubes up and down
     *
     * @param x fixed x-axis value, wont change in order to keep the tube at the right location
     * @param y y-axis value of tube to make it go up and down
     */
    public void repositionY(float x, float y) {
        float height = y;
        if (up == true ) {
            if(height < Game.HEIGHT / 2+20){
                height+=1;
            }
            else if(height >= Game.HEIGHT / 2){
                up = false;
            }
        }
        if (up == false ) {
            if(height >= LOWEST+20){
                height-=1;
            }
            else {
                up = true;
            }
        }
        posTopTube.set(x, height);
        posBotTube.set(x, posTopTube.y - GAP - bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    /**
     * checks if there is a collision between bird and tube
     *
     * @param player the bird
     * @return boolean if a collision has happened
     */
    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    /**
     * method to dispose the textures
     */
    public void dispose () {
        topTube.dispose();
        bottomTube.dispose();
    }

    /**
     * make the tube purple, that means it moves
     *
     * @param tube
     */
    public void makeTubesPurple(Tube tube) {
        tube.topTube = new Texture("toptubePurple.png");
        tube.bottomTube = new Texture("bottomtubePurple.png");
    }
}
