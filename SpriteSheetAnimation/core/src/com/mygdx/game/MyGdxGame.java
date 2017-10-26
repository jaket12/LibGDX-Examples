package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	// Constant rows and columns of the sprite sheet
	private int FRAME_COLS = 6;
	private int FRAME_ROWS = 5;

	// Objects used
	Texture RunningDudeSheet;//The sheet that contains all possible images to draw. Used to build the animation.
	Animation<TextureRegion> RunningAnimation; //The array of sprites that will animate in sequence.
	TextureRegion CurrentFrame;//The actual image we would want to draw on the screen
	float AnimationTime;// A variable for tracking elapsed time for the animation, to determine which image to show
	Vector3 Location;//Lets make a still image, and a moving image.

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		// Load the sprite sheet as a Texture (image) that we will break up into an animation.
		RunningDudeSheet = new Texture(Gdx.files.internal("Image/Sprite/RunningDude.png"));

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(RunningDudeSheet,
				RunningDudeSheet.getWidth() / FRAME_COLS,
				RunningDudeSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		//Normally, the sprite sheet will hold more than just one animation (or combination)
		//So when creating an animation this is the method to make the entire sheet animate.
		//Otherwise, just refer to each array index individually to make a custom one.
		TextureRegion[] runningFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				runningFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames.
		//The number is how fast the animation goes: larger is faster.
		//It relates to seconds / frames: 1 / 30 is 30 frames per second, or 0.033 time for each frame
		RunningAnimation = new Animation<TextureRegion>(0.033f, runningFrames);

		//We need to track where in the animation we currently are, so this will hold the incrementing delta time
		AnimationTime = 0f;
		Location = new Vector3(new Vector2(-100f, 300f), 0);//Vector3 is a 3d with xyz, Vector2 is 2d, xy.
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Increase the delta for this frame, so we know which frame of the animation to show
		AnimationTime += Gdx.graphics.getDeltaTime();
		Location.x += Gdx.graphics.getDeltaTime() * 100;//Increase the location for the second running dude to look like hes moving
		if (Location.x > Gdx.graphics.getWidth() + 100) {
			Location.x = -100f;
		}
		//Get the frame that should be drawn out of the animation. The boolean is for if it's looping.
		//currentFrame is the image that we would want to draw!
		CurrentFrame = RunningAnimation.getKeyFrame(AnimationTime, true);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(CurrentFrame, 50, 50);//Draw it like usual for an image.
		batch.draw(CurrentFrame, Location.x, Location.y);//Draw it like usual for an image.
		batch.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
