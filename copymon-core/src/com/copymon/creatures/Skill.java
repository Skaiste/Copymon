package com.copymon.creatures;

import com.copymon.fileHandling.ReadFromXml;

public class Skill {

	private String name;
	private String type;
	final private String fileName1 = "continue/creatures/"; 
	final private String fileName2 = "Skills.xml"; 
	
	public Skill(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return name;
	}
	public String getDisplayName(){
		return ReadFromXml.readString(fileName1 + type + fileName2, name, "name");
	}
	public String getType(){
		return type;
	}
	public String getDescribtion(){
		return ReadFromXml.readString(fileName1 + type + fileName2, name, "describtion");
	}
	public int getPower(){
		return ReadFromXml.readInt(fileName1 + type + fileName2, name, "power");
	}
}
