package taylor.murias.scenes;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import taylor.murias.managers.SceneManager;
import taylor.murias.managers.SceneManager.SceneType;
import taylor.murias.turbosquares.MainActivity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class OptionsScene extends AbstractScene {
	
	public static final int OPTION_DIFFICULTY_EASY = 0;
	public static final int OPTION_DIFFICULTY_MEDIUM = 1;
	public static final int OPTION_DIFFICULTY_HARD = 2;
	
	public static final int OPTION_VIBRATE_ON = 0;
	public static final int OPTION_VIBRATE_OFF = 1;
	
	private static final int BUTTON_DEFAULT_INDEX = 0;
	private static final int BUTTON_PRESSED_INDEX = 1;
	
	private Text difficultyText, vibrateText;
	private TiledSprite easyButton, mediumButton, hardButton, onButton, offButton;
	
	private int difficultyOption, vibrateOption;

	@Override
	public void createScene() {
		createBackground();
		createHeadings();
		createMenuItems();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
	}
	
	private void createHeadings() {
		difficultyText = new Text(270, 188, resourceManager.titleFont, "DIFFICULTY", vbom);
		difficultyText.setPosition(270 - difficultyText.getWidth() / 2, 188 - difficultyText.getHeight() / 2);
		
		vibrateText = new Text(270, 640, resourceManager.titleFont, "VIBRATE", vbom);
		vibrateText.setPosition(270 - vibrateText.getWidth() / 2, 640 - vibrateText.getHeight() / 2);
		
		attachChild(difficultyText);
		attachChild(vibrateText);
	}
	
	private void createMenuItems() {
		SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		difficultyOption = sPrefs.getInt(MainActivity.OPTION_DIFFICULTY_KEY, MainActivity.OPTION_DIFFICULTY_DEFAULT);
		vibrateOption = sPrefs.getInt(MainActivity.OPTION_VIBRATE_KEY, MainActivity.OPTION_VIBRATE_DEFAULT);
		
		easyButton = new TiledSprite(158, 268, resourceManager.easyButtonRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					difficultyOption = OPTION_DIFFICULTY_EASY;
					if (this.getCurrentTileIndex() == BUTTON_DEFAULT_INDEX) {
						this.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
						this.setScale(1.6f);
					}
					if (mediumButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						mediumButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						mediumButton.setScale(1.0f);
					}
					if (hardButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						hardButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						hardButton.setScale(1.0f);
					}
				}
				return true;
			}
		};
		mediumButton = new TiledSprite(142, 343, resourceManager.mediumButtonRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					difficultyOption = OPTION_DIFFICULTY_MEDIUM;
					if (this.getCurrentTileIndex() == BUTTON_DEFAULT_INDEX) {
						this.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
						this.setScale(1.6f);
					}
					if (easyButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						easyButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						easyButton.setScale(1.0f);
					}
					if (hardButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						hardButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						hardButton.setScale(1.0f);
					}
				}
				return true;
			}
		};
		hardButton = new TiledSprite(149, 418, resourceManager.hardButtonRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					difficultyOption = OPTION_DIFFICULTY_HARD;
					if (this.getCurrentTileIndex() == BUTTON_DEFAULT_INDEX) {
						this.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
						this.setScale(1.6f);
					}
					if (easyButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						easyButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						easyButton.setScale(1.0f);
					}
					if (mediumButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						mediumButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						mediumButton.setScale(1.0f);
					}
				}
				return true;
			}
		};
		onButton = new TiledSprite(240, 718, resourceManager.onButtonRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					vibrateOption = OPTION_VIBRATE_ON;
					if (this.getCurrentTileIndex() == BUTTON_DEFAULT_INDEX) {
						this.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
						this.setScale(1.8f);
					}
					if (offButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						offButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						offButton.setScale(1.0f);
					}
				}
				return true;
			}
		};
		offButton = new TiledSprite(232, 793, resourceManager.offButtonRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					vibrateOption = OPTION_VIBRATE_OFF;
					if (this.getCurrentTileIndex() == BUTTON_DEFAULT_INDEX) {
						this.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
						this.setScale(1.8f);
					}
					if (onButton.getCurrentTileIndex() == BUTTON_PRESSED_INDEX) {
						onButton.setCurrentTileIndex(BUTTON_DEFAULT_INDEX);
						onButton.setScale(1.0f);
					}
				}
				return true;
			}
		};
		
		switch (difficultyOption) {
		case OPTION_DIFFICULTY_EASY:
			easyButton.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
			easyButton.setScale(1.6f);
			break;
		case OPTION_DIFFICULTY_MEDIUM:
			mediumButton.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
			mediumButton.setScale(1.6f);
			break;
		case OPTION_DIFFICULTY_HARD:
			hardButton.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
			hardButton.setScale(1.6f);
			break;
		}
		
		switch (vibrateOption) {
		case OPTION_VIBRATE_ON:
			onButton.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
			onButton.setScale(1.8f);
			break;
		case OPTION_VIBRATE_OFF:
			offButton.setCurrentTileIndex(BUTTON_PRESSED_INDEX);
			offButton.setScale(1.8f);
			break;
		}
		
		attachChild(easyButton);
		attachChild(mediumButton);
		attachChild(hardButton);
		attachChild(onButton);
		attachChild(offButton);
		
		registerTouchArea(easyButton);
		registerTouchArea(mediumButton);
		registerTouchArea(hardButton);
		registerTouchArea(onButton);
		registerTouchArea(offButton);
	}

	@Override
	public void disposeScene() {
		difficultyText = null;
		vibrateText = null;
		easyButton = null;
		mediumButton = null;
		hardButton = null;
		onButton = null;
		offButton = null;
		clearChildScene();
		clearEntityModifiers();
		clearTouchAreas();
		clearUpdateHandlers();
		System.gc();
		
		SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor = sPrefs.edit();
		
		editor.putInt(MainActivity.OPTION_DIFFICULTY_KEY, difficultyOption);
		editor.putInt(MainActivity.OPTION_VIBRATE_KEY, vibrateOption);
		editor.commit();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuSceneFromOptions(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_OPTIONS;
	}

}
