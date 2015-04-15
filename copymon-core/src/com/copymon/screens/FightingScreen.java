package com.copymon.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copymon.creatures.Creature;
import com.copymon.creatures.PlayerCreatures;
import com.copymon.creatures.Type;

public class FightingScreen {

	// two screens in fighting that may go one after another:
	//		1. Choosing creature at the start and if players creature is out of hp
	//		2. Fighting
	
	private static boolean isChoosingScreen = true; 		// states which of the screens should be on
	
	// **************  Choosing creature screen  ********************
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
	
	// ******************  Fighting screen  **************************
	
	
	
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
			cBg.get(i).setPosition(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 1.558441558f - i * (Gdx.graphics.getHeight() / 8.571428571f));
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
		cImage = new Sprite(new Texture("continue/creatures/images/" + getSelectedCreature().getName() + "/inventory.gif"));
		cImage.setSize(cImage.getTexture().getWidth(), cImage.getTexture().getHeight());
		cImage.setCenter(Gdx.graphics.getWidth() / 1.503759398f, Gdx.graphics.getHeight() / 1.849710983f);
		//		hp bar
		cHpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		cHpBar = new Sprite(new Texture("continue/fighting/hpBar.gif"));
		//			position
		cHpBarBg.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 3.609022556f);
		cHpBar.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 3.609022556f);
		//			size
		cHpBarBg.setSize(Gdx.graphics.getWidth() / 2.469135802f, Gdx.graphics.getHeight() / 24);
		cHpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * getSelectedCreature().getHpPercentage() / 100, Gdx.graphics.getHeight() / 24);
		//		exp bar
		cExpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		cExpBar = new Sprite(new Texture("continue/fighting/ExpBar.gif"));
		//			position
		cExpBarBg.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 4.705882353f);
		cExpBar.setPosition(Gdx.graphics.getWidth() / 1.843317972f, Gdx.graphics.getHeight() / 4.705882353f);
		//			size
		cExpBarBg.setSize(Gdx.graphics.getWidth() / 2.469135802f, Gdx.graphics.getHeight() / 24);
		cExpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * (getSelectedCreature().getExpPercentage() / 100), Gdx.graphics.getHeight() / 24);
		// 		bar labels
		cHpLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cHp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cExpLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cExp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		//		type
		cType = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2 - Copy.png"), false);
		cType.setColor(setFontBgColor(getSelectedCreature().getType()));
		cType.setScale(1.75f);
		//		power
		cPower = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cPower.setScale(1.5f);
		//		defence
		cDefence = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cDefence.setScale(1.5f);
		//		agility
		cAgility = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		cAgility.setScale(1.5f);
		
		// action buttons
		runButton = new Sprite(new Texture("continue/fighting/runButton.gif"));
		runButton.setPosition(Gdx.graphics.getWidth() / 1.449275362f, Gdx.graphics.getHeight() / 18.46153846f);
		fightButton = new Sprite(new Texture("continue/fighting/fightButton.gif"));
		fightButton.setPosition(Gdx.graphics.getWidth() / 1.219512195f, Gdx.graphics.getHeight() / 18.46153846f);
		updateFightButton();
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			public boolean touchDown (int x, int y, int pointer, int button) {
				// creature list
				for (int i = 0; i < playerCreatures.getActiveCreatureN(); i++){
					if ((x >= cBg.get(i).getX()) &&
						(x <= cBg.get(i).getX() + cBg.get(i).getWidth()) &&
						(y >= Play.getCamera().getHeight() - (cBg.get(i).getY() + cBg.get(i).getHeight())) &&
						(y <= Play.getCamera().getHeight() - cBg.get(i).getY()))
					{
						int tmp = selected;
						selected = i;
						// update describtion (creature image, hp and exp bars and fight button)
						cImage.setTexture(new Texture("continue/creatures/images/" + getSelectedCreature().getName() + "/inventory.gif"));
						cImage.setSize(cImage.getTexture().getWidth(), cImage.getTexture().getHeight());
						cImage.setCenter(Gdx.graphics.getWidth() / 1.503759398f, Gdx.graphics.getHeight() / 1.849710983f);
						cBg.get(i).setTexture(new Texture("continue/fighting/selectedCreatureBg.gif"));
						cBg.get(tmp).setTexture(new Texture("continue/fighting/creatureBg.gif"));
						cHpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * getSelectedCreature().getHpPercentage() / 100, Gdx.graphics.getHeight() / 24);
						cExpBar.setSize(Gdx.graphics.getWidth() / 2.469135802f * getSelectedCreature().getExpPercentage() / 100, Gdx.graphics.getHeight() / 24);
						cType.setColor(setFontBgColor(getSelectedCreature().getType()));
						updateFightButton();
					}
				}
				// run button
				if ((x >= runButton.getX()) &&
					(x <= runButton.getX() + runButton.getRegionWidth()) &&
					(y >= Play.getCamera().getHeight() - (runButton.getY() + runButton.getRegionHeight())) &&
					(y <= Play.getCamera().getHeight() - runButton.getY()))
				{
					FightingScreen.dispose();
					Continue.show();
					Menu.setFighting(false);
					Menu.setContinue(true);
				}
				// fight button
				else if ((x >= fightButton.getX()) &&
					(x <= fightButton.getX() + fightButton.getRegionWidth()) &&
					(y >= Play.getCamera().getHeight() - (fightButton.getY() + fightButton.getRegionHeight())) &&
					(y <= Play.getCamera().getHeight() - fightButton.getY()) &&
					(getSelectedCreature().getHp() != 0))
				{

					FightingScreen.dispose();
					Continue.show();
					Menu.setFighting(false);
					Menu.setContinue(true);
					
					// take creature into inventory
					if (Continue.getPlayerCreatures().getActiveCreatures().size() < 6)
						Continue.getPlayerCreatures().addActiveCreature(CreatureHere.getCreature());

					//startFighting();
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
		cType.draw(batch, getSelectedCreature().getTypeInString(), Gdx.graphics.getWidth() / 1.25f, Gdx.graphics.getHeight() / 1.4f);
		cPower.draw(batch, "Power: " + getSelectedCreature().getAp(), Gdx.graphics.getWidth() / 1.25f, Gdx.graphics.getHeight() / 1.563517915f);
		cDefence.draw(batch, "Defence: " + getSelectedCreature().getDefence(), Gdx.graphics.getWidth() / 1.25f, Gdx.graphics.getHeight() / 1.77028451f);
		cAgility.draw(batch, "Agility: " + getSelectedCreature().getAgility(), Gdx.graphics.getWidth() / 1.25f, Gdx.graphics.getHeight() / 2.04007286f);
		
		//		hp bar
		cHpBarBg.draw(batch);
		cHpBar.draw(batch);
		cHpLabel.draw(batch, "Health:", cHpBarBg.getX() + cHpLabel.getBounds("A").width, cHpBarBg.getY() + cHpBarBg.getHeight());
		cHp.draw(batch, getSelectedCreature().getHpPercentage() + "%", cHpBarBg.getX() + cHpBarBg.getWidth() / 2, cHpBarBg.getY() + cHpBarBg.getHeight());
		
		//		exp bar
		cExpBarBg.draw(batch);
		cExpBar.draw(batch);
		cExpLabel.draw(batch, "Exp:", cExpBarBg.getX() + cExpLabel.getBounds("A").width, cExpBarBg.getY() + cExpBarBg.getHeight());
		cExp.draw(batch, getSelectedCreature().getExpPercentage() + "%", cExpBarBg.getX() + cExpBarBg.getWidth() / 2, cExpBarBg.getY() + cExpBarBg.getHeight());
		
		// action button
		runButton.draw(batch);
		fightButton.draw(batch);
		
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
		
		Gdx.input.setInputProcessor(null);
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
	
	private static Color setFontBgColor(Type t){
		switch (t){
		case BUG:
			return new Color(0.568627451f, 0.611764706f, 0.007843137f, 1);
		case DARK:
			return new Color(1, 1, 1, 1);
		case ELECTRIC:
			return new Color(1, 0.996078431f, 0.6f, 1);
		case FIGHTING:
			return new Color(0.776470588f, 0.270588235f, 0.066666667f, 1);
		case FIRE:
			return new Color(0.996078431f, 0.423529412f, 0.423529412f, 1);
		case FLYING:
			return new Color(0.91372549f, 0.639215686f, 0.847058824f, 1);
		case GHOST:
			return new Color(0.439215686f, 0.188235294f, 0.62745098f, 1);
		case GRASS:
			return new Color(0.662745098f, 0.815686275f, 0.556862745f, 1);
		case GROUND:
			return new Color(1, 1, 1, 1);
		case NORMAL:
			return new Color(1, 1, 1, 1);
		case POISON:
			return new Color(1, 1, 1, 1);
		case PSYCHIC:
			return new Color(1, 0.835294118f, 0.929411765f, 1);
		case ROCK:
			return new Color(0.749019608f, 0.749019608f, 0.749019608f, 1);
		case STEEL:
			return new Color(1, 1, 1, 1);
		case WATER:
			return new Color(0.607843137f, 0.760784314f, 0.901960784f, 1);
		default:
			return new Color(1, 1, 1, 1);
		}
	}
}
