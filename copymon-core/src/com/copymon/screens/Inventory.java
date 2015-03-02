package com.copymon.screens;



import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.copymon.creatures.Creature;
import com.copymon.creatures.PlayerCreatures;

public class Inventory {
		
	private static boolean show = false,
						   once = true;
	
	private static SpriteBatch batch;
	private static ShapeRenderer transparentBg;
	
	private static PlayerCreatures playerCreatures;
	
	// ***************** Main screen **********************
	private static Sprite bg;
	private static int creatureLayer = 0,
					   skillLayer = 0;
	private static boolean isItActive = true; // is it active creatures (creature inventory)
	// for creature list part
	final private static int nDisplayedCreatures = 3;
	private static int selectedCreature = 0;
	private static Sprite creatureUp, creatureDown, skillUp, skillDown;
	private static ArrayList <Sprite> creatureBg, crHpBar, crExpBar, crBarBg4Hp, crBarBg4Exp;
	private static ArrayList <BitmapFont> crName, crLv, crHp, crHpPer, crExp, crExpPer;
	// for description part
	private static ArrayList <BitmapFont> creatureDescription;
	private static ArrayList <String> creatureDescr;
	private static BitmapFont creatureAp, creatureDef, creatureAg;
	private static Sprite creatureImage;
	// for skill part
	final private static int nDisplayedSkills = 2;
	private static ArrayList <BitmapFont> skillName, skillType, skillAp;
	private static ArrayList <ArrayList <BitmapFont>> skillDescr;
	private static ArrayList <ArrayList <String>> skillDescrText;
	private static ArrayList <Sprite> skillBg;
	
	
	public static void update() {
		playerCreatures = Continue.getPlayerCreatures();
		show = Continue.getPlayer().inventory && !Continue.getPlayer().switchSorC;
		if (show)
		{
			if (once) {
				show();
				once = false;
			}
			render();
		}
		else if (!show && !once)
		{
			dispose();
			once = true;
		}
	}
	
	public static void render() {
		// painting transparent background
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		transparentBg.begin(ShapeType.Filled);
		transparentBg.setColor(0, 0, 0, 0.5f);
		transparentBg.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		transparentBg.end();
		Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
		
		mainRender();
	}
	public static void show() {
		transparentBg = new ShapeRenderer();
		batch = new SpriteBatch();
		
		mainShow();
	}
	public static void dispose() {
		
		mainDispose();
		
		batch.dispose();
		transparentBg.dispose();
	}
	
