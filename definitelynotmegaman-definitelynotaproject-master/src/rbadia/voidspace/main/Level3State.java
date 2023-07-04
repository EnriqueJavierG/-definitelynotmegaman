package rbadia.voidspace.main;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.UpdatedGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Level very similar to LevelState1 and LevelState2, but with 2 asteroids. 
 * Platforms arranged in two columns of 6 platforms.
 * Asteroids travel at 0 and 225 degree angle.
 */
public class Level3State extends NewLevel1State {
	
	protected Asteroid otherAsteroid;

	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level3State(int level, MainFrame frame, GameStatus status, 
			LevelLogic gameLogic, InputHandler inputHandler,
			UpdatedGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		this.numPlatforms=12;
	}
	
	//Getters
	public Asteroid getAsteroid() 				{ return otherAsteroid; 		}
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		
		GameStatus status = this.getGameStatus();
		status.setGameOver(false);
		status.setNewAsteroid(false);
		newAsteroid2(this);
	}
	
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
		drawAsteroid();
		drawOtherAsteroid();
		drawBullets();
		drawBigBullets();
		checkBullletAsteroidCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();
		checkBullletOtherAsteroidCollisions();
		checkBigBulletOtherAsteroidCollisions();
		checkMegaManOtherAsteroidCollisions();
		checkOtherAsteroidFloorCollisions();

		// update asteroids destroyed (score) label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}
	
	protected void checkOtherAsteroidFloorCollisions() {
		for(int i=0; i<9; i++){
			if (otherAsteroid.intersects(floor[i])){
					removeAsteroid(otherAsteroid);
			}
		}
	}
	
	protected void checkMegaManOtherAsteroidCollisions() {
		GameStatus status = getGameStatus();
		if (otherAsteroid.intersects(megaMan)){
				status.setLivesLeft(status.getLivesLeft() - 1);
				removeAsteroid(otherAsteroid);
		}
	}
	
	protected void checkBigBulletOtherAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if (otherAsteroid.intersects(bigBullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				removeAsteroid(otherAsteroid);
				damage=0;
			}
		}
	}
	
	protected void checkBullletOtherAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(otherAsteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				removeAsteroid(otherAsteroid);
				levelAsteroidsDestroyed++;
				damage=0;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	protected void drawOtherAsteroid() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if((otherAsteroid.getX() + otherAsteroid.getWidth() >  0)){
			otherAsteroid.translate(-otherAsteroid.getSpeed(), otherAsteroid.getSpeed()/2);
			getGraphicsManager().drawAsteroid(otherAsteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new other asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				otherAsteroid.setLocation (this.getWidth() - otherAsteroid.getPixelsWide(),
						(rand.nextInt(this.getHeight() - otherAsteroid.getPixelsTall() - 32)));
			}

			else{
				// draw explosion
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}
	}
	
	public Asteroid newAsteroid2(Level3State screen){
		int xPos = (int) (screen.getWidth() - Asteroid.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT- 32));
		otherAsteroid = new Asteroid(xPos, yPos);
		return otherAsteroid;
	}
	
	public void removeOtherAsteroid(Asteroid otherAsteroid){
		// "remove" asteroid
		asteroidExplosion = new Rectangle(
				otherAsteroid.x,
				otherAsteroid.y,
				otherAsteroid.getPixelsWide(),
				otherAsteroid.getPixelsTall());
		otherAsteroid.setLocation(-otherAsteroid.getPixelsWide(), -otherAsteroid.getPixelsTall());
		this.getGameStatus().setNewAsteroid(true);
		lastAsteroidTime = System.currentTimeMillis();
		// play asteroid explosion sound
		this.getSoundManager().playAsteroidExplosionSound();
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<6)	platforms[i].setLocation(150, getHeight()/2 + 140 - i*60);
			if(i>=6){	
				int k=6;
				platforms[i].setLocation(300, getHeight()/2 + 140 - (i-k)*60 );
				k=k+2;
			}
		}
		return platforms;
	}	
}