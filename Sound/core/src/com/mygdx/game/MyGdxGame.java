package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	private GameAssetManager GameAssets;
	private Texture MainMenuImage;
	private Sound TestSound1;
	private Sound TestSound2;
	private long TimeCounter;//Constantly incrementing time value to track with

	/**
	 * A sound MUST be under 1mb in order to work. Only WAV and MP3 work on iOS and Android.
	 * Android also supports OGG, but iOS does not.
	 */
	private Music TestMusic1;
	/**
	 * A music is streamed from the hard disk, not loaded into memory. It's required for anything
	 * greater than 1mb in file size.
	 * Apparently these are very resource intensive, so you should have no more than 2 in memory
	 * at a time.
	 */

	@Override
	public void create () {
		batch = new SpriteBatch();
		GameAssets = new GameAssetManager();
		GameAssets.LoadAllResources();
		TimeCounter = TimeUtils.millis();

		MainMenuImage = GameAssets.manager.get(GameAssets.LoadingImage);

		//Load the files for this screen.
		TestSound1 = GameAssets.manager.get(GameAssets.Sound1);
		TestSound2 = GameAssets.manager.get(GameAssets.Sound2);
		TestMusic1 = GameAssets.manager.get(GameAssets.Music1);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(MainMenuImage, 0, 0);
		batch.end();

		TestMusic1.play();

		if(TimeUtils.timeSinceMillis(TimeCounter) > 2000){
			TimeCounter = TimeUtils.millis();
			TestSound1.play(1.0f);
			TestSound2.play();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		MainMenuImage.dispose();
	}
}
