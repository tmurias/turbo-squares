package taylor.murias.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

import taylor.murias.gameobjects.BadSquare;
import taylor.murias.gameobjects.GoodSquare;
import taylor.murias.managers.SceneManager;
import taylor.murias.managers.SceneManager.SceneType;
import taylor.murias.turbosquares.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;

public class GameScene extends AbstractScene implements IOnMenuItemClickListener, IOnSceneTouchListener {
	
	public enum GameState {
		Running, Paused, GameOver
	}
	
	private GameState gameState = GameState.Running;
	
	private static final int NUM_BAD_SQUARES = 8;
	
	private final int MENU_RESUME = 0;
	private final int MENU_QUIT = 1;
	private final int MENU_GO_TO_MENU = 2;
	
	private HUD gameHUD;
	private MenuScene pausedMenuScene, gameOverMenuScene;
	private Text scoreText, pausedText, gameOverText, finalScoreText;
	public static GoodSquare goodSquare;
	private BadSquare[] badSquares;
	
	private int score = 0;
	private int scoringUpdates = 0;
	private int difficulty;
	private int enemySpeed;
	private int productionTime;
	private int productionUpdates = 0;
	private int touchX, touchY, swipeX, swipeY;
	
	private boolean pointerDown;
	private boolean vibrate;
	private boolean dead = false;

	@Override
	public void createScene() {
		createBackground();
		createHUD();
		createText();
		createMenuScenes();
		loadOptions();
		setTouchListener();
		createInitialSquares();
		createUpdateHandler();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
	}
	
