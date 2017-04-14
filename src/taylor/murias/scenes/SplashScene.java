package taylor.murias.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import taylor.murias.managers.SceneManager.SceneType;

public class SplashScene extends AbstractScene {
	
	private Sprite thumb;
	private Text text;

	@Override
	public void createScene() {
		createBackground();
		createThumb();
		createText();
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, resourceManager.backgroundRegion, vbom)));
		setBackground(background);
	}
	
	private void createThumb() {
		thumb = new Sprite(0, 0, resourceManager.thumbRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		thumb.setPosition(270 - thumb.getWidth() / 2, 480 - thumb.getHeight() / 2);
		attachChild(thumb);
	}
	
	private void createText() {
		text = new Text(270, 650, resourceManager.loadingFont, "games by taylor murias", vbom);
		text.setScale(0.4f);
		text.setPosition(270 - text.getWidth() / 2, 650 - text.getHeight() / 2);
		attachChild(text);
	}

	@Override
	public void disposeScene() {
		thumb.detachSelf();
		thumb.dispose();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public void onBackKeyPressed() {
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

}
