package com.odisee.project2game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.odisee.project2game.Game;

/**
 * menu with 2 buttons, one to play and another to show the leaderboard
 *
 * Created by Wouter Vande Velde on 26/10/2016.
 */
public class MenuState extends State {
    private  Texture logo;
    private Texture background;
    private Texture playBtn;
    private Texture scoreBtn;
    private Rectangle bounds;
    private Rectangle bounds2;
    public final int sizeImgScale = 5;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH /2, Game.HEIGHT / 2);
        playBtn = new Texture("playbtn2.png");
        scoreBtn = new Texture("scorebtn.png");
        background = new Texture("bgs.png");
        bounds = new Rectangle(Game.HEIGHT / 2-playBtn.getHeight()/sizeImgScale, Game.HEIGHT / 2-playBtn.getHeight()/sizeImgScale, playBtn.getWidth()*2/sizeImgScale, playBtn.getHeight()*2/sizeImgScale);
        bounds2 = new Rectangle(Game.WIDTH /2, Game.HEIGHT / 2+playBtn.getHeight()/sizeImgScale-10, playBtn.getWidth()*2/sizeImgScale, playBtn.getHeight()*2/sizeImgScale);
        //50 is width playbtn/2, 25 height playbtn/2
        //after unproject: range y value -> 120-240, 47 is 95(drawlocation)/2, +120 because offset is 120
        //75 is drawlocation
        bounds = new Rectangle(75, 47 + 120, 50, 25);
        // -30 because button is 60 lower
        bounds2 = new Rectangle(75, 47 + 120 - 30, 50, 25);


        logo = new Texture("logo2.png");
    }

    @Override
    public void handleInput() {

        Vector3 tmp = new Vector3(Gdx.input.getX()/2, Gdx.input.getY()/2,0);
        cam.unproject(tmp); //<---
        if(Gdx.input.justTouched()) {

            if (bounds.contains(tmp.x, tmp.y)) {
                gsm.set(new PlayState(gsm));
                System.out.println("aaaaaaa");
            }
            if (bounds2.contains(tmp.x, tmp.y)) {
                System.out.println("bbbbbbb");
                gsm.set(new HighscoreState(gsm));
            }
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
        sb.draw(background,-300,0,804,247);
        sb.draw(logo, cam.position.x- 170/ 2,cam.position.y+40, 170,67);
        sb.draw(playBtn, cam.position.x-playBtn.getWidth() / 2 / sizeImgScale, cam.position.y - 25,playBtn.getWidth()/sizeImgScale,playBtn.getHeight()/sizeImgScale);
        sb.draw(scoreBtn, cam.position.x-playBtn.getWidth() / 2 / sizeImgScale, cam.position.y - playBtn.getHeight() /sizeImgScale -35,playBtn.getWidth()/sizeImgScale,playBtn.getHeight()/sizeImgScale);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menustate disposed");
    }
}
