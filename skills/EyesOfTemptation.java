package skills;

import status.Enthralled;
import global.Global;
import characters.Attribute;
import characters.Character;
import characters.Emotion;
import combat.Combat;
import combat.Result;

public class EyesOfTemptation extends Skill {

	public EyesOfTemptation(Character self) {
		super("Eyes of Temptation", self, 5);
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Seduction) > 45 || user.get(Attribute.Dark) > 20 || user.get(Attribute.Arcane) > 10;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return getSelf().canRespond() && c.getStance().facing() && getSelf().canSpend(30) && !target.wary();
	}

	@Override
	public void resolve(Combat c, Character target) {
		Result result = target.roll(this, c, accuracy())? Result.normal : Result.miss;
		getSelf().spendMojo(c, 30);
		if(getSelf().human()) {
			c.write(getSelf(),deal(c,0,result, target));
		}
		else if(target.human()) {
			c.write(getSelf(),receive(c,0,result, target));
		}
		if (result == Result.normal) {
			target.add(new Enthralled(target, getSelf(), 5));
			getSelf().emote(Emotion.dominant, 50);
		}
	}

	@Override
	public Skill copy(Character user) {
		return new EyesOfTemptation(user);
	}
	public int speed(){
		return 9;
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
	public String describe() {
		return "Entralls your opponent with a single gaze.";
	}
}
