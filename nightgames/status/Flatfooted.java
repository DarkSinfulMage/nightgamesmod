package nightgames.status;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.combat.Combat;

public class Flatfooted extends DurationStatus {
	public Flatfooted(Character affected, int duration) {
		super("Flat-Footed", affected, duration);
		flag(Stsflag.distracted);
	}

	@Override
	public String describe() {
		if(affected.human()){
			return "You are caught off-guard.";
		}
		else{
			return affected.name()+" is flat-footed and not ready to fight.";
		}
	}

	@Override
	public String initialMessage(Combat c, boolean replaced) {
		return String.format("%s now flatfooted.\n", affected.subjectAction("are", "is"));
	}

	@Override
	public boolean mindgames(){
		return false;
	}
	
	@Override
	public float fitnessModifier () {
		return -3;
	}
	
	@Override
	public int mod(Attribute a) {
		return 0;
	}

	@Override
	public void onRemove(Combat c, Character other) {
		affected.addlist.add(new Wary(affected, 3));
	}

	@Override
	public int regen(Combat c) {
		super.regen(c);
		affected.emote(Emotion.nervous,5);
		return 0;
	}

	@Override
	public int damage(Combat c, int x) {
		return 0;
	}

	@Override
	public double pleasure(Combat c, double x) {
		return 0;
	}

	@Override
	public int weakened(int x) {
		return 0;
	}

	@Override
	public int tempted(int x) {
		return 0;
	}

	@Override
	public int evade() {
		return -10;
	}

	@Override
	public int escape() {
		return -20;
	}

	@Override
	public int gainmojo(int x) {
		return 0;
	}

	@Override
	public int spendmojo(int x) {
		return 0;
	}
	@Override
	public int counter() {
		return -3;
	}

	@Override
	public int value() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new Flatfooted(newAffected, getDuration());
	}
}
