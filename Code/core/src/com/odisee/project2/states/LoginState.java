package com.odisee.project2.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.odisee.project2.Game;

/**
 * Created by Walter on 13/11/2016.
 */

public class LoginState extends State {

    GameStateManager gsm;
    private Texture background;
    private Texture logo;
    public LoginState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        logo = new Texture("logo2.png");

        cam.setToOrtho(false, Game.HEIGHT/2, Game.WIDTH / 2);
        background = new Texture("bgs.png");

        Game.myGameCallback.onStartActivityA();
    }


    @Override
    public void handleInput() {
        Vector3 tmp = new Vector3(Gdx.input.getX()/2, Gdx.input.getY()/2, 0);
        if(Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
            cam.unproject(tmp);

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
        sb.draw(logo, cam.position.x-logo.getWidth() / 2,cam.position.y-logo.getHeight() / 2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("LoginState disposed");
    }
}
