package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu {
	private static SpriteBatch batch;
	private static Sprite menuBg,
						  buttonContinue,
						  buttonNewGame,
						  buttonWithFriend,
						  buttonOptions,
						  buttonQuit,
						  gameHasNotStarted;
	private static boolean isContinue	  = false,
						   isNewGame	  = false,
						   isWithFriend	  = false,	
						   isOptions	  = false,
						   gameHasStarted = Gdx.files.local("save.xml").exists(); //Gdx.app.getPreferences("Copymon Preferences").contains("gender");
	
	public static void render() {
		batch.begin();
		menuBg.draw(batch);
		buttonContinue.draw(batch);
		buttonNewGame.draw(batch);
		buttonWithFriend.draw(batch);
		buttonOptions.draw(batch);
		buttonQuit.draw(batch);
		if (!gameHasStarted)
			gameHasNotStarted.draw(batch);		
		batch.end();
	}
	public static void show(final Camera camera) {
		// set camera to the base position
		camera.setPositionZero();
		
		// set up the sprite batch
		batch = new SpriteBatch();
		// set up all the sprites including background and buttons
		menuBg = new Sprite(new Texture("menu/background.gif"));
		buttonContinue = new Sprite(new Texture("menu/continue.gif"));
		buttonNewGame = new Sprite(new Texture("menu/newGame.gif"));
		buttonWithFriend = new Sprite(new Texture("menu/playWithFriend.gif"));
		buttonOptions = new Sprite(new Texture("menu/options.gif"));
		buttonQuit = new Sprite(new Texture("menu/quit.gif"));
		
		// set sizes
		menuBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		buttonContinue.setSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 7.16f);
		buttonNewGame.setSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 7.16f);
		buttonWithFriend.setSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 7.16f);
		buttonOptions.setSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 7.16f);
		buttonQuit.setSize(Gdx.graphics.getWidth() / 5.48f, Gdx.graphics.getHeight() / 7.16f);
		
		// set up button positions
		buttonContinue.setPosition((float)(Gdx.graphics.getWidth() / 4.4), (float) (Gdx.graphics.getHeight() / 2.1));
		buttonNewGame.setPosition((float)(Gdx.graphics.getWidth() / 1.9), (float) (Gdx.graphics.getHeight() / 2.1));
		buttonWithFriend.setPosition((float)(Gdx.graphics.getWidth() / 4.4), (float) (Gdx.graphics.getHeight() / 4.06));
		buttonOptions.setPosition((float)(Gdx.graphics.getWidth() / 1.9), (float) (Gdx.graphics.getHeight() / 4.06));
		buttonQuit.setPosition((float)(Gdx.graphics.getWidth() / 2.43), (float) (Gdx.graphics.getHeight() / 17.78));
		
		
		// if the game hasn't started don't allow to continue
		if (!gameHasStarted)
		{
			gameHasNotStarted = new Sprite(new Texture("menu/hasNotStarted.gif"));
			gameHasNotStarted.setSize(buttonContinue.getWidth() / 1.093f, buttonContinue.getHeight() / 1.367f);
			gameHasNotStarted.setPosition((float)(Gdx.graphics.getWidth() / 4.21), (float) (Gdx.graphics.getHeight() / 2.02));
			System.out.println(buttonContinue.getHeight() + " " + gameHasNotStarted.getHeight());
		}		
		
		// set up input processor for buttons to work
		Gdx.input.setInputProcessor(new InputAdapter(){
			public boolean touchDown (int x, int y, int pointer, int button) {
				if(!isContinue && !isNewGame && !isWithFriend && !isOptions)
				{
					// continue button
					if ((x >= buttonContinue.getX()) &&
						(x <= buttonContinue.getX() + buttonContinue.getRegionWidth()) &&
						(y >= camera.getHeight() - (buttonContinue.getY() + buttonContinue.getRegionHeight())) &&
						(y <= camera.getHeight() - buttonContinue.getY()))
					{
						if (gameHasStarted){
							dispose();
							Continue.show();
							isContinue = true;
						}
					}
					// new game button
					if ((x >= buttonNewGame.getX()) &&
						(x <= buttonNewGame.getX() + buttonNewGame.getRegionWidth()) &&
						(y >= camera.getHeight() - (buttonNewGame.getY() + buttonNewGame.getRegionHeight())) &&
						(y <= camera.getHeight() - buttonNewGame.getY()))
					{
						dispose();
						NewGame.show();
						isNewGame = true;
					}
					// play with friend button
					if ((x >= buttonWithFriend.getX()) &&
						(x <= buttonWithFriend.getX() + buttonWithFriend.getRegionWidth()) &&
						(y >= camera.getHeight() - (buttonWithFriend.getY() + buttonWithFriend.getRegionHeight())) &&
						(y <= camera.getHeight() - buttonWithFriend.getY()))
					{
						System.out.println("play with friend");
					}
					// options button
					if ((x >= buttonOptions.getX()) &&
						(x <= buttonOptions.getX() + buttonOptions.getRegionWidth()) &&
						(y >= camera.getHeight() - (buttonOptions.getY() + buttonOptions.getRegionHeight())) &&
						(y <= camera.getHeight() - buttonOptions.getY()))
					{
						dispose();
						Options.show();
						isOptions = true;
					}
					// quit button
					if ((x >= buttonQuit.getX()) &&
						(x <= buttonQuit.getX() + buttonQuit.getRegionWidth()) &&
						(y >= camera.getHeight() - (buttonQuit.getY() + buttonQuit.getRegionHeight())) &&
						(y <= camera.getHeight() - buttonQuit.getY()))
					{
						Gdx.app.exit();
					}
				}
				return true;
			}
		});
	}
	public static void dispose() {
		batch.dispose();
		buttonContinue.getTexture().dispose();
		buttonNewGame.getTexture().dispose();
		buttonWithFriend.getTexture().dispose();
		buttonOptions.getTexture().dispose();
		buttonQuit.getTexture().dispose();
		if (gameHasNotStarted != null)
			gameHasNotStarted.getTexture().dispose();
	}
	public static boolean isContinue() {
		return isContinue;
	}
	public static void setContinue(boolean isContinue) {
		Menu.isContinue = isContinue;
	}
	public static boolean isNewGame() {
		return isNewGame;
	}
	public static void setNewGame(boolean isNewGame) {
		Menu.isNewGame = isNewGame;
	}
	public static boolean isWithFriend() {
		return isWithFriend;
	}
	public static void setWithFriend(boolean isWithFriend) {
		Menu.isWithFriend = isWithFriend;
	}
	public static boolean isOptions() {
		return isOptions;
	}
	public static void setOptions(boolean isOptions) {
		Menu.isOptions = isOptions;
	}
	public static boolean isGameHasStarted() {
		return gameHasStarted;
	}
	public static void setGameHasStarted(boolean gameHasStarted) {
		Menu.gameHasStarted = gameHasStarted;
	}
	
}