	// main inventory
	public static void mainRender() {		
		batch.begin();
		bg.draw(batch);
		// arrows
		// creature upper arrow
		if (isCreatureUpVisible())
			creatureUp.draw(batch);
		// creature lower arrow
		if (isCreatureDownVisible())
			creatureDown.draw(batch);
		// skill upper arrow
		if (isSkillUpVisible())
			skillUp.draw(batch);
		// skill lower arrow
		if (isSkillDownVisible())
			skillDown.draw(batch);
		
		// 				CREATURE PART
		
		for (int i = 0; i < nDisplayedCreatures; i++)
		{
			int currentCreature = i + creatureLayer * nDisplayedCreatures;
			if (currentCreature < creatures().size())
			{
				// sprites
				creatureBg.get(i).draw(batch);
				crBarBg4Hp.get(i).draw(batch);
				crBarBg4Exp.get(i).draw(batch);
				crHpBar.get(i).draw(batch);
				crExpBar.get(i).draw(batch);
				
				// bitmap font
				crName.get(i).draw(batch, creatures().get(currentCreature).getRealName(), creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f,  creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 5.552631578947f);
				crLv.get(i).draw(batch, creatures().get(currentCreature).getLvl() + " Level", creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f + crBarBg4Hp.get(i).getWidth() - crLv.get(i).getBounds(creatures().get(currentCreature).getLvl() + " Level").width,  creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 5.552631578947f);
				crHp.get(i).draw(batch, "Health:", creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 80, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 8.13f);
				crHpPer.get(i).draw(batch, "100%", crBarBg4Hp.get(i).getX() + crBarBg4Hp.get(i).getWidth()/2 - crHpPer.get(i).getBounds("100%").width/2, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 8.13f);
				crExp.get(i).draw(batch, "Exp:", creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 80, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 17.14f);
				crExpPer.get(i).draw(batch, "0%", creatureBg.get(i).getX() + crBarBg4Exp.get(i).getWidth()/2 - crExpPer.get(i).getBounds("0%").width/2, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 17.14f);
			}
		}
		
		// 			DESCRIPTION PART
		if ((selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size()))
		{
			creatureImage.draw(batch);
			for (int i = 0; i < creatureDescription.size(); i++)
			{
				creatureDescription.get(i).draw(batch, creatureDescr.get(i), Gdx.graphics.getWidth() / 2.711864407f, Gdx.graphics.getHeight() / 2.5f - (Gdx.graphics.getHeight() / 22.85714288f) * i);
			}
			creatureAp.draw(batch, "Power: " + creatures().get(selectedCreature).getAp(), Gdx.graphics.getWidth() / 2.711864407f, Gdx.graphics.getHeight() / 5.517241379f);
			creatureDef.draw(batch, "Defence: " + creatures().get(selectedCreature).getDefence(), Gdx.graphics.getWidth() / 2.711864407f, Gdx.graphics.getHeight() / 7.272727273f);
			creatureAg.draw(batch, "Agility: " + creatures().get(selectedCreature).getAgility(), Gdx.graphics.getWidth() / 2.711864407f, Gdx.graphics.getHeight() / 10.66666667f);
	
			//			SKILL PART
			for (int i = 0; i < nDisplayedSkills; i++)
			{
				int currentSkill = i + skillLayer * nDisplayedSkills;
				if (currentSkill < creatures().get(selectedCreature).getActiveSkillN())
				{
					skillBg.get(i).draw(batch);
					
					skillName.get(i).setScale(1.20f);
					skillName.get(i).draw(batch, creatures().get(selectedCreature).getActiveSkillByIndex(currentSkill).getDisplayName(), skillBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, skillBg.get(i).getY() + Gdx.graphics.getHeight() / 4.285714286f);
					skillType.get(i).draw(batch, "Type: " + creatures().get(selectedCreature).getActiveSkillByIndex(currentSkill).getType(), skillBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, skillBg.get(i).getY() + Gdx.graphics.getHeight() / 5.274725275f);
					for (int j = 0; j < skillDescr.get(i).size(); j++)
					{
						skillDescr.get(i).get(j).draw(batch, skillDescrText.get(i).get(j), skillBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, skillBg.get(i).getY() + Gdx.graphics.getHeight() / 6.486486486f - (Gdx.graphics.getHeight() / 28.23529412f) * j);
					}
					skillAp.get(i).draw(batch, "Power: " + creatures().get(selectedCreature).getActiveSkillByIndex(currentSkill).getPower(), skillBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, skillBg.get(i).getY() + Gdx.graphics.getHeight() / 20.86956522f);
				}
			}
		}
		batch.end();
	}
	public static void mainShow() {
		// setting up sprites
		if (isItActive)
			bg = new Sprite(new Texture("continue/inventory/backgroundActive.gif"));
		else 
			bg = new Sprite(new Texture("continue/inventory/backgroundInactive.gif"));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Arrows
		creatureUp = new Sprite(new Texture("continue/inventory/arrowUp.gif"));
		creatureDown = new Sprite(new Texture("continue/inventory/arrowDown.gif"));
		skillUp = new Sprite(new Texture("continue/inventory/arrowUp.gif"));
		skillDown = new Sprite(new Texture("continue/inventory/arrowDown.gif"));
		
		// Arrows size
		float width = Gdx.graphics.getWidth() / 21.05263158f, height = Gdx.graphics.getHeight() / 24;
		creatureUp.setSize(width, height);
		creatureDown.setSize(width, height);
		skillUp.setSize(width, height);
		skillDown.setSize(width, height);
		
		// Arrows position
		creatureUp.setPosition(Gdx.graphics.getWidth() / 6.779661017f, Gdx.graphics.getHeight() / 1.391304348f);
		creatureDown.setPosition(Gdx.graphics.getWidth() / 6.779661017f, Gdx.graphics.getHeight() / 22.85714286f);
		skillUp.setPosition(Gdx.graphics.getWidth() / 1.242236025f, Gdx.graphics.getHeight() / 1.589403974f);
		skillDown.setPosition(Gdx.graphics.getWidth() / 1.242236025f, Gdx.graphics.getHeight() / 22.85714286f);
		
		// Sprite arraylists
		creatureBg = new ArrayList<Sprite>();
		crBarBg4Hp =  new ArrayList<Sprite>();
		crHpBar =  new ArrayList<Sprite>();
		crBarBg4Exp =  new ArrayList<Sprite>();
		crExpBar =  new ArrayList<Sprite>();
		
		// bitmap arraylists
		crName =  new ArrayList<BitmapFont>();	
		crLv =  new ArrayList<BitmapFont>();	
		crHp =  new ArrayList<BitmapFont>();	
		crHpPer =  new ArrayList<BitmapFont>();	
		crExp =  new ArrayList<BitmapFont>();	
		crExpPer =  new ArrayList<BitmapFont>();	

		// 				CREATURE PART
		createCreaturePart();
		
		//			DESCRIPTION PART
		createDescriptionPart();
		
		//			SKILL PART
		createSkillPart();
	}
	public static void mainDispose() {
		bg.getTexture().dispose();
		
		// Arrows
		creatureUp.getTexture().dispose();
		creatureDown.getTexture().dispose();
		skillUp.getTexture().dispose();
		skillDown.getTexture().dispose();
		
		//			CREATURE PART
		disposeCreaturePart();
		
		//			DESCRIPTION PART
		disposeDescriptionPart();
		
		//			SKILL PART
		disposeSkillPart();
	}
	
