package com.copymon.creatures;

import java.util.ArrayList;

public class PlayerCreatures {

	private ArrayList<Creature> activeCreatures, inactiveCreatures;
	
	public PlayerCreatures(){
		activeCreatures = new ArrayList<Creature>();
		inactiveCreatures = new ArrayList<Creature>();
	}
	
	public void addActiveCreature(Creature c){
		if (activeCreatures.size() < 5)
			activeCreatures.add(c);
	}
	public void addInactiveCreature(Creature c){
		inactiveCreatures.add(c);
	}
	
	public void removeActiveCreature(Creature c){
		activeCreatures.remove(c);
	}
	public void removeInactiveCreature(Creature c){
		inactiveCreatures.remove(c);
	}

	public Creature getActiveCreature(String name){
		if(activeCreatures.size() > 0){
			for (int i = 0; i < activeCreatures.size(); i++)
			{
				if (activeCreatures.get(i).getName().equals(name))
					return activeCreatures.get(i);
			}
		}
		return null;
	}
	public Creature getInactiveCreature(String name){
		if(inactiveCreatures.size() > 0){
			for (int i = 0; i < inactiveCreatures.size(); i++)
			{
				if (inactiveCreatures.get(i).getName().equals(name))
					return inactiveCreatures.get(i);
			}
		}
		return null;
	}
	
	public Creature getInactiveCreature(int i){
		return inactiveCreatures.get(i);
	}
	public Creature getActiveCreature(int i){
		return activeCreatures.get(i);
	}
	
	public boolean fromActiveToInactive(int i){
		boolean succeed = true;
		if (activeCreatures.size() <= 0)
			succeed = false;
		else
		{
			addInactiveCreature(getActiveCreature(i));
			removeActiveCreature(getActiveCreature(i));
		}
		return succeed;
	}
	public boolean fromInactiveToActive(int i){
		boolean succeed = true;
		if ((inactiveCreatures.size() <= 0) && (inactiveCreatures.size() > 6))
			succeed = false;
		else
		{
			addActiveCreature(getInactiveCreature(i));
			removeInactiveCreature(getInactiveCreature(i));
		}
		return succeed;
	}
	
	public void healAllActive(){
		for (int i = 0; i < getActiveCreatureN(); i++)
		{
			activeCreatures.get(i).setFullHp();
		}
	}
	
	public int getActiveCreatureN(){
		return activeCreatures.size();
	}
	public int getInactiveCreatureN(){
		return inactiveCreatures.size();
	}
	
	public ArrayList<Creature> getActiveCreatures() {
		return activeCreatures;
	}

	public ArrayList<Creature> getInactiveCreatures() {
		return inactiveCreatures;
	}

	public boolean areAllActiveCreaturesWeak(){
		boolean allweak = true;
		for (int i = 0; i < activeCreatures.size(); i++){
			if(activeCreatures.get(i).getHealth() > 0){
				allweak = false;
				break;
			}
		}
		return allweak;
	}
}
