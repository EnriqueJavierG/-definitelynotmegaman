/**
 * Class for implementing new images 
 * 
 * Implemented methods for drawing each new image
 * 
 * @author Enrique Javier
 * @author Paola Guadalupe
 */


package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
//import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
//import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.Floor;
//import rbadia.voidspace.model.BulletBoss;
//import rbadia.voidspace.model.BulletBoss2;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.SparkMan;

/**
 * Manages and draws game graphics and images.
 */
public class UpdatedGraphicsManager extends GraphicsManager{

	private BufferedImage sparkManLImg;
	private BufferedImage sparkFireLImg;
	private BufferedImage backgroundLvl3Img;
	private BufferedImage backgroundLvl4Img;
	private BufferedImage bigAsteroidImg;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage megaManLImg;
	private BufferedImage megaFireLImg;
	private BufferedImage megaFallLImg;
	private BufferedImage bulletBillImg;


	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public UpdatedGraphicsManager(){
		// load images
		super();
		try {
			this.megaManLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3L.png"));
			this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireLeft.png"));
			this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallLeft.png"));
			this.sparkManLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/sparkMan.png"));
			this.sparkFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/sparkManShooting.png"));
			this.backgroundLvl3Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/level3.png"));
			this.backgroundLvl4Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/level4.png"));
			this.bigAsteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.bulletBillImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Bullet_Bill.png"));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Methods for drawing megaMan left images 
	 * 
	 * @param megaMan
	 * @param g2d
	 * @param observer
	 */
	public void drawMegaFallL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallLImg, megaMan.x, megaMan.y, observer);	
	}
	public void drawMegaFireL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireLImg, megaMan.x, megaMan.y, observer);	
	}
	public void drawMegaManL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManLImg, megaMan.x, megaMan.y, observer);	
	}

	/**
	 * Methods for drawing sparkMan images in the specified canvas 
	 * @param sparkMan
	 * @param g2d
	 * @param observer
	 */
	public void drawSparkMan(SparkMan sparkMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(sparkManLImg, sparkMan.x, sparkMan.y, observer);
	}

	public void drawSparkFire(SparkMan sparkMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(sparkFireLImg, sparkMan.x, sparkMan.y, observer);	
	}

	/**
	 * Method for drawing bullet bill(enemy bullet)
	 * @param bulletBill
	 * @param g2d
	 * @param observer
	 */
	public void drawBulletBill(EnemyBullet bulletBill, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletBillImg, bulletBill.x, bulletBill.y, observer);
	}

	/**
	 * Draws a big asteroid image to the specified graphics canvas.
	 * @param bigAsteroid the bigAasteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigAsteroid(BigAsteroid bigAsteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidImg, bigAsteroid.x, bigAsteroid.y, observer);
	}

	/**
	 * Draws a big asteroid explosion image to the specified graphics canvas.
	 * @param bigAsteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}

	/**
	 * Methods for changing backgrounds 
	 * @param g2d
	 * @param observer
	 */
	public void drawBackground3(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(backgroundLvl3Img, 0, 0, observer);

	}
	public void drawBackground4(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(backgroundLvl4Img, 0, 0, observer);

	}


}

