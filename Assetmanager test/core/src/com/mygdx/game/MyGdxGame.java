package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bigimage;
	Assets assets;

	@Override
	public void create () {
		batch = new SpriteBatch();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("LibGDXGame", "my informative message");
		Gdx.app.error("LibGDXGame", "my error message");
		Gdx.app.debug("LibGDXGame", "my debug message");

		switch (Gdx.app.getType()) {
			case Android:
				Gdx.app.log("LibGDXGame", "using android");
				break;
			case Desktop:
				Gdx.app.log("LibGDXGame", "using Desktop");
				break;
			case iOS:
				Gdx.app.log("LibGDXGame", "using iOS");
				break;
			case WebGL:
				Gdx.app.log("LibGDXGame", "using WebGL");
				break;
			case HeadlessDesktop:
				Gdx.app.log("LibGDXGame", "using HeadlessDesktop");
				break;
			case Applet:
				Gdx.app.log("LibGDXGame", "using Applet");
				break;
			default:
				// Other platforms specific code
		}

		assets = new Assets();//Pass this assets variable around to other screens, don't make another
		assets.load(); //starts loading assets
		assets.manager.finishLoading(); //Continues when done loading.
		//it won't continue until all assets are finished loading.

		bigimage = assets.manager.get(Assets.someTexture);
		//atlas = assets.manager.get(Assets.uiAtlas);
		//skin = assets.manager.get(Assets.uiSkin);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bigimage, 0, 0);
		batch.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}

	@Override
	public void resize (int width, int height) {

	}

	@Override
	public void pause () {

	}

	@Override
	public void resume () {

	}
}
