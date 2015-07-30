package skills;

import combat.Combat;
import combat.Result;

import global.Global;
import characters.Attribute;
import characters.Character;

public class CounterPin extends CounterBase {
	public CounterPin(Character self) {
		super("Counter", self, 4, Global.format("{self:SUBJECT-ACTION:hold|holds} a low stance.", self, self));
	}

	@Override
	public float priorityMod(Combat c) {
		return Global.randomfloat();
	}
	
	@Override
	public void resolveCounter(Combat c, Character target) {
		Restrain skill = new Restrain(getSelf());
		skill.resolve(c, target, true);
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Power) > 12;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return !c.getStance().dom(getSelf()) && !c.getStance().dom(target) && getSelf().canAct();
	}

	@Override
	public int getMojoCost(Combat c) {
		return 10;
	}

	@Override
	public String describe() {
		return "Sets up a counter";
	}

	@Override
	public Skill copy(Character user) {
		return new CounterPin(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.positioning;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if (modifier == Result.setup && getSelf().hasPussy()) {
			return Global.format("You shift into a low stance, beckoning her inside your reach.", getSelf(), target);
		} else {
			return "";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier,
			Character target) {
		if (modifier == Result.setup && getSelf().hasPussy()) {
			return Global.format("Eyeing you carefully, {self:SUBJECT} shifts to a low stance.", getSelf(), target);
		} else {
			return "";
		}
	}
}
