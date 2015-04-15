package com.copymon.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copymon.creatures.Creature;
import com.copymon.creatures.PlayerCreatures;

public class Fighting {

	// two screens in fighting that may go one after another:
	//		1. Choosing creature at the start and if players creature is out of hp
	//		2. Fighting
	
	private static boolean isChoosingScreen = true; 		// states which of the screens should be on
	
	// Choosing creature screen
	// batch
	private static SpriteBatch batch;
	// background
	private static Sprite bg;
	// list of creatures in team
	private static ArrayList <Sprite> cBg;
	private static ArrayList <BitmapFont> cNames, cLvls;
	private static int selected = 0; 		// from 0 to 5
	// describtion of selected creature
	private static Sprite cImage, cHpBar, cHpBarBg, cExpBar, cExpBarBg;
	private static BitmapFont cType, cPower, cDefence, cAgility, cHpLabel, cHp, cExpLabel, cExp;
	// buttons
	private static Sprite runButton, fightButton;
	// player creatures
	private static PlayerCreatures playerCreatures;
	
	// Fighting screen
	
	public static void show(){
		if (isChoosingScreen)
			showChoosing();
		else 
			showFighting();
	}
	public static void render(){
		if (isChoosingScreen)
			renderChoosing();
		else
			renderFighting();
	}
	public static void dispose(){
		if (isChoosingScreen)
			disposeChoosing();
		else
			disposeFighting();
	}
	
