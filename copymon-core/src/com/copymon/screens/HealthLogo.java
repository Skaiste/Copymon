package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.copymon.entities.Nurse;

public class HealthLogo {
	private static boolean show = false,
						   once = true;
	
	private static SpriteBatch batch;
	private static Sprite health;
	
	private static Vector2 position = new Vector2(Gdx.graphics.getWidth() / 4.819277108433f, Gdx.graphics.getHeight() / 2.981366459627f); 
	
	private static final int frameNumber = 7;
	private static final int counterMax = 50;
	private static int counterAnimation = 0;
	private static int counterframe = 1;
	
	
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
		if (show)
		{
			health.setTexture(new Texture("continue/health/" + counterframe + ".png"));
			
			if (counterAnimation == counterMax)
			{
				counterAnimation = 0;
				counterframe++;
			}
			else
				counterAnimation++;
			
			if (counterframe == frameNumber + 1){
				show = false;
				Nurse.goBack();
				counterframe = 1;
			}
		}
	}
	
	public static void startHealing(){
		show = true;
	}
}
