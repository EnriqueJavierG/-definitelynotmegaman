package rbadia.voidspace.model;

public class BigAsteroid extends Asteroid {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;
	
	public BigAsteroid(int xPos, int yPos) {
		super(BigAsteroid.WIDTH, BigAsteroid.HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}
}
