package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayingMenu {
	
	private static boolean menu;
	private static boolean once = true;
	
	private static SpriteBatch batch;
	private static ShapeRenderer bg;
	private static Sprite inventory, creaturesAtHome, switchCreatures;
	
	public static void render() {
		menu = Continue.getPlayer().menu;
		if (menu) 
		{
			if (once){
				show();
				once = false;
			}
			
			// render!!!
			// painting transparent background
			Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
			bg.begin(ShapeType.Filled);
			bg.setColor(0, 0, 0, 0.5f);
			bg.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			bg.end();
			Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
			
			batch.begin();
			inventory.draw(batch);
			if (Continue.getPlayer().computerOn)
			{
				creaturesAtHome.draw(batch);
				switchCreatures.draw(batch);
			}
			batch.end();			
		}
		else if (!menu && !once){
			dispose();
			once = true;
		}
	}
	
	public static void show() {
		bg = new ShapeRenderer();
		batch = new SpriteBatch();
		
		// inventory button
		if (Continue.getPlayer().computerOn)
		{
			inventory = new Sprite(new Texture("continue/inventory.gif"));
			creaturesAtHome = new Sprite(new Texture("continue/creaturesAtHome.gif"));
			switchCreatures = new Sprite(new Texture("continue/switchCreatures.gif"));
		}
		else if (!Continue.getPlayer().inventory)
			inventory = new Sprite(new Texture("continue/inventory.gif"));
		else
			inventory = new Sprite(new Texture("continue/switchSkills.gif"));
		
		inventory.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 9.6f);
		inventory.setPosition(Gdx.graphics.getWidth() - inventory.getWidth(), 0);
		if (Continue.getPlayer().computerOn)
		{
			creaturesAtHome.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 9.6f);
			creaturesAtHome.setPosition(Gdx.graphics.getWidth() - inventory.getWidth(), inventory.getHeight());
			
			switchCreatures.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 9.6f);
			switchCreatures.setPosition(Gdx.graphics.getWidth() - inventory.getWidth(), inventory.getHeight() + creaturesAtHome.getHeight());
		}
		
	}
	public static void dispose() {
		bg.dispose();
		batch.dispose();
		inventory.getTexture().dispose();
		if (Continue.getPlayer().computerOn)
		{
			creaturesAtHome.getTexture().dispose();
			switchCreatures.getTexture().dispose();
		}
	}
	
	public static float getInventoryX(){
		return inventory.getX();
	}
	public static float getInventoryY(){
		return inventory.getY();
	}
	public static float getInventoryWidth(){
		return inventory.getRegionWidth();
	}
	public static float getInventoryHeight(){
		return inventory.getRegionHeight();
	}

	public static float getAtHomeX(){
		return creaturesAtHome.getX();
	}
	public static float getAtHomeY(){
		return creaturesAtHome.getY();
	}
	public static float getAtHomeWidth(){
		return creaturesAtHome.getRegionWidth();
	}
	public static float getAtHomeHeight(){
		return creaturesAtHome.getRegionHeight();
	}

	public static float getSwitchX(){
		return switchCreatures.getX();
	}
	public static float getSwitchY(){
		return switchCreatures.getY();
	}
	public static float getSwitchWidth(){
		return switchCreatures.getRegionWidth();
	}
	public static float getSwitchHeight(){
		return switchCreatures.getRegionHeight();
	}
}
