package taylor.murias.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BadSquare extends Sprite {
	
	private float x, y;
	private int speed;
	private int speedX, speedY;
	private boolean onScreen = true;

	public BadSquare(float pX, float pY, int pSpeed, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.speed = pSpeed;
		switch (BadSquarePlacer.randomSide()) {
		case 0:
			//Spawn from left
			int yPlacementLeft = BadSquarePlacer.randomY();
			setCoords(-60, yPlacementLeft * 60);
			setSpeeds(speed, 0);
			break;
		case 1:
			//Spawn from top
			int xPlacementTop = BadSquarePlacer.randomX();
			setCoords(xPlacementTop * 60, -60);
			setSpeeds(0, speed);
			break;
		case 2:
			//Spawn from right
			int yPlacementRight = BadSquarePlacer.randomY();
			setCoords(540, yPlacementRight * 60);
			setSpeeds(-speed, 0);
			break;
		case 3:
			//Spawn from bottom
			int xPlacementBottom = BadSquarePlacer.randomX();
			setCoords(xPlacementBottom * 60, 960);
			setSpeeds(0, -speed);
			break;
		}
	}
	
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		/*
		if (pSecondsElapsed > 1f/30f) {
			x += speedX * 2;
			y += speedY * 2;
		} else {
			x += speedX * pSecondsElapsed * 60;
			y += speedY * pSecondsElapsed * 60;
		}*/
		x += speedX;
		y += speedY;
		
		if (x < -60 || x > 540 || y < -60 || y > 960) {
			//Outside of the screen
			setVisible(false);
			setIgnoreUpdate(true);
			setOnScreen(false);
		} else {
			setX(x);
			setY(y);
		}
	}
	
	public void setNewCoords() {
		switch (BadSquarePlacer.randomSide()) {
		case 0:
			//Spawn from left
			int yPlacementLeft = BadSquarePlacer.randomY();
			setCoords(-60, yPlacementLeft * 60);
			setSpeeds(speed, 0);
			break;
		case 1:
			//Spawn from top
			int xPlacementTop = BadSquarePlacer.randomX();
			setCoords(xPlacementTop * 60, -60);
			setSpeeds(0, speed);
			break;
		case 2:
			//Spawn from right
			int yPlacementRight = BadSquarePlacer.randomY();
			setCoords(540, yPlacementRight * 60);
			setSpeeds(-speed, 0);
			break;
		case 3:
			//Spawn from bottom
			int xPlacementBottom = BadSquarePlacer.randomX();
			setCoords(xPlacementBottom * 60, 960);
			setSpeeds(0, -speed);
			break;
		}
	}
	
	public boolean isOnScreen() {
		return onScreen;
	}
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
		setX(x);
		setY(y);
	}
	
	public void setSpeeds(int speedX, int speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

}
