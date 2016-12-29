package com.odisee.project2game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.odisee.project2game.Game;

/**
 * Created by Wouter Vande Velde on 10/12/2016.
 */

public class HighscoreState extends State{
    private final int highscore;
    private final String currPlayer;
    private final String highscoreOwner;
    private Texture playBtn;
    private Preferences prefs;
    private GlyphLayout glyphLayout;
    private Texture background;
    private Rectangle bounds;

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
    static int OFFSET_Y = 0;
    private int drawn;

    BitmapFont font;

    public HighscoreState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH /2, Game.HEIGHT / 2);
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
        background = new Texture("backgroundSimple.png");
        playBtn = new Texture("playbtn.png");

        glyphLayout = new GlyphLayout();
        font = new BitmapFont();

        prefs = Game.getPrefs();
        highscoreOwner = prefs.getString("highscoreOwner");
        highscore = prefs.getInteger("highscore");
        currPlayer = prefs.getString("currPlayer");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            prefs = Game.getPrefs();
            if(!prefs.getString("currPlayer", currPlayer).equals("Guest")) {
                prefs.putString("action", "showLeaderboard");
                prefs.flush();
                Game.myGameCallback.onStartActivityLogin();
            }
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
        sb.begin();

        sb.draw(background,(Game.WIDTH /2 - background.getWidth())/2,(Game.HEIGHT /2 - background.getHeight())/2);

        // glyphLayout used to center the sentence on the screen since you cannot know the width of the string
        // sentence: the local highscore is
        String localHighscore = "the local highscore is";
        font.getData().setScale(1);
        font.setColor(Color.BLACK);
        glyphLayout.setText(font,localHighscore);
        float w = glyphLayout.width;
        float textHeight = glyphLayout.height;
        font.draw(sb, localHighscore, cam.position.x-w / 2,cam.viewportHeight - textHeight*2);

        // highscore
        String strScore = "" + highscore;
        // 27 is width of each character in pixels
        int widthStrScore = strScore.length() * 27;

        // by
        String by = "by";
        glyphLayout.setText(font,by);
        w = glyphLayout.width;
        font.draw(sb, by, cam.position.x-w / 2,cam.viewportHeight - textHeight*8);

        // highscoreOwner
        font.getData().setScale(1.5f);
        glyphLayout.setText(font,highscoreOwner);
        w = glyphLayout.width;
        font.draw(sb, highscoreOwner, cam.position.x-w / 2,cam.viewportHeight - textHeight*10);

        // width of screen : cam.position.x - widthStrScore / 2 7
        // move a little left or right according to the amount of characters : widthStrScore / strScore.length() * i
        // draw the highscore
        for (int i = 0; i < strScore.length(); i++ ) {
            if (strScore.charAt(i) == 48) {
                sb.draw(zero, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 49) {
                sb.draw(one, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 50) {
                sb.draw(two, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 51) {
                sb.draw(three, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 52) {
                sb.draw(four, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 53) {
                sb.draw(five, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 54) {
                sb.draw(six, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 55) {
                sb.draw(seven, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 56) {
                sb.draw(eight, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }
            else if (strScore.charAt(i) == 57) {
                sb.draw(nine, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight - textHeight*7);
            }

        }

        sb.draw(playBtn, cam.position.x-playBtn.getWidth() / 2,cam.viewportHeight - textHeight*18);
        sb.end();
    }



    @Override
    public void dispose() {
        background.dispose();
        one.dispose();
        two.dispose();
        three.dispose();
        four.dispose();
        five.dispose();
        six.dispose();
        seven.dispose();
        eight.dispose();
        nine.dispose();
        zero.dispose();
        playBtn.dispose();
        System.out.println("HighscoreState disposed");
    }

}
