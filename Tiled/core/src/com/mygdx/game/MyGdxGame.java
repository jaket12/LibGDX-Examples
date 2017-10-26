package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	OrthographicCamera camera;
	Viewport viewport;

	TiledMap TiledMap;
	TiledMapTileLayer TileLayer;
	OrthogonalTiledMapRenderer OrthoMapRenderer;
	
	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20, 15);//Determines how many tiles are expected to be drawn on the screen. Keep it false, otherwise the image flips
		camera.update();

		viewport = new FitViewport(640,480,camera);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		viewport.apply();


		//Contains the tiles and any objects or whatever was created on the file
		TiledMap = new TmxMapLoader().load("Level1.tmx");
		TileLayer = (TiledMapTileLayer)TiledMap.getLayers().get(0);//If you're going to access the tile layer a lot, save it as a variable
		//Actually draws the map on the screen. Requires the tile map to draw, and the universal unit size of the game.
		//This is how many units equal a pixel on the tile map. If you did 1 pixel per unit, the answer would be whatever the tile width is
		OrthoMapRenderer = new OrthogonalTiledMapRenderer(TiledMap, 1f);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		OrthoMapRenderer.setView(camera);
		OrthoMapRenderer.render();

		//Can also choose which layers to draw this way so you can draw entities in between
		//OrthoMapRenderer.render(new int[] {0, 1});
		//OrthoMapRenderer.render(new int[] {2});


	}

	@Override
	public void dispose () {
		TiledMap.dispose();
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		//camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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

		Vector3 tilecell = new Vector3((float)Math.floor(worldCoordinates.x / 32f), (float)Math.floor(worldCoordinates.y / 32f), 0);
		Cell ClickedTileCell = TileLayer.getCell((int)tilecell.x, (int)tilecell.y);
		if (ClickedTileCell != null) {
			Gdx.app.log("Mouse Event", "Clicked this tile " + ClickedTileCell.getTile().getId());
			ClickedTileCell.getTile().getProperties().getValues();
		} else {
			Gdx.app.log("Mouse Event", "Clicked this tile " + "that don't exist!");
		}

		//Click any tile and lock into it's location.
		camera.position.set(new Vector3(((float)Math.ceil(worldCoordinates.x / 32f) * 32f), ((float)Math.ceil(worldCoordinates.y / 32f) * 32f) + 16, 0));
		//Jump directly to point in the map
		//camera.position.set(worldCoordinates);

		camera.update();

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

	private void tempjunk() {

/*
		map.getProperties().get("custom-property", String.class);
		layer.getProperties().get("another-property", Float.class);
		object.getProperties().get("foo", Boolean.class);

		MapLayer layer = map.getLayers().get(0);
		MapLayer layer = map.getLayers().get("my-layer");
		TiledMapTileLayer tiledLayer = (TiledMapTileLayer)map.getLayers().get(0);

		String name = layer.getName();
		float opacity = layer.getOpacity();
		boolean isVisible = layer.isVisible();

		MapObjects objects = layer.getObjects();

		String name = object.getName();
		float opacity = object.getOpacity();
		boolean isVisible = object.isVisible();
		Color color = object.getColor();

		Polygon poly = polyObject.getPolygon();

		//To load a tiled map from an asset manager, do this
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("level1.tmx", TiledMap.class);
		TiledMap map = assetManager.get("level1.tmx");



		TiledMapTileLayer TileMapLayer = (TiledMapTileLayer)TiledMap.getLayers().get(0);//Layer 0 is always the tile map section.
		int columns = TileMapLayer.getWidth();//Get size of the entire map
		int rows = TileMapLayer.getHeight();
		TiledMapTileLayer.Cell cell = TileMapLayer.getCell(1, 1);//Get a single location on the map by XY. LibGDX is bottom-left oriented still

		*/
	}
}
