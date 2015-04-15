package com.copymon.fighting;

import com.copymon.creatures.Creature;
import com.copymon.creatures.Skill;
import com.copymon.creatures.Type;

public class Fighting {

	// creatures that are fighting
	private Creature firstCreature, secondCreature;

	// skills that each player chooses, changes at each step
	private Skill firstPSkill, secondPSkill;

	// states whether the first player is attacking first, changes at each step
	private boolean isFirstPAttackingFirst;

	// damage dealt to each creature, changes at each step
	private int damageForFirstP, damageForSecondP;

	public Fighting(Creature firstCreature, Creature secondCreature){
		this.firstCreature = firstCreature;
		this.secondCreature = secondCreature;
	}

	public int spellEfectivenessByType(Type skillType,Type oponentType){
		switch(oponentType){
		case GRASS:
			switch(skillType){
			case GRASS:
				return 100;
			case BUG:
				return 125;

			case DARK:
				return 100;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 125;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 100;
				
			case GROUND:
				return 125;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 125;
				
			case PSYCHIC:
				return 100;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 75;		
			}
			break;

		case BUG:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 100;

			case DARK:
				return 75;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 125;
				
			case FIRE:
				return 125;
				
			case FLYING:
				return 125;
				
			case GHOST:
				return 75;
				
			case GROUND:
				return 100;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 75;
				
			case PSYCHIC:
				return 75;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 100;
				
			case WATER:
				return 125;		
			}
			break;

		case DARK:
			switch(skillType){
			case GRASS:
				return 100;

			case BUG:
				return 100;

			case DARK:
				return 100;

			case ELECTRIC:
				return 125;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 125;
				
			case FLYING:
				return 100;
				
			case GHOST:
				return 75;
				
			case GROUND:
				return 100;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 100;
				
			case PSYCHIC:
				return 125;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 75;				
			}
			break;

		case ELECTRIC:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 100;

			case DARK:
				return 100;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 125;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 100;
				
			case PSYCHIC:
				return 125;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 100;
				
			case WATER:
				return 75;		
			}
			break;

		case FIGHTING:
			switch(skillType){
			case GRASS:
				return 125;

			case BUG:
				return 75;

			case DARK:
				return 125;

			case ELECTRIC:
				return 125;

			case FIGHTING:
				return 100;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 125;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 100;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 100;
				
			case PSYCHIC:
				return 125;
				
			case ROCK:
				return 75;
				
			case STEEL:
				return 100;
				
			case WATER:
				return 75;		
			}
			break;
			
		case FIRE:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 75;

			case DARK:
				return 100;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 100;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 125;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 75;
				
			case PSYCHIC:
				return 125;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 100;
				
			case WATER:
				return 125;		
			}
			break;
			
		case FLYING:
			switch(skillType){
			case GRASS:
				return 125;

			case BUG:
				return 75;

			case DARK:
				return 100;

			case ELECTRIC:
				return 125;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 75;
				
			case FLYING:
				return 100;
				
			case GHOST:
				return 100;
				
			case GROUND:
				return 75;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 125;
				
			case PSYCHIC:
				return 100;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 75;
				
			case WATER:
				return 125;			
			}
			break;
			
		case GHOST:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 75;

			case DARK:
				return 125;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 125;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 75;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 75;
				
			case PSYCHIC:
				return 75;
				
			case ROCK:
				return 75;
				
			case STEEL:
				return 100;
				
			case WATER:
				return 75;
			}
			break;
			
		case GROUND:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 100;

			case DARK:
				return 75;

			case ELECTRIC:
				return 75;

			case FIGHTING:
				return 100;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 100;
				
			case GHOST:
				return 100;
				
			case GROUND:
				return 75;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 100;
				
			case PSYCHIC:
				return 125;
						
			case ROCK:
				return 100;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 100;
			}
			break;
			
		case NORMAL:
			switch(skillType){
			case GRASS:
				return 125;

			case BUG:
				return 125;

			case DARK:
				return 75;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 125;
				
			case FIRE:
				return 125;
				
			case FLYING:
				return 125;
				
			case GHOST:
				return 75;
				
			case GROUND:
				return 125;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 100;
				
			case PSYCHIC:
				return 100;
				
			case ROCK:
				return 100;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 100;		
			}
			break;
			
		case POISON:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 125;

			case DARK:
				return 75;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 125;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 100;
				
			case GROUND:
				return 100;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 75;
				
			case PSYCHIC:
				return 100;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 100;		
			}
			break;
			
		case PSYCHIC:
			switch(skillType){
			case GRASS:
				return 100;

			case BUG:
				return 125;

			case DARK:
				return 75;

			case ELECTRIC:
				return 100;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 100;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 125;
				
			case PSYCHIC:
				return 75;
				
			case ROCK:
				return 125;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 100;		
			}
			break;
			
		case ROCK:
			switch(skillType){
			case GRASS:
				return 100;

			case BUG:
				return 75;

			case DARK:
				return 100;

			case ELECTRIC:
				return 75;

			case FIGHTING:
				return 100;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 100;
				
			case GROUND:
				return 75;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 75;
				
			case PSYCHIC:
				return 125;
				
			case ROCK:
				return 100;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 125;		
			}
			break;
			
		case STEEL:
			switch(skillType){
			case GRASS:
				return 75;

			case BUG:
				return 75;

			case DARK:
				return 100;

			case ELECTRIC:
				return 125;

			case FIGHTING:
				return 75;
				
			case FIRE:
				return 100;
				
			case FLYING:
				return 75;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 75;
				
			case NORMAL:
				return 75;
				
			case POISON:
				return 75;
				
			case PSYCHIC:
				return 125;
				
			case ROCK:
				return 100;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 75;		
			}
			break;
			
		case WATER:
			switch(skillType){
			case GRASS:
				return 100;

			case BUG:
				return 75;

			case DARK:
				return 100;

			case ELECTRIC:
				return 125;

			case FIGHTING:
				return 100;
				
			case FIRE:
				return 75;
				
			case FLYING:
				return 100;
				
			case GHOST:
				return 125;
				
			case GROUND:
				return 100;
				
			case NORMAL:
				return 100;
				
			case POISON:
				return 125;
				
			case PSYCHIC:
				return 100;
				
			case ROCK:
				return 100;
				
			case STEEL:
				return 125;
				
			case WATER:
				return 75;		
			}
			break;				
		}

		return 0;
	}

	public Skill chooseBotSkill(){
		for (int i = 1; i < firstCreature.getActiveSkillN(); i++){
			firstCreature.getActiveSkillByIndex(i).getPower();
		}
		// change return variable
		return firstCreature.getActiveSkillByIndex(0);
	}

	public void choose1PlayerSkill(Skill s){
		firstPSkill = s;
	}
	public void choose2PlayerSkill(Skill s){
		secondPSkill = s;
	}

	public void doTheAction(){
		// choose who attacks first
		whoAttacksFirst();
		// attack
		attack();
	}

	private void whoAttacksFirst(){

		// change this value
		isFirstPAttackingFirst = true;
	}
	private void attack(){

		// change these values
		damageForFirstP = 0;
		damageForSecondP = 0;
	}

	public boolean isFirstPAttackingFirst() {
		return isFirstPAttackingFirst;
	}

	public int getDamageForFirstP() {
		return damageForFirstP;
	}

	public int getDamageForSecondP() {
		return damageForSecondP;
	}

	public void setFirstCreature(Creature firstCreature) {
		this.firstCreature = firstCreature;
	}

	public void setSecondCreature(Creature secondCreature) {
		this.secondCreature = secondCreature;
	}

}
