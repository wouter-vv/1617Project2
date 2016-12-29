package com.odisee.project2game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.odisee.project2game.Game;

/**
 * Created by Wouter Vande Velde on 26/10/2016.
 */

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Rectangle bounds;
    private Rectangle bounds2;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH /2, Game.HEIGHT / 2);
        playBtn = new Texture("playbtn.png");
        background = new Texture("bgs.png");
        bounds = new Rectangle(Game.WIDTH /2, Game.HEIGHT / 2-playBtn.getHeight()*2, playBtn.getWidth()*2, playBtn.getHeight()*2);
        bounds2 = new Rectangle(Game.WIDTH /2, Game.HEIGHT / 2+playBtn.getHeight()-10, playBtn.getWidth()*2, playBtn.getHeight()*2);
    }





    @Override
    public void handleInput() {
        Vector2 tmp = new Vector2(Gdx.input.getX()/2, Gdx.input.getY()/2);

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
        sb.draw(background,0,0);
        sb.draw(playBtn, cam.position.x-playBtn.getWidth() / 2,cam.position.y);
        sb.draw(playBtn, cam.position.x-playBtn.getWidth() / 2,cam.position.y - playBtn.getHeight()-10);


        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menustate disposed");
    }
}
