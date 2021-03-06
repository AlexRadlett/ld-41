package com.ld41.menu.clicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.ld41.core.Button_;
import com.ld41.core.Screen_;
import com.ld41.main.Game;
import com.ld41.menu.Menu;


public class ClickerMainMenu extends Screen_ {

    private Texture castle;
    OrthographicCamera camera;
    SpriteBatch batch;
    Button_ clickerButton;
    Vector3 mPos;
    int x;
    int goldCounter;
    String goldString;
    BitmapFont font;
    Button_ dungeonButton;
    Button_ minerButton;
    int minerCounter;
    int pickaxeCounter;
    int pickaxePrice;
    String pickaxeString;
    String pickaxeStringPrice;
    String minerString;
    String minerStringPrice;
    float deltaTimer;
    int height;
    int width;
    int minerPrice;
    int gps;
    Button_ mainMenuButton;
    Button_ pickaxeButton;

    public ClickerMainMenu(Game game) {
        super(game);
    }


    @Override
    public void show() {
        goldCounter = 0;
        goldString = "Gold: 0";
        minerString = "Miners: 0";
        minerPrice = 50;
        minerStringPrice = "Price: " + minerPrice;

        pickaxePrice = 25;
        pickaxeString = "Pickaxe Upgrades: 0";
        pickaxeStringPrice = "Price: " + pickaxePrice;

        font = new BitmapFont();

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        camera = new OrthographicCamera(width, height);
        camera.position.x = width / 2;
        camera.position.y = height / 2;
        batch = new SpriteBatch();

        // add main castle texture
        castle = new Texture(Gdx.files.internal("castle/starterCastle.png"));

        // add button that adds 1 gold to total
        clickerButton = new Button_((width / 2 - 48), height - 40, "clickGold");
        clickerButton.onClick(() -> increaseGold());

        // add button that goes to dungeon... todo: or will do when I make it do that
        dungeonButton = new Button_(width - 110, 10, "toDungeon");
        dungeonButton.onClick(() ->  sendToDungeon());

        // add button that sends the user back to main menu
        mainMenuButton = new Button_(10, 10, "toMainMenu");
        mainMenuButton.onClick(() -> sendToMainMenu());


        /*
            upgrades menu
         */

        // add upgrade pickaxe button
        pickaxeButton = new Button_(10, Gdx.graphics.getHeight() - 100, "upgradePickaxe");
        pickaxeButton.onClick(() -> upgradePickaxe());

        // add miner button
        minerButton = new Button_(10, Gdx.graphics.getHeight() - 180, "addMiner");
        minerButton.onClick(() -> addMiner());

        }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();

        // draw castle texture and buttons
        x = (Gdx.graphics.getWidth() / 2) - (castle.getWidth() / 2);
        batch.draw(castle, x, 0);
        clickerButton.render(batch);
        dungeonButton.render(batch);
        minerButton.render(batch);
        mainMenuButton.render(batch);
        pickaxeButton.render(batch);

        // update gold counter
        font.draw(batch, goldString, 10, Gdx.graphics.getHeight() - 10);

        // add and update pickaxe counter and price
        font.draw(batch, pickaxeStringPrice, 115, Gdx.graphics.getHeight() - 45);
        font.draw(batch, pickaxeString, 115, Gdx.graphics.getHeight() - 75);

        // add and update miner counter and price
        font.draw(batch, minerStringPrice, 115, Gdx.graphics.getHeight() - 125);
        font.draw(batch, minerString, 115, Gdx.graphics.getHeight() - 155);

        batch.end();

        mPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mPos);

        // add hover functionality to buttons
        clickerButton.logic(mPos);
        dungeonButton.logic(mPos);
        minerButton.logic(mPos);
        mainMenuButton.logic(mPos);
        pickaxeButton.logic(mPos);

        // updates the gold every second
        deltaTimer += delta;

        if (deltaTimer > 1) {
            updateGold();
            deltaTimer = 0;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void increaseGold() {
        goldCounter ++;

        goldString = "Gold: " + String.valueOf(goldCounter);
        System.out.println(String.valueOf(goldCounter));
    }

    public void upgradePickaxe() {
        if (goldCounter >= pickaxePrice) {
            pickaxeCounter ++;
            pickaxeString = "Pickaxe Upgrades: " + String.valueOf(pickaxeCounter);
            goldCounter -= pickaxePrice;
            goldString = "Gold: " + String.valueOf(goldCounter);

            if (pickaxeCounter > 0) {
                pickaxePrice += pickaxePrice * 0.6;
                pickaxeStringPrice = "Price " + pickaxePrice;
                gps += pickaxeCounter;
            }
        } else {
            System.out.println("Not enough gold");
        }
    }

    public void addMiner() {

        if (goldCounter >= minerPrice) {
            minerCounter ++;
            minerString = "Miners: " + String.valueOf(minerCounter);
            goldCounter = goldCounter - minerPrice;
            goldString = "Gold: " + String.valueOf(goldCounter);

            // increases price of a miner by a third each time a new one is bought
            if (minerCounter > 0) {
                minerPrice += minerPrice * 0.5;
                minerStringPrice = "Price: " + minerPrice;
                gps += minerCounter * 2;
            }

        } else {
            System.out.println("Not enough gold");
        }
    }


    public void sendToDungeon() {
        // TODO: I dunno, however Oli wants to do this bit
        System.out.println("PLACEHOLDER");
    }

    public void sendToMainMenu() {
        getGame().setScreen(new Menu(getGame()));
        this.dispose();
    }

    public void updateGold() {

        System.out.println(String.valueOf(gps));
        goldCounter = goldCounter + gps;
        goldString = "Gold: " + String.valueOf(goldCounter);
    }

}
