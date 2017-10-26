package com.mygdx.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GameAssetManager {

    public AssetManager manager = new AssetManager();

    /**
     * List of all things the manager should load at any point in the game.
     * This list can be accessed from outside the object in order to request loading that asset.
     * So you can think of it like an enum of sorts: MainMenuImage = GameAssets.manager.get(GameAssets.LoadingImage);
     */
    public static final AssetDescriptor<Texture> LoadingImage = new AssetDescriptor<Texture>("Images/LoadingImage.png", Texture.class);
    public static final AssetDescriptor<Texture> TestImage1 = new AssetDescriptor<Texture>("Images/testimage (1).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage2 = new AssetDescriptor<Texture>("Images/testimage (2).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage3 = new AssetDescriptor<Texture>("Images/testimage (3).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage4 = new AssetDescriptor<Texture>("Images/testimage (4).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage5 = new AssetDescriptor<Texture>("Images/testimage (5).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage6 = new AssetDescriptor<Texture>("Images/testimage (6).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage7 = new AssetDescriptor<Texture>("Images/testimage (7).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage8 = new AssetDescriptor<Texture>("Images/testimage (8).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage9 = new AssetDescriptor<Texture>("Images/testimage (9).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage10 = new AssetDescriptor<Texture>("Images/testimage (10).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage11 = new AssetDescriptor<Texture>("Images/testimage (11).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage12 = new AssetDescriptor<Texture>("Images/testimage (12).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage13 = new AssetDescriptor<Texture>("Images/testimage (13).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage14 = new AssetDescriptor<Texture>("Images/testimage (14).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage15 = new AssetDescriptor<Texture>("Images/testimage (15).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage16 = new AssetDescriptor<Texture>("Images/testimage (16).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage17 = new AssetDescriptor<Texture>("Images/testimage (17).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage18 = new AssetDescriptor<Texture>("Images/testimage (18).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage19 = new AssetDescriptor<Texture>("Images/testimage (19).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage20 = new AssetDescriptor<Texture>("Images/testimage (20).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage21 = new AssetDescriptor<Texture>("Images/testimage (21).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage22 = new AssetDescriptor<Texture>("Images/testimage (22).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage23 = new AssetDescriptor<Texture>("Images/testimage (23).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage24 = new AssetDescriptor<Texture>("Images/testimage (24).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage25 = new AssetDescriptor<Texture>("Images/testimage (25).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage26 = new AssetDescriptor<Texture>("Images/testimage (26).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage27 = new AssetDescriptor<Texture>("Images/testimage (27).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage28 = new AssetDescriptor<Texture>("Images/testimage (28).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage29 = new AssetDescriptor<Texture>("Images/testimage (29).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage30 = new AssetDescriptor<Texture>("Images/testimage (30).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage31 = new AssetDescriptor<Texture>("Images/testimage (31).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage32 = new AssetDescriptor<Texture>("Images/testimage (32).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage33 = new AssetDescriptor<Texture>("Images/testimage (33).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage34 = new AssetDescriptor<Texture>("Images/testimage (34).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage35 = new AssetDescriptor<Texture>("Images/testimage (35).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage36 = new AssetDescriptor<Texture>("Images/testimage (36).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage37 = new AssetDescriptor<Texture>("Images/testimage (37).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage38 = new AssetDescriptor<Texture>("Images/testimage (38).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage39 = new AssetDescriptor<Texture>("Images/testimage (39).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage40 = new AssetDescriptor<Texture>("Images/testimage (40).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage41 = new AssetDescriptor<Texture>("Images/testimage (41).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage42 = new AssetDescriptor<Texture>("Images/testimage (42).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage43 = new AssetDescriptor<Texture>("Images/testimage (43).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage44 = new AssetDescriptor<Texture>("Images/testimage (44).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage45 = new AssetDescriptor<Texture>("Images/testimage (45).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage46 = new AssetDescriptor<Texture>("Images/testimage (46).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage47 = new AssetDescriptor<Texture>("Images/testimage (47).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage48 = new AssetDescriptor<Texture>("Images/testimage (48).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage49 = new AssetDescriptor<Texture>("Images/testimage (49).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage50 = new AssetDescriptor<Texture>("Images/testimage (50).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage51 = new AssetDescriptor<Texture>("Images/testimage (51).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage52 = new AssetDescriptor<Texture>("Images/testimage (52).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage53 = new AssetDescriptor<Texture>("Images/testimage (53).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage54 = new AssetDescriptor<Texture>("Images/testimage (54).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage55 = new AssetDescriptor<Texture>("Images/testimage (55).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage56 = new AssetDescriptor<Texture>("Images/testimage (56).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage57 = new AssetDescriptor<Texture>("Images/testimage (57).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage58 = new AssetDescriptor<Texture>("Images/testimage (58).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage59 = new AssetDescriptor<Texture>("Images/testimage (59).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage60 = new AssetDescriptor<Texture>("Images/testimage (60).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage61 = new AssetDescriptor<Texture>("Images/testimage (61).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage62 = new AssetDescriptor<Texture>("Images/testimage (62).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage63 = new AssetDescriptor<Texture>("Images/testimage (63).jpg", Texture.class);
    public static final AssetDescriptor<Texture> TestImage1gif = new AssetDescriptor<Texture>("Images/testimage (1).gif", Texture.class);

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
        manager.load(TestImage1);
        manager.load(TestImage2);
        manager.load(TestImage3);
        manager.load(TestImage4);
        manager.load(TestImage5);
        manager.load(TestImage6);
        manager.load(TestImage7);
        manager.load(TestImage8);
        manager.load(TestImage9);
        manager.load(TestImage10);
        manager.load(TestImage11);
        manager.load(TestImage12);
        manager.load(TestImage13);
        manager.load(TestImage14);
        manager.load(TestImage15);
        manager.load(TestImage16);
        manager.load(TestImage17);
        manager.load(TestImage18);
        manager.load(TestImage19);
        manager.load(TestImage20);
        manager.load(TestImage21);
        manager.load(TestImage22);
        manager.load(TestImage23);
        manager.load(TestImage24);
        manager.load(TestImage25);
        manager.load(TestImage26);
        manager.load(TestImage27);
        manager.load(TestImage28);
        manager.load(TestImage29);
        manager.load(TestImage30);
        manager.load(TestImage31);
        manager.load(TestImage32);
        manager.load(TestImage33);
        manager.load(TestImage34);
        manager.load(TestImage35);
        manager.load(TestImage36);
        manager.load(TestImage37);
        manager.load(TestImage38);
        manager.load(TestImage39);
        manager.load(TestImage40);
        manager.load(TestImage41);
        manager.load(TestImage42);
        manager.load(TestImage43);
        manager.load(TestImage44);
        manager.load(TestImage45);
        manager.load(TestImage46);
        manager.load(TestImage47);
        manager.load(TestImage48);
        manager.load(TestImage49);
        manager.load(TestImage50);
        manager.load(TestImage51);
        manager.load(TestImage52);
        manager.load(TestImage53);
        manager.load(TestImage54);
        manager.load(TestImage55);
        manager.load(TestImage56);
        manager.load(TestImage57);
        manager.load(TestImage58);
        manager.load(TestImage59);
        manager.load(TestImage60);
        manager.load(TestImage61);
        manager.load(TestImage62);
        manager.load(TestImage63);
        manager.load(TestImage1gif);
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
