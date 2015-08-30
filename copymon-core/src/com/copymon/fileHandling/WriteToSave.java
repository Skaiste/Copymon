package com.copymon.fileHandling;

import java.io.IOException;
import java.io.StringWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlWriter;
import com.copymon.creatures.Creature;
import com.copymon.creatures.PlayerCreatures;
import com.copymon.screens.Continue;
import com.copymon.screens.Play;

public class WriteToSave {

	public WriteToSave() {}
	
	public WriteToSave(String gender, String name, float playerX, float playerY, float cameraX, float cameraY, PlayerCreatures playerCreatures){
		
		Preferences prefs = Gdx.app.getPreferences("Copymon Preferences");
		prefs.putString("name", name);
		prefs.putString("gender", gender);
		//prefs.putInteger("missions", missionsCompleted);
		prefs.putInteger("money", playerCreatures.getMoney());
		prefs.putInteger("map", Continue.getMap().mapN);
		prefs.putString("place", Continue.getMap().place);
		prefs.putFloat("playerX", playerX);
		prefs.putFloat("playerY", playerY);
		prefs.putFloat("cameraX", cameraX);
		prefs.putFloat("cameraY", cameraY);
		prefs.flush();

		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		try {
			xml.element("Player")
			  	.element("activeCreatures")
				.attribute("n", playerCreatures.getActiveCreatures().size());
				for (int j = 0; j < playerCreatures.getActiveCreatures().size(); j++)
				{
					xml.element(Integer.toString(j+1))
					.attribute("name", playerCreatures.getActiveCreatures().get(j).getName())
					.attribute("lvl", playerCreatures.getActiveCreatures().get(j).getLvl())
					.attribute("exp", playerCreatures.getActiveCreatures().get(j).getExp())
					.attribute("hp", playerCreatures.getActiveCreatures().get(j).getHealth())
						.element("activeSkills")
						.attribute("n", playerCreatures.getActiveCreatures().get(j).getActiveSkillN());
						for(int i = 0; i < playerCreatures.getActiveCreatures().get(j).getActiveSkillN(); i++)
						{
							xml.attribute("name" + (i+1), playerCreatures.getActiveCreatures().get(j).getActiveSkillByIndex(i).getName())
							   .attribute("type" + (i+1), playerCreatures.getActiveCreatures().get(j).getActiveSkillByIndex(i).getTypeInString());
						}
						xml.pop()
						.element("inactiveSkills")
						.attribute("n", playerCreatures.getActiveCreatures().get(j).getInactiveSkillN());
						for(int i = 0; i < playerCreatures.getActiveCreatures().get(j).getInactiveSkillN(); i++)
						{
							xml.attribute("name" + (i+1), playerCreatures.getActiveCreatures().get(j).getInactiveSkillByIndex(i).getName())
							   .attribute("type" + (i+1), playerCreatures.getActiveCreatures().get(j).getInactiveSkillByIndex(i).getTypeInString());
						}
						xml.pop()
					.pop();
				}
				xml.pop()
				.element("inactiveCreatures")
				.attribute("n", playerCreatures.getInactiveCreatures().size());
				for (int j = 0; j < playerCreatures.getInactiveCreatures().size(); j++)
				{
					xml.element(Integer.toString(j+1))
					.attribute("name", playerCreatures.getInactiveCreatures().get(j).getName())
					.attribute("lvl", playerCreatures.getInactiveCreatures().get(j).getLvl())
					.attribute("exp", playerCreatures.getInactiveCreatures().get(j).getExp())
					.attribute("hp", playerCreatures.getInactiveCreatures().get(j).getHealth())
						.element("activeSkills")
						.attribute("n", playerCreatures.getInactiveCreatures().get(j).getActiveSkillN());
						for(int i = 0; i < playerCreatures.getInactiveCreatures().get(j).getActiveSkillN(); i++)
						{
							xml.attribute("name" + (i+1), playerCreatures.getInactiveCreatures().get(j).getActiveSkillByIndex(i).getName())
							   .attribute("type" + (i+1), playerCreatures.getInactiveCreatures().get(j).getActiveSkillByIndex(i).getTypeInString());
						}
						xml.pop()
						.element("inactiveSkills")
						.attribute("n", playerCreatures.getInactiveCreatures().get(j).getInactiveSkillN());
						for(int i = 0; i < playerCreatures.getInactiveCreatures().get(j).getInactiveSkillN(); i++)
						{
							xml.attribute("name" + (i+1), playerCreatures.getInactiveCreatures().get(j).getInactiveSkillByIndex(i).getName())
							   .attribute("type" + (i+1), playerCreatures.getInactiveCreatures().get(j).getInactiveSkillByIndex(i).getTypeInString());
						}
						xml.pop()
					.pop();
				}
				xml.pop()
			.pop();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileHandle file = Gdx.files.local("save.xml");
		if (file.exists())
		{	
			file.delete();
			file = Gdx.files.local("save.xml");
		}
		file.writeString(writer.toString(), true);
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void WriteToSaveNew(String gender, Creature c, String name){
		if (!Gdx.app.getPreferences("Copymon Options").contains("sound")) {
			Preferences prefsOptions = Gdx.app.getPreferences("Copymon Options");
			prefsOptions.putBoolean("sound", true);
			prefsOptions.putBoolean("creatures appearing", true);
			prefsOptions.putInteger("creature appearing chance", 50);
			prefsOptions.putBoolean("place at start", false);
			prefsOptions.putBoolean("remind controls", true);
			prefsOptions.flush();
		}
		
		Preferences prefs = Gdx.app.getPreferences("Copymon Preferences");
		prefs.putString("name", name);
		prefs.putString("gender", gender);
		//prefs.putInteger("missions", missionsCompleted);
		prefs.putInteger("money", 0);
		prefs.putInteger("map", 1);
		prefs.putString("place", "map");
		prefs.putFloat("playerX", 40);
		prefs.putFloat("playerY", Play.getCamera().getHeight() / 2);
		prefs.putFloat("cameraX", Play.getCamera().getWidth() / 2);
		prefs.putFloat("cameraY", Play.getCamera().getHeight() / 2);
		prefs.flush();
		
		
		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		try {
			xml.element("Player")
			  	.element("activeCreatures")
				.attribute("n", 1)
					.element("1")
					.attribute("name", c.getName())
					.attribute("lvl", c.getLvl())
					.attribute("exp", c.getExp())
					.attribute("hp", c.getHealth())
						.element("activeSkills")
						.attribute("n", c.getActiveSkillN());
						for(int i = 0; i < c.getActiveSkillN(); i++)
						{
							xml.attribute("name" + (i+1), c.getActiveSkillByIndex(i).getName())
							   .attribute("type" + (i+1), c.getActiveSkillByIndex(i).getTypeInString());
						}
						xml.pop()
						.element("inactiveSkills")
						.attribute("n", c.getInactiveSkillN());
						for(int i = 0; i < c.getInactiveSkillN(); i++)
						{
							xml.attribute("name" + (i+1), c.getInactiveSkillByIndex(i).getName())
							   .attribute("type" + (i+1), c.getInactiveSkillByIndex(i).getTypeInString());
						}
						xml.pop()
					.pop()
				.pop()
				.element("inactiveCreatures")
				.attribute("n", 0)
				.pop()
			.pop();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileHandle file = Gdx.files.local("save.xml");
		if (file.exists())
		{	
			file.delete();
			file = Gdx.files.local("save.xml");
		}
		file.writeString(writer.toString(), true);
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
