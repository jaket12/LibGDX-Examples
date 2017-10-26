package com.mygdx.game;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {

	private LinearLayout Layout;//The main window that everything is put into
	private View GameView;//The top window that the game is run in
	private AdView AdBanner;//The bottom window that the ad will be displayed
	private LinearLayout.LayoutParams AdBannerLayoutParams;//Rules on how to show the ad
	private AdRequest AdRequest;//The call required to fetch an ad from the server
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX";//Key to say who this ad belongs to
	private static final String GalaxyS7DeviceId = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";//Unique Id for a single phone. Required for ad testing. Delete on production!

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide the weird grey bar that has the program name
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//Set preference to allow the status bar to show

		// Create the libgdx View
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = false;//Declare the the android status bar (time and notifications) should be visible
		GameView = initializeForView(new MyGdxGame(), config);//Set the game view as the primary target for the game engine to run with

		//Create the AdMob View
		AdBanner = new AdView(this);
		AdBanner.setVisibility(View.VISIBLE);
		AdBanner.setBackgroundColor(0xff000000); // black
		AdBanner.setAdUnitId(BANNER_AD_UNIT_ID);
		AdBanner.setAdSize(AdSize.SMART_BANNER);//There is a banner and smart banner option
		AdRequest = new AdRequest.Builder().addTestDevice(GalaxyS7DeviceId).build();//Permit the phone to actually display (test) ads
		AdBanner.loadAd(AdRequest);//Request an actual ad. If debugging, this will be a test one

		//Set up how the program will be viewed

		// Create the layout. This one is vertical stacked columns that will fill all possible space
		Layout = new LinearLayout(this);
		Layout.setOrientation(LinearLayout.VERTICAL);

		//Set options for the game view to take all remaining screen space, and have priority weight for always being on screen
		//Height of 0 means it defaults to taking no space, until it actually has something to display: fit height to content
		LinearLayout.LayoutParams GameParams =
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
		GameParams.height = 0;
		GameParams.weight = 1;

		//Set options for ad view. Takes only the height required, and fills the width of the device
		LinearLayout.LayoutParams adParams =
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);

		// Add the libgdx game view first: this one will be on top
		Layout.addView(GameView, GameParams);

		// Add the AdMob view second: it will be on the bottom of the screen
		Layout.addView(AdBanner, adParams);

		// Hook it all up: allow the game to be viewed and start running
		setContentView(Layout);

	}

}
