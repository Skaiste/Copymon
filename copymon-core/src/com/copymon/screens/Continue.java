package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.copymon.creatures.PlayerCreatures;
import com.copymon.entities.Player;
import com.copymon.fileHandling.ReaderFromSave;
import com.copymon.fileHandling.WriteToSave;

public class Continue {

	private static Camera camera = Play.getCamera();
	
	private static Map map;
	private static OrthogonalTiledMapRenderer renderer;
	private static Sprite bg;
	private static Player player;
	private static PlayerCreatures playerCreatures;

	
	public static void render(float delta)
	{
		
		// updating the movement of camera when player moves
		camera.updateBg();
		renderer.setView(camera.getCamera());
		
		// rendering player when creature hasn't appeared
		if (!player.getCreatureAppeared() && !player.inventory && !player.switchSorC){
			player.update(delta);
		}
		// draw background
		renderer.getSpriteBatch().begin();
		bg.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();
		
		// rendering map
		map.render(renderer);
		// drawing player
		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();
		// rendering top layer of the map
		map.renderTopLayer(renderer);
		
		// drawing the creature appearing window
		CreatureHere.update();
		
		// inventory
		Inventory.update();
		
		// switch skills or creatures
		SwitchCorS.update();
		
		// menu for inventory and stuff
		PlayingMenu.render();
		
		
		// Show controls on screen
		ShowControls.update();
				
		// exiting to the menu
		if (player.goBack)
		{
			// save the game
			player.goBack = false;
			dispose();
			Menu.setContinue(false);
			Menu.show(camera);
		}
	}
	public static void show()
	{		
		// getting info from saved file
		ReaderFromSave reader = new ReaderFromSave();
		// setting up map and renderer
		map = new Map();
		renderer = new OrthogonalTiledMapRenderer(map.map);
		reader.setMapProperties();
		map.changeMap();
		
		// setting up the player
		reader.readPositions();
		player = new Player(map.getCollisionLayer(), map.getComputerLayer(), reader.getPlayerX(), reader.getPlayerY(), reader.getGender());		
		
		// setting up the background
		bg = new Sprite(new Texture("continue/background.gif"));
		bg.setSize(map.getWidth(), bg.getTexture().getHeight());
		
		// setting up input processor
		Gdx.input.setInputProcessor(player);
		
		// set position of the camera
		camera.setPosition(reader.getCameraX(), reader.getCameraY());
		
		// get creatures that player has
		playerCreatures = reader.getPlayerCreatures();
	}
	public static void dispose()
	{
		new WriteToSave(player.getGender(), player.getX(), player.getY(), camera.getX(), camera.getY(), playerCreatures);
		map.dispose();
		renderer.dispose();
		bg.getTexture().dispose();
		player.getTexture().dispose();
	}
	public static Map getMap(){
		return map;
	}

	public static Player getPlayer(){
		return player;
	}
	
	public static PlayerCreatures getPlayerCreatures(){
		return playerCreatures;
	}
}
