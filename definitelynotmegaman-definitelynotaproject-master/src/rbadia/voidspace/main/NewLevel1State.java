/**
 * Class made to implement new methods.
 * New methods implemented:draw MegaMan(Edited), fireBullet(Edited), updateScreen(Edited),
 * isLevelWon(Edited),drawBackground(Created)
 * 
 * @author Enrique Javier
 * @author Paola Guadalupe
 * @version 1.0
 * 
 */

package rbadia.voidspace.main;

import java.awt.Color;

import java.awt.Graphics2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.UpdatedGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.sounds.SoundManager;


public class NewLevel1State extends Level1State {	private UpdatedGraphicsManager graphics = new UpdatedGraphicsManager();
	private int direction;
	public NewLevel1State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic,

			InputHandler inputHandler, UpdatedGraphicsManager updatedGraphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, updatedGraphicsMan, soundMan);

	}

	/**
	 * Method edited so it can now fire to the left
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(megaMan.x + megaMan.width - Bullet.WIDTH/2,
				megaMan.y + megaMan.width/2 - Bullet.HEIGHT +2);

		if(direction == -1) {
			bullet.setSpeed(-bullet.getSpeed());
		}
		bullets.add(bullet);
		this.getSoundManager().playBulletSound();
	}

	/**
	 * Fire the "Power Shot" bullet
	 */
	public void fireBigBullet(){
		//BigBullet bigBullet = new BigBullet(megaMan);
		int xPos = megaMan.x + megaMan.width - BigBullet.WIDTH / 2;
		int yPos = megaMan.y + megaMan.width/2 - BigBullet.HEIGHT + 4;
		BigBullet  bigBullet = new BigBullet(xPos, yPos);
		if(direction == -1) {
			bigBullet.setSpeed(-bigBullet.getSpeed());
		}
		bigBullets.add(bigBullet);
		this.getSoundManager().playBulletSound();
	}


	/**
	 * Added audioClip.close to prevent music overlay
	 */
	@Override
	public void doLevelWon(){
		setCurrentState(LEVEL_WON);
		getGameLogic().drawYouWin();
		MegaManMain.audioClip.close();
		repaint();
		LevelLogic.delay(5000);
	}

	
	protected void drawMegaMan() {
		//draw one of six possible MegaMan poses according to situation

		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		//Change direction depending if Left or Right is pressed 
		if(getInputHandler().isRightPressed()) {
			direction = 0;
		}

		if(getInputHandler().isLeftPressed()) {
			direction = -1; 
		}
		if(!status.isNewMegaMan()){
			//draws right megaMan images
			if(direction == 0) {
				if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))){
					getGraphicsManager().drawMegaFallR(megaMan, g2d, this);
				}

				if((Fire() == true || Fire2()== true) && (Gravity()==false)){
					getGraphicsManager().drawMegaFireR(megaMan, g2d, this);
				}

				if((Gravity()==false) && (Fire()==false) && (Fire2()==false)){
					getGraphicsManager().drawMegaMan(megaMan, g2d, this);
				}
			}
			//draws left megaMan images
			else 
				if(direction == -1) {
					if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))){
						graphics.drawMegaFallL(megaMan, g2d, this);
					}

					if((Fire() == true || Fire2()== true) && (Gravity()==false)){
						graphics.drawMegaFireL(megaMan, g2d, this);
					}

					if((Gravity()==false) && (Fire()==false) && (Fire2()==false)){
						graphics.drawMegaManL(megaMan, g2d, this);
					}
				}
		}
	}

	@Override
	public void doPlaying() {
		setCurrentState(PLAYING);
		updateScreen();
	};


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
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		checkBullletAsteroidCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();

		// update asteroids destroyed (score) label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}

	/**
	 * Added a the asteroid counter for when the bigBullet hits them
	 */
	@Override
	protected void checkBigBulletAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(asteroid.intersects(bigBullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				removeAsteroid(asteroid);
				levelAsteroidsDestroyed++; 
				damage=0;
			}
		}
	}
	protected void drawBackground() {
		graphics.drawBackground3(getGraphics2D(), this);
	}

	/**
	 * Added the isNPressed condition for testing the levels
	 */
	@Override
	public boolean isLevelWon() {
		return levelAsteroidsDestroyed >= 3 || getInputHandler().isNPressed(); 
	}
}
