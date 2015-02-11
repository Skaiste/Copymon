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
import com.copymon.creatures.Skill;

// Switch creatures or skills
public class SwitchCorS {
	
	private static boolean show = false,
						   once = true,
						   isSkills = true;

	// if skills are displayed Creature is used
	private static Creature creature;
	// is creatures are displayed PlayerCreatures is used
	private static PlayerCreatures playerCreatures;
	
	// for transparent background
	private static ShapeRenderer bg;
	// batch
	private static SpriteBatch batch;
	// screen background, left and right buttons
	private static Sprite background, left, right;
	
	// number of units (inactive and active skills or creatures) are displayed on screen
	final private static int nDisplayedUnits = 2;
	
	// 		INACTIVE LIST
	private static ArrayList <Sprite> inactiveBg;
	private static ArrayList <BitmapFont> inactiveName, inactiveType, inactivePower;
	//	for skills
	private static ArrayList <ArrayList <BitmapFont>> inactiveSdescr;
	private static ArrayList <ArrayList <String>> inactiveSdescription;
	//	for creatures
	private static ArrayList <BitmapFont> inactiveClvl, inactiveCdef, inactiveCag;
	// layer
	private static int inactiveLayer = 0, inactiveSelectedUnit = 0;
	
	//		ACTIVE LIST
	private static ArrayList <Sprite> activeBg;
	private static ArrayList <BitmapFont> activeName, activeType, activePower;
	//	for skills
	private static ArrayList <ArrayList <BitmapFont>> activeSdescr;
	private static ArrayList <ArrayList <String>> activeSdescription;
	//	for creatures
	private static ArrayList <BitmapFont> activeClvl, activeCdef, activeCag;
	// layer
	private static int activeLayer = 0, activeSelectedUnit = 0;
	
	//		Arrows
	private static Sprite inactiveUp, inactiveDown, activeUp, activeDown;
	
	
	
