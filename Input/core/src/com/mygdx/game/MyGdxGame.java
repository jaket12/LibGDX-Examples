package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * This input explanation sucks. It's better to use the viewport version instead.
 * Remember that game states will also be a thing too.
 */
public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture img;
	Vector3 ImageLocation = new Vector3();

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, ImageLocation.x, ImageLocation.y);
		batch.end();
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("MyTag", "TouchDown at " + screenX + ", " + screenY + " with pointer " + pointer + " and button " + button);
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		ImageLocation.x = screenX;
		ImageLocation.y = screenY;
		return true;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		Gdx.app.log("MyTag", "TouchDragged at " + screenX + ", " + screenY + " with pointer " + pointer);
		return true;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {

		Gdx.app.log("MyTag", "TouchUp at " + screenX + ", " + screenY + " with pointer " + pointer + " and button " + button);
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		return true;
	}

	@Override
	public void resize (int width, int height) {

	}

	@Override
	public boolean keyDown (int keycode) {

		Gdx.app.log("MyTag", "KeyDown for code " + keycode);
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		Gdx.app.log("MyTag", "KeyUp for code " + keycode);
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		Gdx.app.log("MyTag", "KeyTyped for code " + character);
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		Gdx.app.log("MyTag", "Scrolled for amount " + amount);
		return false;
	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
