package taylor.murias.scenes;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import taylor.murias.managers.SceneManager;
import taylor.murias.managers.SceneManager.SceneType;

public class MainMenuScene extends AbstractScene implements IOnMenuItemClickListener {
	
	private final int MENU_START_GAME = 0;
	private final int MENU_OPTIONS = 1;
	private final int MENU_HIGH_SCORES = 2;
	private final int MENU_HOW_TO_PLAY = 3;
	
	private Text titleTextLine1;
	private Text titleTextLine2;
	private MenuScene menuChildScene;

	@Override
	public void createScene() {
		createBackground();
		createTitle();
		createMenuChildScene();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
	}
	
	private void createTitle() {
		titleTextLine1 = new Text(270, 100, resourceManager.titleFont, "TURBO", vbom);
		titleTextLine1.setPosition(270 - titleTextLine1.getWidth() / 2, 100 - titleTextLine1.getHeight() / 2);
		titleTextLine2 = new Text(270, 220, resourceManager.titleFont, "SQUARES", vbom);
		titleTextLine2.setPosition(270 - titleTextLine2.getWidth() / 2, 220 - titleTextLine2.getHeight() / 2);
		
		attachChild(titleTextLine1);
		attachChild(titleTextLine2);
	}
	
	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		
		final IMenuItem startGameMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_START_GAME, resourceManager.startGameButtonRegion, vbom), 1.2f, 1f);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_OPTIONS, resourceManager.optionsButtonRegion, vbom), 1.2f, 1f);
		final IMenuItem highScoresMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_HIGH_SCORES, resourceManager.highScoresButtonRegion, vbom), 1.2f, 1f);
		final IMenuItem howToPlayMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_HOW_TO_PLAY, resourceManager.howToPlayButtonRegion, vbom), 1.2f, 1f);
		
		menuChildScene.addMenuItem(startGameMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		menuChildScene.addMenuItem(highScoresMenuItem);
		menuChildScene.addMenuItem(howToPlayMenuItem);
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		startGameMenuItem.setPosition(100, 330);
		optionsMenuItem.setPosition(145, 480);
		highScoresMenuItem.setPosition(85, 630);
		howToPlayMenuItem.setPosition(85, 780);
		
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}

	@Override
	public void disposeScene() {
		menuChildScene = null;
		clearChildScene();
		clearEntityModifiers();
		clearTouchAreas();
		clearUpdateHandlers();
		System.gc();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_START_GAME:
			SceneManager.getInstance().loadGameScene(engine);
			return true;
		case MENU_OPTIONS:
			SceneManager.getInstance().loadOptionsScene(engine);
			return true;
		case MENU_HIGH_SCORES:
			SceneManager.getInstance().loadHighScoresScene(engine);
			return true;
		case MENU_HOW_TO_PLAY:
			SceneManager.getInstance().loadHowToPlayScene(engine);
		default:
			return false;
		}
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MAIN_MENU;
	}

}