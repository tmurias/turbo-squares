package taylor.murias.scenes;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import taylor.murias.managers.SceneManager;
import taylor.murias.managers.SceneManager.SceneType;
import taylor.murias.turbosquares.MainActivity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class HighScoresScene extends AbstractScene {
	
	private Text titleText;
	private Text easyWordText, mediumWordText, hardWordText;
	private Text easyNumberText, mediumNumberText, hardNumberText;
	
	private int easyScore, mediumScore, hardScore;

	@Override
	public void createScene() {
		createBackground();
		createWords();
		loadHighScores();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
	}
	
	private void createWords() {
		titleText = new Text(270, 220, resourceManager.titleFont, "HIGH SCORES", vbom);
		titleText.setPosition(270 - titleText.getWidth() / 2, 220 - titleText.getHeight() / 2);
		easyWordText = new Text(200, 380, resourceManager.secondaryFont, "SLOW MODE", vbom);
		easyWordText.setPosition(200 - easyWordText.getWidth() / 2, 380 - easyWordText.getHeight() / 2);
		mediumWordText = new Text(200, 540, resourceManager.secondaryFont, "NORMAL MODE", vbom);
		mediumWordText.setPosition(200 - mediumWordText.getWidth() / 2, 540 - mediumWordText.getHeight() / 2);
		hardWordText = new Text(200, 700, resourceManager.secondaryFont, "TURBO MODE", vbom);
		hardWordText.setPosition(200 - hardWordText.getWidth() / 2, 700 - hardWordText.getHeight() / 2);
		
		attachChild(titleText);
		attachChild(easyWordText);
		attachChild(mediumWordText);
		attachChild(hardWordText);
	}
	
	private void loadHighScores() {
		SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		
		easyScore = sPrefs.getInt(MainActivity.EASY_HIGH_SCORE_KEY, MainActivity.EASY_HIGH_SCORE_DEFAULT);
		mediumScore = sPrefs.getInt(MainActivity.MEDIUM_HIGH_SCORE_KEY, MainActivity.MEDIUM_HIGH_SCORE_DEFAULT);
		hardScore = sPrefs.getInt(MainActivity.HARD_HIGH_SCORE_KEY, MainActivity.HARD_HIGH_SCORE_DEFAULT);
		
		easyNumberText = new Text(390, 380, resourceManager.arcadeFont, "01234", vbom);
		easyNumberText.setPosition(390, 380 - easyNumberText.getHeight() / 2);
		mediumNumberText = new Text(390, 540, resourceManager.arcadeFont, "01234", vbom);
		mediumNumberText.setPosition(390, 540 - mediumNumberText.getHeight() / 2);
		hardNumberText = new Text(390, 700, resourceManager.arcadeFont, "01234", vbom);
		hardNumberText.setPosition(390, 700 - hardNumberText.getHeight() / 2);
		
		if (easyScore == 0) {
			easyNumberText.setText("NONE");
		} else {
			easyNumberText.setText(String.valueOf(easyScore));
		}
		
		if (mediumScore == 0) {
			mediumNumberText.setText("NONE");
		} else {
			mediumNumberText.setText(String.valueOf(mediumScore));
		}
		
		if (hardScore == 0) {
			hardNumberText.setText("NONE");
		} else {
			hardNumberText.setText(String.valueOf(hardScore));
		}
		
		attachChild(easyNumberText);
		attachChild(mediumNumberText);
		attachChild(hardNumberText);
	}

	@Override
	public void disposeScene() {
		titleText = null;
		easyWordText = null;
		mediumWordText = null;
		hardWordText = null;
		easyNumberText = null;
		mediumNumberText = null;
		hardNumberText = null;
		clearChildScene();
		clearEntityModifiers();
		clearTouchAreas();
		clearUpdateHandlers();
		System.gc();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuSceneFromHighScores(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_HIGH_SCORES;
	}

}
