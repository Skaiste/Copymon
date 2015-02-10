package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShowControls {
	
	private static SpriteBatch batch;
	private static Sprite bg;
	
	private static boolean is = Gdx.app.getPreferences("Copymon Options").getBoolean("remind controls");
	private static boolean create = true;
	
	final private static int flashingNumber = 3;
	private static int flash = 0;
	final private static float alphaMax = 0.5f;
	private static float alpha = alphaMax;
	final private static float fadingC = 0.01f;
	
	private static boolean fadeOut = true;
	
	public static void update(){
		is = Gdx.app.getPreferences("Copymon Options").getBoolean("remind controls");
		if (is)
		{
			if (create){
				show();
				create = false;
			}
			else if (flash < flashingNumber)
			{
				
				batch.begin();
				flashBg(batch);
				batch.end();
				
			}
			else if (flash == flashingNumber){
				dispose();
				is = false;
				Preferences prefs = Gdx.app.getPreferences("Copymon Options");
				prefs.putBoolean("remind controls", is);
				prefs.flush();
			}
		}
	}
	
	private static void flashBg(SpriteBatch sBatch){
		if (fadeOut)
		{
			//System.out.println(alpha);
			bg.setAlpha(alpha);
			alpha -= fadingC;
			if (alpha <= 0.01)
			{
				alpha = 0;
				fadeOut = false;
				flash++;
			}
		}
		else
		{
			bg.setAlpha(alpha);
			alpha += fadingC;
			if (alpha >= alphaMax)
				fadeOut = true;
		}
		bg.draw(sBatch);
	}
	private static void show(){
		flash = 0;
		alpha = alphaMax;
		fadeOut = true;
		
		batch = new SpriteBatch();
		bg = new Sprite(new Texture("continue/controls.gif"));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	private static void dispose(){
		batch.dispose();
		bg.getTexture().dispose();
		create = true;
	}
}
