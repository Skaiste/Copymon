package com.copymon.creatures;

public enum Type {
    NORMAL, GRASS, FIRE, GHOST, ELECTRIC, ROCK, WATER, PSYCHIC, FLYING, FIGHTING, BUG, POISON, DARK, GROUND, STEEL;
    
    public String toString(){
    	switch(this){
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
}
