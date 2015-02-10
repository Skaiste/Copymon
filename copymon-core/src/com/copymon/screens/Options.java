package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Options {

	private static SpriteBatch batch;
	private static Sprite bg,
						  under,
						  soundText,
						  soundButton,
						  creaturesText,
						  creaturesButton,
						  remControlText,
						  remControlButton,
						  placeAtStartText,
						  placeAtStartButton,
						  whenGameStarts;
	
	private static boolean sound,
						   creatureAppearing,
						   remindControl,
						   placeAtStart,
						   exiting = false;
	
	
	final private static int movingSpeed = 10;
	
	public static void render(){
		batch.begin();
		under.draw(batch);
		exitSprites(batch);
		drawBackground(batch);
		drawTexts(batch);
		drawButtons(batch);
		batch.end();
	}
	public static void show(){
		float buttonWidth  = Gdx.graphics.getWidth() / 5.48f,
			  buttonHeight = Gdx.graphics.getHeight() / 7.164f; 
				
		updateBools();
		
		batch = new SpriteBatch();
		
		// background
		if (Menu.isGameHasStarted())
			bg = new Sprite(new Texture("options/background2.gif"));
		else
			bg = new Sprite(new Texture("options/background1.gif"));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 2);
		bg.setY((-1) * bg.getHeight() / 2);
		
		// background under background :D
		
		under = new Sprite(new Texture("options/under.gif"));
		under.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// sound
		soundText = new Sprite(new Texture("options/sound.gif"));
		soundText.setSize(Gdx.graphics.getWidth() / 5.97f, Gdx.graphics.getHeight() / 13.33f);
		soundText.setPosition(Gdx.graphics.getWidth() / 13.8f, Gdx.graphics.getHeight() / 1.91f - bg.getHeight() / 2);
		if (sound)
			soundButton = new Sprite(new Texture("options/on.gif"));
		else
			soundButton = new Sprite(new Texture("options/off.gif"));
		soundButton.setSize(buttonWidth, buttonHeight);
		soundButton.setPosition(Gdx.graphics.getWidth() / 3.73f, Gdx.graphics.getHeight() / 2.06f - bg.getHeight() / 2);
		
		// creature appearing
		creaturesText = new Sprite(new Texture("options/creatureAppearing.gif"));
		creaturesText.setSize(Gdx.graphics.getWidth() / 5.55f, Gdx.graphics.getHeight() / 8.57f);
		creaturesText.setPosition(Gdx.graphics.getWidth() / 1.86f, Gdx.graphics.getHeight() / 1.99f - bg.getHeight() / 2);
		if (creatureAppearing)
			creaturesButton = new Sprite(new Texture("options/on.gif"));
		else
			creaturesButton = new Sprite(new Texture("options/off.gif"));
		creaturesButton.setSize(buttonWidth, buttonHeight);
		creaturesButton.setPosition(Gdx.graphics.getWidth() / 1.36f, Gdx.graphics.getHeight() / 2.06f - bg.getHeight() / 2);
		
		// reminding controls at the start of the game
		remControlText = new Sprite(new Texture("options/remindControl.gif"));
		remControlText.setSize(Gdx.graphics.getWidth() / 5.55f, Gdx.graphics.getHeight() / 9.06f);
		remControlText.setPosition(Gdx.graphics.getWidth() / 16.32f, Gdx.graphics.getHeight() / 7.74f - bg.getHeight() / 2);
		if (remindControl)
			remControlButton = new Sprite(new Texture("options/on.gif"));
		else
			remControlButton = new Sprite(new Texture("options/off.gif"));
		remControlButton.setSize(buttonWidth, buttonHeight);
		remControlButton.setPosition(Gdx.graphics.getWidth() / 3.74f, Gdx.graphics.getHeight() / 9.41f - bg.getHeight() / 2);

		// placing the player at the start of the map each time the game starts
		placeAtStartText = new Sprite(new Texture("options/placeAtStart.gif"));
		placeAtStartText.setSize(Gdx.graphics.getWidth() / 4.44f, Gdx.graphics.getHeight() / 4.25f);
		placeAtStartText.setPosition(Gdx.graphics.getWidth() / 2.03f, Gdx.graphics.getHeight() / 17.78f - bg.getHeight() / 2);
		if (placeAtStart)
			placeAtStartButton = new Sprite(new Texture("options/on.gif"));
		else
			placeAtStartButton = new Sprite(new Texture("options/off.gif"));
		placeAtStartButton.setSize(buttonWidth, buttonHeight);
		placeAtStartButton.setPosition(Gdx.graphics.getWidth() / 1.36f, Gdx.graphics.getHeight() / 9.41f - bg.getHeight() / 2);
		
		// when the game starts
		whenGameStarts = new Sprite(new Texture("options/whenGameStarts.gif"));
		whenGameStarts.setSize(Gdx.graphics.getWidth() / 2.25f, Gdx.graphics.getHeight() / 13.71f);
		whenGameStarts.setPosition(Gdx.graphics.getWidth() / 3.64f, Gdx.graphics.getHeight() / 3.06f - bg.getHeight() / 2);
		
		// input processor
		Gdx.input.setInputProcessor(new InputAdapter () {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode)
				{
				case Keys.BACK:
					exiting = true;
					break;
				case Keys.BACKSPACE:
					exiting = true;
					break;
				}
				return true;
			}
			
			public boolean touchDown (int x, int y, int pointer, int button) {
				if ((x >= soundButton.getX()) &&
					(x <= soundButton.getX() + soundButton.getRegionWidth()) &&
					(y >= Play.getCamera().getHeight() - (soundButton.getY() + soundButton.getRegionHeight())) &&
					(y <= Play.getCamera().getHeight() - soundButton.getY()))
				{
					if (sound)
					{
						sound = false;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("sound", sound);
						prefs.flush();
						soundButton.getTexture().dispose();
						soundButton.setTexture(new Texture("options/off.gif"));
					}
					else
					{
						sound = true;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("sound", sound);
						prefs.flush();
						soundButton.getTexture().dispose();
						soundButton.setTexture(new Texture("options/on.gif"));
					}
				}
				else if ((x >= creaturesButton.getX()) &&
						(x <= creaturesButton.getX() + creaturesButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (creaturesButton.getY() + creaturesButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - creaturesButton.getY()))
				{
					if (creatureAppearing)
					{
						creatureAppearing = false;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("creatures appearing", false);
						prefs.putInteger("creature appearing chance", 0);
						prefs.flush();
						
						creaturesButton.getTexture().dispose();
						creaturesButton.setTexture(new Texture("options/off.gif"));
					}
					else
					{
						creatureAppearing = true;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("creatures appearing", true);
						prefs.putInteger("creature appearing chance", 50);
						prefs.flush();
						
						creaturesButton.getTexture().dispose();
						creaturesButton.setTexture(new Texture("options/on.gif"));
					}
				}
				else if ((x >= remControlButton.getX()) &&
						(x <= remControlButton.getX() + remControlButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (remControlButton.getY() + remControlButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - remControlButton.getY()))
				{
					if (remindControl)
					{
						remindControl = false;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("remind controls", remindControl);
						prefs.flush();
						remControlButton.getTexture().dispose();
						remControlButton.setTexture(new Texture("options/off.gif"));
					}
					else
					{
						remindControl = true;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("remind controls", remindControl);
						prefs.flush();
						remControlButton.getTexture().dispose();
						remControlButton.setTexture(new Texture("options/on.gif"));
					}
				}
				else if ((x >= placeAtStartButton.getX()) &&
						(x <= placeAtStartButton.getX() + placeAtStartButton.getRegionWidth()) &&
						(y >= Play.getCamera().getHeight() - (placeAtStartButton.getY() + placeAtStartButton.getRegionHeight())) &&
						(y <= Play.getCamera().getHeight() - placeAtStartButton.getY()))
				{
					if (placeAtStart)
					{
						placeAtStart = false;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("place at start", placeAtStart);
						prefs.flush();
						placeAtStartButton.getTexture().dispose();
						placeAtStartButton.setTexture(new Texture("options/off.gif"));
					}
					else
					{
						placeAtStart = true;
						Preferences prefs = Gdx.app.getPreferences("Copymon Options");
						prefs.putBoolean("place at start", placeAtStart);
						prefs.flush();
						placeAtStartButton.getTexture().dispose();
						placeAtStartButton.setTexture(new Texture("options/on.gif"));
					}
				}
				return true;
			}
		});
	}
	public static void dispose(){
		batch.dispose();
		bg.getTexture().dispose();
		under.getTexture().dispose();
		soundText.getTexture().dispose();
		soundButton.getTexture().dispose();
		creaturesText.getTexture().dispose();
		creaturesButton.getTexture().dispose();
		remControlText.getTexture().dispose();
		remControlButton.getTexture().dispose();
		placeAtStartText.getTexture().dispose();
		placeAtStartButton.getTexture().dispose();
		whenGameStarts.getTexture().dispose();
	}
	
	private static void drawBackground(SpriteBatch sBatch){
		if ((bg.getY() < 0) && !exiting)
			bg.setY(bg.getY() + movingSpeed);
		bg.draw(sBatch);
	}
	private static void drawTexts(SpriteBatch sBatch){
		if ((bg.getY() < 0) && !exiting)
		{
			soundText.setY(soundText.getY() + movingSpeed);
			creaturesText.setY(creaturesText.getY() + movingSpeed);
			remControlText.setY(remControlText.getY() + movingSpeed);
			placeAtStartText.setY(placeAtStartText.getY() + movingSpeed);
			whenGameStarts.setY(whenGameStarts.getY() + movingSpeed);
		}
		soundText.draw(sBatch);
		creaturesText.draw(sBatch);
		remControlText.draw(sBatch);
		placeAtStartText.draw(sBatch);
		whenGameStarts.draw(sBatch);
	}
	private static void drawButtons(SpriteBatch sBatch){
		if ((bg.getY() < 0) && !exiting)
		{
			soundButton.setY(soundButton.getY() + movingSpeed);
			creaturesButton.setY(creaturesButton.getY() + movingSpeed);
			remControlButton.setY(remControlButton.getY() + movingSpeed);
			placeAtStartButton.setY(placeAtStartButton.getY() + movingSpeed);
		}
		soundButton.draw(sBatch);
		creaturesButton.draw(sBatch);
		remControlButton.draw(sBatch);
		placeAtStartButton.draw(sBatch);
	}
	
	public static void updateBools(){
		Preferences prefsOptions = Gdx.app.getPreferences("Copymon Options");
		if (!Gdx.app.getPreferences("Copymon Options").contains("sound")){
			
			prefsOptions.putBoolean("sound", true);
			prefsOptions.putBoolean("creatures appearing", true);
			prefsOptions.putInteger("creature appearing chance", 50);
			prefsOptions.putBoolean("place at start", false);
			prefsOptions.putBoolean("remind controls", true);
			prefsOptions.flush();
		}
		
		sound = prefsOptions.getBoolean("sound");
		creatureAppearing = prefsOptions.getBoolean("creatures appearing");
		remindControl = prefsOptions.getBoolean("remind controls");
		placeAtStart = prefsOptions.getBoolean("place at start");
	}
	
	private static void exitSprites(SpriteBatch sBatch){
		if (exiting){
			if (bg.getY() > (-1) * bg.getHeight() / 2){
				// update the position of sprites
				bg.setY(bg.getY() - movingSpeed);
				
				soundText.setY(soundText.getY() - movingSpeed);
				creaturesText.setY(creaturesText.getY() - movingSpeed);
				remControlText.setY(remControlText.getY() - movingSpeed);
				placeAtStartText.setY(placeAtStartText.getY() - movingSpeed);
				whenGameStarts.setY(whenGameStarts.getY() - movingSpeed);
				
				soundButton.setY(soundButton.getY() - movingSpeed);
				creaturesButton.setY(creaturesButton.getY() - movingSpeed);
				remControlButton.setY(remControlButton.getY() - movingSpeed);
				placeAtStartButton.setY(placeAtStartButton.getY() - movingSpeed);
				
				/*
				// draw the sprites
				bg.draw(sBatch);
				soundText.draw(sBatch);
				creaturesText.draw(sBatch);
				remControlText.draw(sBatch);
				placeAtStartText.draw(sBatch);
				whenGameStarts.draw(sBatch);
				soundButton.draw(sBatch);
				creaturesButton.draw(sBatch);
				remControlButton.draw(sBatch);
				placeAtStartButton.draw(sBatch);
				*/
			}
			else if (bg.getY() == (-1) * bg.getHeight() / 2)
			{
				exiting = false;
				dispose();
				Menu.show(Play.getCamera());
				Menu.render();
				Menu.setOptions(false);
			}
		}
	}
}
