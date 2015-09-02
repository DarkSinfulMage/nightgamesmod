package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.stance.Cowgirl;
import nightgames.stance.Missionary;
import nightgames.stance.Position;
import nightgames.stance.Stance;
import nightgames.status.ArmLocked;
import nightgames.status.LegLocked;

public class Invitation extends Skill {
	private static final String divineStringFemale = "Goddess's Invitation";
	private static final String divineStringMale = "Goddess's Invitation";

	public Invitation(Character self) {
		super("Invitation", self, 6);
	}

	@Override
	public float priorityMod(Combat c) {
		return getSelf().has(Trait.submissive) ? 2	 : 0;
	}

	@Override
	public boolean requirements(Combat c, Character user, Character target) {
		return user.get(Attribute.Seduction) > 25 || user.has(Trait.submissive);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		Position p = c.getStance();
		boolean insertable = (c.getStance().insert(getSelf(), getSelf()) != c.getStance() || c.getStance().insert(target, getSelf()) != c.getStance());
		return insertable && getSelf().canRespond()
				&& getSelf().pantsless() && target.pantsless()
				&&((getSelf().hasDick() && target.hasPussy()) || (getSelf().hasPussy() && target.hasDick()));
	}

	@Override
	public int getMojoCost(Combat c) {
		return 30;
	}

	@Override
	public String describe(Combat c) {
		return "Invites opponent into your embrace";
	}

	@Override
	public Skill copy(Character user) {
		return new Invitation(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.fucking;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if (modifier == Result.miss) {
			return Global.format("You try to hug {other:name} and pull her down, but she twists out of your grasp.\n", getSelf(), target);			
		} else if (getSelf().hasPussy()) {
			return Global.format("You embrace {other:name} and smoothly slide her cock into your folds while she's distracted. You then pull her to the ground on top of you and softly wrap your legs around her waist", getSelf(), target);
		} else {
			return Global.format("You embrace {other:name} and pull her on top of you. Taking advantage of her distraction, you slide down to a cowgirl stance with your cock firmly inside her.", getSelf(), target);
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier,
			Character target) {
		if (modifier == Result.miss) {
			return Global.format("{self:NAME} hugs you softly and tries to pull you into her, but you come to your senses in the nick of time and manage to twist out of her grasp, causing {self:NAME} to pout at you cutely.\n", getSelf(), target);			
		} else if (getSelf().hasPussy()) {
			return Global.format("{self:NAME} embraces you and smoothly slide your cock into her folds while you're distracted. She then pulls you to the ground on top of her and softly wrap her legs around your waist preventing your escape.", getSelf(), target);
		} else {
			return Global.format("{self:NAME} embraces you and pulls you on top of her. Taking advantage of your distraction, she slide down to a cowgirl stance with her cock firmly inside you.", getSelf(), target);
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
		int difficulty = target.getLevel() - (target.getArousal().get() * 10 / target.getArousal().max()) + target.get(Attribute.Seduction);
		int strength = getSelf().getLevel() + getSelf().get(Attribute.Seduction) * (getSelf().has(Trait.submissive) ? 2 : 1) * (hasDivinity() ? 2 : 1);

		boolean success = Global.random(Math.min(Math.max(difficulty - strength, 1), 10)) == 0;
		Result result = Result.normal;
		if (!success) {
			result = Result.miss;
		} else if (hasDivinity()) {
			result = Result.divine;
		}

		if (getSelf().human()) {
			c.write(getSelf(), deal(c, 0, result, target));
		} else {
			c.write(getSelf(), receive(c, 0, result, target));
		}
		if (success) {
			c.setStance(c.getStance().insertRandomDom(target));
			if (c.getStance().en == Stance.missionary) {
				target.add(c, new LegLocked(target, 4 * getSelf().get(Attribute.Power)));
			} else {
				target.add(c, new ArmLocked(target, 4 * getSelf().get(Attribute.Power)));
			}
			(new Thrust(target)).resolve(c, getSelf());
		}
		return success;
	}
	
	public boolean hasDivinity() {
		return getSelf().get(Attribute.Divinity) >= 25;
	}

	@Override
	public String getLabel(Combat c){
		if (hasDivinity()) {
			return getSelf().hasPussy() ? divineStringFemale : divineStringMale;
		} else {
			return "Invitation";
		}
	}
}
