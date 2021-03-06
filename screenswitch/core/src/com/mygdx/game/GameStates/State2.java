package com.mygdx.game.GameStates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by McRib on 5/22/2017.
 */

public class State2 implements Screen {

    private Game game;
    SpriteBatch batch;
    Texture img;

    public State2(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("slow.png");
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            game.setScreen(new State1(game));
        }
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
