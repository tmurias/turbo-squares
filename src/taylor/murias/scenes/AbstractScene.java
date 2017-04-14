package taylor.murias.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import taylor.murias.managers.ResourceManager;
import taylor.murias.managers.SceneManager.SceneType;
import taylor.murias.turbosquares.MainActivity;

public abstract class AbstractScene extends Scene {
	
	protected ResourceManager resourceManager;
	protected Engine engine;
	protected MainActivity activity;
	protected Camera camera;
	protected VertexBufferObjectManager vbom;
	
	public AbstractScene() {
		this.resourceManager = ResourceManager.getInstance();
		this.engine = resourceManager.engine;
		this.activity = resourceManager.activity;
		this.camera = resourceManager.camera;
		this.vbom = resourceManager.vbom;
		createScene();
	}
	
	public abstract void createScene();
	
	public abstract void disposeScene();
	
	public abstract void onBackKeyPressed();
	
	public abstract SceneType getSceneType();

}
