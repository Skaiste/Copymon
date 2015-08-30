package com.copymon.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.copymon.creatures.Creature;
import com.copymon.creatures.Skill;
import com.copymon.fileHandling.WriteToSave;

public class NewGame {
	private static SpriteBatch batch;
	private static Sprite bg, 
						  genderBoy, 
						  genderGirl, 
						  strokeGender,
						  nextButton,
						  playButton, 
						  firstCreature, 
						  secondCreature, 
						  thirdCreature, 
						  strokeCreature,
						  creatureImage;
	private static BitmapFont creatureName1, creatureName2, creatureName3,
							  name, type, descr1, descr2, descr3, descr4, descr5, hp, ap, def, agility;
	
	// name text field
	private static Vector2 fieldPos  = new Vector2(Gdx.graphics.getWidth() / 2.051282051f  - Play.getCamera().getRealX(), Gdx.graphics.getHeight() / 2.008368201f  - Play.getCamera().getRealY()), 
						   fieldSize = new Vector2(Gdx.graphics.getWidth() / 4.761904762f, Gdx.graphics.getHeight() / 20.86956522f);

	private static String line = "";
	private static float oneLetterWidth;
	private static BitmapFont playerNameField;
	private static boolean fieldActive = false;
	
	
	//private static String name;
	private static String gender, crName;
	private static boolean isBoy = true;
	private static int whichCreature = 3;

	private static boolean creatureWindow = false;
	
	public static void render() {
		if (creatureWindow)
			render2();
		else
			render1();
	}
	public static void show() {
		batch = new SpriteBatch();
		
		if (creatureWindow)
			show2();
		else
			show1();
	}
	public static void dispose() {
		batch.dispose();
		
		if (creatureWindow)
			dispose2();
		else
			dispose1();
	}
	
	
	public static void render1() {
		
		if (isBoy){
			strokeGender.setPosition(Gdx.graphics.getWidth() / 3.25203252f, Gdx.graphics.getHeight() / 8.13559322f);
		}
		else{
			strokeGender.setPosition(Gdx.graphics.getWidth() / 1.754385965f, Gdx.graphics.getHeight() / 8.13559322f);
		}
		
		batch.begin();
		
		bg.draw(batch);
		genderBoy.draw(batch);
		genderGirl.draw(batch);
		strokeGender.draw(batch);
		nextButton.draw(batch);
		
		playerNameField.draw(batch, displayTextInField(), fieldPos.x, Gdx.graphics.getHeight() / 1.818181818f + 10);
        
		batch.end();

	}
	public static void show1() {
		
		// setting up all sprites that will be shown
		bg = new Sprite(new Texture("newGame/background1.gif"));
		genderBoy = new Sprite(new Texture("newGame/genderBoy.gif"));
		genderGirl = new Sprite(new Texture("newGame/genderGirl.gif"));
		strokeGender = new Sprite(new Texture("newGame/strokeGender.gif"));
		nextButton = new Sprite(new Texture("newGame/nextButton.gif"));
		
		// set size of sprites
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		genderBoy.setSize(Gdx.graphics.getWidth() / 10.66666667f, Gdx.graphics.getHeight() / 5.274725275f);
		genderGirl.setSize(Gdx.graphics.getWidth() / 10.66666667f, Gdx.graphics.getHeight() / 5.274725275f);
		strokeGender.setSize(Gdx.graphics.getWidth() / 9.411764706f, Gdx.graphics.getHeight() / 4.752475248f);
		nextButton.setSize(Gdx.graphics.getWidth() / 7.339449541f, Gdx.graphics.getHeight() / 7.164179104f);
		
		// setting the positions of sprites
		genderBoy.setPosition(Gdx.graphics.getWidth() / 3.187250996f, Gdx.graphics.getHeight() / 7.5f);
		genderGirl.setPosition(Gdx.graphics.getWidth() / 1.735357918f, Gdx.graphics.getHeight() / 7.5f);
		nextButton.setPosition(Gdx.graphics.getWidth() / 1.176470588f, Gdx.graphics.getHeight() / 43.63636364f);
		
		// name text field
		playerNameField = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		playerNameField.scale(1);
		oneLetterWidth = playerNameField.getBounds("a").width;
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode)
				{
				case Keys.BACK:
					if (!fieldActive){
						dispose();
						Menu.setNewGame(false);
						Menu.show(Play.getCamera());
					}
					else{
						turnFieldOff();
					}
					break;
				case Keys.BACKSPACE:
					if (!fieldActive){
						dispose();
						Menu.setNewGame(false);
						Menu.show(Play.getCamera());
					}
					else{
						deleteCharFromString();
					}
					break;
				case Keys.ENTER:
					if (fieldActive)
						turnFieldOff();
					break;
				default:
					if ((Keys.toString(keycode).length() < 2) && fieldActive)
						line += Keys.toString(keycode).toLowerCase();
					break;
				}
				
