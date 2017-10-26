package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	//Everything uses this camera to draw.
	OrthographicCamera camera;
	Viewport viewport;

	//Tile map stuff
	TiledMap TiledMap;
	OrthogonalTiledMapRenderer OrthoMapRenderer;

	//Box2D stuff
	private World world;
	private Array<Body> TileMapCollisionLayerBodies;//The objects in the tile map for solid areas. Used to access them if ever needed.
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private float FrameAccumulator = 0;
	private float CurrentFrameTime;
	private float TimeStep = 1/60f;
	private int VelocityIterations = 6;
	private int PositionIterations = 2;

	private PlayerCharacter Player;

	@Override
	public void create () {
		//Basic set up of the program
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();

		//Set up the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		camera.update();
		viewport = new FitViewport(640,480,camera);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		viewport.apply();
		camera.update();

		//Set up the Tile Map
		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMagFilter = TextureFilter.Nearest;
		params.textureMinFilter = TextureFilter.Nearest;
		TiledMap = new TmxMapLoader().load("Level/TestLevel.tmx", params);
		OrthoMapRenderer = new OrthogonalTiledMapRenderer(TiledMap, 1f);
		//There is a stupid tileset glitch for libgdx: if you resize the camera or pan the tiles, the spacing lines will show.
		//This needs to be run on every tile set every time you edit the file. It adds spaces to it.
		//But, it only works on desktop, not in the core folder. So you have to put a lot of effort to make it run right.
		/*Settings settings = new Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		settings.duplicatePadding = true;
		TexturePacker.process(settings, "source",
				"destination", "name");
		*/

		//Set up Box2D
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		Player = new PlayerCharacter(320, 240);
		Player.SetBody(world.createBody(Player.GetBodyDef()));
		Player.CreateFixture();

		//Pull Object Tile Map Layer and place into Box2D
		TileMapCollisionLayerBodies = MapBodyBuilder.BuildShapes(TiledMap, 1f, world);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Logic Step
		Player.Update();

		//Draw Step
		camera.update();
		OrthoMapRenderer.setView(camera);
		OrthoMapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		debugRenderer.render(world, camera.combined);
		batch.end();

		//Box2D Update Step
		CurrentFrameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
		FrameAccumulator += CurrentFrameTime;
		while (FrameAccumulator >= TimeStep) {
			world.step(TimeStep, VelocityIterations, PositionIterations);
			FrameAccumulator -= TimeStep;
		}
	}

	@Override
	public void dispose () {
		TiledMap.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
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
		Gdx.app.log("Mouse Event", "Click at " + screenX + "," + screenY);
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX,screenY,0));

		Player.StopMovement();

		if (worldCoordinates.x < (camera.viewportWidth / 2f) - 50
				&& worldCoordinates.y > (camera.viewportHeight / 2f) - 50
				&& worldCoordinates.y < (camera.viewportHeight / 2f) + 50) {
			Player.MoveLeft();
		}
		if (worldCoordinates.x >= (camera.viewportWidth / 2f) + 50
				&& worldCoordinates.y > (camera.viewportHeight / 2f) - 50
				&& worldCoordinates.y < (camera.viewportHeight / 2f) + 50) {
			Player.MoveRight();
		}

		if (worldCoordinates.y < (camera.viewportHeight / 2f) - 50
				&& worldCoordinates.x > (camera.viewportWidth / 2f) - 50
				&& worldCoordinates.x < (camera.viewportWidth / 2f) + 50) {
			Player.MoveUp();
		}
		if (worldCoordinates.y >= (camera.viewportHeight / 2f) + 50
				&& worldCoordinates.x > (camera.viewportWidth / 2f) - 50
				&& worldCoordinates.x < (camera.viewportWidth / 2f) + 50) {
			Player.MoveDown();
		}

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
