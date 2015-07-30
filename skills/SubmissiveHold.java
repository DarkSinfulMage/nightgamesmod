package skills;

import stance.Position;
import stance.Stance;
import status.ArmLocked;
import status.LegLocked;
import status.Stsflag;
import combat.Combat;
import combat.Result;

import global.Global;
import characters.Attribute;
import characters.Character;
import characters.Trait;

public class SubmissiveHold extends Skill {
	public SubmissiveHold(Character self) {
		super("Submissive Hold", self);
	}

	@Override
	public float priorityMod(Combat c) {
		return getSelf().has(Trait.submissive) ? 4 : 2;
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Seduction) > 10;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return getSelf().canRespond() && c.getStance().sub(getSelf()) && (c.getStance().inserted(getSelf()) || c.getStance().inserted(target))
				&& getSelf().canSpend(getMojoCost(c)) && !target.is(Stsflag.armlocked)&& !target.is(Stsflag.leglocked);
	}

	public int getMojoCost(Combat c) {
		return 10;
	}

	@Override
	public String describe() {
		return "Holds your opponent in position";
	}
	
	private boolean isArmLock(Position p) {
		if (p.en == Stance.missionary) {
			return false;
		}
		return true;
	}

	@Override
	public String getLabel(Combat c){
		if(isArmLock(c.getStance())){
			return "Hand Lock";
		} else {
			return "Leg Lock";
		}
	}
	
	@Override
	public Skill copy(Character user) {
		return new SubmissiveHold(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.positioning;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if (isArmLock(c.getStance())) {
			return Global.format("You entwine {other:name-possessive} fingers with your own, holding her in her position.", getSelf(), target);
		} else {
			return Global.format("You embrace {other:name} and wrap your legs around her waist, holding her inside you.", getSelf(), target);
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier,
			Character target) {
		if (isArmLock(c.getStance())) {
			return Global.format("{self:SUBJECT} entwines your fingers with her own, holding you in your position.", getSelf(), target);
		} else {
			return Global.format("{self:SUBJECT} embraces you and wrap her lithesome legs around your waist, holding you inside her.", getSelf(), target);
		}
	}

	public String getTargetOrganType(Combat c, Character target) {
		return "none";
	}

	public String getWithOrganType(Combat c, Character target) {
		return "none";
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		if (getSelf().human()) {
			c.write(getSelf(), deal(c, 0, Result.normal, target));
		} else {
			c.write(getSelf(), receive(c, 0, Result.normal, target));
		}
		if (isArmLock(c.getStance())) {
			target.add(c, new ArmLocked(target, getSelf().get(Attribute.Power)));
		} else {
			target.add(c, new LegLocked(target, getSelf().get(Attribute.Power)));
		}
		return true;
	}
}
