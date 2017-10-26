package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
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
	private Box2DDebugRenderer debugRenderer;
	private Boolean DrawBox2DModels = false;
	private SpriteBatch batch;
	private float FrameAccumulator = 0;
	private float CurrentFrameTime;
	private float TimeStep = 1/60f;
	private int VelocityIterations = 6;
	private int PositionIterations = 2;

	private PlayerCharacter Player;
	public Texture PlayerImage;

	//Pathing stuff
	FlatTiledGraph worldMap;
	TiledSmoothableGraphPath<FlatTiledNode> path;
	TiledManhattanDistance<FlatTiledNode> heuristic;
	IndexedAStarPathFinder<FlatTiledNode> pathFinder;
	PathSmoother<FlatTiledNode, Vector2> pathSmoother;
	ShapeRenderer renderer;
	int TileWidth = 32;
	int startTileX;
	int startTileY;
	int endTileX = 7;
	int endTileY = 7;
	Boolean smooth = true;

	Texture img;
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

		//Set up Box2D
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		Player = new PlayerCharacter(320, 240);
		Player.SetBody(world.createBody(Player.GetBodyDef()));
		Player.CreateFixture();
		PlayerImage = new Texture("AwesomeSmall.png");

		//Pull Object Tile Map Layer and place into Box2D
		MapBodyBuilder.BuildShapes(TiledMap, 1f, world);

		//Pathfinding stuff
		renderer = new ShapeRenderer();
		worldMap = new FlatTiledGraph();
		worldMap.SetMap(TiledMap);
		worldMap.init();

		path = new TiledSmoothableGraphPath<FlatTiledNode>();
		heuristic = new TiledManhattanDistance<FlatTiledNode>();
		pathFinder = new IndexedAStarPathFinder<FlatTiledNode>(worldMap, true);
		pathSmoother = new PathSmoother<FlatTiledNode, Vector2>(new TiledRaycastCollisionDetector<FlatTiledNode>(worldMap));

		img = new Texture("badlogic.jpg");
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
		Player.Draw(batch);
		batch.end();

		if (DrawBox2DModels) {
			debugRenderer.render(world, camera.combined);
		}

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(1, 0, 0, 0.3f);
		int nodeCount = path.getCount();
		for (int i = 0; i < nodeCount; i++) {//On actual implementation, instead of drawing nodes, the object moves to the nodes
			FlatTiledNode node = path.nodes.get(i);
			renderer.rect(node.x * TileWidth, node.y * TileWidth, TileWidth, TileWidth);
		}
		if (smooth) {
			renderer.end();
			renderer.begin(ShapeRenderer.ShapeType.Line);
			float hw = TileWidth / 2f;
			if (nodeCount > 0) {
				FlatTiledNode prevNode = path.nodes.get(0);
				for (int i = 1; i < nodeCount; i++) {
					FlatTiledNode node = path.nodes.get(i);
					renderer.line(node.x * TileWidth + hw, node.y * TileWidth + hw, prevNode.x * TileWidth + hw, prevNode.y * TileWidth + hw);
					prevNode = node;
				}
			}
		}
		renderer.end();

		if (Player.IsFollowingPath) {
			renderer.begin(ShapeRenderer.ShapeType.Filled);
			renderer.setColor(0, 1, 0, 1f);
			renderer.rectLine(Player.PlayerBody.getPosition(), Player.TargetPosition, 2);
			renderer.end();
		}

		Gdx.gl.glDisable(GL20.GL_BLEND);

		//Box2D Update Step
		CurrentFrameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
		FrameAccumulator += CurrentFrameTime;
		while (FrameAccumulator >= TimeStep) {
			world.step(TimeStep, VelocityIterations, PositionIterations);
			FrameAccumulator -= TimeStep;
		}
	}

	private void updatePath () {
		FlatTiledNode startNode = worldMap.getNode(startTileX, startTileY);
		FlatTiledNode endNode = worldMap.getNode(endTileX, endTileY);
		if (endNode.type == FlatTiledNode.TILE_FLOOR) {
			path.clear();
			worldMap.startNode = startNode;
			long startTime = nanoTime();
			pathFinder.searchNodePath(startNode, endNode, heuristic, path);
			if (pathFinder.metrics != null) {
				float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
				System.out.println("----------------- Indexed A* Path Finder Metrics -----------------");
				System.out.println("Visited nodes................... = " + pathFinder.metrics.visitedNodes);
				System.out.println("Open list additions............. = " + pathFinder.metrics.openListAdditions);
				System.out.println("Open list peak.................. = " + pathFinder.metrics.openListPeak);
				System.out.println("Path finding elapsed time (ms).. = " + elapsed);
			}
			if (smooth) {
				startTime = nanoTime();
				pathSmoother.smoothPath(path);
				if (pathFinder.metrics != null) {
					float elapsed = (TimeUtils.nanoTime() - startTime) / 1000000f;
					System.out.println("Path smoothing elapsed time (ms) = " + elapsed);
				}
			}
		}
	}

	private long nanoTime () {
		return pathFinder.metrics == null ? 0 : TimeUtils.nanoTime();
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

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			Player.FollowPath(path.nodes);
		}

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
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {

		Gdx.app.log("Mouse Event", "Click at " + screenX + "," + screenY);

		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX,screenY,0));
		Vector2 tilecell = new Vector2((float)Math.floor(worldCoordinates.x / 32f), (float)Math.floor(worldCoordinates.y / 32f));

		Gdx.app.log("Mouse Event", "Tile Clicked: " + tilecell.x + "," + tilecell.y);

		int tileX = (int)tilecell.x;
		int tileY = (int)tilecell.y;
		FlatTiledNode endNode = worldMap.getNode(tileX, tileY);
		if (endNode.type == FlatTiledNode.TILE_FLOOR) {
			endTileX = tileX;
			endTileY = tileY;
			startTileX = (int)Math.floor(Player.GetPosition().x / 32f);
			startTileY = (int)Math.floor(Player.GetPosition().y / 32f);
			updatePath();
		}
		return true;
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
