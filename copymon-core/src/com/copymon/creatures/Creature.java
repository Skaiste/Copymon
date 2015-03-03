package com.copymon.creatures;

import java.util.ArrayList;

import com.copymon.fileHandling.ReadFromXml;

public class Creature {

	final private String fileName = "continue/creatures/creatures.xml";
	final private int maxLvl = 100;
	final private int skillConstant = 8;
	
	private String name;
	private int lvl;
	private int exp;
	private int health;
	private ArrayList <Skill> inactiveSkills, activeSkills;
	
	
	public Creature(String name, int lvl, ArrayList<Skill> inactives, ArrayList<Skill> actives){
		this.name = name;
		exp = 0;
		this.lvl = lvl;
		inactiveSkills = inactives;
		activeSkills = actives;
		health = getHp();
	}
	
	public void getStartingSkills(){
		for (int i = 1; i <= 2; i++)
		{
			String skillName = ReadFromXml.readString(fileName, name, "ability"+i, "name");
			addNewSkilltoActive(skillName);
		}
	}

	public String getDescribtion() {
		return ReadFromXml.readString(fileName, name, "describtion");
	}
	public String getType() {
		return ReadFromXml.readString(fileName, name, "type");
	}
	public String getRealName(){
		return  ReadFromXml.readString(fileName, name, "evolution" + getEvolutionN(), "name");
	}
	
	public int getHp(){
		int minHp = ReadFromXml.readInt(fileName, name, "starting", "hp");
		int maxHp = ReadFromXml.readInt(fileName, name, "maximum", "hp");
		int evolutionN = ReadFromXml.readInt(fileName, name, "evolutionNo");
		return (int)(minHp + (maxHp - minHp) / (maxLvl + (evolutionN - 1) * 4) * (lvl + getEvolutionN() - 1));
	}
	public int getAp(){
		int minAp = ReadFromXml.readInt(fileName, name, "starting", "ap");
		int maxAp = ReadFromXml.readInt(fileName, name, "maximum", "ap");
		int evolutionN = ReadFromXml.readInt(fileName, name, "evolutionNo");
		return (int)(minAp + (maxAp - minAp) / (maxLvl + (evolutionN - 1) * 4) * (lvl + getEvolutionN() - 1));
	}
	public int getDefence(){
		int minDefence = ReadFromXml.readInt(fileName, name, "starting", "defence");
		int maxDefence = ReadFromXml.readInt(fileName, name, "maximum", "defence");
		int evolutionN = ReadFromXml.readInt(fileName, name, "evolutionNo");
		return (int)(minDefence + (maxDefence - minDefence) / (maxLvl + (evolutionN - 1) * 4) * (lvl + getEvolutionN() - 1));
	}
	public int getAgility(){
		int minAgility = ReadFromXml.readInt(fileName, name, "starting", "agility");
		int maxAgility = ReadFromXml.readInt(fileName, name, "maximum", "agility");
		int evolutionN = ReadFromXml.readInt(fileName, name, "evolutionNo");
		return (int)(minAgility + (maxAgility - minAgility) / (maxLvl + (evolutionN - 1) * 4) * (lvl + getEvolutionN() - 1));
	}
	
	public boolean hasEvolved(){
		boolean evolved = false;
		if (getEvolutionN() < ReadFromXml.readInt(fileName, name, "evolutionNo"))
		{
			if (lvl == ReadFromXml.readInt(fileName, name, "evolution" + (getEvolutionN()+1), "lvl"))
				evolved = true;
		}
		return evolved;
	}
	
	public boolean shouldGetSkill(){
		boolean should = false;
		int totalSkills = ReadFromXml.readInt(fileName, name, "abilityNo");
		int currentSkills = inactiveSkills.size() + activeSkills.size();
		if (currentSkills == totalSkills)
			; // if there are no more skills to receive
		else if (lvl == Math.round(96 - (totalSkills - (currentSkills - 1)) * skillConstant)){
			should = true;
		}
		return should;
	}
	
	public int getInactiveSkillN(){
		return inactiveSkills.size();
	}
	public int getActiveSkillN(){
		return activeSkills.size();
	}