	public static void update() {
		
		show = Continue.getPlayer().switchSorC;
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
	
	private static void show(){
		// transparent background
		bg = new ShapeRenderer();
		batch = new SpriteBatch();
		
		// background
		if (isSkills)
			background = new Sprite(new Texture("continue/inventory/changeSkills.gif"));
		else
			background = new Sprite(new Texture("continue/inventory/changeCreatures.gif"));
		
		// 		ARROWS
		inactiveUp = new Sprite(new Texture("continue/inventory/arrowUp.gif"));
		inactiveDown = new Sprite(new Texture("continue/inventory/arrowDown.gif"));
		activeUp = new Sprite(new Texture("continue/inventory/arrowUp.gif"));
		activeDown = new Sprite(new Texture("continue/inventory/arrowDown.gif"));
		left = new Sprite(new Texture("continue/inventory/left.gif"));
		right = new Sprite(new Texture("continue/inventory/right.gif"));
		//	Size
		float width = Gdx.graphics.getWidth() / 21.05263158f, height = Gdx.graphics.getHeight() / 24;
		inactiveUp.setSize(width, height);
		inactiveDown.setSize(width, height);
		activeUp.setSize(width, height);
		activeDown.setSize(width, height);
		width = Gdx.graphics.getWidth() / 5.925925926f; height = Gdx.graphics.getHeight() / 3.902439024f;
		left.setSize(width, height);
		right.setSize(width, height);
		//	Position
		inactiveUp.setPosition(Gdx.graphics.getWidth() / 6.779661017f, Gdx.graphics.getHeight() / 1.589403974f);
		inactiveDown.setPosition(Gdx.graphics.getWidth() / 6.779661017f, Gdx.graphics.getHeight() / 22.85714286f);
		activeUp.setPosition(Gdx.graphics.getWidth() / 1.242236025f, Gdx.graphics.getHeight() / 1.589403974f);
		activeDown.setPosition(Gdx.graphics.getWidth() / 1.242236025f, Gdx.graphics.getHeight() / 22.85714286f);
		left.setPosition(Gdx.graphics.getWidth() / 2.409638554f, Gdx.graphics.getHeight() / 2.474226804f);
		right.setPosition(Gdx.graphics.getWidth() / 2.409638554f, Gdx.graphics.getHeight() / 11.42857143f);
		
		
		// ********************************** INACTIVE LIST *************************************
		createInactiveList();

		// ********************************** ACTIVE LIST *************************************
		createActiveList();
	}
	
	private static void render(){
		// painting transparent background
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		bg.begin(ShapeType.Filled);
		bg.setColor(0, 0, 0, 0.5f);
		bg.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bg.end();
		Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
		
		batch.begin();
		
		background.draw(batch);
		
		//	Arrows
		if (isInactiveUp())
			inactiveUp.draw(batch);
		if (isInactiveDown())
			inactiveDown.draw(batch);
		if (isActiveUp())
			activeUp.draw(batch);
		if (isActiveDown())
			activeDown.draw(batch);
		if (isLeft())
			left.draw(batch);
		if (isRight())
			right.draw(batch);

		// ********************************** INACTIVE LIST *************************************
		for (int i = 0; i < nDisplayedUnits; i++)
		{
			// current unit
			int currentUnit = i + inactiveLayer * nDisplayedUnits;
			// allowed size
			int aSize = 0;
			if (isSkills)
				aSize = creature.getInactiveSkillN();
			else
				aSize = playerCreatures.getInactiveCreatureN();
			if (currentUnit < aSize)
			{
				// background
				inactiveBg.get(i).draw(batch);
				
				// ******** FOR SKILLS ********
				if (isSkills)
				{
					Skill s = creature.getInactiveSkillByIndex(currentUnit);
					
					// name
					inactiveName.get(i).draw(batch, s.getDisplayName(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 4.285714286f);
					// type
					inactiveType.get(i).draw(batch, "Type: " + s.getType(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 5.274725275f);
					// description
					for (int j = 0; j < inactiveSdescr.get(i).size(); j++)
					{
						inactiveSdescr.get(i).get(j).draw(batch, inactiveSdescription.get(i).get(j), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 6.486486486f - (Gdx.graphics.getHeight() / 28.23529412f) * j);
					}
					// power
					inactivePower.get(i).draw(batch, "Power: " + s.getPower(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 20.86956522f);				
				}

				// ******** FOR CREATURES ********
				else {
					Creature c = playerCreatures.getInactiveCreatures().get(currentUnit);
					
					// name
					inactiveName.get(i).draw(batch, c.getRealName(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 4.285714286f);
					// level
					inactiveClvl.get(i).draw(batch, "Level: " + c.getLvl(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 5.274725275f);
					// type
					inactiveType.get(i).draw(batch, "Type: " + c.getType(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 6.153846154f);
					// power
					inactivePower.get(i).draw(batch, "Power: " + c.getAp(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 7.868852459f);
					// defence
					inactiveCdef.get(i).draw(batch, "Defence: " + c.getDefence(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 10.90909091f);
					// agility
					inactiveCag.get(i).draw(batch, "Agility: " + c.getAgility(), inactiveBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, inactiveBg.get(i).getY() + Gdx.graphics.getHeight() / 17.77777778f);
				}
			}
		}
		
		// ********************************** ACTIVE LIST *************************************
		for (int i = 0; i < nDisplayedUnits; i++)
		{
			// current unit
			int currentUnit = i + activeLayer * nDisplayedUnits;
			// allowed size
			int aSize = 0;
			if (isSkills)
				aSize = creature.getActiveSkillN();
			else
				aSize = playerCreatures.getActiveCreatureN();
			if (currentUnit < aSize)
			{
				// background
				activeBg.get(i).draw(batch);
				
				// ******** FOR SKILLS ********
				if (isSkills)
				{
					Skill s = creature.getActiveSkillByIndex(currentUnit);
					
					// name
					activeName.get(i).draw(batch, s.getDisplayName(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 4.285714286f);
					// type
					activeType.get(i).draw(batch, "Type: " + s.getType(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 5.274725275f);
					// description
					for (int j = 0; j < activeSdescr.get(i).size(); j++)
					{
						activeSdescr.get(i).get(j).draw(batch, activeSdescription.get(i).get(j), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 6.486486486f - (Gdx.graphics.getHeight() / 28.23529412f) * j);
					}
					// power
					activePower.get(i).draw(batch, "Power: " + s.getPower(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 20.86956522f);				
				}

				// ******** FOR CREATURES ********
				else {
					Creature c = playerCreatures.getActiveCreatures().get(currentUnit);
					
					// name
					activeName.get(i).draw(batch, c.getRealName(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 4.285714286f);
					// level
					activeClvl.get(i).draw(batch, "Level: " + c.getLvl(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 5.274725275f);
					// type
					activeType.get(i).draw(batch, "Type: " + c.getType(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 6.153846154f);
					// power
					activePower.get(i).draw(batch, "Power: " + c.getAp(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 7.868852459f);
					// defence
					activeCdef.get(i).draw(batch, "Defence: " + c.getDefence(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 10.90909091f);
					// agility
					activeCag.get(i).draw(batch, "Agility: " + c.getAgility(), activeBg.get(i).getX() + Gdx.graphics.getWidth() / 133.3333333f, activeBg.get(i).getY() + Gdx.graphics.getHeight() / 17.77777778f);
				}
			}
		}
		
		batch.end();
		
	}
	
	private static void dispose(){
		bg.dispose();
		background.getTexture().dispose();
		batch.dispose();
		
		//	Arrows
		inactiveUp.getTexture().dispose();
		inactiveDown.getTexture().dispose();
		activeUp.getTexture().dispose();
		activeDown.getTexture().dispose();
		left.getTexture().dispose();
		right.getTexture().dispose();

		// ********************************** INACTIVE LIST *************************************
		disposeInactiveList();

		// ********************************** ACTIVE LIST *************************************
		disposeActiveList();
		
	}
	
	public static void setIsSkills(boolean isIt){
		isSkills = isIt;
	}
	public static void setCreature(Creature c){
		creature = c;
	}
	public static void setPlayerCreatures(PlayerCreatures pc){
		playerCreatures = pc;
	}
	
	public static void selectInactive(int index){
		// selected unit
		int selectedUnit = index + inactiveLayer * nDisplayedUnits;
		// allowed size
		int aSize = 0;
		if (isSkills)
			aSize = creature.getInactiveSkillN();
		else
			aSize = playerCreatures.getInactiveCreatureN();
		
		// if the selection is valid
		if ((index < nDisplayedUnits) && (selectedUnit < aSize)){
			inactiveSelectedUnit = selectedUnit;
			updateInactiveList();
		}
	}
	public static void selectActive(int index){
		// selected unit
		int selectedUnit = index + activeLayer * nDisplayedUnits;
		// allowed size
		int aSize = 0;
		if (isSkills)
			aSize = creature.getActiveSkillN();
		else
			aSize = playerCreatures.getActiveCreatureN();
		
		// if the selection is valid
		if ((index < nDisplayedUnits) && (selectedUnit < aSize)){
			activeSelectedUnit = selectedUnit;
			updateActiveList();
		}
	}
	
	public static void goInactiveUp(){
		if (isInactiveUp()){
			// go to lower layer
			inactiveLayer--;
			// update the inactive list 
			updateInactiveList();
		}
	}
	public static void goInactiveDown(){
		if (isInactiveDown()){
			// go to upper layer
			inactiveLayer++;
			// update the inactive list
			updateInactiveList();
		}
	}
	public static void goActiveUp(){
		if (isActiveUp()){
			// go to lower layer
			activeLayer--;
			// update the active list
			updateActiveList();
		}
	}
	public static void goActiveDown(){
		if (isActiveDown()){
			// go to upper layer
			activeLayer++;
			// update the active list
			updateActiveList();
		}
	}
	
	// from active to inactive
	public static void toLeft(){
		if (isSkills && isLeft() && (activeSelectedUnit < creature.getActiveSkillN()))
		{
			creature.addSkillFromActiveToInactive(activeSelectedUnit);
			
			// if the active unit is alone in one layer
			if ((isSkills && (creature.getActiveSkillN() / nDisplayedUnits == activeLayer) && (creature.getActiveSkillN() % nDisplayedUnits == 0)) ||
					(!isSkills && (playerCreatures.getActiveCreatureN() / nDisplayedUnits == activeLayer) && (playerCreatures.getActiveCreatureN() % nDisplayedUnits == 0))){
				activeLayer--;
				activeSelectedUnit--;
			}				
			
			updateInactiveList();
			updateActiveList();
		}
	}
	// from inactive to active
	public static void toRight(){
		if (isSkills && isRight() && (inactiveSelectedUnit < creature.getInactiveSkillN()))
		{
			creature.addSkillFromInactiveToActive(inactiveSelectedUnit);
			
			// if the inactive unit is alone in one layer
			System.out.println();
			if ((isSkills && (creature.getInactiveSkillN() / nDisplayedUnits == inactiveLayer) && (creature.getInactiveSkillN() % nDisplayedUnits == 0)) ||
				(!isSkills && (playerCreatures.getInactiveCreatureN() / nDisplayedUnits == inactiveLayer) && (playerCreatures.getInactiveCreatureN() % nDisplayedUnits == 0))){
				inactiveLayer--;
				inactiveSelectedUnit--;
			}	
			
			updateInactiveList();
			updateActiveList();
		}
	}
	
	private static void updateInactiveList(){
		// Dispose all elements
		disposeInactiveList();
		
		// Create all elements
		createInactiveList();
	}
	private static void updateActiveList(){
		// Dispose all elements
		disposeActiveList();
		
		// Create all elements
		createActiveList();
	}
	
	private static void createInactiveList(){
		// ********************************** INACTIVE LIST *************************************
		inactiveBg = new ArrayList<Sprite>();
		inactiveName = new ArrayList<BitmapFont>(); 
		inactiveType = new ArrayList<BitmapFont>(); 
		inactivePower = new ArrayList<BitmapFont>();
		if (isSkills){
			inactiveSdescr = new ArrayList<ArrayList<BitmapFont>>();
			inactiveSdescription = new ArrayList<ArrayList<String>>();
		}
		else {
			inactiveClvl = new ArrayList<BitmapFont>(); 
			inactiveCdef = new ArrayList<BitmapFont>(); 
			inactiveCag = new ArrayList<BitmapFont>();
		}
		for (int i = 0; i < nDisplayedUnits; i++)
		{
			// current unit
			int currentUnit = i + inactiveLayer * nDisplayedUnits;
			// allowed size
			int aSize = 0;
			if (isSkills)
				aSize = creature.getInactiveSkillN();
			else
				aSize = playerCreatures.getInactiveCreatureN();
			
			if (currentUnit < aSize)
			{
				// background for description
				String bgPath = "continue/inventory/creatureBg.gif";
				if (inactiveSelectedUnit == currentUnit)
					bgPath = "continue/inventory/selectedCreatureBg.gif";
				inactiveBg.add(new Sprite(new Texture(bgPath)));
				inactiveBg.get(i).setSize(Gdx.graphics.getWidth() / 3.603603604f, Gdx.graphics.getHeight() / 4.173913043f);
				if (i == 0)
					inactiveBg.get(i).setPosition(Gdx.graphics.getWidth() / 30.76923077f, Gdx.graphics.getHeight() / 2.727272727f);
				else
					inactiveBg.get(i).setPosition(Gdx.graphics.getWidth() / 30.76923077f, inactiveBg.get(i-1).getY() - Gdx.graphics.getHeight() / 3.902439024f);
				
				//	******* FOR SKILLS AND CREATURES *******
				// name
				inactiveName.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				inactiveName.get(i).setScale(1.20f);
				
				// type
				inactiveType.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				
				// power
				inactivePower.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				
				// ******** FOR SKILLS ONLY ********
				if (isSkills) {
					// description
					inactiveSdescription.add(NewGame.splittingDescribtion(creature.getInactiveSkillByIndex(i).getDescribtion(), inactiveType.get(i), Gdx.graphics.getWidth() / 4.210526316f));
					inactiveSdescr.add(new ArrayList<BitmapFont>());
					for (int j = 0; j < inactiveSdescription.get(i).size(); j++)
					{
						inactiveSdescr.get(i).add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					}
				}
				// ******** FOR CREATURES ONLY ********
				else {
					// level
					inactiveClvl.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					
					// defence
					inactiveCdef.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					
					// agility
					inactiveCag.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				}
			}
		}
	}
	private static void disposeInactiveList(){
		//		background
		for (int i = 0; i < inactiveBg.size(); i++)
			inactiveBg.get(i).getTexture().dispose();
		inactiveBg.clear();
		//		name
		for (int i = 0; i < inactiveName.size(); i++)
			inactiveName.get(i).dispose();
		inactiveName.clear();
		//		type
		for (int i = 0; i < inactiveType.size(); i++)
			inactiveType.get(i).dispose();
		inactiveType.clear();
		//		power
		for (int i = 0; i < inactivePower.size(); i++)
			inactivePower.get(i).dispose();
		inactivePower.clear();

		// ******** FOR SKILLS ********
		if (isSkills){
			// 		description text
			for (int i = 0; i < inactiveSdescription.size(); i++)
				inactiveSdescription.get(i).clear();
			inactiveSdescription.clear();
			// 		description bitmap font
			for (int i = 0; i < inactiveSdescr.size(); i++){
				for (int j = 0; j < inactiveSdescr.get(i).size(); j++)
					inactiveSdescr.get(i).get(j).dispose();
				inactiveSdescr.get(i).clear();
			}
			inactiveSdescr.clear();
		}
		
		// ******** FOR CREATURES ********
		else {
			//		level
			for (int i = 0; i < inactiveClvl.size(); i++)
				inactiveClvl.get(i).dispose();
			inactiveClvl.clear();
			//		defence
			for (int i = 0; i < inactiveCdef.size(); i++)
				inactiveCdef.get(i).dispose();
			inactiveCdef.clear();
			//		agility
			for (int i = 0; i < inactiveCag.size(); i++)
				inactiveCag.get(i).dispose();
			inactiveCag.clear();
		}
	}
	private static void createActiveList(){
		// ********************************** ACTIVE LIST *************************************
		activeBg = new ArrayList<Sprite>();
		activeName = new ArrayList<BitmapFont>(); 
		activeType = new ArrayList<BitmapFont>(); 
		activePower = new ArrayList<BitmapFont>();
		if (isSkills){
			activeSdescr = new ArrayList<ArrayList<BitmapFont>>();
			activeSdescription = new ArrayList<ArrayList<String>>();
		}
		else {
			activeClvl = new ArrayList<BitmapFont>(); 
			activeCdef = new ArrayList<BitmapFont>(); 
			activeCag = new ArrayList<BitmapFont>();
		}
		for (int i = 0; i < nDisplayedUnits; i++)
		{
			// current unit
			int currentUnit = i + activeLayer * nDisplayedUnits;
			// allowed size
			int aSize = 0;
			if (isSkills)
				aSize = creature.getActiveSkillN();
			else
				aSize = playerCreatures.getActiveCreatureN();
			
			if (currentUnit < aSize)
			{
				// background for description
				String bgPath = "continue/inventory/creatureBg.gif";
				if (activeSelectedUnit == currentUnit)
					bgPath = "continue/inventory/selectedCreatureBg.gif";
				activeBg.add(new Sprite(new Texture(bgPath)));
				activeBg.get(i).setSize(Gdx.graphics.getWidth() / 3.603603604f, Gdx.graphics.getHeight() / 4.173913043f);
				if (i == 0)
					activeBg.get(i).setPosition(Gdx.graphics.getWidth() / 1.449275362f, Gdx.graphics.getHeight() / 2.727272727f);
				else
					activeBg.get(i).setPosition(Gdx.graphics.getWidth() / 1.449275362f, activeBg.get(i-1).getY() - Gdx.graphics.getHeight() / 3.902439024f);

				//	******* FOR SKILLS AND CREATURES *******
				// name
				activeName.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				activeName.get(i).setScale(1.20f);
				
				// type
				activeType.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				
				// power
				activePower.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));

				// ******** FOR SKILLS ONLY ********
				if (isSkills) {
					// description
					activeSdescription.add(NewGame.splittingDescribtion(creature.getActiveSkillByIndex(i).getDescribtion(), activeType.get(i), Gdx.graphics.getWidth() / 4.210526316f));
					activeSdescr.add(new ArrayList<BitmapFont>());
					for (int j = 0; j < activeSdescription.get(i).size(); j++)
					{
						activeSdescr.get(i).add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					}
				}
				// ******** FOR CREATURES ONLY ********
				else {
					// level
					activeClvl.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					
					// defence
					activeCdef.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
					
					// agility
					activeCag.add(new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false));
				}
			}
		}
	}
	private static void disposeActiveList(){
		// ********************************** ACTIVE LIST *************************************
		//		background
		for (int i = 0; i < activeBg.size(); i++)
			activeBg.get(i).getTexture().dispose();
		activeBg.clear();
		//		name
		for (int i = 0; i < activeName.size(); i++)
			activeName.get(i).dispose();
		activeName.clear();
		//		type
		for (int i = 0; i < activeType.size(); i++)
			activeType.get(i).dispose();
		activeType.clear();
		//		power
		for (int i = 0; i < activePower.size(); i++)
			activePower.get(i).dispose();
		activePower.clear();

		// ******** FOR SKILLS ********
		if (isSkills){
			// 		description text
			for (int i = 0; i < activeSdescription.size(); i++)
				activeSdescription.get(i).clear();
			activeSdescription.clear();
			// 		description bitmap font
			for (int i = 0; i < activeSdescr.size(); i++){
				for (int j = 0; j < activeSdescr.get(i).size(); j++)
					activeSdescr.get(i).get(j).dispose();
				activeSdescr.get(i).clear();
			}
			activeSdescr.clear();
		}
		// ******** FOR CREATURES ********
		else {
			//		level
			for (int i = 0; i < activeClvl.size(); i++)
				activeClvl.get(i).dispose();
			activeClvl.clear();
			//		defence
			for (int i = 0; i < activeCdef.size(); i++)
				activeCdef.get(i).dispose();
			activeCdef.clear();
			//		agility
			for (int i = 0; i < activeCag.size(); i++)
				activeCag.get(i).dispose();
			activeCag.clear();
		}
	}
	
	public static boolean isInactiveUp(){
		boolean is = false;
		
		if (isSkills && (inactiveLayer > 0) && (creature.getInactiveSkillN() != 0))
			is = true;
		else if (!isSkills && (inactiveLayer > 0) && (playerCreatures.getInactiveCreatureN() != 0))
			is = true;
		
		return is;
	}
	public static boolean isInactiveDown(){
		boolean is = false;
		
		if (isSkills && (creature.getInactiveSkillN() > nDisplayedUnits * (inactiveLayer + 1)) && (creature.getInactiveSkillN() != 0))
			is = true;
		else if (!isSkills && (playerCreatures.getInactiveCreatureN() > nDisplayedUnits * (inactiveLayer + 1)) && (playerCreatures.getInactiveCreatureN() != 0))
			is = true;
		
		return is;
	}
	public static boolean isActiveUp(){
		boolean is = false;
		
		if (isSkills && (activeLayer > 0) && (creature.getActiveSkillN() != 0))
			is = true;
		else if (!isSkills && (activeLayer > 0) && (playerCreatures.getActiveCreatureN() != 0))
			is = true;
		
		return is;
	}
	public static boolean isActiveDown(){
		boolean is = false;
		
		if (isSkills && (creature.getActiveSkillN() > nDisplayedUnits * (activeLayer + 1)) && (creature.getActiveSkillN() != 0))
			is = true;
		else if (!isSkills && (playerCreatures.getActiveCreatureN() > nDisplayedUnits * (activeLayer + 1)) && (playerCreatures.getActiveCreatureN() != 0))
			is = true;
		
		return is;
	}
	public static boolean isRight(){
		boolean is = false;
		
		if ((isSkills) && (creature.getActiveSkillN() < 4) && (creature.getInactiveSkillN() > 0))
			is = true;
		else if (!isSkills && (playerCreatures.getActiveCreatureN() < 6) && (playerCreatures.getInactiveCreatureN() > 0))
			is = true;
		
		return is;
	}
	public static boolean isLeft(){
		boolean is = false;
		
		if ((isSkills) && (creature.getActiveSkillN() > 0))
			is = true;
		else if (!isSkills && (playerCreatures.getActiveCreatureN() > 0))
			is = true;
		
		return is;
	}

	// arrow positions
	public static Vector2 getInactiveUpPos(){
		return new Vector2(inactiveUp.getX(), inactiveUp.getY());
	}
	public static Vector2 getInactiveDownPos(){
		return new Vector2(inactiveDown.getX(), inactiveDown.getY());
	}
	public static Vector2 getActiveUpPos(){
		return new Vector2(activeUp.getX(), activeUp.getY());
	}
	public static Vector2 getActiveDownPos(){
		return new Vector2(activeDown.getX(), activeDown.getY());
	}
	public static Vector2 getLeftPos(){
		return new Vector2(left.getX(), left.getY());
	}
	public static Vector2 getRightPos(){
		return new Vector2(right.getX(), right.getY());
	}
	// arrow sizes
	public static Vector2 getInactiveUpSize(){
		return new Vector2(inactiveUp.getWidth(), inactiveUp.getHeight());
	}
	public static Vector2 getInactiveDownSize(){
		return new Vector2(inactiveDown.getWidth(), inactiveDown.getHeight());
	}
	public static Vector2 getActiveUpSize(){
		return new Vector2(activeUp.getWidth(), activeUp.getHeight());
	}
	public static Vector2 getActiveDownSize(){
		return new Vector2(activeDown.getWidth(), activeDown.getHeight());
	}
	public static Vector2 getLeftSize(){
		return new Vector2(left.getWidth(), left.getHeight());
	}
	public static Vector2 getRightSize(){
		return new Vector2(right.getWidth(), right.getHeight());
	}

	// inactive and active backgrounds positions and sizes
	public static ArrayList <Vector2> getInactiveBgPos(){
		ArrayList <Vector2> pos = new ArrayList <Vector2>();
		
		for (int i = 0; i < inactiveBg.size(); i++)
		{
			pos.add(new Vector2(inactiveBg.get(i).getX(), inactiveBg.get(i).getY()));
		}
		
		return pos;
	}
	public static ArrayList <Vector2> getInactiveBgSize(){
		ArrayList <Vector2> pos = new ArrayList <Vector2>();
		
		for (int i = 0; i < inactiveBg.size(); i++)
		{
			pos.add(new Vector2(inactiveBg.get(i).getWidth(), inactiveBg.get(i).getHeight()));
		}
		
		return pos;
	}
	public static ArrayList <Vector2> getActiveBgPos(){
		ArrayList <Vector2> pos = new ArrayList <Vector2>();
		
		for (int i = 0; i < activeBg.size(); i++)
		{
			pos.add(new Vector2(activeBg.get(i).getX(), activeBg.get(i).getY()));
		}
		
		return pos;
	}
	public static ArrayList <Vector2> getActiveBgSize(){
		ArrayList <Vector2> pos = new ArrayList <Vector2>();
		
		for (int i = 0; i < activeBg.size(); i++)
		{
			pos.add(new Vector2(activeBg.get(i).getWidth(), activeBg.get(i).getHeight()));
		}
		
		return pos;
	}
}
