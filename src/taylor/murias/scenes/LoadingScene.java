package taylor.murias.scenes;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import taylor.murias.managers.SceneManager.SceneType;

public class LoadingScene extends AbstractScene {

	@Override
	public void createScene() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
		attachChild(new Text(112, 440, resourceManager.loadingFont, "LOADING...", vbom));
	}

	@Override
	public void disposeScene() {
		
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

}
