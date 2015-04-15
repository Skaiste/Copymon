package com.copymon.fileHandling;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.copymon.creatures.Creature;
import com.copymon.creatures.PlayerCreatures;
import com.copymon.creatures.Skill;
import com.copymon.screens.Continue;
import com.copymon.screens.Play;

public class ReaderFromSave {
	// *********** VARIABLES TAKEN FROM XML FILE ************
	// info attributes
	private String name,
				   gender;
	private int missionsCompleted;
	// placement attributes
	private int mapN;
	private String place;
	private float playerX,
				  playerY,
				  cameraX,
				  cameraY;

	// creatures
	private PlayerCreatures playerCreatures;
	
	// ******************************************************
	
	// ******************************************************
	
	public ReaderFromSave() {
		Preferences prefs = Gdx.app.getPreferences("Copymon Preferences");
		//prefs.putString(name, name);
		gender = prefs.getString("gender");
		//prefs.putInteger("missions", missionsCompleted);
		mapN = prefs.getInteger("map");
		place = prefs.getString("place");
		
		playerCreatures = new PlayerCreatures();
		
		FileHandle file = Gdx.files.local("save.xml");
		try {
			XmlReader.Element reader = new XmlReader().parse(file);
			// active creatures
			int creatureN = reader.getChildByName("activeCreatures").getIntAttribute("n");
			
			for (int i = 0; i < creatureN; i++)
			{
				String cName = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getAttribute("name");
				int cLvl = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getIntAttribute("lvl");
				int cExp = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getIntAttribute("exp");
				int cHp = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getIntAttribute("hp");
				Creature c = new Creature(cName, cLvl, new ArrayList<Skill>(), new ArrayList<Skill>(), cExp, cHp);
				// active skills
				int skillN = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getChildByName("activeSkills").getIntAttribute("n");
				for (int j = 0; j < skillN; j++)
				{
					String skillName = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getChildByName("activeSkills").getAttribute("name" + (j+1));
					c.addNewSkilltoActive(skillName);
				}
				// inactive skills
				skillN = reader.getChildByName("activeCreatures").getChildByName(Integer.toString(i+1)).getChildByName("inactiveSkills").getIntAttribute("n");
				for (int j = 0; j < skillN; j++)
				{
					c.addSkillToInactive(j+1);
				}
				
				playerCreatures.addActiveCreature(c);
			}
			
			// inactive creatures
			creatureN = reader.getChildByName("inactiveCreatures").getIntAttribute("n");
			
			for (int i = 0; i < creatureN; i++)
			{
				String cName = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getAttribute("name");
				int cLvl = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getIntAttribute("lvl");
				int cExp = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getIntAttribute("exp");
				int cHp = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getIntAttribute("hp");
				Creature c = new Creature(cName, cLvl, new ArrayList<Skill>(), new ArrayList<Skill>(), cExp, cHp);
				// active skills
				int skillN = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getChildByName("activeSkills").getIntAttribute("n");
				for (int j = 0; j < skillN; j++)
				{
					String skillName = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getChildByName("activeSkills").getAttribute("name" + (j+1));
					c.addNewSkilltoActive(skillName);
				}
				// inactive skills
				skillN = reader.getChildByName("inactiveCreatures").getChildByName(Integer.toString(i+1)).getChildByName("inactiveSkills").getIntAttribute("n");
				for (int j = 0; j < skillN; j++)
				{
					c.addSkillToInactive(j+1);
				}
				
				playerCreatures.addInactiveCreature(c);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readPositions(){
		Preferences prefs = Gdx.app.getPreferences("Copymon Preferences");
		
		if (!Gdx.app.getPreferences("Copymon Options").getBoolean("place at start"))
		{
			playerX = prefs.getFloat("playerX");
			playerY = prefs.getFloat("playerY");
			cameraX = prefs.getFloat("cameraX");
			cameraY = prefs.getFloat("cameraY");
		}
		else
		{
			Vector2 tempPlayer = Continue.getMap().getStartingCoordinates();
			playerX = tempPlayer.x;
			playerY = tempPlayer.y;
			cameraX = Play.getCamera().getWidth() / 2;
			cameraY = Play.getCamera().getHeight() / 2;
		}
	}

	public void setMapProperties() {
		Continue.getMap().mapN = mapN;
		Continue.getMap().place = place;
	}
	
	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getMissionsCompleted() {
		return missionsCompleted;
	}

	public float getPlayerX() {
		return playerX;
	}

	public float getPlayerY() {
		return playerY;
	}

	public float getCameraX() {
		return cameraX;
	}

	public float getCameraY() {
		return cameraY;
	}

	public PlayerCreatures getPlayerCreatures() {
		return playerCreatures;
	}
	
}
