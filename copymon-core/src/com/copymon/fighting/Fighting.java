package com.copymon.fighting;

import java.util.Random;

import com.copymon.creatures.Creature;
import com.copymon.creatures.Skill;
import com.copymon.creatures.Type;

public class Fighting {
	// first player is the player who is accessing the game directly
	// second player may be bot, or other player who is accessing the fight indirectly

	// creatures that are fighting
	private Creature firstCreature, secondCreature;

	// skill effectiveness
	final int NOTEFFECTIVE = 75;
	final int EFFECTIVE = 100;
	final int VERYEFFECTIVE = 125;

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
	
	public Skill chooseBotSkill(){
		Random rn = new Random();
		int random = rn.nextInt(100 - 1 + 1) + 1;
		Skill[] skRay = new Skill[secondCreature.getActiveSkillN()];
		Skill change = new Skill("", "");

		
		for (int i = 0; i < secondCreature.getActiveSkillN(); i++)
			skRay[i] = secondCreature.getActiveSkillByIndex(i);
		
		for(int i = 0; i < secondCreature.getActiveSkillN()-1; i++)
		{
			for(int j=i+1; j < secondCreature.getActiveSkillN(); j++)
			{	
				if (skRay[i].getPower() < skRay[j].getPower())
				{
					change = skRay[i];
					skRay[i] = skRay[i+1];
					skRay[i+1] = change;
				}
				
			}
		}
		

		if(random <= 100)
		{
			if(random <= 65)
			{
				if(random <= 35)
				{
					if(random <= 15)
					{
						return skRay[3 % secondCreature.getActiveSkillN()];
					} //end of random <= 15
					return skRay[2 % secondCreature.getActiveSkillN()];
				} //end of random <=35
				return skRay[1];
			} //end of random <=65
			return skRay[0];
		} //end of random<=100

		// program should never reach here
		Skill fail = new Skill("fail", "fail");
		return fail;

	}

	public void choose1PlayerSkill(Skill s){
		firstPSkill = s;
	}
	public void choose2PlayerSkill(Skill s){
		secondPSkill = s;
	}

	public void doTheAction(boolean firstAttack){
		if (firstAttack){
			if (isFirstPAttackingFirst)
				firstPAttacks();
			else
				secondPAttacks();
		}
		else{
			if (!isFirstPAttackingFirst)
				firstPAttacks();
			else
				secondPAttacks();
		}
	}

	public void whoAttacksFirst(){
		if(firstCreature.getAgility() > secondCreature.getAgility())
			isFirstPAttackingFirst = true;
		else isFirstPAttackingFirst = false;
	}
	/*
	private void attack(){
		Random rn = new Random();
		
		damageForFirstP = (int)(secondPSkill.getPower()*(spellEfectivenessByType(secondPSkill.getType(), firstCreature.getType())/100f)*((rn.nextInt(120 - 80 + 1) + 80)/100f));
		damageForSecondP = (int)(firstPSkill.getPower()*(spellEfectivenessByType(firstPSkill.getType(), secondCreature.getType())/100f)*((rn.nextInt(120 - 80 + 1) + 80)/100f));
		
		if(isFirstPAttackingFirst)
		{
			secondCreature.getDamagedBy(damageForSecondP);
			if(secondCreature.getHealth() != 0)
			{
				firstCreature.getDamagedBy(damageForFirstP);
			}
		} else
		{
			firstCreature.getDamagedBy(damageForFirstP);
			if(firstCreature.getHealth() != 0)
			{
				secondCreature.getDamagedBy(damageForSecondP);
			}
		}
	}
	*/
	private void firstPAttacks(){
		if(canFirstPAttack()){
			Random rn = new Random();
			damageForSecondP = (int)(firstPSkill.getPower()*(spellEfectivenessByType(firstPSkill.getType(), secondCreature.getType())/100f)*((rn.nextInt(120 - 80 + 1) + 80)/100f));
			secondCreature.getDamagedBy(damageForSecondP);
			
		}
	}
	private void secondPAttacks(){
		if (canSecondPAttack()){
			Random rn = new Random();
			damageForFirstP = (int)(secondPSkill.getPower()*(spellEfectivenessByType(secondPSkill.getType(), firstCreature.getType())/100f)*((rn.nextInt(120 - 80 + 1) + 80)/100f));
			firstCreature.getDamagedBy(damageForFirstP);
		}
	}
	
