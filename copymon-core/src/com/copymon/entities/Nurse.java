package com.copymon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.copymon.screens.Continue;
import com.copymon.screens.HealthLogo;
import com.copymon.screens.Play;

public class Nurse {
	
	private static SpriteBatch batch;
	private static Sprite nurse;
	
	private static boolean once = true;
	
	// nurse animation
	private static String path = "continue/player/nurse/";
	private static boolean walking = false;
	private static boolean lookingRight = true;
	// speed of the player animation
	final private static int animationSpeed = 12;
	// counting for animation
	private static int nurseAnimation = animationSpeed;
	
	// nurse walking
	private static final float posRightX = Gdx.graphics.getWidth() / 2.797202797f;
	private static final float posLeftX = Gdx.graphics.getWidth() / 3.539823009f;
	private static final float posY = Gdx.graphics.getHeight() / 3.47826087f;
	
	private static boolean stops = false;
	
	private static Vector2 nursePosition = new Vector2(posRightX, posY);
	
	public static void update(){
		if (toShow())
		{
			if (once) {
				show();
				once = false;
			}
			render();
		}
		else if (!toShow() && !once)
		{
			dispose();
			once = true;
		}
	}
	
	private static void show(){
		batch = new SpriteBatch();
		
		nurse = new Sprite(new Texture(path + "right/1.gif"));
		nurse.setPosition(posRightX - Play.getCamera().getRealX(), posY - Play.getCamera().getRealY());
	}
	
	private static void render(){
		// sprite images changing
		walkingAnimation();
		
		// updating nurse position
		updateNursePosition();
		
		batch.begin();
		nurse.draw(batch);
		batch.end();
	}
	
	private static void dispose(){
		batch.dispose();
		nurse.getTexture().dispose();
	}
	
	private static void updateNursePosition(){		
		// set x
		if (walking)
		{
			// if walking to the right
			if (lookingRight && (nurse.getX() != posRightX - Play.getCamera().getRealX())){
				nursePosition.x++;
			}
			// if reached right corner
			else if (lookingRight){
				walking = false;
			}
			// if walking to the left
			else if (nurse.getX() != posLeftX - Play.getCamera().getRealX()){
				nursePosition.x--;
			}
			// if reached left corner
			else{
				// start healing
				HealthLogo.startHealing();
				stops = true;
			}
		}
		
		nurse.setPosition(nursePosition.x - Play.getCamera().getRealX(), nursePosition.y - Play.getCamera().getRealY());
	}
	
	public static void endWalking(){
		walking = false;
		Continue.getPlayerCreatures().healAllActive();
	}
	
	public static void startWalking(){
		walking = true;
		lookingRight = false;
	}
	
	public static void goBack(){
		lookingRight = true;
		nursePosition.x++;
		nurse.setX(nursePosition.x - Play.getCamera().getRealX());
		stops = false;
	}
	
	private static void walkingAnimation(){
		if (walking)
		{
			if (lookingRight){
				nurse.setTexture(new Texture(path + "right/" + ((nurseAnimation / animationSpeed) / 2 + 1) + ".gif"));
			}
			else if (!stops){
				nurse.setTexture(new Texture(path + "left/" + ((nurseAnimation / animationSpeed) / 2 + 1) + ".gif"));
			}
			
			if (nurseAnimation == animationSpeed * (animationSpeed) - 1)
				nurseAnimation = animationSpeed;
			else
				nurseAnimation++;
		}
		else
		{
			if (lookingRight){
				nurse.setTexture(new Texture(path + "right/1.gif"));				
			}
			else {
				nurse.setTexture(new Texture(path + "left/1.gif"));
			}
			nurseAnimation = animationSpeed;
		}
		nurse.setSize(nurse.getTexture().getWidth(), nurse.getTexture().getHeight());
	}
	
	public static boolean toShow(){
		if (Continue.getMap().place.equals("health"))
			return true;
		return false;
	}
	public static boolean getWalking(){
		return walking;
	}
}
