package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.io.InputStream;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	boolean optionaldelete = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		//See if we are allowed to use the disk at all to begin with
		//By default, desktop testing saves to the assets folder for android.
		String locRoot = Gdx.files.getLocalStoragePath();
		Gdx.app.log("MyTag", "Checking access to local file path: " + locRoot);
		boolean isLocalDirAvailable = Gdx.files.isLocalStorageAvailable();

		if (isLocalDirAvailable) {
			//Access the file
			Gdx.app.log("MyTag", "Accessing file");
			FileHandle filehandle = Gdx.files.local("testfile.json");

			//Check if file exists
			if (filehandle.exists()) {
				Gdx.app.log("MyTag", "File exists");
			} else {
				//If it doesn't, create it
				Gdx.app.log("MyTag", "File does not exist, creating");
				filehandle.write(true);
			}

			//Read the file
			Gdx.app.log("MyTag", "Reading the file");
			String text = filehandle.readString();
			//byte[] binarybytes = filehandle.readBytes();

			//Output the file to console
			Gdx.app.log("MyTag", "File contents:");
			Gdx.app.log("MyTag", text);

			//Write to the file
			Gdx.app.log("MyTag", "Writing to file");
			filehandle.writeString("some sort of json probably", false);

			//Optionally, delete it
			if (optionaldelete) {
				Gdx.app.log("MyTag", "Deleting file");
				filehandle.delete();
			}


		} else {
			Gdx.app.log("MyTag", "Can't access local directory");
			//Try using external instead
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