	private static void showChoosing(){
		// get player creatures
		playerCreatures = Continue.getPlayerCreatures();
		// batch
		batch = new SpriteBatch();
		// background
		bg = new Sprite(new Texture("continue/fighting/chooseCreatureBg.gif"));
		// creature list
		cBg = new ArrayList <Sprite>();
		cNames = new ArrayList <BitmapFont>();
		cLvls = new ArrayList <BitmapFont>();
		for (int i = 0; i < playerCreatures.getActiveCreatureN(); i++){
			// creature list backgrounds
			cBg.add(new Sprite(new Texture("continue/fighting/creatureBg.gif")));
			// if it is selected, change background
			if (i == selected)
				cBg.get(i).setTexture(new Texture("continue/fighting/selectedCreatureBg.gif"));
			// set position
			cBg.get(i).setPosition(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 1.558441558f + i * (Gdx.graphics.getHeight() / 8.571428571f));
			// set size
			cBg.get(i).setSize(Gdx.graphics.getWidth() / 2.339181287f, Gdx.graphics.getHeight() / 9.6f);
			
			// creature names
			cNames.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
			cNames.get(i).setScale(2);
			// creature levels
			cLvls.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
			cLvls.get(i).setScale(1.5f);
		}
		
		// creature describtion
		//		image
		cImage = new Sprite(new Texture("continue/creatures/images/" + playerCreatures.getActiveCreature(selected).getName() + "/inventory.gif"));
		cImage.setCenter(Gdx.graphics.getWidth() / 1.503759398f, Gdx.graphics.getHeight() / 1.849710983f);
		//		hp bar
		cHpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		cHpBar = new Sprite(new Texture("continue/fighting/hpBar.gif"));
		//			position
		cHpBarBg.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 3.609022556f);
		cHpBar.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 3.609022556f);
		//			size
		cHpBarBg.setSize(Gdx.graphics.getWidth() / 2.469135802f, Gdx.graphics.getHeight() / 24);
		int currentHp = getSelectedCreature().getHealth();
		int fullHp = getSelectedCreature().getHp();
		cHpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * ((currentHp == 0) ? (0) : (currentHp / fullHp)), Gdx.graphics.getHeight() / 24);
		//		exp bar
		cExpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		cExpBar = new Sprite(new Texture("continue/fighting/ExpBar.gif"));
		//			position
		cExpBarBg.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 3.609022556f);
		cExpBar.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 3.609022556f);
		//			size
		cExpBarBg.setSize(Gdx.graphics.getWidth() / 2.469135802f, Gdx.graphics.getHeight() / 24);
		int currentExp = getSelectedCreature().getExp();
		int fullExp = (int) Math.pow(getSelectedCreature().getLvl() * 3, 3);
		cHpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * ((currentExp == 0) ? (0) : (currentExp / fullExp)), Gdx.graphics.getHeight() / 24);
		// 		bar labels
		cHpLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cHp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cExpLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cExp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		//		type
		cType = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2 - Copy.png"), false);
		//		power
		cPower = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		//		defence
		cDefence = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		//		agility
		cAgility = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		
		// action buttons
		runButton = new Sprite(new Texture("continue/fighting/runButton.gif"));
		fightButton = new Sprite(new Texture("continue/fighting/fightButton.gif"));
		updateFightButton();
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			public boolean touchDown (int x, int y, int pointer, int button) {
				// run button
				if ((x >= runButton.getX()) &&
					(x <= runButton.getX() + runButton.getRegionWidth()) &&
					(y >= Play.getCamera().getHeight() - (runButton.getY() + runButton.getRegionHeight())) &&
					(y <= Play.getCamera().getHeight() - runButton.getY()))
				{
					
				}
				// fight button
				else if ((x >= fightButton.getX()) &&
					(x <= fightButton.getX() + fightButton.getRegionWidth()) &&
					(y >= Play.getCamera().getHeight() - (fightButton.getY() + fightButton.getRegionHeight())) &&
					(y <= Play.getCamera().getHeight() - fightButton.getY()) &&
					(getSelectedCreature().getHp() != 0))
				{
					startFighting();
				}
				for (int i = 0; i < playerCreatures.getActiveCreatureN(); i++){
					if ((x >= cBg.get(i).getX()) &&
						(x <= cBg.get(i).getX() + cBg.get(i).getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (cBg.get(i).getY() + cBg.get(i).getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - cBg.get(i).getY()))
					{
						selected = i;
						// update describtion (creature image, hp and exp bars and fight button)
						cImage.setTexture(new Texture("continue/creatures/images/" + playerCreatures.getActiveCreature(selected).getName() + "/inventory.gif"));
						cImage.setCenter(Gdx.graphics.getWidth() / 1.503759398f, Gdx.graphics.getHeight() / 1.849710983f);
						int currentHp = getSelectedCreature().getHealth();
						int fullHp = getSelectedCreature().getHp();
						cHpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * ((currentHp == 0) ? (0) : (currentHp / fullHp)), Gdx.graphics.getHeight() / 24);
						int currentExp = getSelectedCreature().getExp();
						int fullExp = (int) Math.pow(getSelectedCreature().getLvl() * 3, 3);
						cHpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * ((currentExp == 0) ? (0) : (currentExp / fullExp)), Gdx.graphics.getHeight() / 24);
						updateFightButton();
					}
				}
				return true;				
			}
		});
	}
	private static void renderChoosing(){
		batch.begin();
		// background
		bg.draw(batch);
		
		// creature list
		for (int i = 0; i < playerCreatures.getActiveCreatureN(); i++){
			cBg.get(i).draw(batch);
			cNames.get(i).draw(batch, playerCreatures.getActiveCreature(i).getRealName(), cBg.get(i).getX() + Gdx.graphics.getWidth() / 53.33333333f, cBg.get(i).getY() + cNames.get(i).getBounds("A").height*1.2f + Gdx.graphics.getHeight() / 32);
			cLvls.get(i).draw(batch, playerCreatures.getActiveCreature(i).getLvl() + " Level", cBg.get(i).getX() + cBg.get(i).getWidth() - Gdx.graphics.getWidth() / 53.3333333f - cLvls.get(i).getBounds(playerCreatures.getActiveCreature(i).getLvl() + " Level").width, cBg.get(i).getY() + cNames.get(i).getBounds("A").height + Gdx.graphics.getHeight() / 32);
		}
		
		// creature describtion
		cImage.draw(batch);
		//cType.draw(batch, playerCreatures.getActiveCreature(selected).getType(), x, y)
		
		batch.end();
	}
	private static void disposeChoosing(){
		// batch
		batch.dispose();
		// background
		bg.getTexture().dispose();
		// creature list
		for (int i = 0; i < playerCreatures.getActiveCreatureN(); i++){
			cBg.get(i).getTexture().dispose();
			cNames.get(i).dispose();
			cLvls.get(i).dispose();
		}
		cBg.clear();
		cNames.clear();
		cLvls.clear();
		
		// creature describtion
		cImage.getTexture().dispose();
		cHpBar.getTexture().dispose();
		cHpBarBg.getTexture().dispose();
		cExpBar.getTexture().dispose();
		cExpBarBg.getTexture().dispose();
		cHpLabel.dispose();
		cHp.dispose();
		cExpLabel.dispose();
		cExp.dispose();
		cType.dispose();
		cPower.dispose();
		cDefence.dispose();
		cAgility.dispose();
		
		// action buttons
		runButton.getTexture().dispose();
		fightButton.getTexture().dispose();
	}
	
	private static void showFighting(){
		
	}
	private static void renderFighting(){
		
	}
	private static void disposeFighting(){
		
	}
	
	private static void updateFightButton(){
		if (getSelectedCreature().getHp() == 0)
			fightButton.setTexture(new Texture("continue/fighting/disbledFightButton.gif"));
		else
			fightButton.setTexture(new Texture("continue/fighting/fightButton.gif"));			
	}
	
	public static void startChoosing(){
		isChoosingScreen = true;
		// dispose fighting screen
		disposeFighting();
		// show choosing screen
		showChoosing();
	}
	public static void startFighting(){
		isChoosingScreen = false;
		// dispose choosing screen	
		disposeChoosing();
		// show fighting screen
		showFighting();
	}
	
	private static Creature getSelectedCreature(){
		return playerCreatures.getActiveCreature(selected);
	}
}
