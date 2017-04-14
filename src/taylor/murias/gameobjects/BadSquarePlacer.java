package taylor.murias.gameobjects;

import java.util.Random;

public class BadSquarePlacer {
	
	private static Random random = new Random();
	
	public static int randomSide() {
		return random.nextInt(4);
	}
	
	public static int randomX() {
		return random.nextInt(9);
	}
	
	public static int randomY() {
		return random.nextInt(16);
	}

}
