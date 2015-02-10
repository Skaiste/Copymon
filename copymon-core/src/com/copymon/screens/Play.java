package com.copymon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class Play implements Screen {

	private static Camera camera;
	
	private boolean isContinue   = Menu.isContinue(),
					isNewGame    = Menu.isNewGame(),
					isWithFriend = Menu.isWithFriend(),
					isOptions	 = Menu.isOptions();
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		//camera.getCamera().update();
		updateMenuBools();
		
		if (isContinue)
			Continue.render(delta);
		else if (isNewGame)
			NewGame.render();
		else if (isWithFriend)
			;
		else if (isOptions)
			Options.render();
		else
			Menu.render();
	}

	@Override
	public void resize(int width, int height) {		
		// set camera size and base position
		camera.setSize(width, height);
		camera.setPositionZero();
	}

	@Override
	public void show() {
		// setting up camera
		camera = new Camera();
		// setting up main menu
		if (isContinue)
			Continue.show();
		else if (isNewGame)
			NewGame.show();
		else if (isWithFriend)
			;
		else if (isOptions)
			Options.show();
		else 
			Menu.show(camera);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		if (isContinue)
			Continue.dispose();
		else if (isNewGame)
			NewGame.dispose();
		else if (isWithFriend)
			;
		else if (isOptions)
			Options.dispose();
		else 
			Menu.dispose();
		
	}
	
	private void updateMenuBools() {
		isContinue 	 = Menu.isContinue();
		isNewGame  	 = Menu.isNewGame();
		isWithFriend = Menu.isWithFriend();
		isOptions    = Menu.isOptions();
	}

	public static Camera getCamera() {
		return camera;
	}
}
