package com.odisee.project2.states;

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

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Rectangle bounds;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.HEIGHT/2, Game.WIDTH / 2);
        playBtn = new Texture("playbtn.png");
        background = new Texture("bgs.png");
        bounds = new Rectangle(Game.HEIGHT/2, Game.WIDTH / 2-playBtn.getHeight()*2, playBtn.getWidth()*2, playBtn.getHeight()*2);
    }





    @Override
    public void handleInput() {
        Vector3 tmp = new Vector3(Gdx.input.getX()/2, Gdx.input.getY()/2, 0);

        if(Gdx.input.justTouched()) {
            if (bounds.contains(tmp.x, tmp.y)) {
                gsm.set(new PlayState(gsm));
                cam.unproject(tmp);
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
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menustate disposed");
    }
}
