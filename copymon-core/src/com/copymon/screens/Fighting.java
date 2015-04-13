package com.copymon.screens;

public class Fighting {

	// two screens in fighting that may go one after another:
	//		1. Choosing creature at the start and if players creature is out of hp
	//		2. Fighting
	
	private boolean isChoosingScreen = true; 		// states which of the screens should be on
	
	public static void show(){
		
	}
	public static void render(){
		
	}
	public static void dispose(){
		
	}
	
	public void startChoosing(){
		isChoosingScreen = true;
		// dispose fighting screen
		// show choosing screen
	}
	public void startFighting(){
		isChoosingScreen = false;
		// dispose choosing screen		
		// show fighting screen
	}
	
}
