package com.copymon.creatures;

import com.copymon.fileHandling.ReadFromXml;

public class Skill {

	public enum Type {
	    NORMAL, GRASS, FIRE, GHOST, ELECTRIC, ROCK, WATER, PSYCHIC, FLYING, FIGHTING, BUG, POISON, DARK, GROUND, STEEL 
	}
	
	private String name;
	private Type type;
	final private String fileName1 = "continue/creatures/"; 
	final private String fileName2 = "Skills.xml"; 
	
	public Skill(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return name;
	}
	public String getDisplayName(){
		return ReadFromXml.readString(fileName1 + type + fileName2, name, "name");
	}
	public Type getType(){
		return type;
	}
	public String getDescribtion(){
		return ReadFromXml.readString(fileName1 + type + fileName2, name, "describtion");
	}
	public int getPower(){
		return ReadFromXml.readInt(fileName1 + type + fileName2, name, "power");
	}
}
