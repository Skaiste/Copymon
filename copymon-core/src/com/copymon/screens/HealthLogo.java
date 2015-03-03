package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HealthLogo {
	private static boolean show = false,
						   once = true;
	
	private static SpriteBatch batch;
	private static Sprite health;
	
	private static Vector2 position = new Vector2(Gdx.graphics.getWidth() / 4.819277108433f, Gdx.graphics.getHeight() / 2.981366459627f); 
	
	private static final int counterMax = 7;
	private static int counter = counterMax;
	
	public static void update() {
		//System.out.println(Continue.getMap().place);
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
	
	public static void show(){
		batch = new SpriteBatch();
		
		health = new Sprite(new Texture("continue/health/7.png"));
		health.setSize(Gdx.graphics.getWidth() / 14.035087719298f, Gdx.graphics.getHeight() / 8.42105263157f);
		health.setPosition(Gdx.graphics.getWidth() / 4.819277108433f, Gdx.graphics.getHeight() / 2.981366459627f);
	}
	public static void render(){
		// TODO: update show value
		
		change();
		
		batch.begin();
		health.setPosition(position.x - Play.getCamera().getRealX(), position.y - Play.getCamera().getRealY());
		health.draw(batch);
		batch.end();
	}
	public static void dispose(){
		health.getTexture().dispose();
		batch.dispose();
	}
	
	public static boolean toShow(){
		if (Continue.getMap().place.equals("health"))
			return true;
		return false;
	}
	
	private static void change(){
		if (show && (counter % counterMax == 0))
		{
			int number = counter / counterMax;
			health.getTexture().dispose();
			health.setTexture(new Texture("continue/health/" + number + ".png"));
			
			if (counter == counterMax * (counterMax + 1) - 1)
			{
				counter = counterMax;
				show = false;
			}
			else
				counter++;
		}
	}
}
