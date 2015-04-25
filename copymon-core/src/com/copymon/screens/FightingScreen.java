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
import com.copymon.fighting.Fighting;
import com.copymon.fileHandling.WriteToSave;

/**
 * @author Skaiste
 *
 */
public class FightingScreen {

	// two screens in fighting that may go one after another:
	//		1. Choosing creature at the start and if players creature is out of hp
	//		2. Fighting
	
	private static boolean isChoosingScreen = true; 		// states which of the screens should be on
	private static boolean againstBot = false;
	// batch
	private static SpriteBatch batch;
	// background
	private static Sprite bg;
	
	// **************  Choosing creature screen  ********************
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
	private static Fighting fighting;
	// creature status
	private static Sprite firstPstatusBg, firstPHpBarBg, firstPHpBar, firstPExpBarBg, firstPExpBar;
	private static BitmapFont firstPName, firstPLevel, firstPType, firstPHpBarLabel, firstPHp, firstPExpBarLabel, firstPExp;
	private static Sprite secondPstatusBg, secondPHpBarBg, secondPHpBar;
	private static BitmapFont secondPName, secondPLevel, secondPType, secondPHpBarLabel, secondPHp;
	// skill panel
	private static Sprite runInFightButton, changeCreatureButton;
	private static ArrayList <Sprite> skillBg;
	private static ArrayList <BitmapFont> skillNames;
	private static boolean shouldShowSkills = true;
	private static boolean hasChosenSkill = false;
	private static boolean isCurrentlyFighting = false;
	private static boolean damagefor1Adone = false, damagefor2Adone = false;
	// log
	private static ArrayList <BitmapFont> log;
	private static String logString = "";
	// creatures
	private static Sprite creature1image, creature2image;
	// animations
	private static boolean secondPDamaged = false, firstPDamaged = false;
	private static Sprite damageWind;
	private static boolean animationStart = true;
	private static int blinkerCounter = 0;
	
