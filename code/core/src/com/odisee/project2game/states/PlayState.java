package com.odisee.project2game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.odisee.project2game.Game;
import com.odisee.project2game.sprites.BadTube;
import com.odisee.project2game.sprites.Bird;
import com.odisee.project2game.sprites.Coin;
import com.odisee.project2game.sprites.Tube;

/**
 * state in which the game is played
 *
 * Created by Wouter Vande Velde on 26/10/2016.
 */

public class PlayState extends State {
    private final double heightScreen;
    private final Coin coin;
    private final BadTube badTube;
    private Bird bird;
    private Texture background;

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

    private static final int TUBE_SPACING = 150;
    private static final int TUBE_COUNT = 5;

    private Array<Tube> tubes;
    private double addMoving;
    private boolean coinTaken;
    private boolean tubeClicked;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,0);

        cam.setToOrtho(false, Game.WIDTH /2, Game.HEIGHT / 2);
        background = new Texture("bgs.png");
        ground = new Texture("ground.png");

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

        heightScreen = cam.viewportHeight;

        tubes = new Array<Tube>();

        coin  = new Coin((float)(TUBE_SPACING*0.5));
        coinTaken = false;

        badTube = new BadTube((float)(TUBE_SPACING*0.5));
        tubeClicked = false;

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBEWIDTH)+100));
        }
    }

    @Override
    protected void handleInput() {
        Vector2 tmp = new Vector2(Gdx.input.getX()/2, Gdx.input.getY()/2);
        if(Gdx.input.justTouched()) {
            /**
             * width of screen is 400, height 240
             * max value of gdx input is 540 and 956
             * y-value: (inputvalue * (240/540))
             * x-value: (inputvalue * (400/956))
             *      since x-value keeps increasing, substract position of the tube with position of cam
             * remove 15 points if clicked on tube
             */
            if( badTube.getBounds().y < (240-(tmp.y*0.44444)) && badTube.getBounds().y+badTube.getBounds().height > (240-(tmp.y*0.44444))) {
                if( badTube.getBounds().x - (cam.position.x-200) < (0.4184*tmp.x) && badTube.getBounds().x+ badTube.getBounds().width- (cam.position.x-200) > (0.4184*tmp.x)) {
                    badTube.repositionX(badTube.getPosBadTube().x + (BadTube.WIDTH));
                    score -= 15;
                } else {
                    bird.jump();
                }
            } else {
                bird.jump();
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        addMoving+=0.1;

        if (cam.position.x - (cam.viewportWidth / 2) > coin.getPosCoin().x + coin.getCoin().getWidth() || coinTaken) {
            coin.repositionX(coin.getPosCoin().x + (Coin.WIDTH + (int)(TUBE_SPACING*0.5)));
            coinTaken = false;
        }

        if (cam.position.x - (cam.viewportWidth / 2) > badTube.getPosBadTube().x + badTube.getBadTube().getWidth() || tubeClicked) {
            badTube.repositionX(badTube.getPosBadTube().x + (BadTube.WIDTH));
            tubeClicked = false;
        }

        for(int i = 0; i<tubes.size;i++) {
            Tube tube = tubes.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.repositionX(tube.getPosTopTube().x + (Tube.TUBEWIDTH + TUBE_SPACING)*TUBE_COUNT );
            }
            // let an additional tube move every +-62 points
            if(addMoving >= 62 ) {
                if(!tube.isMoving()) {
                    addMoving -= 71;
                    tube.makeTubesPurple(tube);
                    tube.setMoving(true);
                }
            }
            if (tube.isMoving()) {
                tube.repositionY(tube.getPosTopTube().x, tube.getPosTopTube().y);
            }

            // if collision, game is over
            if(tube.collides(bird.getBounds())) {
                gsm.set(new EndState(gsm, (int)score));
            }

            if(tube.collides(coin.getBounds())) {
                coin.moveCoin();
            }
        }

        // if collision, coinposition resets
        if(coin.collides(bird.getBounds())) {
            coinTaken = true;
        }

        // if collision, game is over
        if(badTube.collides(bird.getBounds())) {
            gsm.set(new EndState(gsm, (int)score));
        }

        // bird floats on the ground
        if(bird.getPosition().y <= ground.getHeight()) {
            bird.getPosition().y = ground.getHeight();
        }

        // bird reaches ceiling
        if(bird.getPosition().y >= cam.viewportHeight-bird.getHeight()) {
            bird.getPosition().y = cam.viewportHeight-bird.getHeight();
            bird.ceiling();
        }

        // add 30 points if coin is taken
        if (coinTaken ) {
            score += 30;
        }

        // remove 15 points if a tube is removed
        if (tubeClicked ) {
            score -= 15;
        }


        score+=0.1;
        cam.update(); // tell gdx that cam is repositioned
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        // to keep the background on the right place, at the position of the camera
        sb.draw(background, cam.position.x - (cam.viewportWidth/2), 0);

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(coin.getCoin(), coin.getPosCoin().x, coin.getPosCoin().y);
        sb.draw(badTube.getBadTube(), badTube.getPosBadTube().x, badTube.getPosBadTube().y);

        //draw the current score, a bit obsolete but used for good graphics
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

        sb.draw(ground, cam.position.x - (cam.viewportWidth/2), 0);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes ) {
            tube.dispose();
        }

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

        System.out.println("playstate disposed");
        System.out.println((int)score);
    }
}
