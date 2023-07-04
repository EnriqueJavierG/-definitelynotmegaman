/**
 * Class that creates the 4th level 
 * Implements the following methods:isLevelOne(Edited),enemyBullet(Created),moveEnemyBullet(Created),
 * checkBulletEnemyCollision(Created),checkMegaManEnemyBulletCollision(Created)
 * 
 * @author Enrique Javier 
 * 
 * @version 1.0
 */

package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import rbadia.voidspace.graphics.UpdatedGraphicsManager;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.SparkMan;
import rbadia.voidspace.sounds.SoundManager;

public class Level4State extends NewLevel1State {
	
	private SparkMan sparkMan = new SparkMan(400, 15); // sets sparkMan's location
	private UpdatedGraphicsManager updateMan = new UpdatedGraphicsManager();
	private ArrayList<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>(); // EnemyBullet list;
	private long lastBulletTime;

	protected int enemyLives = 5;
	protected static final int NEW_DELAY = 2000;
	
	public Level4State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			UpdatedGraphicsManager updateMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, updateMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		lastBulletTime = -NEW_DELAY;
	}

	/**
	 * Determines if you killed the enemy (the user wins)
	 */
	@Override
	public boolean isLevelWon() {
		return enemyLives == 0 || getInputHandler().isNPressed(); 
	}
	@Override
	public void drawBackground() {
		updateMan.drawBackground4(getGraphics2D(), this);
	}
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<4)	platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i>=4){platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			}
		}
		return platforms;
	}

	/**
	 * Enemy fire bullets
	 */
	public void enemyBullet() {
		// create new bullet for 
		EnemyBullet bulletBill = new EnemyBullet(sparkMan.x - EnemyBullet.WIDTH/2,
				sparkMan.y + sparkMan.width/2 - EnemyBullet.HEIGHT +2);		
		enemyBullets.add(bulletBill);
		this.drawEnemyBullet();
		this.getSoundManager().playBulletSound();
	}
	/**
	 * Checks if sparkMan is going to fire (Enemy)
	 * 
	 * @return true if enough time has passed
	 */
	protected boolean enemyFire(){
				long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_DELAY){
				lastBulletTime = currentTime + 500;
				this.enemyBullet();
				return true;
		}
		return false;
	}

	/**
	 * This method makes the enemy bullet move in the given direction 
	 * 
	 * @param bulletBill
	 * @return false if the bullet is moving
	 */
	public boolean moveEnemyBullet(EnemyBullet bulletBill){
		if(bulletBill.getY() - bulletBill.getSpeed() >= 0){
			bulletBill.translate(-bulletBill.getSpeed(), 8);
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * Draws sparkMan's bullet
	 */
	protected void drawEnemyBullet() {
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<enemyBullets.size(); i++){
			EnemyBullet enemyBullet = enemyBullets.get(i);
			updateMan.drawBulletBill(enemyBullet, g2d, this);
			
			boolean remove = this.moveEnemyBullet(enemyBullet);
			if(remove){
				enemyBullets.remove(i);
				i--;
			}
		}
	}

	/**
	 * draws sparkMan depending on the action
	 */
	public void drawSparkMan() {

		updateMan.drawSparkMan(sparkMan, getGraphics2D(), this);
		if(enemyFire()) {
			updateMan.drawSparkFire(sparkMan, getGraphics2D(), this);
		}
		else updateMan.drawSparkMan(sparkMan, getGraphics2D(), this);
	}

	/**
	 * Checks if the enemy bullet hits megaMan
	 */
	protected void checkMegaManEnemyBulletCollision() {
		GameStatus status = getGameStatus();
		for(int i=0; i<enemyBullets.size(); i++){
			EnemyBullet bulletBill = enemyBullets.get(i);
			if(megaMan.intersects(bulletBill)){
				// increase asteroids destroyed count
				status.setLivesLeft(status.getLivesLeft() - 1);;
				// remove bullet
				enemyBullets.remove(i);
				break;
			}
		}
	}
	/**
	 * Checks if megaMan's bullet hits the enemy 
	 */
	protected void checkBulletEnemyCollision() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(sparkMan.intersects(bullet)){
				// remove bullet
				bullets.remove(i);
				// decrease sparkMan's lives count
				enemyLives--;
				break;
			}
		}
	}

	@Override
	public void updateScreen(){
		Graphics2D g2d = getGraphics2D();
		GameStatus status = this.getGameStatus();


		// save original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		clearScreen();
		drawBackground();
		drawStars(50);
		drawFloor();
		drawPlatforms();
		drawMegaMan();
		drawSparkMan();
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		drawEnemyBullet();
		checkBullletAsteroidCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();
		checkMegaManEnemyBulletCollision();
		checkBulletEnemyCollision();

		// update asteroids destroyed (score) label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}
}