	private void createHUD() {
		gameHUD = new HUD();
		
		scoreText = new Text(50, 40, resourceManager.arcadeFont, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setText("0");
		gameHUD.attachChild(scoreText);
		
		camera.setHUD(gameHUD);
	}
	
	private void createText() {
		pausedText = new Text(270, 225, resourceManager.titleFont, "PAUSED", vbom);
		pausedText.setPosition(270 - pausedText.getWidth() / 2, 225 - pausedText.getHeight() / 2);
		pausedText.setVisible(false);
		
		gameOverText = new Text(270, 150, resourceManager.titleFont, "GAME OVER", vbom);
		gameOverText.setPosition(270 - gameOverText.getWidth() / 2, 150 - gameOverText.getHeight() / 2);
		gameOverText.setVisible(false);
		
		finalScoreText = new Text(270, 450, resourceManager.arcadeFont, "01234", vbom);
		finalScoreText.setScale(1.5f);
		finalScoreText.setVisible(false);
		
		attachChild(pausedText);
		attachChild(gameOverText);
		attachChild(finalScoreText);
	}
	
	private void createMenuScenes() {
		pausedMenuScene = new MenuScene(camera);
		pausedMenuScene.setPosition(0, 0);
		gameOverMenuScene = new MenuScene(camera);
		gameOverMenuScene.setPosition(0, 0);
		
		final IMenuItem resumeMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_RESUME, resourceManager.resumeButtonRegion, vbom), 1.2f, 1f);
		final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_QUIT, resourceManager.quitButtonRegion, vbom), 1.2f, 1f);
		final IMenuItem goToMenuMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_GO_TO_MENU, resourceManager.goToMenuButtonRegion, vbom), 1.2f, 1f);
		
		pausedMenuScene.addMenuItem(resumeMenuItem);
		pausedMenuScene.addMenuItem(quitMenuItem);
		pausedMenuScene.buildAnimations();
		pausedMenuScene.setBackgroundEnabled(false);
		gameOverMenuScene.addMenuItem(goToMenuMenuItem);
		gameOverMenuScene.buildAnimations();
		gameOverMenuScene.setBackgroundEnabled(false);
		
		resumeMenuItem.setPosition(158, 450);
		quitMenuItem.setPosition(210, 675);
		goToMenuMenuItem.setPosition(105, 712);
		
		pausedMenuScene.setOnMenuItemClickListener(this);
		gameOverMenuScene.setOnMenuItemClickListener(this);
	}
	
	private void loadOptions() {
		SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		
		difficulty = sPrefs.getInt(MainActivity.OPTION_DIFFICULTY_KEY, MainActivity.OPTION_DIFFICULTY_DEFAULT);
		switch (difficulty) {
		case OptionsScene.OPTION_DIFFICULTY_EASY:
			enemySpeed = 4;
			productionTime = 4;
			break;
		case OptionsScene.OPTION_DIFFICULTY_MEDIUM:
			enemySpeed = 6;
			productionTime = 3;
			break;
		case OptionsScene.OPTION_DIFFICULTY_HARD:
			enemySpeed = 9;
			productionTime = 2;
			break;
		default:
			enemySpeed = 6;
			productionTime = 3;
			break;
		}
		
		vibrate = sPrefs.getInt(MainActivity.OPTION_VIBRATE_KEY, MainActivity.OPTION_VIBRATE_DEFAULT) == OptionsScene.OPTION_VIBRATE_ON;
	}
	
	private void setTouchListener() {
		setOnSceneTouchListener(this);
	}
	
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (gameState == GameState.Running) {
			if (pSceneTouchEvent.isActionDown()) {
				touchX = Math.round(pSceneTouchEvent.getX());
				touchY = Math.round(pSceneTouchEvent.getY());
				pointerDown = true;
			} else if (pSceneTouchEvent.isActionUp()) {
				pointerDown = false;
			} else if (pSceneTouchEvent.isActionMove() && pointerDown) {
				swipeX = Math.round(pSceneTouchEvent.getX());
				swipeY = Math.round(pSceneTouchEvent.getY());
				int xDisplacement = swipeX - touchX;
				int yDisplacement = swipeY - touchY;
				if (Math.abs(xDisplacement) > Math.abs(yDisplacement)) {
					//Swipe is horizontal
					if (xDisplacement > 20) {
						//Swipe right
						pointerDown = false;
						goodSquare.setSpeedX(GoodSquare.SPEED_RIGHT);
						goodSquare.setSpeedY(0);
					} else if (xDisplacement < -20) {
						//Swipe left
						pointerDown = false;
						goodSquare.setSpeedX(GoodSquare.SPEED_LEFT);
						goodSquare.setSpeedY(0);
					}
				} else {
					//Swipe is vertical
					if (yDisplacement > 20) {
						//Swipe down
						pointerDown = false;
						goodSquare.setSpeedX(0);
						goodSquare.setSpeedY(GoodSquare.SPEED_DOWN);
					} else if (yDisplacement < -20) {
						//Swipe up
						pointerDown = false;
						goodSquare.setSpeedX(0);
						goodSquare.setSpeedY(GoodSquare.SPEED_UP);
					}
				}
			}
		}
		return false;
	}
	
	private void createInitialSquares() {
		goodSquare = new GoodSquare(270, 480, resourceManager.goodSquareRegion, vbom);
		attachChild(goodSquare);
		
		badSquares = new BadSquare[NUM_BAD_SQUARES];
		int len = NUM_BAD_SQUARES;
		for (int i = 0; i < len; i++) {
			badSquares[i] = new BadSquare(0, 0, enemySpeed, resourceManager.badSquareRegion, vbom) {
				@Override
				public void onManagedUpdate(final float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					
					if (Math.abs(this.getX() - goodSquare.getX()) < 70 && Math.abs(this.getY() - goodSquare.getY()) < 70) {
						if (this.collidesWith(goodSquare)) {
							if (!dead) {
								dead = true;
								die();
							}
						}
					}
				}
			};
			attachChild(badSquares[i]);
			badSquares[i].setVisible(false);
			badSquares[i].setIgnoreUpdate(true);
		}
	}
	
	private void createUpdateHandler() {
		registerUpdateHandler(new TimerHandler(0.1f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				scoringUpdates++;
				productionUpdates++;
	            	
				if (scoringUpdates >= 10) {
					scoringUpdates = 0;
					incrementScore();
				}
	            	
				if (productionUpdates >= productionTime) {
					productionUpdates = 0;
					int len = badSquares.length;
					for (int i = 0; i < len; i++) {
						if (!badSquares[i].isVisible()) {
							badSquares[i].setNewCoords();
							badSquares[i].setOnScreen(true);
							badSquares[i].setVisible(true);
							badSquares[i].setIgnoreUpdate(false);
							break;
						}
					}
				}
			}
		}));
	}
	
	private void incrementScore() {
		score++;
		scoreText.setText(String.valueOf(score));
	}
	
	public void pause() {
		gameState = GameState.Paused;
		camera.getHUD().setVisible(false);
		pausedText.setVisible(true);
		pausedMenuScene.getChildByIndex(MENU_RESUME).setPosition(158, 450);  //Position gets reset every time for some reason
		pausedMenuScene.getChildByIndex(MENU_QUIT).setPosition(210, 675);    //This is stupid
		setChildScene(pausedMenuScene, false, true, true);
	}
	
	private void resume() {
		gameState = GameState.Running;
		pausedMenuScene.back();
		pausedText.setVisible(false);
		camera.getHUD().setVisible(true);
	}
	
	public void die() {
		if (vibrate) {
			Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(150);
		}
		gameState = GameState.GameOver;
		camera.getHUD().setVisible(false);
		goodSquare.setVisible(false);
		for (int i = 0; i < badSquares.length; i++) {
			badSquares[i].setVisible(false);
		}
		badSquares = null;
		clearUpdateHandlers();
		gameOverText.setVisible(true);
		
		finalScoreText.setText(String.valueOf(score));
		finalScoreText.setPosition(270 - finalScoreText.getWidth() / 2, 450 - finalScoreText.getHeight() / 2);
		finalScoreText.setVisible(true);
		setChildScene(gameOverMenuScene, false, true, true);
	}
	
	private void quit() {
		SceneManager.getInstance().loadMenuSceneFromGame(engine);
	}

	@Override
	public void disposeScene() {
		camera.setHUD(null);
		camera.setCenter(270, 480);
		goodSquare = null;
		badSquares = null;
		pausedMenuScene = null;
		gameOverMenuScene = null;
		scoreText = null;
		pausedText = null;
		gameOverText = null;
		gameHUD = null;
		clearChildScene();
		clearEntityModifiers();
		clearTouchAreas();
		System.gc();
		
		SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor = sPrefs.edit();
		
		switch (difficulty) {
		case OptionsScene.OPTION_DIFFICULTY_EASY:
			if (score > sPrefs.getInt(MainActivity.EASY_HIGH_SCORE_KEY, MainActivity.EASY_HIGH_SCORE_DEFAULT)) {
				editor.putInt(MainActivity.EASY_HIGH_SCORE_KEY, score).commit();
			}
			break;
		case OptionsScene.OPTION_DIFFICULTY_MEDIUM:
			if (score > sPrefs.getInt(MainActivity.MEDIUM_HIGH_SCORE_KEY, MainActivity.MEDIUM_HIGH_SCORE_DEFAULT)) {
				editor.putInt(MainActivity.MEDIUM_HIGH_SCORE_KEY, score).commit();
			}
			break;
		case OptionsScene.OPTION_DIFFICULTY_HARD:
			if (score > sPrefs.getInt(MainActivity.HARD_HIGH_SCORE_KEY, MainActivity.HARD_HIGH_SCORE_DEFAULT)) {
				editor.putInt(MainActivity.HARD_HIGH_SCORE_KEY, score).commit();
			}
			break;
		}
	}

	@Override
	public void onBackKeyPressed() {
		switch (gameState) {
		case Running:
			pause();
			break;
		case Paused:
			resume();
			break;
		case GameOver:
			quit();
			break;
		}
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_RESUME:
			resume();
			return true;
		case MENU_QUIT:
		case MENU_GO_TO_MENU:
			quit();
			return true;
		}
		return false;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}
	
	public GameState getGameState() {
		return gameState;
	}

}
