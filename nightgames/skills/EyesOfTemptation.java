package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.status.Enthralled;

public class EyesOfTemptation extends Skill {

	public EyesOfTemptation(Character self) {
		super("Eyes of Temptation", self, 5);
	}

	@Override
	public boolean requirements(Combat c, Character user, Character target) {
		return user.get(Attribute.Seduction) >= 45 || user.get(Attribute.Dark) >= 20 || user.get(Attribute.Arcane) >= 10;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return getSelf().canRespond() && c.getStance().facing() && !target.wary();
	}

	@Override
	public int getMojoCost(Combat c) {
		return 40;
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		Result result = target.roll(this, c, accuracy(c))? Result.normal : Result.miss;
		if(getSelf().human()) {
			c.write(getSelf(),deal(c,0,result, target));
		}
		else if(target.human()) {
			c.write(getSelf(),receive(c,0,result, target));
		}
		if (result == Result.normal) {
			target.add(c, new Enthralled(target, getSelf(), 5));
			getSelf().emote(Emotion.dominant, 50);
		}
		return result != Result.miss;
	}

	@Override
	public Skill copy(Character user) {
		return new EyesOfTemptation(user);
	}
	public int speed(){
		return 9;
	}

	@Override
	public int accuracy(Combat c) {
		return 100;
	}

	public Tactics type(Combat c) {
		return Tactics.pleasure;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if (modifier == Result.normal)
			return Global.format("As {other:subject-action:gaze|gazes} into {self:name-possessive} eyes, {other:subject-action:feel|feels} {other:possessive} will slipping into the abyss.", getSelf(), target);
		else
			return Global.format("{other:SUBJECT-ACTION:look|looks} away as soon as {self:subject-action:focus|focuses} {self:possessive} eyes on {other:direct-object}", getSelf(), target);
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		return deal(c, damage, modifier, target);
	}

	@Override
	public String describe(Combat c) {
		return "Enthralls your opponent with a single gaze.";
	}
}
