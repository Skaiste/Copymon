package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copymon.creatures.Creature;

public class CreatureHere {
	private static boolean hasCreatureAppeared = Continue.getPlayer().getCreatureAppeared();
	
	private static SpriteBatch batch;
	private static Sprite winBg, attackButton, avoidButton, crImage;
	private static BitmapFont name, lvl;
	
	private static Creature creature;
	
	private static boolean once = false;
	
	public static void update(){
		hasCreatureAppeared = Continue.getPlayer().getCreatureAppeared();
		if (hasCreatureAppeared)
		{
			if (!once)
			{
				create();
				once = true;
			}
				
			batch.begin();
			winBg.draw(batch);
			attackButton.draw(batch);
			avoidButton.draw(batch);
			crImage.draw(batch);
			name.draw(batch, creature.getRealName(), winBg.getX() + 115 - name.getBounds(creature.getRealName()).width/2, winBg.getY() + (winBg.getHeight() - 130));
			lvl.draw(batch, creature.getLvl() + " Level", winBg.getX() + 100 - name.getBounds(creature.getLvl() + " Level").width/2, winBg.getY() + (winBg.getHeight() - 326));
			batch.end();
		}
		else if (!hasCreatureAppeared && once)
		{
			dispose();
			once = false;
		}
	}
	public static void dispose() {
		winBg.getTexture().dispose();
		batch.dispose();
		attackButton.getTexture().dispose();
		avoidButton.getTexture().dispose();
		crImage.getTexture().dispose();
		name.dispose();
		lvl.dispose();
	}
	
	public static void create(){
		
		batch = new SpriteBatch();
		winBg = new Sprite(new Texture("continue/spot/background.gif"));
		attackButton = new Sprite(new Texture("continue/spot/attack.gif"));
		avoidButton = new Sprite(new Texture("continue/spot/avoid.gif"));
		crImage = new Sprite(new Texture("continue/creatures/images/" + creature.getRealName() + "/spot.gif"));
		name = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		lvl = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		
		winBg.setCenterX(Gdx.graphics.getWidth() / 2);
		winBg.setCenterY(Gdx.graphics.getHeight() / 2);
		
		attackButton.setPosition(winBg.getX() + 232, winBg.getY() + (winBg.getHeight() - 225));
		avoidButton.setPosition(winBg.getX() + 232, winBg.getY() + (winBg.getHeight() - 307));
		
		crImage.setCenterX(winBg.getX() + 114);
		crImage.setCenterY(winBg.getY() + 155);
		
		name.setScale(1.6f);
		lvl.setScale(2);	
		
	}
	
	public static float getAttackPosX(){
		if(attackButton == null)
			return 0;
		return attackButton.getX();
	}
	public static float getAttackPosY(){
		if(attackButton == null)
			return 0;
		return attackButton.getY();
	}
	public static float getAttackWidth(){
		if(attackButton == null)
			return 0;
		return attackButton.getWidth();
	}
	public static float getAttackHeight(){
		if(attackButton == null)
			return 0;
		return attackButton.getHeight();
	}
	public static float getAvoidPosX(){
		if(avoidButton == null)
			return 0;
		return avoidButton.getX();
	}
	public static float getAvoidPosY(){
		if(avoidButton == null)
			return 0;
		return avoidButton.getY();
	}
	public static float getAvoidWidth(){
		if(avoidButton == null)
			return 0;
		return avoidButton.getWidth();
	}
	public static float getAvoidHeight(){
		if(avoidButton == null)
			return 0;
		return avoidButton.getHeight();
	}
	
	public static boolean exist(){
		if ((winBg != null) && (batch != null))
		{
			System.out.println("true");
			return true;
		}
		return false;
	}
	
	public static void setCreature(Creature c){
		creature = c;
	}
	public static Creature getCreature(){
		return creature;
	}
}