	// ******************** Ending fight ****************************
	private static Sprite transparentBg;
	// when player loses and has no creatures left to play with
	private static Sprite totalLossBg;
	private static Sprite totalLossButton;
	private static boolean totallyLost = false;
	// when players creature is too weak and player has more creatures that can play
	private static Sprite canChangeCreatureBg;
	private static Sprite canChangeButRunsButton, canChangeAndDoesButton;
	private static boolean canChangeCreature = false;
	// when player wins
	private static Sprite wonFightBg;
	private static Sprite wonFightTakesMoneyButton, wonFightTakesCreatureButton;
	private static BitmapFont wonFightMoney;
	private static boolean wonFight = false;
	
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
						if (i != tmp)
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
					startFighting();
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
		for (int i = 0; i < cBg.size(); i++){
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
		fighting = new Fighting(getSelectedCreature(), CreatureHere.getCreature());
		addToLog("Choose skill!");
		// batch
		batch = new SpriteBatch();
		// background
		bg = new Sprite(new Texture("continue/fighting/fightingBg.gif"));
		
		// first player status 
		//		background
		firstPstatusBg = new Sprite(new Texture("continue/fighting/1Pstatus.gif"));
		firstPstatusBg.setSize(Gdx.graphics.getWidth() / 3.112840467f, Gdx.graphics.getHeight() / 4.173913043f);
		firstPstatusBg.setPosition(Gdx.graphics.getWidth() / 16.66666667f, Gdx.graphics.getHeight() / 1.375358166f);
		// 		health bar
		//			background
		firstPHpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		firstPHpBarBg.setSize(Gdx.graphics.getWidth() / 3.265306122f, Gdx.graphics.getHeight() / 24);
		firstPHpBarBg.setPosition(Gdx.graphics.getWidth() / 14.81481481f, Gdx.graphics.getHeight() / 1.256544503f);
		//			bar itself
		firstPHpBar = new Sprite(new Texture("continue/fighting/hpBar.gif"));
		firstPHpBar.setSize(Gdx.graphics.getWidth() / 3.265306122f * getSelectedCreature().getHpPercentage() / 100, Gdx.graphics.getHeight() / 24);
		firstPHpBar.setPosition(Gdx.graphics.getWidth() / 14.81481481f, Gdx.graphics.getHeight() / 1.256544503f);
		//		exp bar
		//			background
		firstPExpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		firstPExpBarBg.setSize(Gdx.graphics.getWidth() / 4.255319149f, Gdx.graphics.getHeight() / 36.92307692f);
		firstPExpBarBg.setPosition(Gdx.graphics.getWidth() / 13.33333333f, Gdx.graphics.getHeight() / 1.355932203f);
		//			bar itself
		firstPExpBar = new Sprite(new Texture("continue/fighting/ExpBar.gif"));
		firstPExpBar.setSize(Gdx.graphics.getWidth() / 4.255319149f * getSelectedCreature().getExpPercentage() / 100, Gdx.graphics.getHeight() / 36.92307692f);
		firstPExpBar.setPosition(Gdx.graphics.getWidth() / 13.33333333f, Gdx.graphics.getHeight() / 1.355932203f);
		//		fonts
		firstPName = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		firstPName.setScale(1.5f);
		firstPLevel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		firstPLevel.setScale(1.25f);
		firstPType = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2 - Copy.png"), false);
		firstPType.setScale(1.25f);
		firstPType.setColor(setFontBgColor(getSelectedCreature().getType()));
		firstPHpBarLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		firstPHp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		firstPExpBarLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		firstPExp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		
		// second player status
		//		background
		secondPstatusBg = new Sprite(new Texture("continue/fighting/2Pstatus.gif"));
		secondPstatusBg.setSize(Gdx.graphics.getWidth() / 3.112840467f, Gdx.graphics.getHeight() / 5.161290323f);
		secondPstatusBg.setPosition(Gdx.graphics.getWidth() / 1.616161616f, Gdx.graphics.getHeight() / 1.293800539f);
		// 		health bar
		//			background
		secondPHpBarBg = new Sprite(new Texture("continue/fighting/barBg.gif"));
		secondPHpBarBg.setSize(Gdx.graphics.getWidth() / 3.265306122f, Gdx.graphics.getHeight() / 24);
		secondPHpBarBg.setPosition(Gdx.graphics.getWidth() / 1.596806387f, Gdx.graphics.getHeight() / 1.256544503f);
		//			bar itself
		secondPHpBar = new Sprite(new Texture("continue/fighting/hpBar.gif"));
		secondPHpBar.setSize(Gdx.graphics.getWidth() / 3.265306122f * getOpponentCreature().getHpPercentage() / 100, Gdx.graphics.getHeight() / 24);
		secondPHpBar.setPosition(Gdx.graphics.getWidth() / 1.596806387f, Gdx.graphics.getHeight() / 1.256544503f);
		//		fonts
		secondPName = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		secondPName.setScale(1.5f);
		secondPLevel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		secondPLevel.setScale(1.25f);
		secondPType = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2 - Copy.png"), false);
		secondPType.setScale(1.25f);
		secondPType.setColor(setFontBgColor(getOpponentCreature().getType()));
		secondPHpBarLabel = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		secondPHp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
				
		// skill panel
		//		run button
		runInFightButton = new Sprite(new Texture("continue/fighting/runFromFight.gif"));
		runInFightButton.setSize(Gdx.graphics.getWidth() / 9.411764706f, Gdx.graphics.getHeight() / 15.48387097f);
		runInFightButton.setPosition(Gdx.graphics.getWidth() / 2.631578947f, Gdx.graphics.getHeight() / 9.230769231f);
		//		change creature button
		changeCreatureButton = new Sprite(new Texture("continue/fighting/changeCreature.gif"));
		changeCreatureButton.setSize(Gdx.graphics.getWidth() / 9.411764706f, Gdx.graphics.getHeight() / 10.43478261f);
		changeCreatureButton.setPosition(Gdx.graphics.getWidth() / 2.631578947f, Gdx.graphics.getHeight() / 160);
		// skills
		skillBg = new ArrayList<Sprite>();
		skillNames = new ArrayList <BitmapFont>();
		for (int i = 0; i < getSelectedCreature().getActiveSkillN(); i++){
			skillBg.add(new Sprite(new Texture("continue/fighting/skillBgs/" + getSelectedCreature().getActiveSkillByIndex(i).getTypeInString() + ".gif")));
			skillBg.get(i).setSize(Gdx.graphics.getWidth() / 4.12371134f, Gdx.graphics.getHeight() / 12.63157895f);
			// x
			if ((i + 1) % 2 == 0)
				skillBg.get(i).setX(Gdx.graphics.getWidth() / 1.342281879f);
			else
				skillBg.get(i).setX(Gdx.graphics.getWidth() / 2.010050251f);
			// y
			if (i < 2)
				skillBg.get(i).setY(Gdx.graphics.getHeight() / 10.66666667f);
			else
				skillBg.get(i).setY(Gdx.graphics.getHeight() / 160);
			
			
			skillNames.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
			skillNames.get(i).setScale(1.25f);
		}
		
		// creature images
		creature1image = new Sprite(new Texture("continue/creatures/images/" + getSelectedCreature().getName() + "/inventory.gif"));
		creature1image.setCenter(Gdx.graphics.getWidth() / 5.992509363f, Gdx.graphics.getHeight() / 1.88976378f);
		creature2image = new Sprite(new Texture("continue/creatures/images/" + getOpponentCreature().getName() + "/inventory2.gif"));
		creature2image.setCenter(Gdx.graphics.getWidth() / 1.171303075f, Gdx.graphics.getHeight() / 1.88976378f);
		
		// log
		log = new ArrayList <BitmapFont>();
		for (int i = 0; i < 4; i++){
			log.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
		}
		
		// animations
		damageWind = new Sprite(new Texture("continue/fighting/damage.gif"));
		
		// after fight
		transparentBg = new Sprite(new Texture("continue/fighting/transparent.gif"));
		transparentBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		transparentBg.setPosition(0, 0);
		transparentBg.setAlpha(0.5f);
		// when player loses and has no creatures left to play with
		totalLossBg = new Sprite(new Texture("continue/fighting/loseFight.gif"));
		totalLossBg.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);		
		totalLossButton = new Sprite(new Texture("continue/fighting/getMeOuty.gif"));
		totalLossButton.setPosition(totalLossBg.getX() + Gdx.graphics.getWidth() / 13.1147541f, totalLossBg.getY() + Gdx.graphics.getHeight() / 30);		
		// when players creature is too weak and player has more creatures that can play
		canChangeCreatureBg = new Sprite(new Texture("continue/fighting/chooseOrRun.gif"));
		canChangeCreatureBg.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		canChangeButRunsButton = new Sprite(new Texture("continue/fighting/runFromFight.gif"));
		canChangeButRunsButton.setPosition(canChangeCreatureBg.getX() + Gdx.graphics.getWidth() / 16.32653061f, canChangeCreatureBg.getY() + Gdx.graphics.getHeight() / 12.63157895f);	
		canChangeAndDoesButton = new Sprite(new Texture("continue/fighting/changeCreature.gif"));
		canChangeAndDoesButton.setPosition(canChangeCreatureBg.getX() + Gdx.graphics.getWidth() / 3.112840467f, canChangeCreatureBg.getY() + Gdx.graphics.getHeight() / 16);		
		// when player wins
		wonFightBg = new Sprite(new Texture("continue/fighting/takeMoneyOrCreature.gif"));
		wonFightBg.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		wonFightTakesMoneyButton = new Sprite(new Texture("continue/fighting/leaveWithMoney.gif"));
		wonFightTakesMoneyButton.setPosition(wonFightBg.getX() + Gdx.graphics.getWidth() / 40, wonFightBg.getY() + Gdx.graphics.getHeight() / 15.48387097f);	
		wonFightTakesCreatureButton = new Sprite(new Texture("continue/fighting/leaveWithCreature.gif"));
		wonFightTakesCreatureButton.setPosition(wonFightBg.getX() + Gdx.graphics.getWidth() / 3.619909502f, wonFightBg.getY() + Gdx.graphics.getHeight() / 20);	
		wonFightMoney = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		wonFightMoney.setScale(1.5f);
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			public boolean touchDown (int x, int y, int pointer, int button) {
				if (shouldShowSkills){
					// skills
					for (int i = 0; i < getSelectedCreature().getActiveSkillN(); i++){
						if ((x >= skillBg.get(i).getX()) &&
							(x <= skillBg.get(i).getX() + skillBg.get(i).getRegionWidth()) &&
							(y >= Play.getCamera().getHeight() - (skillBg.get(i).getY() + skillBg.get(i).getRegionHeight())) &&
							(y <= Play.getCamera().getHeight() - skillBg.get(i).getY()))
						{
							fighting.choose1PlayerSkill(getSelectedCreature().getActiveSkillByIndex(i));
							hasChosenSkill = true;
						}
					}
					// run button
					if ((x >= runInFightButton.getX()) &&
						(x <= runInFightButton.getX() + runInFightButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (runInFightButton.getY() + runInFightButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - runInFightButton.getY()))
					{
						exitFighting();
					}
					// change creature button
					else if ((x >= changeCreatureButton.getX()) &&
						(x <= changeCreatureButton.getX() + changeCreatureButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (changeCreatureButton.getY() + changeCreatureButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - changeCreatureButton.getY()))
					{
						startChoosing();
						shouldShowSkills = false;
					}
				}
				
				if (totallyLost){
					if ((x >= totalLossButton.getX()) &&
						(x <= totalLossButton.getX() + totalLossButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (totalLossButton.getY() + totalLossButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - totalLossButton.getY()))
					{
						exitFighting();
					}
				}
				else if (canChangeCreature){
					if ((x >= canChangeButRunsButton.getX()) &&
						(x <= canChangeButRunsButton.getX() + canChangeButRunsButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (canChangeButRunsButton.getY() + canChangeButRunsButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - canChangeButRunsButton.getY()))
					{
						exitFighting();
					}
					else if ((x >= canChangeAndDoesButton.getX()) &&
							 (x <= canChangeAndDoesButton.getX() + canChangeAndDoesButton.getRegionWidth()) &&
							 (y >= Play.getCamera().getHeight() - (canChangeAndDoesButton.getY() + canChangeAndDoesButton.getRegionHeight())) &&
							 (y <= Play.getCamera().getHeight() - canChangeAndDoesButton.getY()))
					{
						startChoosing();
						shouldShowSkills = false;
						canChangeCreature = false;
					}
				}
				else if (wonFight){
					if ((x >= wonFightTakesMoneyButton.getX()) &&
						(x <= wonFightTakesMoneyButton.getX() + wonFightTakesMoneyButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (wonFightTakesMoneyButton.getY() + wonFightTakesMoneyButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - wonFightTakesMoneyButton.getY()))
					{
						// money is added
						
						// exiting
						exitFighting();
					}
					else if ((x >= wonFightTakesCreatureButton.getX()) &&
							 (x <= wonFightTakesCreatureButton.getX() + wonFightTakesCreatureButton.getRegionWidth()) &&
							 (y >= Play.getCamera().getHeight() - (wonFightTakesCreatureButton.getY() + wonFightTakesCreatureButton.getRegionHeight())) &&
							 (y <= Play.getCamera().getHeight() - wonFightTakesCreatureButton.getY()))
					{	
						
						// take creature into inventory
						if (playerCreatures.getActiveCreatureN() < 6){
							System.out.println("gets a creature");
							playerCreatures.addActiveCreature(getOpponentCreature());
						}
						else
							playerCreatures.addInactiveCreature(getOpponentCreature());
						
						// exiting
						exitFighting();
					}
				}
				return true;
			}
		});
	}
	private static void renderFighting(){
		batch.begin();
		bg.draw(batch);
		
		// creature status
		//		first
		firstPstatusBg.draw(batch);
		firstPHpBarBg.draw(batch);
		firstPHpBar.setSize(Gdx.graphics.getWidth() / 3.265306122f * getSelectedCreature().getHpPercentage() / 100, Gdx.graphics.getHeight() / 24);
		firstPHpBar.draw(batch);
		firstPExpBarBg.draw(batch);
		firstPExpBar.setSize(Gdx.graphics.getWidth() / 4.255319149f * getSelectedCreature().getExpPercentage() / 100, Gdx.graphics.getHeight() / 36.92307692f);
		firstPExpBar.draw(batch);
		// 			fonts
		firstPName.draw(batch, getSelectedCreature().getRealName(), firstPstatusBg.getX() + Gdx.graphics.getWidth() / 72.72727273f, firstPstatusBg.getY() + Gdx.graphics.getHeight() / 5.647058824f);
		firstPLevel.draw(batch, getSelectedCreature().getLvl() + " Level", firstPstatusBg.getX() + firstPstatusBg.getWidth() - firstPLevel.getBounds(getSelectedCreature().getLvl() + " Level").width - Gdx.graphics.getWidth() / 72.72727273f, firstPstatusBg.getY() + Gdx.graphics.getHeight() / 5.889570552f);
		firstPType.draw(batch, getSelectedCreature().getTypeInString(), firstPstatusBg.getX() + Gdx.graphics.getWidth() / 5.555555556f + firstPType.getBounds(getSelectedCreature().getTypeInString()).width, firstPstatusBg.getY() + Gdx.graphics.getHeight() / 4.173913043f);
		firstPHpBarLabel.draw(batch, "Health:", firstPHpBarBg.getX() + Gdx.graphics.getWidth() / 160, firstPHpBarBg.getY() + Gdx.graphics.getHeight() / 25f);
		firstPHp.draw(batch, getSelectedCreature().getHpPercentage() + "%", firstPHpBarBg.getX() + firstPHpBarBg.getWidth() / 2, firstPHpBarBg.getY() + Gdx.graphics.getHeight() / 25f);
		firstPExpBarLabel.draw(batch, "Exp:", firstPExpBarBg.getX() + Gdx.graphics.getWidth() / 200, firstPExpBarBg.getY() + Gdx.graphics.getWidth() / 50);
		firstPExp.draw(batch, getSelectedCreature().getExpPercentage() + "%", firstPExpBarBg.getX()  + firstPExpBarBg.getWidth() / 2, firstPExpBarBg.getY() + Gdx.graphics.getWidth() / 50);
		//		second
		secondPstatusBg.draw(batch);
		secondPHpBarBg.draw(batch);
		secondPHpBar.setSize(Gdx.graphics.getWidth() / 3.265306122f * getOpponentCreature().getHpPercentage() / 100, Gdx.graphics.getHeight() / 24);
		secondPHpBar.draw(batch);
		// 			fonts
		secondPName.draw(batch, getOpponentCreature().getRealName(), secondPstatusBg.getX() + Gdx.graphics.getWidth() / 72.72727273f, firstPstatusBg.getY() + Gdx.graphics.getHeight() / 5.647058824f);
		secondPLevel.draw(batch, getOpponentCreature().getLvl() + " Level", secondPstatusBg.getX() + secondPstatusBg.getWidth() - secondPLevel.getBounds(getOpponentCreature().getLvl() + " Level").width - Gdx.graphics.getWidth() / 72.72727273f, firstPstatusBg.getY() + Gdx.graphics.getHeight() / 5.889570552f);
		secondPType.draw(batch, getOpponentCreature().getTypeInString(), secondPstatusBg.getX() + Gdx.graphics.getWidth() / 5.555555556f + secondPType.getBounds(getOpponentCreature().getTypeInString()).width, firstPstatusBg.getY() + Gdx.graphics.getHeight() / 4.173913043f);
		secondPHpBarLabel.draw(batch, "Health:", secondPHpBarBg.getX() + Gdx.graphics.getWidth() / 160, secondPHpBarBg.getY() + Gdx.graphics.getHeight() / 25f);
		secondPHp.draw(batch, getOpponentCreature().getHpPercentage() + "%", secondPHpBarBg.getX() + secondPHpBarBg.getWidth() / 2, secondPHpBarBg.getY() + Gdx.graphics.getHeight() / 25f);
		
		// skill panel
		if (shouldShowSkills)
		{
			runInFightButton.draw(batch);
			changeCreatureButton.draw(batch);
			for (int i = 0; i < skillBg.size(); i++){
				skillBg.get(i).draw(batch);
				skillNames.get(i).draw(batch, getSelectedCreature().getActiveSkillByIndex(i).getDisplayName(), skillBg.get(i).getX() + Gdx.graphics.getWidth() / 80, skillBg.get(i).getY() + Gdx.graphics.getHeight() / 16f);
			}
		}
		// creature images
		creature1image.draw(batch);
		creature2image.draw(batch);
		
		// log
		ArrayList <String> tmp = splittingForLog(logString, log.get(0), Gdx.graphics.getWidth() / 2.777777778f);
		for (int i = 0; i < log.size(); i++){
			log.get(i).draw(batch, (tmp.size() > i) ? tmp.get(i) : "", Gdx.graphics.getWidth() / 160, Gdx.graphics.getHeight() / 5.853658537f - i * Gdx.graphics.getHeight() / 25);
		}
		
		// attacking part!!!
		if (shouldShowSkills && hasChosenSkill){
			shouldShowSkills = false;
			hasChosenSkill = false;
			
			// choosing second player skill
			if (againstBot){
				fighting.choose2PlayerSkill(fighting.chooseBotSkill());
			}
			else{
				fighting.choose2PlayerSkill(null);
			}			
			
			// action!!
			fighting.whoAttacksFirst();
			isCurrentlyFighting = true;
			damagefor1Adone = false;
			damagefor2Adone = false;
		}
		// animations and logs
		if (isCurrentlyFighting)
		{
			if (fighting.isFirstPAttackingFirst()){
				//System.out.println("damage of first: " + fighting.getDamageForSecondP() + ", damage of second: " + fighting.getDamageForFirstP());
				// first creature attacks
				// animation
				if (!secondPDamaged){
					if (!damagefor1Adone){
						fighting.doTheAction(true);
						damagefor1Adone = true;
					}
					doAnimationDamaging2P(batch);
				}
				// second creature attacks if it isn't weak
				// animation
				if (!firstPDamaged && secondPDamaged){
					if (!damagefor2Adone){
						fighting.doTheAction(false);
						damagefor2Adone = true;
					}
					if (fighting.canSecondPAttack())
						doAnimationDamaging1P(batch);
					else
						firstPDamaged = true;
				}
			}
			else {
				// second creature attacks
				// animation
				if (!firstPDamaged){
					if (!damagefor1Adone){
						fighting.doTheAction(true);
						damagefor1Adone = true;
					}
					doAnimationDamaging1P(batch);	
				}
				// first creature attacks if it isn't weak
				// animation
				if (!secondPDamaged && firstPDamaged){
					if (!damagefor2Adone){
						fighting.doTheAction(false);
						damagefor2Adone = true;
					}
					if (getSelectedCreature().getHealth() != 0)
						doAnimationDamaging2P(batch);
					else
						secondPDamaged = true;
				}
			}
			// after animations
			if (secondPDamaged && firstPDamaged){
				transparentBg.draw(batch);
				// if players creature is too weak to play,
				// let the player choose other creature (if he has one of course)
				// or run
				if (getSelectedCreature().getHealth() <= 0){
					// checking if there are any player creatures that are not too weak
					if (!playerCreatures.areAllActiveCreaturesWeak()){
						// let player choose to choose a creature or run
						canChangeCreature = true;
						canChangeCreatureBg.draw(batch);
						canChangeButRunsButton.draw(batch);
						canChangeAndDoesButton.draw(batch);
					}
					else{
						// let player know he lost and let him/her press a button that exits the fighting
						totallyLost = true;
						totalLossBg.draw(batch);
						totalLossButton.draw(batch);
					}
				}
				// if opponent creature is too weak to play and it is a wild creature
				// let player keep the creature
				else if (getOpponentCreature().getHealth() <= 0){
					wonFight = true;
					wonFightBg.draw(batch);
					wonFightTakesMoneyButton.draw(batch);
					wonFightMoney.draw(batch, "25", wonFightTakesMoneyButton.getX() + wonFightTakesMoneyButton.getWidth() / 3 * 2 - wonFightMoney.getBounds("25").width, wonFightTakesMoneyButton.getY() + Gdx.graphics.getHeight() / 13);
					wonFightTakesCreatureButton.draw(batch);
				}
				else{
					shouldShowSkills = true;
					secondPDamaged = false;
					firstPDamaged = false;
					isCurrentlyFighting = false;
				}
			}
		}
		batch.end();
	}
	
	private static void doAnimationDamaging2P(SpriteBatch batch){
		if (animationStart){
			addToLog(getSelectedCreature().getRealName() + " attackes with " + fighting.getFirstPSkill().getDisplayName() + "\n");
			damageWind.setPosition(Gdx.graphics.getWidth() / 3.47826087f, Gdx.graphics.getHeight() / 2.191780822f);
			animationStart = false;
		}
		else{
			if (damageWind.getX() > (Gdx.graphics.getWidth() / 1.809954751f)){
				if (blinkerCounter < 100){
					if(blinkerCounter % 20 == 0){
						creature2image.setAlpha(0);
					}
					else{
						creature2image.setAlpha(1);
					}
					blinkerCounter++;
				}
				else{
					secondPDamaged = true;
					blinkerCounter = 0;
					animationStart = true;			
					// displaying done damage
					addToLog(getOpponentCreature().getRealName() + " gets damaged by " + fighting.getDamageForSecondP() + "\n");
				}
			}
			else{
				damageWind.setX(damageWind.getX() + 7);
				damageWind.draw(batch);
			}
		}
	}
	private static void doAnimationDamaging1P(SpriteBatch batch){
		if (animationStart){
			addToLog(getOpponentCreature().getRealName() + " attackes with " + fighting.getSecondPSkill().getDisplayName() + "\n");
			damageWind.setPosition(Gdx.graphics.getWidth() / 1.809954751f, Gdx.graphics.getHeight() / 2.191780822f);
			animationStart = false;
		}
		else{
			if (damageWind.getX() < (Gdx.graphics.getWidth() / 3.47826087f)){
				if (blinkerCounter < 100){
					if(blinkerCounter % 20 == 0){
						creature1image.setAlpha(0);
					}
					else{
						creature1image.setAlpha(1);
					}
					blinkerCounter++;
				}
				else{
					firstPDamaged = true;
					blinkerCounter = 0;
					animationStart = true;			
					// displaying done damage
					addToLog("Your " + getSelectedCreature().getRealName() + " gets damaged by " + fighting.getDamageForFirstP() + "\n");
				}
			}
			else{
				damageWind.setX(damageWind.getX() - 7);
				damageWind.draw(batch);
			}
		}
	}
	
	
	
	private static void disposeFighting(){
		batch.dispose();
		bg.getTexture().dispose();
		// creature status
		firstPstatusBg.getTexture().dispose();
		firstPHpBarBg.getTexture().dispose();
		firstPHpBar.getTexture().dispose();
		firstPExpBarBg.getTexture().dispose();
		firstPExpBar.getTexture().dispose();
		firstPName.dispose();
		firstPLevel.dispose();
		firstPType.dispose();
		firstPHpBarLabel.dispose();
		firstPHp.dispose();
		firstPExpBarLabel.dispose();
		firstPExp.dispose();
		secondPstatusBg.getTexture().dispose();
		secondPHpBarBg.getTexture().dispose();
		secondPHpBar.getTexture().dispose();
		secondPName.dispose();
		secondPLevel.dispose();
		secondPType.dispose();
		secondPHpBarLabel.dispose();
		secondPHp.dispose();
		// skill panel
		runInFightButton.getTexture().dispose();
		changeCreatureButton.getTexture().dispose();
		for (int i = 0; i < skillBg.size(); i++){
			skillBg.get(i).getTexture().dispose();
			skillNames.get(i).dispose();
		}
		
		creature1image.getTexture().dispose();
		creature2image.getTexture().dispose();
		
		for (int i = 0; i < log.size(); i++){
			log.get(i).dispose();
		}
		log.clear();
		
		damageWind.getTexture().dispose();
		
		totalLossBg.getTexture().dispose();
		totalLossButton.getTexture().dispose();
		canChangeCreatureBg.getTexture().dispose();
		canChangeButRunsButton.getTexture().dispose();
		canChangeAndDoesButton.getTexture().dispose();
		wonFightBg.getTexture().dispose();
		wonFightTakesMoneyButton.getTexture().dispose();
		wonFightTakesCreatureButton.getTexture().dispose();
		wonFightMoney.dispose();
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
		logString = "";
		isChoosingScreen = false;
		// dispose choosing screen	
		disposeChoosing();
		// show fighting screen
		showFighting();
	}
	
	public static ArrayList <String> splittingForLog(String describtion, BitmapFont font, float maxWidth) {
		// first split lines
		String[] splitLines = describtion.split("\\n+");
		ArrayList <String> lines = new ArrayList<String>();
		// then split words
		for (int j = 0; j < splitLines.length; j++){
			String[] split = splitLines[j].split("\\s+");
			float length = 0;
			for (int i = 0; i < split.length; i++)
			{
				if ((i == 0) || (length + font.getBounds(split[i]).width > maxWidth + font.getBounds("a").width)) {
					lines.add("");
					length = 0;
				}
				lines.set(lines.size() - 1, lines.get(lines.size() - 1) + split[i] + " " );
				length = lines.get(lines.size() - 1).length() * font.getBounds("a").width;
			}
		}
		return lines;
	}
	private static void exitFighting(){
		new WriteToSave(Continue.getPlayer().getGender(), Continue.getPlayer().getX(), Continue.getPlayer().getY(), Play.getCamera().getX(), Play.getCamera().getY(), playerCreatures);
		FightingScreen.dispose();
		Continue.show();
		Menu.setFighting(false);
		Menu.setContinue(true);
		wonFight = false;
		shouldShowSkills = true;
		secondPDamaged = false;
		firstPDamaged = false;
		isCurrentlyFighting = false;
		isChoosingScreen = true;
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
	public static boolean isAgainstBot() {
		return againstBot;
	}
	public static void setAgainstBot(boolean againstBot) {
		FightingScreen.againstBot = againstBot;
	}
	public static Creature getOpponentCreature(){
		if (againstBot){
			return CreatureHere.getCreature();
		}
		else return null;
	}
	public static void showSkills(boolean should){
		shouldShowSkills = should;
	}
	
	public static void addToLog (String s){
		StringBuilder lines = new StringBuilder(s);
		lines.append(logString);
		logString = lines.toString();
	}
}