	public Skill getActiveSkillByName(String skillName){
		if (getActiveSkillN() > 0)
		{
			for (int i = 0; i < getActiveSkillN(); i++){
				if (activeSkills.get(i).getName().equals(skillName))
					return activeSkills.get(i);
			}
		}
		return null;
	}
	public Skill getActiveSkillByIndex(int index){
		return activeSkills.get(index);
	}
	public Skill getInactiveSkillByName(String skillName){
		if (getInactiveSkillN() > 0)
		{
			for (int i = 0; i < getActiveSkillN(); i++){
				if (inactiveSkills.get(i).getName().equals(skillName)){
					return inactiveSkills.get(i);
				}
			}
		}
		return null;
	}
	public Skill getInactiveSkillByIndex(int index){
		return inactiveSkills.get(index);
	}
	
	// add skill when the creature should get skill (when the time has come)
	public void addNewSkilltoActive(String skillName){
		if (getActiveSkillN() >= 4){
			activeSkills.remove(getActiveSkillByName(skillName));
			int totalSkills = getActiveSkillN() + getInactiveSkillN();
			addSkillToActive(totalSkills + 1);
		}
		else {
			int totalSkills = getActiveSkillN() + getInactiveSkillN();
			addSkillToActive(totalSkills + 1);
		}
	}
	public void addNewSkilltoInactive(){
		if (shouldGetSkill()){
			int totalSkills = getActiveSkillN() + getInactiveSkillN();
			addSkillToInactive(totalSkills + 1);
		}
	}
	public void addSkillToActive(int skillIndex){
		String sName = ReadFromXml.readString(fileName, name, "ability" + skillIndex, "name");
		String sType = ReadFromXml.readString(fileName, name, "ability" + skillIndex, "type");
		activeSkills.add(new Skill(sName, sType));
	}
	public void addSkillToInactive(int skillIndex){
		String sName = ReadFromXml.readString(fileName, name, "ability" + skillIndex, "name");
		String sType = ReadFromXml.readString(fileName, name, "ability" + skillIndex, "type");
		inactiveSkills.add(new Skill(sName, sType));
	}
	
	public void addSkillFromActiveToInactive(int i) {
		if (getActiveSkillN() > 0)
		{
			inactiveSkills.add(activeSkills.get(i));
			activeSkills.remove(activeSkills.get(i));
		}
	}
	public boolean addSkillFromInactiveToActive(int i) { 		// fails if there isn't enough space
		boolean success = true;
		
		if (getInactiveSkillN() > 0){
			if (getActiveSkillN() >= 4)
				success = false;
			else{
				activeSkills.add(inactiveSkills.get(i));
				inactiveSkills.remove(inactiveSkills.get(i));
			}
		}
		else
			success = false;
		
		return success;
	}
	
	public void raiseLvl(){
		lvl++;
	}
	public void raiseExp(int n){
		exp += n;
	}
	
	public boolean levelUp(){
		boolean up = false;
		int needExp = (int) Math.pow(lvl * 3, 3);
		if ((exp >= needExp) && (lvl < maxLvl))
		{
			up = true;
			exp -= needExp;
			raiseLvl();
		} 
		return up;
	}
	
  	private int getEvolutionN(){
		int evolutionN = ReadFromXml.readInt(fileName, name, "evolutionNo");
		for (int i = 1; i <= evolutionN; i++){
			if (lvl > ReadFromXml.readInt(fileName, name, "evolution" + i, "lvl"))
			{
				return i;
			}
		}
		return 0;
	}

  	public String getName() {
		return name;
	}

	public int getLvl() {
		return lvl;
	}
	
	public int getHealth() {
		return health;
	}

	public int getExp() {
		return exp;
	}

	public ArrayList<Skill> getInactiveSkills() {
		return inactiveSkills;
	}

	public ArrayList<Skill> getActiveSkills() {
		return activeSkills;
	}

	public void getSkillsForBot(){

		// Counting how many skills are availible
		int totalSkills = ReadFromXml.readInt(fileName, name, "abilityNo") - 2;
		int currentSkills = 2;
		int skillHappens = Math.round(96 - (totalSkills - (currentSkills - 1)) * skillConstant);
		for (int tmpLvl = 1; (tmpLvl <= lvl) && (tmpLvl <= skillHappens); tmpLvl++)
		{
			if (tmpLvl == skillHappens)
				currentSkills++;
			skillHappens = Math.round(96 - (totalSkills - (currentSkills - 1)) * skillConstant);
		}
		// Adding the skills
		for (int i = currentSkills; i > 0; i--)
		{
			// to active
			if (currentSkills - i < 4)
			{
				addSkillToActive(i);
			}
			// to inactive
			else
			{
				addSkillToInactive(i);
			}
		}
	}
	
	public void setFullHp(){
		health = getHp();
	}
}
