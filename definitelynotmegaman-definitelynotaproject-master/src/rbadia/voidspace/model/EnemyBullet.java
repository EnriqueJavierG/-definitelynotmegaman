/**
 * Model for Enemy Bullet
 * 
 * @author Enrique Javier
 * 
 */

package rbadia.voidspace.model;

public class EnemyBullet extends GameObject  {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	public static final  int WIDTH = 20;
	public static final int HEIGHT = 15;
	
	public EnemyBullet(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(12);
	}
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
