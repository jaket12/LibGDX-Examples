package com.mygdx.game.GameStates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameAssetManager;

/**
 * Created by McRib on 5/22/2017.
 */

public class MainMenu implements Screen {

    private Game game;
    private GameAssetManager GameAssets;

    private SpriteBatch batch;
    private Texture MainMenuImage;

    public MainMenu(Game game, GameAssetManager assets) {
        this.game = game;
        this.GameAssets = assets;//We expect the asset object to be passed around for EVERY screen.
        //This means that one object is shared between all screens, so the memory is minimal.
        //This object is not static though, so it avoid the weird LIBGDX issues like broken images
        //on resume or pause.


        MainMenuImage = GameAssets.manager.get(GameAssets.TestImage1);//The screen will load any
        //resources it needs at this point, and it can pass the manager into sub objects too like
        //game entities to share the same resources.
        //This GameAssets.LoadingImage is like an enum of sorts, it doesn't show up in the code hint,
        //but it does exist in the mananger. Not sure why it isn't visible.
        //If the manager for whatever reason did not load this asset yet, the game will crash as 'Asset not loaded'

        batch = new SpriteBatch();//While the assets are held in one place, actually drawing them
        //is another problem itself. Each screen has to handle the drawing part itself, so they all
        // need their own sprite batch.
    }

    @Override
    public void render(float delta) {
        //Nothing different happens here.
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(MainMenuImage, 0, 0);
        batch.end();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
//Every time the screen becomes active, this will run
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
