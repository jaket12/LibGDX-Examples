package com.mygdx.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public AssetManager manager = new AssetManager();

    public static final AssetDescriptor<Texture> DefaultImage =
            new AssetDescriptor<Texture>("badlogic.jpg", Texture.class);

    public static final AssetDescriptor<TextureAtlas> UiSkinAtlas =
            new AssetDescriptor<TextureAtlas>("Scene2DUI/uiskin.atlas", TextureAtlas.class);

    public static final AssetDescriptor<Skin> UiSkinJson =
            new AssetDescriptor<Skin>("Scene2DUI/uiskin.json", Skin.class);

    public void load()
    {
        manager.load(DefaultImage);
        manager.load(UiSkinAtlas);
        manager.load(UiSkinJson);

    }

    public void dispose()
    {
        manager.dispose();
    }
}