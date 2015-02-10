package com.copymon.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
	private OrthographicCamera camera;
	
	public void updateBg(){
		camera.update();
		
		setRealX((Continue.getPlayer().getX()) - camera.viewportWidth  / 2);
		setRealY((Continue.getPlayer().getY()) - camera.viewportHeight / 2);
		
		// prevents camera from going through edges
		if (camera.position.x >= Continue.getMap().getWidth() - camera.viewportWidth / 2)
			camera.position.x = Continue.getMap().getWidth() - camera.viewportWidth / 2;
		else if (camera.position.x <= camera.viewportWidth / 2)
			camera.position.x = camera.viewportWidth / 2;
		if (camera.position.y >= Continue.getMap().getHeight() - camera.viewportHeight / 2)
			camera.position.y = Continue.getMap().getHeight() - camera.viewportHeight / 2;
		else if (camera.position.y <= camera.viewportHeight / 2)
			camera.position.y = camera.viewportHeight / 2;
		
		roundPos();
	}
	
	public Camera() {
		camera = new OrthographicCamera();
	}
	
	public void roundPos() {
		camera.position.x = Math.round(camera.position.x);
		camera.position.y = Math.round(camera.position.y);
	}
	
	public void setSize(int width, int height) {
		camera.viewportWidth  = width;
		camera.viewportHeight = height;
	}
	public void setPosition(float x, float y) {
		camera.position.x = x;
		camera.position.y = y;
	}
	public void setPositionZero() {
		camera.position.x = camera.viewportWidth  / 2;
		camera.position.y = camera.viewportHeight / 2;
	}
	
	public float getRealX() {
		return camera.position.x - camera.viewportWidth / 2;
	}
	public float getRealY() {
		return camera.position.y - camera.viewportHeight / 2;
	}
	public void setRealX(float x){
		camera.position.x = camera.viewportWidth / 2 + x;
	}
	public void setRealY(float y){
		camera.position.y = camera.viewportHeight / 2 + y;
	}
	
	public float getX(){
		return camera.position.x;
	}
	public float getY(){
		return camera.position.y;
	}
	public void setX(float x){
		camera.position.x = x;
	}
	public void setY(float y){
		camera.position.y = y;
	}
	public float getWidth(){
		return camera.viewportWidth;
	}
	public float getHeight(){
		return camera.viewportHeight;
	}
	public OrthographicCamera getCamera(){
		return camera;
	}
}
