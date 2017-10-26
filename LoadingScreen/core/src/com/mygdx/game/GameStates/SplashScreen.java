package com.mygdx.game.GameStates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameAssetManager;

/**
 * The first screen to be loaded into the game. On initialize, it will call the AssetManager
 * to load up everything the game needs. Once that is done, the screen will automatically switch
 * to the Main Menu state so that the game can start.
 */
public class SplashScreen implements Screen {

    private Game game;
    private GameAssetManager GameAssets;
    private long GameStartTime;
    /**
     * Since the splash loading screen is loading everything, it needs to have it's own private
     * images just for this screen. Otherwise nothing would be shown.
     * This will be dispose()'d once the screen is done.
     */
    SpriteBatch batch;
    Texture img;

    public SplashScreen(Game game) {
        this.game = game;
        GameStartTime = TimeUtils.millis();

        batch = new SpriteBatch();
        img = new Texture("Images/LoadingImage.png");

        GameAssets = new GameAssetManager();
        GameAssets.LoadStartUp();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        //Run the loading queue of the manager. Once Update() returns true, that means everything is done.
        //If the manager loads things really fast then this screen just blinks away.
        //We also have a timer delay to ensure that you can at least see the screen for a moment.
        if(GameAssets.Update() && TimeUtils.timeSinceMillis(GameStartTime) > 1000){
            game.setScreen(new MainMenu(game, GameAssets));//Whenever you switch states, ALWAYS pass the assets!
            //This is so that everything shares the same object for assets and there are no duplicates.
            //It avoids the static issues but allows sharing all the same.
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
