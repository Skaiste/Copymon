package com.copymon.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.copymon.creatures.Creature;
import com.copymon.creatures.Skill;
import com.copymon.fileHandling.ReadFromXml;
import com.copymon.screens.Camera;
import com.copymon.screens.Continue;
import com.copymon.screens.CreatureHere;
import com.copymon.screens.Fighting;
import com.copymon.screens.Inventory;
import com.copymon.screens.Map;
import com.copymon.screens.Menu;
import com.copymon.screens.Play;
import com.copymon.screens.PlayingMenu;
import com.copymon.screens.SwitchCorS;

public class Player extends Sprite implements InputProcessor {

	// ************************ VARIABLES ************************ //
	// the movement velocity
	private Vector2 velocity = new Vector2();
	
	// speed and gravity of the player and screen
	public float speed   = 120 * 2, gravity = 120 * 1.8f;
	
	// collision layer and key of blocked tile property
	private static TiledMapTileLayer collisionLayer, computerLayer;
	final private String blockedKey = "blocked";
	// tile size
	private float tileWidth, tileHeight;
	// map
	private Map map = Continue.getMap();
	final private int enteringDelayTime = 300;
	
	// camera
	private Camera camera = Play.getCamera();
	
	// determines if the player is able to jump
	private boolean jumped = false;
	
	// speed of the player animation
	final private int animationSpeed = 7;
	// counting for animation
	private int playerAnimation = animationSpeed;
	// is player going to left
	private boolean isLeft = false;
	// player gender
	private static String gender;
	// was the back button pressed
	public boolean goBack = false;
	// has the game began
	public boolean startPlaying;
	
	// inventory menu
	public boolean menu = false;
	public boolean inventory = false, switchSorC = false, computerOn = false, canHeal = false;
	
	// for fighting
	// creature appearing chance, 1 out of appearChance
	private int appearChance;
	// has creature appeared
	private static boolean creatureAppeared = false;
	
	// ************************************************************ //
	
	public Player(TiledMapTileLayer colLayer, TiledMapTileLayer compLayer, float x, float y, String genderr) {
		super(new Texture(genderr + "right/1.gif"));
		gender = genderr;
		collisionLayer = colLayer;
		computerLayer = compLayer;
		tileHeight = collisionLayer.getTileHeight();
		tileWidth  = collisionLayer.getTileWidth();
		setPosition(x, y);
		setSize(getTexture().getWidth(), getTexture().getHeight());
		startPlaying = true;
		
		Preferences prefs = Gdx.app.getPreferences("Copymon Options");
		appearChance = prefs.getInteger("creature appearing chance");
	}

	public void update(float delta) {
		
		//if (velocity.x != 0)
		//	System.out.println(getX() + "/" + getY());
		
		collisionLayer = map.getCollisionLayer();
		computerLayer = map.getComputerLayer();
		
		if (velocity.x < 0)
			isLeft = true;
		else if (velocity.x > 0)
			isLeft = false;
		
		// make player move by animation
		playerAnimation();
		
		// save old positions
		float oldX = getX(),
			  oldY = getY(),
			  oldCameraX = camera.getX(),
			  oldCameraY = camera.getY();
		
		// is there collision
		boolean collisionX = false,
				collisionY = false;
		
		// move on x
		moveX(delta);
		
		// check for collision on x
		if (velocity.x < 0)
			collisionX = collidesLeft();
		else if (velocity.x > 0)
			collisionX = collidesRight();
		
		// react to x collision
		if (collisionX || creatureAppeared || inventory || switchSorC || Nurse.getWalking())
		{
			setX(oldX);
			camera.setX(oldCameraX);
			velocity.x = 0;
		}
		
		// move on y
		moveY(delta);
		
		// checking for collision on y
		if (velocity.y > 0)
			collisionY = collidesTop();
		else if (velocity.y < 0)
		{
			collisionY = collidesBottom();
			jumped = collisionY;
		}
		
		// react to y collision
		if (collisionY || creatureAppeared || inventory || switchSorC || Nurse.getWalking())
		{
			setY(oldY);
			camera.setY(oldCameraY);
			velocity.y = 0;
		}
		
		if ((speed < gravity) && (collidesBottom()))
			speed = 120 * 2f;
		
		
		// portals & creature search & computer
		portals();
		if (appearChance == 0)
			creatureAppeared = false;
		else if (velocity.x != 0)
			searchForCreatures();
		computer();
		healingCreatures();
		
		
		// if player gets stuck
		if ((collidesTop() && collidesLeft() && collidesRight()) || (collidesLeft() && collidesRight()))
		{
			Vector2 tmp = map.getCoordinatesWhenBugged();
			setPosition(tmp.x, tmp.y);
		}
		
		if (getY() < 0)
			setY(camera.getHeight() / 2);
		
	}

