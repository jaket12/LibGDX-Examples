package com.mygdx.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by McRib on 7/9/2017.
 */

public class GameAssetManager {


    public AssetManager manager = new AssetManager();

    /**
     * List of all things the manager should load at any point in the game.
     * This list can be accessed from outside the object in order to request loading that asset.
     * So you can think of it like an enum of sorts: MainMenuImage = GameAssets.manager.get(GameAssets.LoadingImage);
     */
    public static final AssetDescriptor<Texture> LoadingImage = new AssetDescriptor<Texture>("Image/LoadingImage.png", Texture.class);
    public static final AssetDescriptor<Sound> Sound1 = new AssetDescriptor<Sound>("Sound/BusterCharge.ogg", Sound.class);
    public static final AssetDescriptor<Sound> Sound2 = new AssetDescriptor<Sound>("Sound/TigerRoar.wav", Sound.class);
    public static final AssetDescriptor<Music> Music1 = new AssetDescriptor<Music>("Music/Whistle2.ogg", Music.class);


    /**
     * Called at the start of the game, this is intended to load the mandatory things that the game
     * needs to run, like basic fonts, music, UI, and images.
     *
     * The load() function will set up a queue of items to access over time. It does not instantly
     * place everything into memory all in one frame. You must call update() in order to iterate
     * through each item.
     *
     * If the game is very simple then everything can just be loaded right here without any issue.
     * If the game is complex, then load the required here, and have other functions for other things,
     * along with letting the screen manually choose what to load as well.
     */
    public void LoadStartUp()
    {
        manager.load(LoadingImage);
        manager.load(Sound1);
        manager.load(Sound2);
        manager.load(Music1);
    }

    /**
     * Load everything in the resource list into memory, and force the game to wait until everything
     * is finished. This may result is a paused game until everything finishes, the delay is based
     * on the overall file size of the resources (each 10mb takes about a second).
     */
    public void LoadAllResources() {
        manager.load(LoadingImage);
        manager.load(Sound1);
        manager.load(Sound2);
        manager.load(Music1);

        manager.finishLoading();//Block the calls until everything is done loading.
    }

    /**
     * Call the dispose() method for each asset that has been loaded.
     * This will essentially remove every asset from the game.
     */
    public void Dispose()
    {
        manager.dispose();
    }

    public boolean Update() {
        return manager.update();
    }

}
