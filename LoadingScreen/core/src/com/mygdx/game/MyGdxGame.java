package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.GameStates.SplashScreen;

public class MyGdxGame extends Game {

	private Game game;

	public MyGdxGame() {
		game = this;
	}

	@Override
	public void create () {
		setScreen(new SplashScreen(game));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