	// makes the player image change when it moves
	private void playerAnimation() {
		// if the player has jumped
		if (velocity.y > 0)
		{
			if (isLeft)
				setTexture(new Texture(gender + "jump_left.gif"));
			else
				setTexture(new Texture(gender + "jump_right.gif"));
		}
		else
		{
			// left
			if ((velocity.x < 0) && (playerAnimation % animationSpeed == 0))
			{
				setTexture(new Texture(gender + "left/" + (playerAnimation / animationSpeed) + ".gif"));
			}
			// right
			else if ((velocity.x > 0) && (playerAnimation % animationSpeed == 0))
			{
				setTexture(new Texture(gender + "right/" + (playerAnimation / animationSpeed) + ".gif"));
			}
			// standing
			else if (playerAnimation % animationSpeed == 0)
			{
				if (isLeft)
					setTexture(new Texture(gender + "left/1.gif"));
				else
					setTexture(new Texture(gender + "right/1.gif"));
			}
			
			if (playerAnimation == animationSpeed * (animationSpeed + 1) - 1)
				playerAnimation = animationSpeed;
			else
				playerAnimation++;
		}
		setSize(getTexture().getWidth(), getTexture().getHeight());
	}
	
	// movement on x through the map
	private void moveX(float delta) {
		
		// prevents player from going through edges
		if (getX() + velocity.x * delta <= 5)
			setX(5);
		if (getX() + velocity.x * delta >= Continue.getMap().getWidth() - 30)
			setX(Continue.getMap().getWidth() - 30);
		
		// player moves
		if (velocity.x != 0)
			setX(getX() + velocity.x * delta);
		
	}
	// movement on y through the map
	private void moveY(float delta) {
		// apply gravity
		velocity.y -= gravity * 3 * delta;
		// clamp velocity
		if (velocity.y > speed)
			velocity.y = speed;
		else if (velocity.y < -speed)
			velocity.y = -speed;

		
		// prevents from going through edges
		if (getY() + velocity.x * delta <= 5)
			setY(5);
		if (getY() + velocity.x * delta >= Continue.getMap().getHeight() - 50)
			setY(Continue.getMap().getHeight() - 50);
		
		// moves the player according to the velocity
		setY(getY() + velocity.y * 3 * delta);
		
	}

