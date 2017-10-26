package com.mygdx.game;

		import android.os.Bundle;
		import android.view.View;
		import android.view.ViewGroup;
		import android.view.Window;
		import android.view.WindowManager;
		import android.widget.RelativeLayout;

		import com.badlogic.gdx.backends.android.AndroidApplication;
		import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
		import com.google.android.gms.ads.AdRequest;
		import com.google.android.gms.ads.AdSize;
		import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {

	private RelativeLayout Layout;
	private View GameView;
	private AdView AdBanner;
	private RelativeLayout.LayoutParams AdBannerLayoutParams;
	private AdRequest AdRequest;
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXXX/XXXXXXXXX";
	private static final String GalaxyS7DeviceId = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";//Delete on production!

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		GameView = initializeForView(new MyGdxGame(), config);


		//Create the AdMob View
		AdBanner = new AdView(this);
		AdBanner.setVisibility(View.VISIBLE);
		AdBanner.setBackgroundColor(0xff000000); // black
		AdBanner.setAdUnitId(BANNER_AD_UNIT_ID);
		AdBanner.setAdSize(AdSize.SMART_BANNER);
		/*AdBannerLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		AdBannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		AdBanner.setLayoutParams(AdBannerLayoutParams);*/
		AdRequest = new AdRequest.Builder().addTestDevice(GalaxyS7DeviceId).build();
		AdBanner.loadAd(AdRequest);

		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		// Create the layout
		Layout = new RelativeLayout(this);

		// Add the libgdx view
		Layout.addView(GameView);

		// Add the AdMob view
		Layout.addView(AdBanner, adParams);

		// Hook it all up
		setContentView(Layout);

	}

}
