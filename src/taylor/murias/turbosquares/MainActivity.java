package taylor.murias.turbosquares;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import taylor.murias.managers.ResourceManager;
import taylor.murias.managers.SceneManager;
import taylor.murias.scenes.GameScene;
import taylor.murias.scenes.OptionsScene;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends BaseGameActivity {
	
	public static final long SPLASH_DURATION = 2000;
	
	public static final String EASY_HIGH_SCORE_KEY = "easyHighScore";
	public static final String MEDIUM_HIGH_SCORE_KEY = "mediumHighScore";
	public static final String HARD_HIGH_SCORE_KEY = "hardHighScore";
	
	public static final String OPTION_DIFFICULTY_KEY = "optionDifficulty";
	public static final String OPTION_VIBRATE_KEY = "optionVibrate";
	
	public static final int EASY_HIGH_SCORE_DEFAULT = 0;
	public static final int MEDIUM_HIGH_SCORE_DEFAULT = 0;
	public static final int HARD_HIGH_SCORE_DEFAULT = 0;
	
	public static final int OPTION_DIFFICULTY_DEFAULT = OptionsScene.OPTION_DIFFICULTY_MEDIUM;
	public static final int OPTION_VIBRATE_DEFAULT = OptionsScene.OPTION_VIBRATE_ON;
	
	private static final int CAMERA_WIDTH = 540;
	private static final int CAMERA_HEIGHT = 960;
	
	private static final int FPS_LIMIT = 60;
	
	private Camera camera;
	private AdView adView;
	
	@Override
    @SuppressLint("NewApi")
    protected void onSetContentView() {

			final FrameLayout frameLayout = new FrameLayout(this);
        	final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.FILL);
            final FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.BOTTOM);

            adView = new AdView(this);
            adView.setAdUnitId("ca-app-pub-9096503496243407/3282344376");
            adView.setAdSize(AdSize.BANNER);
            adView.setVisibility(AdView.VISIBLE);
            adView.refreshDrawableState();

            AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView.loadAd(adRequest);

            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
                    adView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }


            this.mRenderSurfaceView = new RenderSurfaceView(this);
            mRenderSurfaceView.setRenderer(mEngine, this);

     

            final FrameLayout.LayoutParams surfaceViewLayoutParams = new FrameLayout.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            surfaceViewLayoutParams.gravity = Gravity.CENTER;

            frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
            frameLayout.addView(adView, adViewLayoutParams);
            this.setContentView(frameLayout, frameLayoutLayoutParams);

    }
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, FPS_LIMIT);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		ResourceManager.prepare(mEngine, this, camera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}
	
	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.registerUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMenuScene();
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
			} catch (NullPointerException e) {
				
			}
		}
		return false;
	}
	
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		
		//For clearing all high scores from the cheaty first version
		SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		if (sPrefs.getBoolean("firstOpen", true)) {
			SharedPreferences.Editor editor = sPrefs.edit();
			editor.putBoolean("firstOpen", false)
				  .putInt(EASY_HIGH_SCORE_KEY, EASY_HIGH_SCORE_DEFAULT)
				  .putInt(MEDIUM_HIGH_SCORE_KEY, MEDIUM_HIGH_SCORE_DEFAULT)
				  .putInt(HARD_HIGH_SCORE_KEY, HARD_HIGH_SCORE_DEFAULT)
				  .commit();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (SceneManager.getInstance().getCurrentSceneType() == SceneManager.SceneType.SCENE_GAME) {
			GameScene scene = (GameScene) SceneManager.getInstance().getCurrentScene();
			if (scene.getGameState() == GameScene.GameState.Running) {
				scene.pause();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

}
