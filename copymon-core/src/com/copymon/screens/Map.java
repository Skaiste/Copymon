package com.copymon.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Map{

	public TiledMap map;
	public int mapN = 1;
	public String place = "map";
	
	public Map(){
		map = new TmxMapLoader().load("continue/maps/map" + mapN + ".tmx");
	}
	public void changeMap(){
		map.dispose();
		map = new TmxMapLoader().load("continue/maps/map" + mapN + ".tmx");
	}
	
	public void render(OrthogonalTiledMapRenderer renderer){
		if (mapN == 1)
		{
			if (place.equalsIgnoreCase("map"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("map"));
				renderer.getSpriteBatch().end();
			}
			else if (place.equalsIgnoreCase("home"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("home1"));
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("home2"));
				renderer.getSpriteBatch().end();
			}
			else if (place.equalsIgnoreCase("lab"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("lab1"));
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("lab2"));
				renderer.getSpriteBatch().end();
			}
			else if (place.equalsIgnoreCase("health"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("health1"));
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("health2"));
				renderer.getSpriteBatch().end();
			}
		}
		else if (mapN == 2)
		{
			if (place.equalsIgnoreCase("map"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("map"));
				renderer.getSpriteBatch().end();
			}
		}
	}
	public void renderTopLayer(OrthogonalTiledMapRenderer renderer){
		if (mapN == 1)
		{
			if (place.equalsIgnoreCase("home"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("home3"));
				renderer.getSpriteBatch().end();
			}
			else if (place.equalsIgnoreCase("lab"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("lab3"));
				renderer.getSpriteBatch().end();
			}
			else if (place.equalsIgnoreCase("health"))
			{
				renderer.getSpriteBatch().begin();
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("health3"));
				renderer.getSpriteBatch().end();
			}
		}
	}
	public TiledMapTileLayer getCollisionLayer(){
		if (mapN == 1)
		{
			if (place.equalsIgnoreCase("map"))
			{
				return (TiledMapTileLayer) map.getLayers().get(0);
			}
			else if (place.equalsIgnoreCase("home"))
			{
				return (TiledMapTileLayer) map.getLayers().get(1);
			}
			else if (place.equalsIgnoreCase("lab"))
			{
				return (TiledMapTileLayer) map.getLayers().get(4);
			}
			else if (place.equalsIgnoreCase("health"))
			{
				return (TiledMapTileLayer) map.getLayers().get(7);
			}
		}
		else if (mapN == 2)
		{
			if (place.equalsIgnoreCase("map"))
			{
				return (TiledMapTileLayer) map.getLayers().get(0);
			}
		}
		return null;
	}
	public TiledMapTileLayer getComputerLayer(){
		if (mapN == 1)
		{
			if (place.equalsIgnoreCase("map"))
			{
				return null;
			}
			else if (place.equalsIgnoreCase("home"))
			{
				return (TiledMapTileLayer) map.getLayers().get("home2");
			}
			else if (place.equalsIgnoreCase("lab"))
			{
				return (TiledMapTileLayer) map.getLayers().get("lab2");
			}
			else if (place.equalsIgnoreCase("health"))
			{
				return (TiledMapTileLayer) map.getLayers().get("health2");
			}
		}
		else if (mapN == 2)
		{
			if (place.equalsIgnoreCase("map"))
			{
				return null;
			}
		}
		return null;
	}
	public void dispose() {
		map.dispose();
	}
	
	public Vector2 getStartingCoordinates(){
		Vector2 coordinates = new Vector2();
		if (mapN == 1)
		{
			if (place.equals("map")){
				coordinates.x = 40;
				coordinates.y = 128;
			}
			else if (place.equals("home")){
				coordinates.x = 240;
				coordinates.y = 160;
			}
			else if (place.equals("lab")){
				coordinates.x = 576;
				coordinates.y = 192;
			}
			else if (place.equals("health")){
				coordinates.x = 416;
				coordinates.y = 160;
			}
		}
		else if (mapN == 2)
		{
			if (place.equals("map")){
				coordinates.x = 100;
				coordinates.y = 130;
			}
		}
		return coordinates;
	}
	
	public Vector2 getCoordinatesWhenBugged(){
		Vector2 coordinates = new Vector2();
		if (mapN == 1)
		{
			if (place.equals("map")){
				coordinates.x = Continue.getPlayer().getX();
				coordinates.y = Continue.getPlayer().getY();
			}
			else if (place.equals("home")){
				coordinates.x = 240;
				coordinates.y = 160;
			}
			else if (place.equals("lab")){
				coordinates.x = 576;
				coordinates.y = 192;
			}
			else if (place.equals("health")){
				coordinates.x = 416;
				coordinates.y = 160;
			}
		}
		if (mapN == 2)
		{
			if (place.equals("map")){
				coordinates.x = 100;
				coordinates.y = Play.getCamera().getHeight() / 2;
			}
		}
		return coordinates;
	}
	
	public int getWidth(){
		int width = map.getProperties().get("width", Integer.class),
			tileWidth = map.getProperties().get("tilewidth", Integer.class);
		return width * tileWidth;
	}
	public int getHeight(){
		int height = map.getProperties().get("height", Integer.class),
			tileHeight = map.getProperties().get("tileheight", Integer.class);
		return height * tileHeight;
	}
}
