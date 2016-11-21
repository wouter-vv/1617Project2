package com.odisee.project2.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.odisee.project2.Game;
import com.odisee.project2.sprites.Bird;
import com.odisee.project2.sprites.Tube;

/**
 * Created by Walter on 26/10/2016.
 */

public class PlayState extends State {
    private Bird bird;
    private Texture bg;
    private Tube tube;
    int posXBck1=0,posXBck2=0;

    private Vector2 groundPos1, groundPos2;

    private Texture ground;
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
    private double score;

    private static final int TUBE_SPACING = 250;
    private static final int TUBE_COUNT = 6;
    private static final int GROUND_Y_OFFSET = -90;

    private Array<Tube> tubes;
    private Texture curNumber;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,0);
        cam.setToOrtho(false, Game.HEIGHT/2, Game.WIDTH / 2);
        bg = new Texture("bgs.png");
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

        /*ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x-cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.position.x-cam.viewportWidth/2 + ground.getWidth(), GROUND_Y_OFFSET);*/

        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * TUBE_SPACING + tube.TUBE_WIDTH));
        }
    }

    public double getScore() {
        return score;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        //updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(int i = 0; i<tubes.size;i++) {
            Tube tube = tubes.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACING)*TUBE_COUNT);
            }

            // if collision, game is over
            if(tube.collides(bird.getBounds())) {
                gsm.set(new EndState(gsm, (int)score));
            }
            // bird floats on the ground
            if(bird.getPosition().y <= bg.getHeight()/20) {
                bird.getPosition().y = bg.getHeight()/20;
            }
            // bird reaches ceiling
            if(bird.getPosition().y >= cam.viewportHeight-bird.getHeight()) {
                bird.getPosition().y = cam.viewportHeight-bird.getHeight();
                bird.ceiling();
            }
        }
        score+=0.1;
        cam.update(); // tell gdx that cam is repositioned
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        // to keep the background on the right place, at the position of the camera
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        //draw the current score
        String strScore = "" + (int)score;
        int widthStrScore = strScore.length() * 27;
        for (int i = 0; i < strScore.length(); i++ ) {
            if (strScore.charAt(i) == 48) {
                sb.draw(zero, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 49) {
                sb.draw(one, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 50) {
                sb.draw(two, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 51) {
                sb.draw(three, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 52) {
                sb.draw(four, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 53) {
                sb.draw(five, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 54) {
                sb.draw(six, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 55) {
                sb.draw(seven, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 56) {
                sb.draw(eight, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }
            else if (strScore.charAt(i) == 57) {
                sb.draw(nine, cam.position.x - widthStrScore / 2 + widthStrScore / strScore.length() * i, cam.viewportHeight-zero.getHeight()-zero.getHeight()/2);
            }

        }

        //sb.draw(ground, groundPos1.x, groundPos1.y);
        //sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
//        ground.dispose();
        for(Tube tube : tubes ) {
            tube.dispose();
        }


        System.out.println("playstate disposed");
        System.out.println((int)score);
    }

    /*private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth()* 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth()* 2, 0);
        }
    }*/
}
