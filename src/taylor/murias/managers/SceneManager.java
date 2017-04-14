package taylor.murias.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import taylor.murias.scenes.AbstractScene;
import taylor.murias.scenes.GameScene;
import taylor.murias.scenes.HighScoresScene;
import taylor.murias.scenes.HowToPlayScene;
import taylor.murias.scenes.LoadingScene;
import taylor.murias.scenes.MainMenuScene;
import taylor.murias.scenes.OptionsScene;
import taylor.murias.scenes.SplashScene;

public class SceneManager {
	
	public enum SceneType {
		SCENE_SPLASH, SCENE_MAIN_MENU, SCENE_GAME, SCENE_OPTIONS, SCENE_HIGH_SCORES, SCENE_HOW_TO_PLAY, SCENE_LOADING
	}
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private AbstractScene splashScene, mainMenuScene, gameScene, optionsScene, highScoresScene, howToPlayScene, loadingScene;
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private AbstractScene currentScene;
	private Engine engine = ResourceManager.getInstance().engine;
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourceManager.getInstance().loadGenericResources();
		ResourceManager.getInstance().loadSplashResources();
		splashScene = new SplashScene();
		currentScene = new SplashScene();
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void createMenuScene() {
		ResourceManager.getInstance().loadGenericResources();
		ResourceManager.getInstance().loadMenuResources();
		mainMenuScene = new MainMenuScene();
		loadingScene = new LoadingScene();
		setScene(mainMenuScene);
		disposeSplashScene();
	}
	
	public void loadMenuSceneFromGame(final Engine mEngine) {
		setScene(loadingScene);
		gameScene.disposeScene();
		ResourceManager.getInstance().unloadGameTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadMenuResources();
				mainMenuScene = new MainMenuScene();
				setScene(mainMenuScene);
			}
		}));
	}
	
	public void loadMenuSceneFromOptions(final Engine mEngine) {
		setScene(loadingScene);
		optionsScene.disposeScene();
		ResourceManager.getInstance().unloadOptionsTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadMenuResources();
				mainMenuScene = new MainMenuScene();
				setScene(mainMenuScene);
			}
		}));
	}
	
	public void loadMenuSceneFromHighScores(final Engine mEngine) {
		setScene(loadingScene);
		highScoresScene.disposeScene();
		ResourceManager.getInstance().unloadHighScoresTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadMenuResources();
				mainMenuScene = new MainMenuScene();
				setScene(mainMenuScene);
			}
		}));
	}
	
	public void loadMenuSceneFromHowToPlay(final Engine mEngine) {
		setScene(loadingScene);
		howToPlayScene.disposeScene();
		ResourceManager.getInstance().unloadHowToPlayTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadMenuResources();
				mainMenuScene = new MainMenuScene();
				setScene(mainMenuScene);
			}
		}));
	}
	
	public void loadGameScene(final Engine mEngine) {
		setScene(loadingScene);
		ResourceManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadGameResources();
				gameScene = new GameScene();
				setScene(gameScene);
			}
		}));
	}
	
	public void loadOptionsScene(final Engine mEngine) {
		setScene(loadingScene);
		ResourceManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadOptionsResources();
				optionsScene = new OptionsScene();
				setScene(optionsScene);
			}
		}));
	}
	
	public void loadHighScoresScene(final Engine mEngine) {
		setScene(loadingScene);
		ResourceManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadHighScoresResources();
				highScoresScene = new HighScoresScene();
				setScene(highScoresScene);
			}
		}));
	}
	
	public void loadHowToPlayScene(final Engine mEngine) {
		setScene(loadingScene);
		ResourceManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadHowToPlayResources();
				howToPlayScene = new HowToPlayScene();
				setScene(howToPlayScene);
			}
		}));
	}
	
	private void disposeSplashScene() {
		ResourceManager.getInstance().unloadSplashScene();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void setScene(AbstractScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		case SCENE_MAIN_MENU:
			setScene(mainMenuScene);
			break;
		case SCENE_GAME:
			setScene(gameScene);
			break;
		case SCENE_OPTIONS:
			setScene(optionsScene);
			break;
		case SCENE_HIGH_SCORES:
			setScene(highScoresScene);
			break;
		case SCENE_HOW_TO_PLAY:
			setScene(howToPlayScene);
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		}
	}
	
	public static SceneManager getInstance() {
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}
	
	public AbstractScene getCurrentScene() {
		return currentScene;
	}

}
