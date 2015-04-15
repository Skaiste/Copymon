package com.copymon.creatures;

import com.copymon.fileHandling.ReadFromXml;

public class Skill {

	
	private String name;
	private Type type;
	final private String fileName1 = "continue/creatures/"; 
	final private String fileName2 = "Skills.xml"; 
	
	public Skill(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public Skill(String name, String type) {
		this.name = name;
		this.type = convertStringToType(type);
	}
	
	private Type convertStringToType(String t){
		
		return Type.ROCK;
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
