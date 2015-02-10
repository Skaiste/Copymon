package com.copymon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.copymon.screens.Play;

public class Copymon extends Game {

	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		setScreen(new Play());
	}
	@Override
	public void render() {
		super.render();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
}