	public static boolean isItActive() {
		return isItActive;
	}
	public static void setItActive(boolean isItActive) {
		Inventory.isItActive = isItActive;
	}
	
	private static ArrayList <Creature> creatures() {
		if (isItActive)
			return playerCreatures.getActiveCreatures();
		else
			return playerCreatures.getInactiveCreatures();
	}
	
	public static Creature getSelectedCreature(){
		if (isItActive)
			return playerCreatures.getActiveCreatures().get(selectedCreature);
		else
			return playerCreatures.getInactiveCreatures().get(selectedCreature);
	}
	public static void selectCreature(int index) {
		// selected creature
		int selectCreature = index + creatureLayer * nDisplayedCreatures;
		// allowed size
		int aSize = 0;
		if (isItActive)
			aSize = playerCreatures.getActiveCreatureN();
		else
			aSize = playerCreatures.getInactiveCreatureN();
		
		// if the selection is valid
		if ((index < nDisplayedCreatures) && (selectedCreature < aSize)){
			selectedCreature = selectCreature;
			updateCreatureList();
			updateCreatureDescription();
		}
	}
	
	public static void goCreatureUp(){
		if (isCreatureUpVisible())
		{
			// go to lower layer
			creatureLayer--;
			// update list and description
			updateCreatureList();
			updateCreatureDescription();			
		}
	}
	public static void goCreatureDown(){
		if(isCreatureDownVisible())
		{
			// go to upper layer
			creatureLayer++;
			// update list and description
			updateCreatureList();
			updateCreatureDescription();
		}
	}
	public static void goSkillUp(){
		if (isSkillUpVisible())
		{
			// go to lower layer
			skillLayer--;
			// update list
			updateSkillList();
		}
	}
	public static void goSkillDown(){
		if (isSkillDownVisible())
		{
			// go to upper layer
			skillLayer++;
			// update list
			updateSkillList();
		}
	}
	
