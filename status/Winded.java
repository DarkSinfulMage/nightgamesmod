package status;

import combat.Combat;

import characters.Attribute;
import characters.Character;
import characters.Emotion;

public class Winded extends Status {
	private int duration;
	public Winded(Character affected) {
		super("Winded", affected);
		duration=1;
		flag(Stsflag.stunned);
	}
	public Winded(Character affected, int duration) {
		super("Winded", affected);
		this.duration=duration;
		flag(Stsflag.stunned);
	}

	@Override
	public String describe() {
		if(affected.human()){
			return "You need a moment to catch your breath";
		}
		else{
			return affected.name()+" is panting and trying to recover";
		}
	}

	@Override
	public float fitnessModifier () {
		return -.3f;
	}
	@Override
	public int mod(Attribute a) {
		if(a==Attribute.Power||a==Attribute.Speed){
			return -2;
		} else {
			return 0;
		}
	}

	@Override
	public int regen(Combat c) {
		if(duration<=0){
			affected.removelist.add(this);
		}
		affected.emote(Emotion.nervous,15);
		affected.emote(Emotion.angry,10);
		duration--;
		return affected.getStamina().max()/4;
	}

	@Override
	public int damage(Combat c, int x) {
		return -x;
	}

	@Override
	public int pleasure(Combat c, int x) {
		return 0;
	}

	@Override
	public int weakened(int x) {
		return -x;
	}

	@Override
	public int tempted(int x) {
		return -x;
	}

	@Override
	public int evade() {
		return -10;
	}

	@Override
	public int escape() {
		return 0;
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
		return -10;
	}
	@Override
	public int value() {
		// TODO Auto-generated method stub
		return 0;
	}
}
