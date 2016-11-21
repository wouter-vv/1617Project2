package com.odisee.project2.states;

/**
 * Created by Walter on 16/11/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.odisee.project2.Game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.odisee.project2.Game;

/**
 * Created by Walter on 26/10/2016.
 */

public class EndState extends State {
    private Texture background;
    private Texture GO;
    private Rectangle bounds;
    int score;

    private Texture one;
    private Texture two;
    private Texture three;
    private Texture four;
    private Texture five;
    private Texture six;
    private Texture seven;
    private Texture eight;
    private Texture nine;
    private Texture zero;
    private Texture cont;

    public EndState(GameStateManager gsm, int score) {
        super(gsm);
        this.score = score;
        one = new Texture("1s.png");
        two = new Texture("2s.png");
        three = new Texture("3s.png");
        four = new Texture("4s.png");
        five = new Texture("5s.png");
        six = new Texture("6s.png");
        seven = new Texture("7s.png");
        eight = new Texture("8s.png");
        nine = new Texture("9s.png");
        zero = new Texture("0s.png");
        cont = new Texture("continue.png");
        cam.setToOrtho(false, Game.HEIGHT/2, Game.WIDTH / 2);
        GO = new Texture("GO2.png");
        background = new Texture("bgs.png");
        //bounds = new Rectangle(Game.HEIGHT/2, Game.WIDTH / 2-playBtn.getHeight()*2, playBtn.getWidth()*2, playBtn.getHeight()*2);
    }





    @Override
    public void handleInput() {
        Vector3 tmp = new Vector3(Gdx.input.getX()/2, Gdx.input.getY()/2, 0);

        if(Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        //first open the spritebatch and add everything in it
        sb.begin();
        sb.draw(background,0,0);
        sb.draw(GO, cam.position.x-GO.getWidth() / 2,cam.position.y-GO.getHeight()/4);
        String strScore = "" + (int)score;
        int widthStrScore = strScore.length() * 27;
        for (int i = 0; i < strScore.length(); i++ ) {
            if (strScore.charAt(i) == 48) {
                sb.draw(zero, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 49) {
                sb.draw(one, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 50) {
                sb.draw(two, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 51) {
                sb.draw(three, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 52) {
                sb.draw(four, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 53) {
                sb.draw(five, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 54) {
                sb.draw(six, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 55) {
                sb.draw(seven, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 56) {
                sb.draw(eight, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }
            else if (strScore.charAt(i) == 57) {
                sb.draw(nine, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, 0 + zero.getHeight()*3/2);
            }

        }
        sb.draw(cont, cam.position.x - cont.getWidth() / 2, 0);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        GO.dispose();
        System.out.println("Endstate disposed");
    }
}