package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {

	private Stage UIStage;
	private Table UIRoot;
	private Skin UISkin;
	private BitmapFont UIFontMega;
	private LabelStyle UILabelStyleMega;

	private int PowerLevelValue = 0;

	private Label PowerLevel;
	private Label SayajinScream;
	private int ChargeLevel;

	@Override
	public void create () {

		//Basic set up of structure and design
		UIStage = new Stage();
		Gdx.input.setInputProcessor(UIStage);
		Table RootTable = new Table();
		RootTable.setFillParent(true);
		RootTable.top();
		RootTable.left();
		UIStage.addActor(RootTable);
		RootTable.setDebug(true);
		UISkin = GenerateSkinDefault();
		UIFontMega = GenerateFontMega();
		UILabelStyleMega = new LabelStyle(new Label("", UISkin).getStyle());//Make a temp label to set style and copy it
		UILabelStyleMega.font = UIFontMega;

		//Build out the widgets to display and use
		Label PlayerName = new Label("Player 1 Name", UISkin);

		Label MiniMap = new Label("Minimap", UISkin);

		PowerLevel = new Label("Power Level: " + PowerLevelValue, UISkin);

		SayajinScream = new Label("", UISkin);
		SayajinScream.setStyle(UILabelStyleMega);

		//Insert the widgets in the correct format
		Cell CellPlayerName = RootTable.add(PlayerName);
		CellPlayerName.minHeight(48);
		Cell CellRow1Empty = RootTable.add();
		CellRow1Empty.expandX();
		Cell CellMiniMap = RootTable.add(MiniMap);

		RootTable.row();
		Cell CellSayajinScream = RootTable.add(SayajinScream);
		CellSayajinScream.colspan(3);
		CellSayajinScream.expand();

		RootTable.row();
		Cell CellPowerLevel = RootTable.add(PowerLevel);
		Cell CellRow3Empty = RootTable.add();
		CellRow3Empty.colspan(2);
		CellRow3Empty.expandX();

	}

	private Skin GenerateSkinDefault() {
		Skin result = new Skin();
		FileHandle fileHandle = Gdx.files.internal("Scene2DUI/uiskin.json");
		FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
		if (atlasFile.exists()) {
			result.addRegions(new TextureAtlas(atlasFile));
		}
		result.load(fileHandle);

		return result;
	}

	private BitmapFont GenerateFontMega() {
		FreeTypeFontGenerator FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/year_200x.ttf"));
		FreeTypeFontParameter FontParameters = new FreeTypeFontParameter();
		FontParameters.size = 16;
		FontParameters.kerning = false;
		FontParameters.color = new Color(1, 1, 1, 1);//Color white to allow for other color tinting
		BitmapFont result = FontGenerator.generateFont(FontParameters);
		FontGenerator.dispose();
		return result;
	}

	@Override
	public void resize (int width, int height) {
		UIStage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		UIStage.act(Gdx.graphics.getDeltaTime());
		UIStage.draw();

		PowerLevelValue += 1;
		PowerLevel.setText("Power Level: " + PowerLevelValue);

		SayajinScream.setText("A" + ChargeLevelScream(PowerLevelValue, 'A') + "H!");
	}

	private String ChargeLevelScream(int powerlevel, char spam) {
		ChargeLevel = powerlevel / 100;
		char[] repeat = new char[ChargeLevel];
		Arrays.fill(repeat, spam);
		return new String(repeat);
	}

	@Override
	public void dispose() {
		UIStage.dispose();
	}
}
