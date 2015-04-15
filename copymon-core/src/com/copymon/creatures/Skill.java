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
		if (t.toLowerCase().equals("bug"))
			return Type.BUG;
		else if (t.toLowerCase().equals("dark"))
			return Type.DARK;
		else if (t.toLowerCase().equals("electric"))
			return Type.ELECTRIC;
		else if (t.toLowerCase().equals("fighting"))
			return Type.FIGHTING;
		else if (t.toLowerCase().equals("fire"))
			return Type.FIRE;
		else if (t.toLowerCase().equals("flying"))
			return Type.FLYING;
		else if (t.toLowerCase().equals("ghost"))
			return Type.GHOST;
		else if (t.toLowerCase().equals("grass"))
			return Type.GRASS;
		else if (t.toLowerCase().equals("ground"))
			return Type.GROUND;
		else if (t.toLowerCase().equals("normal"))
			return Type.NORMAL;
		else if (t.toLowerCase().equals("poison"))
			return Type.POISON;
		else if (t.toLowerCase().equals("psychic"))
			return Type.PSYCHIC;
		else if (t.toLowerCase().equals("rock"))
			return Type.ROCK;
		else if (t.toLowerCase().equals("steel"))
			return Type.STEEL;
		else 
			return Type.WATER;
	}
	
	private String convertTypeToString(Type t){
		switch(t){
		case BUG:
			return "Bug";
		case DARK:
			return "Dark";
		case ELECTRIC:
			return "Electric";
		case FIGHTING:
			return "Fighting";
		case FIRE:
			return "Fire";
		case FLYING:
			return "Flying";
		case GHOST:
			return "Ghost";
		case GRASS:
			return "Grass";
		case GROUND:
			return "Ground";
		case NORMAL:
			return "Normal";
		case POISON:
			return "Poison";
		case PSYCHIC:
			return "Psychic";
		case ROCK:
			return "Rock";
		case STEEL:
			return "Steel";
		case WATER:
			return "Water";
		default:
			return "";
		}
	}
	
	public String getName(){
		return name;
	}
	public String getDisplayName(){
		return ReadFromXml.readString(fileName1 + convertTypeToString(type) + fileName2, name, "name");
	}
	public Type getType(){
		return type;
	}
	public String getTypeInString(){
		return convertTypeToString(type);
	}
	public String getDescribtion(){
		return ReadFromXml.readString(fileName1 + convertTypeToString(type) + fileName2, name, "describtion");
	}
	public int getPower(){
		return ReadFromXml.readInt(fileName1 + convertTypeToString(type) + fileName2, name, "power");
	}
}