	// checks for collision from different sides
	private boolean collidesLeft()
	{
		for (float step = 0; step < getHeight(); step += tileHeight / 2) {
			if (hasCellProp(getX(), getY() + step, blockedKey, collisionLayer))
				return true;
		}
		return false;
	}
	private boolean collidesRight()
	{
		for (float step = 0; step < getHeight(); step += tileHeight / 2) {
			if (hasCellProp(getX() + getWidth(), getY() + step, blockedKey, collisionLayer))
				return true;
		}
		return false;
	}
	private boolean collidesTop()
	{
		float lastStep = getWidth();
		if (gender.equals("continue/player/boy/"))
			lastStep = getWidth() - tileWidth;
		
		for (float step = 0; step < lastStep; step += tileWidth / 2) {
			if (hasCellProp(getX() + step, getY() + getHeight(), blockedKey, collisionLayer))
				return true;
		}
		return false;
	}
	private boolean collidesBottom()
	{
		for (float step = 0; step < getWidth(); step += tileWidth / 2) {
			if (hasCellProp(getX() + step, getY(), blockedKey, collisionLayer))
				return true;
		}
		return false;
	}
	// checks if cell has a certain property
	private boolean hasCellProp (float x, float y, String key, TiledMapTileLayer layer) {
		Cell cell = layer.getCell((int) (x / layer.getTileWidth()),
										   (int) (y / layer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(key);
	}
	
	private void portals() {
		home();
		lab();
		healthCenter();
		portalToMap();
	}
	private void home() {
		if (map.place.equals("map"))
		{
			if (hasCellProp(getX(), getY(), "home", collisionLayer))
			{
				delay(enteringDelayTime);
				map.place = "home";
				setPosition(240, 160);
				velocity.y = 0;
			}
		}
		else if (map.place.equals("home"))
		{
			if (hasCellProp(getX(), getY(), "map", collisionLayer))
			{
				delay(enteringDelayTime);
				map.place = "map";
				setPosition(40, camera.getHeight() / 2);
				velocity.y = 0;
			}
		}
	}

	private void lab() {
		if(map.mapN == 1)
		{
			if (map.place.equals("map"))
			{
				if (hasCellProp(getX(), getY(), "lab", collisionLayer))
				{
					delay(enteringDelayTime);
					map.place = "lab";
					setPosition(576, 192);
					velocity.y = 0;
				}
			}
			else if (map.place.equals("lab"))
			{
				if (hasCellProp(getX(), getY(), "map", collisionLayer))
				{
					delay(enteringDelayTime);
					map.place = "map";
					setPosition(256, 768);
					velocity.y = 0;
				}
			}
		}
	}
	private void healthCenter() {
		if(map.mapN == 1)
		{
			if (map.place.equals("map"))
			{
				if (hasCellProp(getX(), getY(), "health", collisionLayer))
				{
					delay(enteringDelayTime);
					map.place = "health";
					setPosition(416, 160);
					velocity.y = 0;
				}
			}
			else if (map.place.equals("health"))
			{
				if (hasCellProp(getX(), getY(), "map", collisionLayer))
				{
					delay(enteringDelayTime);
					map.place = "map";
					setPosition(2464, 850);
					velocity.y = 0;
					//speed = 40 * 2f;
				}
			}
		}
	}

	private void portalToMap() {
		if (map.mapN == 1)
		{
			if (hasCellProp(getX(), getY(), "map2", collisionLayer))
			{
				System.out.println("lets go to map 2!!!");
				delay(enteringDelayTime);
				map.mapN = 2;
				map.changeMap();
				setPosition(100, camera.getHeight() / 2);
				velocity.y = 0;
			}
		}
		else if (map.mapN == 2)
		{
			if (hasCellProp(getX(), getY(), "map1", collisionLayer))
			{
				System.out.println("lets go to map 1!!!");
				delay(enteringDelayTime);
				map.mapN = 1;
				map.changeMap();
				setPosition(map.getWidth() - 100, camera.getHeight() / 2);
				velocity.y = 0;
			}
		}
	}
	
	private void searchForCreatures() {
		if (appearChance == 0)
			creatureAppeared = false;
		else if (hasCellProp(getX(), getY(), "grass", collisionLayer) && chanceRandom(appearChance)){
			//checking for grass and bug creatures
			velocity.setZero();
			String whichType = "Grass";
			
			int whichCreature = (int) (Math.random() * ReadFromXml.readInt("continue/creatures/creatureList.xml", whichType, "n") + 1);
			String creatureName = "";
			if (whichCreature > 0)
			{
				creatureName = ReadFromXml.readString("continue/creatures/creatureList.xml", whichType, Integer.toString(whichCreature), "name");
			}
			int lvl = 0;
			if (map.mapN == 2)
			{
				lvl = (int) (Math.random() * 10 + 1);
			}
			Creature c = new Creature(creatureName, lvl, new ArrayList<Skill>(), new ArrayList<Skill>());
			c.getSkillsForBot();
			// put this creature to fight!!!
			CreatureHere.setCreature(c);
			
			creatureAppeared = true;
		}
		else if (hasCellProp(getX(), getY(), "bug", collisionLayer) && chanceRandom(appearChance)){
			//checking for grass and bug creatures
			velocity.setZero();
			String whichType = "Bug";
			
			int whichCreature = (int) (Math.random() * ReadFromXml.readInt("continue/creatures/creatureList.xml", whichType, "n") + 1);
			String creatureName = "";
			if (whichCreature > 0)
			{
				creatureName = ReadFromXml.readString("continue/creatures/creatureList.xml", whichType, Integer.toString(whichCreature), "name");
			}
			int lvl = 0;
			if (map.mapN == 2)
			{
				lvl = (int) (Math.random() * 10 + 1);
			}
			Creature c = new Creature(creatureName, lvl, new ArrayList<Skill>(), new ArrayList<Skill>());
			c.getSkillsForBot();
			// put this creature to fight!!!
			CreatureHere.setCreature(c);
			
			creatureAppeared = true;
		}
		else if (hasCellProp(getX(), getY() - tileHeight, "water", collisionLayer) && chanceRandom(appearChance)){
			//checking for grass and bug creatures
			velocity.setZero();
			String whichType = "Water";

			int whichCreature = (int) (Math.random() * ReadFromXml.readInt("continue/creatures/creatureList.xml", whichType, "n") + 1);
			String creatureName = "";
			if (whichCreature > 0)
			{
				creatureName = ReadFromXml.readString("continue/creatures/creatureList.xml", whichType, Integer.toString(whichCreature), "name");
			}
			int lvl = 0;
			if (map.mapN == 2)
			{
				lvl = (int) (Math.random() * 10 + 1);
			}
			Creature c = new Creature(creatureName, lvl, new ArrayList<Skill>(), new ArrayList<Skill>());
			c.getSkillsForBot();
			// put this creature to fight!!!
			CreatureHere.setCreature(c);
			
			creatureAppeared = true;
		}
	}
	
	private void computer(){
		
		if (computerLayer != null)
		{
			if (hasCellProp(getX(), getY() + computerLayer.getTileHeight(), "computer", computerLayer))
			{
				computerOn = true;
			}
			else if (computerOn)
				computerOn = false;
		}
	}
	
	private void healingCreatures(){
		if (map.place.equals("health") && (getX() < 325) && (getY() < 130) && !Nurse.getWalking())
			canHeal = true;
		else
			canHeal = false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode)
		{
		case Keys.LEFT:
			velocity.x = -speed;
			break;
		case Keys.RIGHT:
			velocity.x = speed;
			break;
		case Keys.UP:
			if (jumped){
				velocity.y = speed;
				jumped = false;
			}
			break;
		case Keys.BACK:
			if (switchSorC)
				switchSorC = false;
			else if (inventory)
				inventory = false;
			else if (menu)
				menu = false;
			else
				goBack = true;
			break;
		case Keys.BACKSPACE:
			if (switchSorC)
				switchSorC = false;
			else if (inventory)
				inventory = false;
			else if (menu)
				menu = false;
			else
				goBack = true;
			break;
		case Keys.MENU:
			menu = true;
			break;
		case Keys.ENTER:
			menu = true;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode)
		{
		case Keys.LEFT:
			velocity.x = 0;
			break;
		case Keys.RIGHT:
			velocity.x = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (creatureAppeared){
			if ((screenX >= CreatureHere.getAvoidPosX()) &&
				(screenX <= CreatureHere.getAvoidPosX() + CreatureHere.getAvoidWidth()) &&
				(screenY >= camera.getHeight() - (CreatureHere.getAvoidPosY() + CreatureHere.getAvoidHeight())) &&
				(screenY <= camera.getHeight() - CreatureHere.getAvoidPosY()))
			{
				creatureAppeared = false;
			}
			else if ((screenX >= CreatureHere.getAttackPosX()) &&
					(screenX <= CreatureHere.getAttackPosX() + CreatureHere.getAttackWidth()) &&
					(screenY >= camera.getHeight() - (CreatureHere.getAttackPosY() + CreatureHere.getAttackHeight())) &&
					(screenY <= camera.getHeight() - CreatureHere.getAttackPosY()))
			{
				// start attacking
				System.out.println("ATTACK!!");
				Continue.dispose();
				Fighting.show();
				Menu.setContinue(false);
				Menu.setFighting(true);
				/* take creature into inventory
				if (Continue.getPlayerCreatures().getActiveCreatures().size() < 6)
					Continue.getPlayerCreatures().addActiveCreature(CreatureHere.getCreature());
				*/
				creatureAppeared = false;
			}
		}
		// Playing menu
		else if (menu){
			if ((screenX >= PlayingMenu.getInventoryX()) &&
				(screenX <= PlayingMenu.getInventoryX() + PlayingMenu.getInventoryWidth()) &&
				(screenY >= Play.getCamera().getHeight() - (PlayingMenu.getInventoryY() + PlayingMenu.getInventoryHeight())) &&
				(screenY <= Play.getCamera().getHeight() - PlayingMenu.getInventoryY()))
			{
					if (!inventory){
						inventory = true;
						Inventory.setItActive(true);
					}
					else{
						switchSorC = true;
						SwitchCorS.setIsSkills(true);
						SwitchCorS.setCreature(Inventory.getSelectedCreature());
					}
			}
			else if (computerOn)
			{
				// creatures left at home
				if ((screenX >= PlayingMenu.getAtHomeX()) &&
					(screenX <= PlayingMenu.getAtHomeX() + PlayingMenu.getAtHomeWidth()) &&
					(screenY >= Play.getCamera().getHeight() - (PlayingMenu.getAtHomeY() + PlayingMenu.getAtHomeHeight())) &&
					(screenY <= Play.getCamera().getHeight() - PlayingMenu.getAtHomeY()))
				{
					inventory = true;
					Inventory.setItActive(false);
				}
				// switch creatures
				else if ((screenX >= PlayingMenu.getSwitchX()) &&
						(screenX <= PlayingMenu.getSwitchX() + PlayingMenu.getSwitchWidth()) &&
						(screenY >= Play.getCamera().getHeight() - (PlayingMenu.getSwitchY() + PlayingMenu.getSwitchHeight())) &&
						(screenY <= Play.getCamera().getHeight() - PlayingMenu.getSwitchY()))
				{
					switchSorC = true;
					SwitchCorS.setIsSkills(false);
				}
			}
			else if (canHeal)
			{
				// creatures left at home
				if ((screenX >= PlayingMenu.getHealX()) &&
					(screenX <= PlayingMenu.getHealX() + PlayingMenu.getHealWidth()) &&
					(screenY >= Play.getCamera().getHeight() - (PlayingMenu.getHealY() + PlayingMenu.getHealHeight())) &&
					(screenY <= Play.getCamera().getHeight() - PlayingMenu.getHealY()))
				{
					Nurse.startWalking();
				}
			}
			menu = false;
		}
		// Inventory
		else if (inventory && !switchSorC) {
			// creature arrow up
			if ((screenX >= Inventory.getCreatureUpPos().x) &&
				(screenX <= Inventory.getCreatureUpPos().x + Inventory.getCreatureUpSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (Inventory.getCreatureUpPos().y + Inventory.getCreatureUpSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - Inventory.getCreatureUpPos().y) && Inventory.isCreatureUpVisible())
			{
				Inventory.goCreatureUp();
			}
			// creature arrow down
			if ((screenX >= Inventory.getCreatureDownPos().x) &&
				(screenX <= Inventory.getCreatureDownPos().x + Inventory.getCreatureDownSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (Inventory.getCreatureDownPos().y + Inventory.getCreatureDownSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - Inventory.getCreatureDownPos().y) && Inventory.isCreatureDownVisible())
			{
				Inventory.goCreatureDown();
			}
			// skill arrow up
			if ((screenX >= Inventory.getSkillUpPos().x) &&
				(screenX <= Inventory.getSkillUpPos().x + Inventory.getSkillUpSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (Inventory.getSkillUpPos().y + Inventory.getSkillUpSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - Inventory.getSkillUpPos().y && Inventory.isSkillUpVisible()))
			{
				Inventory.goSkillUp();
			}
			// skill arrow down
			if ((screenX >= Inventory.getSkillDownPos().x) &&
				(screenX <= Inventory.getSkillDownPos().x + Inventory.getSkillDownSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (Inventory.getSkillDownPos().y + Inventory.getSkillDownSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - Inventory.getSkillDownPos().y) && Inventory.isSkillDownVisible())
			{
				Inventory.goSkillDown();
			}
			// creatures
			for (int i = 0; i < Inventory.getCreatureBgPos().size(); i++)
			{
				if ((screenX >= Inventory.getCreatureBgPos().get(i).x) &&
					(screenX <= Inventory.getCreatureBgPos().get(i).x + Inventory.getCreatureBgSize().get(i).x) &&
					(screenY >= Play.getCamera().getHeight() - (Inventory.getCreatureBgPos().get(i).y + Inventory.getCreatureBgSize().get(i).y)) &&
					(screenY <= Play.getCamera().getHeight() - Inventory.getCreatureBgPos().get(i).y))
				{
					Inventory.selectCreature(i);
				}
			}
		}
		else if (switchSorC){
			//System.out.println("Touch: x: " + screenX + ", y: " + screenY);
			//System.out.println("Arrow: x: " + SwitchCorS.getActiveDownPos().x + "-" + (SwitchCorS.getActiveDownPos().x + SwitchCorS.getActiveDownSize().x) + ", y: " + (Play.getCamera().getHeight() - (SwitchCorS.getActiveDownPos().y + SwitchCorS.getActiveDownSize().y)) + "-" + (Play.getCamera().getHeight() - SwitchCorS.getActiveDownPos().y));
			// inactive arrow up
			if ((screenX >= SwitchCorS.getInactiveUpPos().x) &&
				(screenX <= SwitchCorS.getInactiveUpPos().x + SwitchCorS.getInactiveUpSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getInactiveUpPos().y + SwitchCorS.getInactiveUpSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - SwitchCorS.getInactiveUpPos().y) && SwitchCorS.isInactiveUp())
			{
				SwitchCorS.goInactiveUp();
			}
			// inactive arrow down
			if ((screenX >= SwitchCorS.getInactiveDownPos().x) &&
				(screenX <= SwitchCorS.getInactiveDownPos().x + SwitchCorS.getInactiveDownSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getInactiveDownPos().y + SwitchCorS.getInactiveDownSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - SwitchCorS.getInactiveDownPos().y) && SwitchCorS.isInactiveDown())
			{
				SwitchCorS.goInactiveDown();
			}
			// active arrow up
			if ((screenX >= SwitchCorS.getActiveUpPos().x) &&
				(screenX <= SwitchCorS.getActiveUpPos().x + SwitchCorS.getActiveUpSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getActiveUpPos().y + SwitchCorS.getActiveUpSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - SwitchCorS.getActiveUpPos().y) && SwitchCorS.isActiveUp())
			{
				SwitchCorS.goActiveUp();
			}
			// active arrow down
			if ((screenX >= SwitchCorS.getActiveDownPos().x) &&
				(screenX <= SwitchCorS.getActiveDownPos().x + SwitchCorS.getActiveDownSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getActiveDownPos().y + SwitchCorS.getActiveDownSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - SwitchCorS.getActiveDownPos().y) && SwitchCorS.isActiveDown())
			{
				SwitchCorS.goActiveDown();
			}
			// left arrow
			if ((screenX >= SwitchCorS.getLeftPos().x) &&
				(screenX <= SwitchCorS.getLeftPos().x + SwitchCorS.getLeftSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getLeftPos().y + SwitchCorS.getLeftSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - SwitchCorS.getLeftPos().y) && SwitchCorS.isLeft())
			{
				SwitchCorS.toLeft();
			}
			// right arrow
			if ((screenX >= SwitchCorS.getRightPos().x) &&
				(screenX <= SwitchCorS.getRightPos().x + SwitchCorS.getRightSize().x) &&
				(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getRightPos().y + SwitchCorS.getRightSize().y)) &&
				(screenY <= Play.getCamera().getHeight() - SwitchCorS.getRightPos().y) && SwitchCorS.isRight())
			{
				SwitchCorS.toRight();
			}
			// inactive units
			for (int i = 0; i < SwitchCorS.getInactiveBgPos().size(); i++)
			{
				if ((screenX >= SwitchCorS.getInactiveBgPos().get(i).x) &&
					(screenX <= SwitchCorS.getInactiveBgPos().get(i).x + SwitchCorS.getInactiveBgSize().get(i).x) &&
					(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getInactiveBgPos().get(i).y + SwitchCorS.getInactiveBgSize().get(i).y)) &&
					(screenY <= Play.getCamera().getHeight() - SwitchCorS.getInactiveBgPos().get(i).y))
				{
					SwitchCorS.selectInactive(i);
				}
			}
			// active units
			for (int i = 0; i < SwitchCorS.getActiveBgPos().size(); i++)
			{
				if ((screenX >= SwitchCorS.getActiveBgPos().get(i).x) &&
					(screenX <= SwitchCorS.getActiveBgPos().get(i).x + SwitchCorS.getActiveBgSize().get(i).x) &&
					(screenY >= Play.getCamera().getHeight() - (SwitchCorS.getActiveBgPos().get(i).y + SwitchCorS.getActiveBgSize().get(i).y)) &&
					(screenY <= Play.getCamera().getHeight() - SwitchCorS.getActiveBgPos().get(i).y))
				{
					SwitchCorS.selectActive(i);
				}
			}
		}
		else {
			// left
			if ((screenX > 0) && (screenX <= camera.getWidth() / 4))
				velocity.x = -speed;
			// jump
			else if ((screenX > camera.getWidth() / 4) && (screenX <= camera.getWidth() / 4 * 3))
			{
				if (jumped){
					velocity.y = speed;
					jumped = false;
				}
			}
			// right
			else
				velocity.x = speed;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// left
		if ((screenX > 0) && (screenX <= camera.getWidth() / 4))
			velocity.x = 0;
		// right
		else if ((screenX > camera.getWidth() / 4 * 3) && (screenX <= camera.getWidth()))
			velocity.x = 0;
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	private void delay(int length)
	{
		try {
			Thread.sleep(length);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean getCreatureAppeared(){
		return creatureAppeared;
	}
	public boolean chanceRandom (int n) {
		boolean answ = false;
		if (random(1, n) == n)
			answ = true;
		return answ;
	}
	public int random (int min, int max){
		int rand = (int) (Math.random() * max + min);
		return rand;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		Player.gender = gender;
	}
	
	public void setAppearChance(int i){
		appearChance = i;
	}
	
}
