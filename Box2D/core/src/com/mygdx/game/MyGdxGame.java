package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	private World world;
	private float FrameAccumulator = 0;//Used for the update loop to smooth out frames
	private float CurrentFrameTime;//The delta for this frame, which does not exceed the limit
	private float TimeStep = 1/60f;//The expected time it takes to perform one frame
	private int VelocityIterations = 6;//How many times Box2D will update the velocity of things on an update?
	private int PositionIterations = 2;//How many times Box2d will update the position of things on an update?
	private Box2DDebugRenderer debugRenderer;//Draws the outline for each box.

	private Body PlayerBody;
	private float PlayerSpeed = 200f;
	private float VelocityDifferenceX;
	private float VelocityDifferenceY;
	private float CurrentVelocityX;
	private float CurrentVelocityY;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	@Override
	public void create () {

		//Set up the camera for the game. Box2D requires this for the debug rendering to show outlines
		//Using stuff from the Viewport test project
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(300,300,camera);//The screen resolution will impact the size of all things in the Box2D World!
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);

		//Initialize the Box2D system so that we can use it anywhere in the program.
		Box2D.init();

		//Create a world which contains all boxes.
		//Boxes have no visuals: it is only math and logic. You have to draw them yourself with LibGDX.
		//First parameter is gravity: things are based from the bottom left of the screen, not the top left.
		//The gravity is arbitrary: whatever value you put in is in relation to whatever forces apply to boxes.
		//Make sure the values are reasonably similar. 1 unit = 1 meter
		//Second parameter is if you want boxes to sleep if nothing happens to them for some time
		world = new World(new Vector2(0, 0), true);

		//Draw box outlines
		debugRenderer = new Box2DDebugRenderer();


		//Test objects

		//Step 1: Create a Body Definition (Dynamic)
		//Step 2: Add a new Body to the World based on the Body Definition. The return value lets you modify it further.
		//Step 3: Create a Fixture for the Body. Define its shape and parameters.
		//Step 4: Add a Fixture to the Body based on Step 3.
		//Step 5: Dispose of the Shape if you aren't using it to make anymore Fixtures.



		//Objects are called "Bodies" in Box2D.
		//A Body contains "Fixtures" which are parts of the body. Many parts can make up something like those crappy flash games
		//A fixture needs a defined "Shape" which is how the box outline looks
		//A fixture also needs "Density". High values are heavy, low values are light
		//A fixture also needs "Friction". Low values don't reduce speed much, high values make the Body stop very fast
		//A fixture also needs "Restitution". Low values means when the Body hits another Body, it immediately stops. A value of 1 or more means it immediately starts going the opposite way. A value greater than one means it's going to speed up.

		//Dynamic body. This one is a ball that falls and bounces
		BodyDef bodyDef = new BodyDef();
		//A body can be one of 3 things:
		//Dynamic: able to move and interact with other Bodies. Something like the player.
		//Static: doesn't move and doesn't interact with other Bodies. The ground or any immovable object.
		//Kinematic: A mix: the Body can move however it wants, but other Bodies cannot interfere with it. An unstoppable force.
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		//Set our body's starting position in the world. Two positive values means it will be fully on screen.
		bodyDef.position.set(20, 300);

		//Create our body in the world using our body definition.
		Body body = world.createBody(bodyDef);

		//Create a circle shape and set its radius to 6 units in the world (whatever that means, based similar to the gravity values)
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);

		//Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		//Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

		//Dispose of shapes after you're done with them. BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();



		//Create a Static Body for the ground
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;//Static is default, but setting it anyways
		groundBodyDef.position.set(0, 10);
		Body groundBody = world.createBody(groundBodyDef);
		//Use a Polygon to make the box for this thing as an example.
		PolygonShape groundBox = new PolygonShape();
		//Set Shape as a box. This function will actually result in a box that is double your input for whatever reason.
		groundBox.setAsBox(camera.viewportWidth, 10.0f);
		//Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f);//Second parameter is density. Friction and Restitution are left default.
		groundBox.dispose();

		//Kinematic body to just move around
		BodyDef KinematicBodyDef = new BodyDef();
		KinematicBodyDef.type = BodyDef.BodyType.KinematicBody;
		KinematicBodyDef.position.set(70, 100);
		Body KinematicBody = world.createBody(KinematicBodyDef);
		FixtureDef KinematicFixtureDef = new FixtureDef();
		KinematicFixtureDef.density = 1;
		KinematicFixtureDef.friction = 1;
		KinematicFixtureDef.restitution = 1;
		PolygonShape KinematicShape = new PolygonShape();
		KinematicShape.setAsBox(10, 10);//Going to be 20,20 in size actually
		KinematicFixtureDef.shape = KinematicShape;
		Fixture KinematicFixture = KinematicBody.createFixture(KinematicFixtureDef);
		KinematicShape.dispose();

		//For motion, we can use Impulses or Forces.
		//An Impulse is an instant shift in velocity. It's Mega Man physics.
		//Forces are gradual shifts in velocity. It's Kerbal Space Program physics.
		//Force and Impulse can be applied to different locations on a Body. This might actually spin it or shift it.
		//A force applied to the dead center of a Body is Mega Man physics: it just moves in that direction.
		//A force applied anywhere else will give it torque and make it spin or move weird.

		//Let's make a player person thing
		BodyDef PlayerBodyDef = new BodyDef();
		PlayerBodyDef.type = BodyDef.BodyType.DynamicBody;
		PlayerBodyDef.position.set(150, 150);
		PlayerBody = world.createBody(PlayerBodyDef);
		FixtureDef PlayerFixtureDef = new FixtureDef();
		PlayerFixtureDef.density = 1;
		PlayerFixtureDef.friction = 1;
		PlayerFixtureDef.restitution = 1;
		PolygonShape PlayerShape = new PolygonShape();
		PlayerShape.setAsBox(10, 10);
		PlayerFixtureDef.shape = PlayerShape;
		Fixture PlayerFixture = PlayerBody.createFixture(PlayerFixtureDef);
		PlayerShape.dispose();



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//This would be where the logic step of the game applies.

		//Player movement

		//Negate the previous frame velocity for X
		VelocityDifferenceX = 0 - PlayerBody.getLinearVelocity().x;
		CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
		PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
		//Negate the previous frame velocity for Y
		VelocityDifferenceY = 0 - PlayerBody.getLinearVelocity().y;
		CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
		PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);

		//Given player input, apply velocity
		if (Gdx.input.isKeyPressed(Input.Keys.A) && PlayerBody.getLinearVelocity().x > -PlayerSpeed) {
			//PlayerBody.applyLinearImpulse(-500f, 0, pos.x, pos.y, true);
			//PlayerBody.applyForce(-500f, 0, pos.x, pos.y, true);

			VelocityDifferenceX = -PlayerSpeed - PlayerBody.getLinearVelocity().x;
			CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
			PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) && PlayerBody.getLinearVelocity().x < PlayerSpeed) {
			//PlayerBody.applyLinearImpulse(500f, 0, pos.x, pos.y, true);
			//PlayerBody.applyForce(500f, 0, pos.x, pos.y, true);

			VelocityDifferenceX = PlayerSpeed - PlayerBody.getLinearVelocity().x;
			CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
			PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S) && PlayerBody.getLinearVelocity().y > -PlayerSpeed) {
			//PlayerBody.applyLinearImpulse(0, -500f, pos.x, pos.y, true);
			//PlayerBody.applyForce(0, -500f, pos.x, pos.y, true);

			VelocityDifferenceY = -PlayerSpeed - PlayerBody.getLinearVelocity().y;
			CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
			PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W) && PlayerBody.getLinearVelocity().y < PlayerSpeed) {
			//PlayerBody.applyLinearImpulse(0, 500f, pos.x, pos.y, true);
			//PlayerBody.applyForce(0, 500f, pos.x, pos.y, true);

			VelocityDifferenceY = PlayerSpeed - PlayerBody.getLinearVelocity().y;
			CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
			PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
		}

		//Drawing step
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//When drawing the boxes or sprites they represent, it should be before the update step, but after the game logic step.
		debugRenderer.render(world, camera.combined);

		batch.end();


		//Update step
		//When it's time to update the boxes, it's best to do so at the very end of the frame.
		//Set up their statuses and values first, and then make them move.

		//Interpolated method to update?
		CurrentFrameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
		FrameAccumulator += CurrentFrameTime;
		while (FrameAccumulator >= TimeStep) {
			world.step(TimeStep, VelocityIterations, PositionIterations);
			FrameAccumulator -= TimeStep;
		}

		//The default simple way to update. Don't ever use it.
		// /world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Gdx.app.log("Mouse Event","Click at " + screenX + "," + screenY);
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX,screenY,0));
		Gdx.app.log("Mouse Event","Projected at " + worldCoordinates.x + "," + worldCoordinates.y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
