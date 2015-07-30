package skills;

import stance.Position;
import stance.Stance;
import global.Global;
import characters.Attribute;
import characters.Character;
import characters.Trait;
import combat.Combat;
import combat.Result;

public class Grind extends Thrust {
	public Grind(Character self) {
		super("Grind", self);
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Seduction)>=14;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return getSelf().canAct()&&c.getStance().dom(getSelf())&&c.getStance().penetration(getSelf())&&c.getStance().en!=Stance.anal;
	}

	@Override
	public int getMojoBuilt(Combat c) {
		return 5;
	}

	public int[] getDamage(Character target, Position stance) {
		int results[] = new int[2];

		int ms = 6;

		int mt = 4;
		if(getSelf().has(Trait.experienced)){
			mt = mt * 3 / 4;
		}
		mt = Math.max(1, mt);
		results[0] = ms;
		results[1] = mt;
		
		return results;
	}

	@Override
	public Skill copy(Character user) {
		return new Grind(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.fucking;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if (modifier == Result.reverse) {
			return Global.format("{self:SUBJECT-ACTION:grind|grinds} against {other:direct-object}, stimulating {other:possessive} entire manhood and bringing {other:direct-object} closer to climax.", getSelf(), target);
		} else {
			return Global.format("{self:SUBJECT} grind {self:possessive} hips against {other:direct-object} without thrusting. {other:SUBJECT} trembles and gasps as the movement stimulates {other:possessive} clit and the walls of {other:possessive} {other:body-part:pussy}.", getSelf(), target);
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character attacker) {
		return deal(c, damage, modifier, attacker);
	}

	@Override
	public String describe() {
		return "Grind against your opponent with minimal thrusting. Extremely consistent pleasure and builds some mojo";
	}

	@Override
	public String getName(Combat c) {
		return "Grind";
	}
	@Override
	public boolean makesContact() {
		return true;
	}
}
