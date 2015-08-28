package nightgames.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.custom.requirement.CustomRequirement;
import nightgames.combat.Combat;
import nightgames.skills.Skill;

public abstract class Status implements Cloneable {
	public String name;
	public Character affected;
	protected HashSet<Stsflag> flags;
	protected List<CustomRequirement> requirements;

	public Status(String name, Character affected){
		this.name=name;
		this.affected=affected;
		this.requirements = new ArrayList<>();
		flags = new HashSet<Stsflag>();
	}
	public String toString(){
		return name;
	}
	
	public Collection<Skill> allowedSkills(){
		return Collections.emptySet();
	}
	
	public boolean meetsRequirements(Combat c, Character self, Character other) {
		return requirements.stream().allMatch((req) -> req.meets(c, self, other));
	}

	public abstract String initialMessage(Combat c, boolean replaced);
	public abstract String describe();
	public abstract int mod(Attribute a);
	public abstract int regen(Combat c);
	public abstract int damage(Combat c, int x);
	public abstract double pleasure(Combat c, double x);
	public abstract int weakened(int x);
	public abstract int tempted(int x);
	public abstract int evade();
	public abstract int escape();
	public abstract int gainmojo(int x);
	public abstract int spendmojo(int x);
	public abstract int counter();
	public abstract int value();

	public int drained(int x) {
		return 0;
	}
	public float fitnessModifier () {
		return 0;
	}
	public boolean lingering(){
		return false;
	}
	public void flag(Stsflag status){
		flags.add(status);
	}
	public HashSet<Stsflag> flags(){
		return this.flags;
	}
	public boolean overrides(Status s) {
		return s.getClass() == this.getClass();
	}

	public void replace(Status newStatus) {
	}

	public boolean mindgames(){
		return false;
	}

	public Object clone () {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract Status instance(Character newAffected, Character newOther);
	public String getVariant() {
		return toString();
	}
	public void struggle(Character character) {}
	public void onRemove(Combat c, Character other) {}
}
