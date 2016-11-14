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
    private Texture playBtn, playBtn2;
    private Rectangle boundsGuest, boundsGoogle;
    public LoginState(GameStateManager gsm) {

        super(gsm);
        this.gsm = gsm;

        cam.setToOrtho(false, Game.HEIGHT/2, Game.WIDTH / 2);
        playBtn = new Texture("playbtn.png");
        playBtn2 = playBtn;
        background = new Texture("bgs.png");
        boundsGuest = new Rectangle(Game.HEIGHT/2, cam.position.y*2-playBtn.getHeight()*2, playBtn.getWidth()*2, playBtn.getHeight()*2);
        boundsGoogle = new Rectangle(Game.HEIGHT/2, cam.position.y*2 +10, playBtn.getWidth()*2, playBtn.getHeight()*2);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         //       .requestEmail()
         //       .build();
    }


    @Override
    public void handleInput() {
        Vector3 tmp = new Vector3(Gdx.input.getX()/2, Gdx.input.getY()/2, 0);
        if(Gdx.input.justTouched()) {
            if (boundsGuest.contains(tmp.x, tmp.y)) {
                //gsm.set(new MenuState(gsm));
                Game.myGameCallback.onStartActivityA();
                cam.unproject(tmp);
            }
            if (boundsGoogle.contains(tmp.x, tmp.y)) {
                System.out.print("---------");
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
        sb.draw(playBtn2, cam.position.x-playBtn.getWidth() / 2,cam.position.y - playBtn.getHeight()-10);
        sb.end();
    }

    @Override
    public void dispose() {
        /*background.dispose();
        playBtn.dispose();
        System.out.println("LoginState disposed");*/
    }
}
