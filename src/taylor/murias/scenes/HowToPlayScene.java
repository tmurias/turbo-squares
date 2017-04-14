package taylor.murias.scenes;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import taylor.murias.managers.SceneManager;
import taylor.murias.managers.SceneManager.SceneType;

public class HowToPlayScene extends AbstractScene {
	
	private Text line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11;

	@Override
	public void createScene() {
		createBackground();
		createText();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
	}
	
	private void createText() {
		line1 = new Text(270, 220, resourceManager.secondaryFont, "YOU ARE THE GREEN", vbom);
		line1.setPosition(270 - line1.getWidth() / 2, 220 - line1.getHeight() / 2);
		line2 = new Text(270, 270, resourceManager.secondaryFont, "SQUARE. YOU ARE", vbom);
		line2.setPosition(270 - line2.getWidth() / 2, 270 - line2.getHeight() / 2);
		line3 = new Text(270, 320, resourceManager.secondaryFont, "CONSTANTLY MOVING", vbom);
		line3.setPosition(270 - line3.getWidth() / 2, 320 - line3.getHeight() / 2);
		line4 = new Text(270, 370, resourceManager.secondaryFont, "BUT YOU CAN SWIPE", vbom);
		line4.setPosition(270 - line4.getWidth() / 2, 370 - line4.getHeight() / 2);
		line5 = new Text(270, 420, resourceManager.secondaryFont, "TO CHANGE", vbom);
		line5.setPosition(270 - line5.getWidth() / 2, 420 - line5.getHeight() / 2);
		line6 = new Text(270, 470, resourceManager.secondaryFont, "DIRECTION. YOUR", vbom);
		line6.setPosition(270 - line6.getWidth() / 2, 470 - line6.getHeight() / 2);
		line7 = new Text(270, 520, resourceManager.secondaryFont, "GOAL IS TO AVOID", vbom);
		line7.setPosition(270 - line7.getWidth() / 2, 520 - line7.getHeight() / 2);
		line8 = new Text(270, 570, resourceManager.secondaryFont, "THE RED SQUARES", vbom);
		line8.setPosition(270 - line8.getWidth() / 2, 570 - line8.getHeight() / 2);
		line9 = new Text(270, 620, resourceManager.secondaryFont, "AND SURVIVE FOR", vbom);
		line9.setPosition(270 - line9.getWidth() / 2, 620 - line9.getHeight() / 2);
		line10 = new Text(270, 670, resourceManager.secondaryFont, "AS LONG AS", vbom);
		line10.setPosition(270 - line10.getWidth() / 2, 670 - line10.getHeight() / 2);
		line11 = new Text(270, 720, resourceManager.secondaryFont, "POSSIBLE.", vbom);
		line11.setPosition(270 - line11.getWidth() / 2, 720 - line11.getHeight() / 2);
		
		attachChild(line1);
		attachChild(line2);
		attachChild(line3);
		attachChild(line4);
		attachChild(line5);
		attachChild(line6);
		attachChild(line7);
		attachChild(line8);
		attachChild(line9);
		attachChild(line10);
		attachChild(line11);
	}

	@Override
	public void disposeScene() {
		this.detachSelf();
		this.dispose();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuSceneFromHowToPlay(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_HOW_TO_PLAY;
	}

}
