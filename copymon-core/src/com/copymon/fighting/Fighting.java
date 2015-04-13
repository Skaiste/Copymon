package com.copymon.fighting;

import com.copymon.creatures.Creature;
import com.copymon.creatures.Skill;

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
	
	public Skill chooseBotSkill(){
		// change return variable
		return new Skill("name", "type");
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