	private static void updateCreatureList(){
		// Dispose all elements
		disposeCreaturePart();
		
		// Create all elements
		createCreaturePart();
	}
	private static void updateCreatureDescription(){
		// Dispose all elements
		disposeDescriptionPart();
		
		// Create all elements
		createDescriptionPart();
	}
	private static void updateSkillList(){
		// Dispose all elements
		disposeSkillPart();
		
		// Create all elements
		createSkillPart();		
	}

 	public static boolean isCreatureUpVisible(){
		if ((creatureLayer != 0) && (creatures().size() != 0))
			return true;
		else
			return false;
	}
	public static boolean isCreatureDownVisible(){
		if (creatures().size() > nDisplayedCreatures * (creatureLayer + 1))
			return true;
		else
			return false;
	}
	public static boolean isSkillUpVisible(){
		if ((selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size()) && (creatures().get(selectedCreature).getActiveSkillN() != 0) && (skillLayer != 0))
			return true;
		else
			return false;
	}
	public static boolean isSkillDownVisible(){
		if ((selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size()) && (creatures().get(selectedCreature).getActiveSkillN() > nDisplayedSkills * (skillLayer + 1)))
			return true;
		else
			return false;
	}
	
	private static void createCreaturePart(){
		for (int i = 0; i < nDisplayedCreatures; i++)
		{
			int currentCreature = i + creatureLayer * nDisplayedCreatures;
			if (currentCreature < creatures().size())
			{
				// background for creatures
				if (selectedCreature == currentCreature)
					creatureBg.add(new Sprite(new Texture("continue/inventory/selectedCreatureBg.gif")));
				else
					creatureBg.add(new Sprite(new Texture("continue/inventory/creatureBg.gif")));
				if (i == 0)
					creatureBg.get(i).setPosition(Gdx.graphics.getWidth() / 30.76923077f, Gdx.graphics.getHeight() / 1.959183673f);
				else
					creatureBg.get(i).setPosition(Gdx.graphics.getWidth() / 30.76923077f, creatureBg.get(i-1).getY() - Gdx.graphics.getHeight() / 4.8f);
				creatureBg.get(i).setSize(Gdx.graphics.getWidth() / 3.603603604f, Gdx.graphics.getHeight() / 5.106382979f);
				
				// Health bar background
				crBarBg4Hp.add(new Sprite(new Texture("continue/inventory/barBg.gif")));
				crBarBg4Hp.get(i).setPosition(creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 12.30769231f);
				crBarBg4Hp.get(i).setSize(Gdx.graphics.getWidth() / 3.827751196f, Gdx.graphics.getHeight() / 24);
				
				// Experience bar background
				crBarBg4Exp.add(new Sprite(new Texture("continue/inventory/barBg.gif")));
				crBarBg4Exp.get(i).setPosition(creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 60);
				crBarBg4Exp.get(i).setSize(Gdx.graphics.getWidth() / 3.827751196f, Gdx.graphics.getHeight() / 24);
	
				// Health Bar
				crHpBar.add(new Sprite(new Texture("continue/inventory/hpBar.gif")));
				crHpBar.get(i).setPosition(creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 12.30769231f);
				int currentHp = creatures().get(currentCreature).getHealth();
				int fullHp = creatures().get(currentCreature).getHp();
				crHpBar.get(i).setSize(Gdx.graphics.getWidth() / 3.827751196f, Gdx.graphics.getHeight() / 24 * ((currentHp == 0) ? (0) : (currentHp / fullHp))); 	// currentExp / fullExp
				
				// Experience bar
				crExpBar.add(new Sprite(new Texture("continue/inventory/ExpBar.gif")));
				crExpBar.get(i).setPosition(creatureBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, creatureBg.get(i).getY() + Gdx.graphics.getHeight() / 60);
				int currentExp = creatures().get(currentCreature).getExp();
				int fullExp = (int) Math.pow(creatures().get(currentCreature).getLvl() * 3, 3);
				crExpBar.get(i).setSize(Gdx.graphics.getWidth() / 3.827751196f, Gdx.graphics.getHeight() / 24 * (currentExp / fullExp));
				
				// BITMAP FONTS
				
				// creature name
				crName.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				// creature level
				crLv.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				// health
				crHp.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				// health percentage
				crHpPer.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				// experience
				crExp.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				// experience percentage
				crExpPer.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
			}
		}
	}
	private static void disposeCreaturePart(){
		// background
		for (int i = 0; i < creatureBg.size(); i++)
			creatureBg.get(i).getTexture().dispose();
		creatureBg.clear();
		
		// Health bar background
		for (int i = 0; i < crBarBg4Hp.size(); i++)
			crBarBg4Hp.get(i).getTexture().dispose();
		crBarBg4Hp.clear();
		
		// Experience bar background
		for (int i = 0; i < crBarBg4Exp.size(); i++)
			crBarBg4Exp.get(i).getTexture().dispose();
		crBarBg4Exp.clear();
		
		// Health Bar
		for (int i = 0; i < crHpBar.size(); i++)
			crHpBar.get(i).getTexture().dispose();
		crHpBar.clear();
		
		// Experience bar
		for (int i = 0; i < crExpBar.size(); i++)
			crExpBar.get(i).getTexture().dispose();
		crExpBar.clear();
		
		// BITMAP FONTS
		
		// creature name
		for (int i = 0; i < crName.size(); i++)
			crName.get(i).dispose();
		crName.clear();
		
		// creature level
		for (int i = 0; i < crLv.size(); i++)
			crLv.get(i).dispose();
		crLv.clear();
		
		// health
		for (int i = 0; i < crHp.size(); i++)
			crHp.get(i).dispose();
		crHp.clear();
		
		// health percentage
		for (int i = 0; i < crHpPer.size(); i++)
			crHpPer.get(i).dispose();
		crHpPer.clear();
		
		// experience
		for (int i = 0; i < crExp.size(); i++)
			crExp.get(i).dispose();
		crExp.clear();
		
		// experience percentage
		for (int i = 0; i < crExpPer.size(); i++)
			crExpPer.get(i).dispose();
		crExpPer.clear();
	}
	private static void createDescriptionPart(){
		if (selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size())
		{
			creatureDescription = new ArrayList <BitmapFont>();
			creatureAp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
			creatureDef = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
			creatureAg = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
			creatureImage = new Sprite(new Texture("continue/creatures/images/" + creatures().get(selectedCreature).getName() + "/inventory.gif"));
			creatureImage.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 1.714285714f);
			creatureDescr = NewGame.splittingDescribtion(creatures().get(selectedCreature).getDescribtion(), creatureAp, Gdx.graphics.getWidth() / 3.827751196f);
			for (int i = 0; i < creatureDescr.size(); i++)
			{
				creatureDescription.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
			}
		}
	}
	private static void disposeDescriptionPart(){
		if ((selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size()))
		{
			// creature power
			creatureAp.dispose();
			
			// creature defence
			creatureDef.dispose();
			
			// creature agility
			creatureAg.dispose();
			
			// creature image
			creatureImage.getTexture().dispose();
			
			// creature description text
			creatureDescr.clear();
	
			// creature description bitmap font
			for (int i = 0; i < creatureDescription.size(); i++)
				creatureDescription.get(i).dispose();
		}
	}
	private static void createSkillPart(){
		if (selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size())
		{
			skillBg = new ArrayList <Sprite>();
			skillName = new ArrayList <BitmapFont>();
			skillType = new ArrayList <BitmapFont>();
			skillDescr = new ArrayList <ArrayList <BitmapFont>>();
			skillDescrText = new ArrayList <ArrayList <String>>();
			skillAp = new ArrayList <BitmapFont>();
			for (int i = 0; i < nDisplayedSkills; i++)
			{
				int currentSkill = i + skillLayer * nDisplayedSkills;
				if (currentSkill < creatures().get(selectedCreature).getActiveSkillN())
				{
					skillBg.add(new Sprite(new Texture("continue/inventory/creatureBg.gif")));
					if (i == 0)
						skillBg.get(i).setPosition(Gdx.graphics.getWidth() / 1.449275362f, Gdx.graphics.getHeight() / 2.727272727f);
					else
						skillBg.get(i).setPosition(Gdx.graphics.getWidth() / 1.449275362f, skillBg.get(i-1).getY() - Gdx.graphics.getHeight() / 3.902439024f);
					skillBg.get(i).setSize(Gdx.graphics.getWidth() / 3.603603604f, Gdx.graphics.getHeight() / 4.173913043f);
					
					skillName.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					skillType.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					skillDescrText.add(NewGame.splittingDescribtion(creatures().get(selectedCreature).getActiveSkillByIndex(currentSkill).getDescribtion(), skillName.get(i), Gdx.graphics.getWidth() / 4.210526316f));
					skillDescr.add(new ArrayList <BitmapFont>());
					for (int j = 0; j < skillDescrText.get(i).size(); j++)
					{
						skillDescr.get(i).add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					}
					skillAp.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				}
			}
		}
	}
	private static void disposeSkillPart(){
		if ((selectedCreature + creatureLayer * nDisplayedCreatures < creatures().size()))
		{
			// background
			for (int i = 0; i < skillBg.size(); i++)
				skillBg.get(i).getTexture().dispose();
			skillBg.clear();
			
			// name
			for (int i = 0; i < skillName.size(); i++)
				skillName.get(i).dispose();
			skillName.clear();
			
			// type
			for (int i = 0; i < skillType.size(); i++)
				skillType.get(i).dispose();
			skillType.clear();
			
			// description text
			for (int i = 0; i < skillDescrText.size(); i++)
				skillDescrText.get(i).clear();
			skillDescrText.clear();
			
			// description bitmap font
			for (int i = 0; i < skillDescr.size(); i++){
				for (int j = 0; j < skillDescr.get(i).size(); j++)
					skillDescr.get(i).get(j).dispose();
				skillDescr.get(i).clear();
			}
			skillDescr.clear();
			
			// power
			for (int i = 0; i < skillAp.size(); i++)
				skillAp.get(i).dispose();
			skillAp.clear();
		}
	}

	// positions and sizes of arrows
	public static Vector2 getCreatureUpPos(){
		return new Vector2(creatureUp.getX(), creatureUp.getY());
	}
	public static Vector2 getCreatureDownPos(){
		return new Vector2(creatureDown.getX(), creatureDown.getY());
	}
	public static Vector2 getSkillUpPos(){
		return new Vector2(skillUp.getX(), skillUp.getY());
	}
	public static Vector2 getSkillDownPos(){
		return new Vector2(skillDown.getX(), skillDown.getY());
	}
	public static Vector2 getCreatureUpSize(){
		return new Vector2(creatureUp.getWidth(), creatureUp.getHeight());
	}
	public static Vector2 getCreatureDownSize(){
		return new Vector2(creatureDown.getWidth(), creatureDown.getHeight());
	}
	public static Vector2 getSkillUpSize(){
		return new Vector2(skillUp.getWidth(), skillUp.getHeight());
	}
	public static Vector2 getSkillDownSize(){
		return new Vector2(skillDown.getWidth(), skillDown.getHeight());
	}

	// creatures backgrounds positions and sizes
	public static ArrayList <Vector2> getCreatureBgPos(){
		ArrayList <Vector2> pos = new ArrayList <Vector2>();
		
		for (int i = 0; i < creatureBg.size(); i++)
			pos.add(new Vector2(creatureBg.get(i).getX(), creatureBg.get(i).getY()));
		
		return pos;
	}
	public static ArrayList <Vector2> getCreatureBgSize(){
		ArrayList <Vector2> size = new ArrayList <Vector2>();
		
		for (int i = 0; i < creatureBg.size(); i++)
			size.add(new Vector2(creatureBg.get(i).getWidth(), creatureBg.get(i).getHeight()));
		
		return size;
	}
}