				return true;
			}
			
			public boolean touchDown (int x, int y, int pointer, int button) {
				System.out.println(fieldActive);
				if ((x >= genderBoy.getX()) &&
					(x <= genderBoy.getX() + genderBoy.getRegionWidth()) &&
					(y >= Play.getCamera().getHeight() - (genderBoy.getY() + genderBoy.getRegionHeight())) &&
					(y <= Play.getCamera().getHeight() - genderBoy.getY()))
				{
					isBoy = true;
				}
				else if ((x >= genderGirl.getX()) &&
						(x <= genderGirl.getX() + genderGirl.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (genderGirl.getY() + genderGirl.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - genderGirl.getY()))
				{
					isBoy = false;
				}
				else if ((x >= fieldPos.x) &&
						(x <= fieldPos.x + fieldSize.x) &&
						(y >= Play.getCamera().getHeight() - (fieldPos.y + fieldSize.y)) &&
						(y <= Play.getCamera().getHeight() - fieldPos.y))
				{
					if (!fieldActive){
						turnFieldOn();
					}
				}
				else if ((x >= nextButton.getX()) &&
						(x <= nextButton.getX() + nextButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (nextButton.getY() + nextButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - nextButton.getY()))
				{
					if (isBoy)
						gender = "continue/player/boy/";
					else
						gender = "continue/player/girl/";
					
					dispose();
					creatureWindow = true;
					show();
				}
				else if (fieldActive){
					turnFieldOff();
				}
				
				return true;
			}
		});
		
	}	
	public static void dispose1() {
		bg.getTexture().dispose();
		genderBoy.getTexture().dispose();
		genderGirl.getTexture().dispose();
		strokeGender.getTexture().dispose();
		nextButton.getTexture().dispose();
		
	}
	
	
	public static void render2() {
		switch (whichCreature)
		{
		case 1:
			crName = "Weediesaur";
			strokeCreature.setPosition(Gdx.graphics.getWidth() / 1.353637902f, Gdx.graphics.getHeight() / 2f);
			break;
		case 2:
			crName = "Flameander";
			strokeCreature.setPosition(Gdx.graphics.getWidth() / 1.353637902f, Gdx.graphics.getHeight() / 2.75862069f);
			break;
		case 3:
			crName = "Drizzle";
			strokeCreature.setPosition(Gdx.graphics.getWidth() / 1.353637902f, Gdx.graphics.getHeight() / 4.444444444f);
			break;
		}
		
		// updating creature image
		creatureImage.getTexture().dispose();
		if (whichCreature == 1) {
			creatureImage.setTexture(new Texture("continue/creatures/images/Weediesaur/start.gif"));
		}
		else if (whichCreature == 2) {
			creatureImage.setTexture(new Texture("continue/creatures/images/Flameander/start.gif"));
		}
		else if (whichCreature == 3) {
			creatureImage.setTexture(new Texture("continue/creatures/images/Drizzle/start.gif")); 
		}
		creatureImage.setSize(creatureImage.getTexture().getWidth(), creatureImage.getTexture().getHeight());
		creatureImage.setCenter(Gdx.graphics.getWidth() / 4.90797546f, Gdx.graphics.getHeight() / 3.636363636f);
		
		batch.begin();
		
		bg.draw(batch);
		firstCreature.draw(batch);
		secondCreature.draw(batch);
		thirdCreature.draw(batch);
		strokeCreature.draw(batch);
		playButton.draw(batch);
		creatureImage.draw(batch);
		
		creatureName1.draw(batch, "Weediesaur", firstCreature.getX() + firstCreature.getWidth()/2 - creatureName1.getBounds("Weediesaur").width/2, firstCreature.getY() + firstCreature.getHeight()/2 + creatureName1.getBounds("Weediesaur").height/5*4);
		creatureName2.draw(batch, "Flameander", secondCreature.getX() + secondCreature.getWidth()/2 - creatureName2.getBounds("Flameander").width/2, secondCreature.getY() + secondCreature.getHeight()/2 + creatureName2.getBounds("Flameander").height/5*4);	
		creatureName3.draw(batch, "Drizzle", thirdCreature.getX() + thirdCreature.getWidth()/2 - creatureName3.getBounds("Drizzle").width/2, thirdCreature.getY() + thirdCreature.getHeight()/2 + creatureName3.getBounds("Drizzle").height/5*4);
		
		
		// creature name
		name.draw(batch, crName, Gdx.graphics.getWidth() / 4.90797546f - name.getBounds(crName).width/2, Gdx.graphics.getHeight() / 1.846153846f + firstCreature.getHeight()/2 - name.getBounds(crName).height/2);
		
		Creature tmp = new Creature(crName, 1 , new ArrayList<Skill>(), new ArrayList<Skill>());
		// description
		ArrayList <String> d = splittingDescribtion(tmp.getDescribtion(), descr1, Gdx.graphics.getWidth()/4);
		descr1.draw(batch, d.get(0), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 1.751824818f);
		if (d.size() > 1)
			descr2.draw(batch, d.get(1), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 1.897233202f);
		if (d.size() > 2)
			descr2.draw(batch, d.get(2), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 2.068965517f);
		if (d.size() > 3)
			descr2.draw(batch, d.get(3), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 2.274881517f);
		if (d.size() > 4)
			descr2.draw(batch, d.get(4), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 2.526315789f);
		
		// type
		type.draw(batch, "Type: " + tmp.getTypeInString(), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 2.840236686f);
		// health
		hp.draw(batch, "Health: " + tmp.getHp(), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 3.243243243f);
		// ability power
		ap.draw(batch, "Ability power: " + tmp.getAp(), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 3.779527559f);
		// defence
		def.draw(batch, "Defence: " + tmp.getDefence(),  Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 4.528301887f);
		// agility
		agility.draw(batch, "Agility: " + tmp.getAgility(), Gdx.graphics.getWidth() / 2.461538462f, Gdx.graphics.getHeight() / 5.647058824f);
		
		batch.end();
		

	}
	public static void show2() {
		
		// setting up all sprites that will be shown
		bg = new Sprite(new Texture("newGame/background2.gif"));
		firstCreature = new Sprite(new Texture("newGame/name.gif"));
		secondCreature = new Sprite(new Texture("newGame/name.gif"));
		thirdCreature = new Sprite(new Texture("newGame/name.gif"));
		strokeCreature = new Sprite(new Texture("newGame/strokeCreature.gif"));
		playButton = new Sprite(new Texture("newGame/playButton.gif"));
		if (whichCreature == 1)
			creatureImage = new Sprite(new Texture("continue/creatures/images/Weediesaur/start.gif"));
		else if (whichCreature == 2)
			creatureImage = new Sprite(new Texture("continue/creatures/images/Flameander/start.gif"));
		else if (whichCreature == 3)
			creatureImage = new Sprite(new Texture("continue/creatures/images/Drizzle/start.gif"));
		
		// set size of sprites
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		firstCreature.setSize(Gdx.graphics.getWidth() / 4.395604396f, Gdx.graphics.getHeight() / 9.230769231f);
		secondCreature.setSize(Gdx.graphics.getWidth() / 4.395604396f, Gdx.graphics.getHeight() / 9.230769231f);
		thirdCreature.setSize(Gdx.graphics.getWidth() / 4.395604396f, Gdx.graphics.getHeight() / 9.230769231f);
		strokeCreature.setSize(Gdx.graphics.getWidth() / 4.166666667f, Gdx.graphics.getHeight() / 7.741935484f);
		
		// setting the positions of sprites
		firstCreature.setPosition(Gdx.graphics.getWidth() / 1.342281879f, Gdx.graphics.getHeight() / 1.959183673f);
		secondCreature.setPosition(Gdx.graphics.getWidth() / 1.342281879f, Gdx.graphics.getHeight() / 2.681564246f);
		thirdCreature.setPosition(Gdx.graphics.getWidth() / 1.342281879f, Gdx.graphics.getHeight() / 4.247787611f);
		playButton.setPosition(Gdx.graphics.getWidth() / 1.192250373f, Gdx.graphics.getHeight() / 22.85714286f);
		creatureImage.setCenter(Gdx.graphics.getWidth() / 4.90797546f, Gdx.graphics.getHeight() / 3.636363636f);
		
		// setting up all fonts
		creatureName1 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		creatureName2 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		creatureName3 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		name = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		type = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		descr1 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		descr2 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		descr3 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		descr4 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		descr5 = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		hp = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		ap = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		def = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		agility = new BitmapFont(Gdx.files.internal("terminal.fnt"), Gdx.files.internal("terminal2.png"), false);
		

		creatureName1.scale(0.6f);
		creatureName2.scale(0.6f);
		creatureName3.scale(0.6f);
		name.scale(0.6f);
		descr1.scale(0.2f);
		descr2.scale(0.2f);
		descr3.scale(0.2f);
		descr4.scale(0.2f);
		descr5.scale(0.2f);
		type.scale(0.2f);
		hp.scale(0.2f);
		ap.scale(0.2f);
		def.scale(0.2f);
		agility.scale(0.2f);
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode)
				{
				case Keys.BACK:
					dispose2();
					show1();
					creatureWindow = false;
					break;
				case Keys.BACKSPACE:
					dispose2();
					show1();
					creatureWindow = false;
					break;
				}
				return true;
			}
			
			public boolean touchDown (int x, int y, int pointer, int button) {
				if ((x >= firstCreature.getX()) &&
						(x <= firstCreature.getX() + firstCreature.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (firstCreature.getY() + firstCreature.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - firstCreature.getY()))
				{
					whichCreature = 1;
				}
				else if ((x >= secondCreature.getX()) &&
						(x <= secondCreature.getX() + secondCreature.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (secondCreature.getY() + secondCreature.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - secondCreature.getY()))
				{
					whichCreature = 2;
				}
				else if ((x >= thirdCreature.getX()) &&
						(x <= thirdCreature.getX() + thirdCreature.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (thirdCreature.getY() + thirdCreature.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - thirdCreature.getY()))
				{
					whichCreature = 3;
				}
				else if ((x >= playButton.getX()) &&
						(x <= playButton.getX() + playButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (playButton.getY() + playButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - playButton.getY()))
				{
					// check if every input is fulfilled
					
					// save into xml
					int lvl = 1;
					Creature c = new Creature(crName, lvl, new ArrayList<Skill>(), new ArrayList<Skill>());
					c.getStartingSkills();
					System.out.println("Starting skills: " + c.getActiveSkillN());
					new WriteToSave().WriteToSaveNew(gender, c, line);
					// dispose everything
					dispose();
					Menu.setNewGame(false);
					Continue.show();
					Menu.setContinue(true);
					Menu.setGameHasStarted(true);
				}
				
				return true;
			}
		});
		
			
	}
	public static void dispose2() {
		
		bg.getTexture().dispose();
		firstCreature.getTexture().dispose();
		secondCreature.getTexture().dispose();
		thirdCreature.getTexture().dispose();
		strokeCreature.getTexture().dispose();
		playButton.getTexture().dispose();
		creatureName1.dispose();
		creatureName2.dispose();
		creatureName3.dispose();
		name.dispose();
		type.dispose();
		descr1.dispose();
		descr2.dispose();
		descr3.dispose();
		descr4.dispose();
		descr5.dispose();
		hp.dispose();
		ap.dispose();
		def.dispose();
		agility.dispose();
	}

	public static ArrayList <String> splittingDescribtion(String describtion, BitmapFont font, float maxWidth) {
		String[] split = describtion.split("\\s+");
		float length = 0;
		ArrayList <String> lines = new ArrayList<String>();
		for (int i = 0; i < split.length; i++)
		{
			if ((i == 0) || (length + font.getBounds(split[i]).width > maxWidth + font.getBounds("a").width)) {
				lines.add("");
				length = 0;
			}
			lines.set(lines.size() - 1, lines.get(lines.size() - 1) + split[i] + " " );
			length = lines.get(lines.size() - 1).length() * font.getBounds("a").width;
		}
		
		return lines;
	}
	
	
	// ******* Writing in field *******
	// making the field react to keyboard and placing the cursor
	private static void turnFieldOn(){
		System.out.println("turn field on");
		// activate field reaction
		fieldActive = true;
	}
	// disables the keyboard and reaction to placing the cursor
	private static void turnFieldOff(){
		System.out.println("turn field off");
		
		// deactivate field reaction
		fieldActive = false;
	}
	
	// deleting a character to the string at the cursor position
	private static void deleteCharFromString(){
		//System.out.println("delete from string");
		StringBuilder str = new StringBuilder("");
		if (line.length() > 0)
			str.append(line.substring(0, line.length() - 1));
		line = str.toString();
	}
	
	// returns a string that is displayed in the field
	private static String displayTextInField(){
		String theLine = "";
		int lettersFit = Math.round(fieldSize.x / oneLetterWidth);
		
		if (line.length() <= lettersFit)
		{
			theLine = line;
		}
		else {
			theLine = line.substring(line.length() - lettersFit);
		}
		//System.out.println(theLine);
		return theLine;
	}
	
}