	public boolean canFirstPAttack(){
		if (firstCreature.getHealth() > 0)
			return true;
		else
			return false;
	}
	public boolean canSecondPAttack(){
		if (secondCreature.getHealth() > 0)
			return true;
		else
			return false;
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

	public int spellEfectivenessByType(Type skillType,Type oponentType){
		switch(oponentType){
		case GRASS:
			switch(skillType){
			case GRASS:
				return EFFECTIVE;
			case BUG:
				return VERYEFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return VERYEFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return EFFECTIVE;
			case GROUND:
				return VERYEFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return VERYEFFECTIVE;
			case PSYCHIC:
				return EFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;		
			}
			break;
		case BUG:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return EFFECTIVE;
			case DARK:
				return NOTEFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return VERYEFFECTIVE;
			case FIRE:
				return VERYEFFECTIVE;
			case FLYING:
				return VERYEFFECTIVE;
			case GHOST:
				return NOTEFFECTIVE;
			case GROUND:
				return EFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return NOTEFFECTIVE;
			case PSYCHIC:
				return NOTEFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return EFFECTIVE;
			case WATER:
				return VERYEFFECTIVE;		
			}
			break;
		case DARK:
			switch(skillType){
			case GRASS:
				return EFFECTIVE;
			case BUG:
				return EFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return VERYEFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return VERYEFFECTIVE;
			case FLYING:
				return EFFECTIVE;
			case GHOST:
				return NOTEFFECTIVE;
			case GROUND:
				return EFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return EFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;				
			}
			break;
		case ELECTRIC:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return EFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return VERYEFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return EFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return EFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;		
			}
			break;
		case FIGHTING:
			switch(skillType){
			case GRASS:
				return VERYEFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return VERYEFFECTIVE;
			case ELECTRIC:
				return VERYEFFECTIVE;
			case FIGHTING:
				return EFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return VERYEFFECTIVE;
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return EFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return EFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return NOTEFFECTIVE;
			case STEEL:
				return EFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;		
			}
			break;
		case FIRE:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return EFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return VERYEFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return NOTEFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return EFFECTIVE;
			case WATER:
				return VERYEFFECTIVE;		
			}
			break;
		case FLYING:
			switch(skillType){
			case GRASS:
				return VERYEFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return VERYEFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return NOTEFFECTIVE;
			case FLYING:
				return EFFECTIVE;
			case GHOST:
				return EFFECTIVE;
			case GROUND:
				return NOTEFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return VERYEFFECTIVE;
			case PSYCHIC:
				return EFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return NOTEFFECTIVE;
			case WATER:
				return VERYEFFECTIVE;			
			}
			break;
		case GHOST:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return VERYEFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return VERYEFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;	
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return NOTEFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return NOTEFFECTIVE;
			case PSYCHIC:
				return NOTEFFECTIVE;
			case ROCK:
				return NOTEFFECTIVE;
			case STEEL:
				return EFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;
			}
			break;

		case GROUND:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return EFFECTIVE;
			case DARK:
				return NOTEFFECTIVE;
			case ELECTRIC:
				return NOTEFFECTIVE;
			case FIGHTING:
				return EFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return EFFECTIVE;
			case GHOST:
				return EFFECTIVE;
			case GROUND:
				return NOTEFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return EFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return EFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return EFFECTIVE;
			}
			break;
		case NORMAL:
			switch(skillType){
			case GRASS:
				return VERYEFFECTIVE;
			case BUG:
				return VERYEFFECTIVE;
			case DARK:
				return NOTEFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return VERYEFFECTIVE;
			case FIRE:
				return VERYEFFECTIVE;
			case FLYING:
				return VERYEFFECTIVE;
			case GHOST:
				return NOTEFFECTIVE;
			case GROUND:
				return VERYEFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return EFFECTIVE;
			case PSYCHIC:
				return EFFECTIVE;
			case ROCK:
				return EFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return EFFECTIVE;		
			}
			break;
		case POISON:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return VERYEFFECTIVE;
			case DARK:
				return NOTEFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return VERYEFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return EFFECTIVE;
			case GROUND:
				return EFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return NOTEFFECTIVE;
			case PSYCHIC:
				return EFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return EFFECTIVE;		
			}
			break;
		case PSYCHIC:
			switch(skillType){
			case GRASS:
				return EFFECTIVE;
			case BUG:
				return VERYEFFECTIVE;
			case DARK:
				return NOTEFFECTIVE;
			case ELECTRIC:
				return EFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return EFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return VERYEFFECTIVE;
			case PSYCHIC:
				return NOTEFFECTIVE;
			case ROCK:
				return VERYEFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return EFFECTIVE;		
			}
			break;
		case ROCK:
			switch(skillType){
			case GRASS:
				return EFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return NOTEFFECTIVE;
			case FIGHTING:
				return EFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return EFFECTIVE;
			case GROUND:
				return NOTEFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return NOTEFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return EFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return VERYEFFECTIVE;		
			}
			break;
		case STEEL:
			switch(skillType){
			case GRASS:
				return NOTEFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return VERYEFFECTIVE;
			case FIGHTING:
				return NOTEFFECTIVE;
			case FIRE:
				return EFFECTIVE;
			case FLYING:
				return NOTEFFECTIVE;
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return NOTEFFECTIVE;
			case NORMAL:
				return NOTEFFECTIVE;
			case POISON:
				return NOTEFFECTIVE;
			case PSYCHIC:
				return VERYEFFECTIVE;
			case ROCK:
				return EFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;		
			}
			break;

		case WATER:
			switch(skillType){
			case GRASS:
				return EFFECTIVE;
			case BUG:
				return NOTEFFECTIVE;
			case DARK:
				return EFFECTIVE;
			case ELECTRIC:
				return VERYEFFECTIVE;
			case FIGHTING:
				return EFFECTIVE;
			case FIRE:
				return NOTEFFECTIVE;
			case FLYING:
				return EFFECTIVE;
			case GHOST:
				return VERYEFFECTIVE;
			case GROUND:
				return EFFECTIVE;
			case NORMAL:
				return EFFECTIVE;
			case POISON:
				return VERYEFFECTIVE;
			case PSYCHIC:
				return EFFECTIVE;
			case ROCK:
				return EFFECTIVE;
			case STEEL:
				return VERYEFFECTIVE;
			case WATER:
				return NOTEFFECTIVE;		
			}
			break;				
		}
		return 0;
	}

}
