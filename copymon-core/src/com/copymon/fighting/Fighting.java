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

	public Skill chooseBotSkill(){
		
		Random rn = new Random();
		int random = rn.nextInt(100 - 1 + 1) + 1;
		Skill[] skRay = {};
		Skill change = new Skill("", "");
				
		for (int i = 0; i < secondCreature.getActiveSkillN(); i++)
			skRay[i] = secondCreature.getActiveSkillByIndex(i);
		
		for(int i = 0; i < secondCreature.getActiveSkillN(); i++)
			for(int j=i+1; i < secondCreature.getActiveSkillN(); j++)
			if (skRay[i].getPower() < skRay[j % secondCreature.getActiveSkillN()].getPower())
			{
				change = skRay[i];
				skRay[i] = skRay[(i+1) % secondCreature.getActiveSkillN()];
				skRay[(i+1) % secondCreature.getActiveSkillN()] = change;
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
		System.out.println(s.getDisplayName());
	}
	public void choose2PlayerSkill(Skill s){
		secondPSkill = s;
		System.out.println(s.getDisplayName());
	}

	public void doTheAction(){
		// choose who attacks first
		whoAttacksFirst();
		// attack
		attack();
	}

	private void whoAttacksFirst(){
		if(firstCreature.getAgility() < secondCreature.getAgility())
		isFirstPAttackingFirst = true;
		else isFirstPAttackingFirst = false;
	}
	private void attack(){
		Random rn = new Random();
		damageForFirstP = secondPSkill.getPower()*spellEfectivenessByType(secondPSkill.getType(), firstCreature.getType())*((rn.nextInt(120 - 80 + 1) + 80)/100);
		damageForSecondP = firstPSkill.getPower()*spellEfectivenessByType(firstPSkill.getType(), secondCreature.getType())*((rn.nextInt(120 - 80 + 1) + 80)/100);
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
