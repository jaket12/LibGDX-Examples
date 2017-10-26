package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	BitmapFont FontBebasNeue;
	BitmapFont FontGotham;
	BitmapFont FontMega;



	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		//A FontGenerator only works for one font at a time. If you want to use a multiple fonts,
		//you have to create multiple generators.
		FreeTypeFontGenerator FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/BebasNeue.ttf"));

		//Parameters let you do really cool stuff that is hard to replicate. Shadows, borders, colors
		//And anything else are set here. Once the font is generated, you can't change these values.
		//That means you'd need new fonts for a color strobe (or modify the resulting bitmapfont).
		FreeTypeFontGenerator.FreeTypeFontParameter FontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FontParameters.size = 12;//This is in terms of pixels
		FontParameters.borderColor = new Color(0, 0, 0, 1);
		FontParameters.borderWidth = 3;
		FontParameters.color = new Color(1, 1, 1, 1);

		//Generating the font results in the exact output you want, into the default LibGDX BitmapFont.
		//We are done with the FreeTypeFontGenerator plug in now, and go back to the default stuff.
		FontBebasNeue = FontGenerator.generateFont(FontParameters);
		FontGenerator.dispose(); // don't forget to dispose to avoid memory leaks!

		//Lets make a few more!
		FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Gotham.ttf"));
		FontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FontParameters.size = 18;
		FontParameters.borderColor = new Color(0.5f, 0.3f, 0.7f, 1f);
		FontParameters.borderWidth = 1;
		FontParameters.color = new Color(1, 1, 1, 1);
		FontGotham = FontGenerator.generateFont(FontParameters);

		FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/year_200x.ttf"));
		FontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FontParameters.size = 16;
		FontParameters.kerning = false;
		//FontParameters.genMipMaps = false;
		//FontParameters.mono = true;
		//FontParameters.minFilter = Texture.TextureFilter.MipMapLinearNearest;
		//FontParameters.magFilter = Texture.TextureFilter.Linear;
		FontParameters.color = new Color(0, 0, 0, 1);
		FontMega = FontGenerator.generateFont(FontParameters);

		//Now that we have the fonts we intend to use, we have to draw text with them.
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);

		//Fonts need the batch object to draw, but they take it as a parameter unlike images, which
		//are drawn through the batch itself. There's a couple parameters extra for fonts, like
		//horizontal alignment and with cutoff (I think), so things can get a little fancy, but not much.
		//There is a BitmapFontCache, which is where the image of the font+text lives. If you get this
		//image and manipulate it, you can do a strobe effect or some other image manipulation to it.
		//Each font has it's own cache, so changing one font will affect all text using it!
		FontBebasNeue.draw(batch, "The be be's new", 50, 100);
		FontGotham.draw(batch, "Batman font", 50, 200);
		FontMega.draw(batch, "I'm gonna break you, Mega Man!", 50, 300);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
