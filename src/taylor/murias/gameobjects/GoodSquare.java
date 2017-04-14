package taylor.murias.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GoodSquare extends Sprite {
	
	public static final int SPEED_LEFT = -6;
	public static final int SPEED_UP = -6;
	public static final int SPEED_RIGHT = 6;
	public static final int SPEED_DOWN = 6;
	
	private float x, y;
	private int speedX, speedY;

	public GoodSquare(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		x = Math.round(pX);
		y = Math.round(pY);
		setX(x);
		setY(y);
		speedX = 0;
		speedY = SPEED_UP;
	}
	
	@Override
	public void onManagedUpdate(float pSecondsElapsed) {
		x += speedX;
		y += speedY;
		
		if (x < 0) {
			x = 0;
		} else if (x > 495) {
			x = 495;
		}
		
		if (y < 0) {
			y = 0;
		} else if (y > 915) {
			y = 915;
		}
		setX(x);
		setY(y);
	}
	
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

}
