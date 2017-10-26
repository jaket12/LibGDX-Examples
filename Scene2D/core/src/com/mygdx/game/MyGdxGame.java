package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MyGdxGame extends ApplicationAdapter {

	private Assets assets;
	private Stage stage;
	private Table table;
	private Skin skin;

	private BitmapFont FontBebasNeue;
	private BitmapFont FontGotham;
	private BitmapFont FontMega;

	@Override
	public void create () {

		//Original();

		Testing();

	}

	private void Testing() {

		//Set up the structure to hold the interface in. Make a stage and table to contain and update every widget
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Table RootTable = new Table();
		RootTable.setFillParent(true);
		stage.addActor(RootTable);
		//RootTable.setDebug(true);

		//Get a custom font to use on any widget you want. If not specified, the default font within the skin will be used
		FreeTypeFontGenerator FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/year_200x.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter FontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FontParameters.size = 16;
		FontParameters.kerning = false;
		FontParameters.color = new Color(1, 1, 1, 1);//Color white to allow for other color tinting
		FontMega = FontGenerator.generateFont(FontParameters);
		FontGenerator.dispose();
		//Use this Label Style on any Label widget to apply the font. If the BitmapFont was generated as white, you can color it anything you want here.
		Label.LabelStyle LabelStyleMega = new Label.LabelStyle(FontMega, Color.BLUE);

		//Load the skin from the file. This contains the images, co-ordinates, and css styles for each widget
		skin = new Skin();
		FileHandle fileHandle = Gdx.files.internal("Scene2DUI/uiskin.json");
		FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
		if (atlasFile.exists()) {
			skin.addRegions(new TextureAtlas(atlasFile));
		}
		skin.load(fileHandle);

		//Create a widget! Make a text label with whatever you want in it. Say what kind of skin it will use
		Label nameLabel = new Label("Name:", skin);
		//Use a custom font for this widget. If this line is commented, you will get the default font from the skin itself
		nameLabel.setStyle(LabelStyleMega);
		//Add the widget to the table. At this point you can chain commands to make spacing and padding
		RootTable.add(nameLabel);

		//Add a text field for typing in. If you want to get the value that was typed, you have to save a reference to this widget
		TextField nameText = new TextField("", skin);
		//Create a style for TextFields (based on the original skin we used as a shortcut), and change the font too.
		TextField.TextFieldStyle TextFieldStyle = new TextField.TextFieldStyle(nameText.getStyle());//If you try to change the style directly, then it applies to everything using this skin!
		TextFieldStyle.font = FontMega;
		TextFieldStyle.fontColor = Color.LIME;
		nameText.setStyle(TextFieldStyle);
		//Add the widget to the table
		RootTable.add(nameText);

		//Make some default widgets too. These have no custom style outside of the skin itself
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("", skin);

		//Add these default widgets to the table, but drop them into another row
		RootTable.row();
		RootTable.add(addressLabel);
		RootTable.add(addressText);
	}

	private void Original() {

		assets = new Assets();//Pass this assets variable around to other screens, don't make another
		assets.load(); //starts loading assets
		assets.manager.finishLoading(); //Continues when done loading.

		stage = new Stage();//Scene2D uses Stage as the top level thing to contain all things. It can draw and update actors/widgets inside of it
		Gdx.input.setInputProcessor(stage);

		table = new Table();//A layout type of thing used to manage multiple actors/widgets. It is an actor itself
		table.setFillParent(true);//Setting a table to fill parent means it will always take max space. This should only be used at the root level table.
		stage.addActor(table);//Add the table to the stage so it can be drawn and updated

		table.setDebug(true); // This is optional, but enables debug lines for tables.

		FreeTypeFontGenerator FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/BebasNeue.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter FontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FontParameters.size = 12;//This is in terms of pixels
		FontParameters.borderColor = new Color(0, 0, 0, 1);
		FontParameters.borderWidth = 3;
		FontParameters.color = new Color(1, 1, 1, 1);
		FontBebasNeue = FontGenerator.generateFont(FontParameters);

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
		FontGenerator.dispose();
		Label.LabelStyle LabelStyleTitle = new Label.LabelStyle(FontMega, Color.WHITE);


		skin = new Skin();
		skin.add("default-font", FontMega, BitmapFont.class);
		FileHandle fileHandle = Gdx.files.internal("Scene2DUI/uiskin.json");
		FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
		if (atlasFile.exists()) {
			skin.addRegions(new TextureAtlas(atlasFile));
		}
		skin.load(fileHandle);




		//	skin = assets.manager.get(Assets.UiSkinJson);
		//	skin.add("default-font", FontBebasNeue);
		//	skin.addRegions(new TextureAtlas(Gdx.files.internal("Scene2DUI/uiskin.atlas")));
		//	skin.add("default-font", FontMega, BitmapFont.class);

		// Add widgets to the table here
		TextButton button1 = new TextButton("Button 1", skin);//Whatever text it will show, and the style of button it will be.
		button1.pad(0, 20, 0, 20);//You can really only add padding to an element through code. the JSON supposedly can do it, but I don't see how
		table.add(button1);

		TextButton button2 = new TextButton("      ", skin);
		table.add(button2);

		Label nameLabel = new Label("Name:", skin);
		nameLabel.setStyle(LabelStyleTitle);
		TextField nameText = new TextField("", skin);
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("", skin);

		table.add(nameLabel).width(Value.percentWidth(1.33f));//Percents can be used to denote values as well using this exact method
		table.add(nameText).width(100);//dimensions are always pixel based otherwise
		table.row();
		table.add(addressLabel);
		Cell addresscell = table.add(addressText).width(100);//widgets do not set their own size. The thing that contains it does.

		//Everything done on a table will return the cell in the table. You can modify it later on by saving it to a variable
		addresscell.pad(40);

		table.bottom();//You can anchor things using a direction

		//Padding and spacing are similar.
		//padding is extra space within a cell that will combine with other nearby cells if they also have padding
		//spacing does the same thing, but it will not combine
		//spacing is good for aligning text in a form field

		//Apparently it's not possible to have a smart sizing button or image background. They will always stretch out to fill the space?

		/*
		UISkin.atlas is where each 'thing' is defined.

		//Start of file
		uiskin.png//Reference to the actual image this is using
		size: 256,128//Total dimensions of the image it's using
		format: RGBA8888//Colors. I don't know.
		filter: Linear,Linear//Any processing to apply. Linear Linear means show it exactly as you see it, but stretched images will smear.
		repeat: none//Not sure
		check-off//The first element we are defining. This is the name of it: check-off
		  rotate: false//If the image is flipped in any way post process
		  xy: 11, 5//The topleft pixel coordinate of the image section to use
		  size: 14, 14//The width and height of the box for the image. This ends up being 11+14 and 5+14, so the image section coordinates on the image are 11,5 to 25, 19
		  orig: 14, 14//Usually set to the same as size, I'm not sure. deals with padding some how
		  offset: 0, 0//doesn't seem to do anything
		  index: -1//doesn't seem to do anything
			split: 5, 5, 5, 4//take the corners of this image section and do not stretch them to fit the width and height of the final widget. each number is the pixels to not stretch per corner.
		If you want to add another thing to the skin, then just add another name right below and it works. Remember that attributes to the thing have two spaces and are below the name.

		 */

		/*
		uiskin.json is the css style of the widgets. it follows the same methodology

		com.badlogic.gdx.scenes.scene2d.ui.Skin$TintedDrawable: {
			dialogDim: {
				name: white,
				color: { r: 0, g: 0, b: 0, a: 0.45 },
			},
			default: {
				down: default-round-down,
				up: default-round
			}
		}
		 */
	}
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}