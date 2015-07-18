package skills;

import global.Global;
import characters.Attribute;
import characters.Character;
import characters.Emotion;
import combat.Combat;
import stance.FlyingCarry;
import stance.StandingOver;
import combat.Result;

public class Fly extends Fuck {
	public Fly(Character self) {
		super("Fly", self, 5);
	}

	public Fly(String name, Character self) {
		super(name, self, 5);
	}

	@Override
	public boolean requirements(Character user) {
		return (user.body.get("wings").size() > 0) && user.get(Attribute.Power)>=15;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return fuckable(c, target)
				&& !target.wary()
				&& (this.getSelf().canAct())
				&& (c.getStance().mobile(this.getSelf()))
				&& (!c.getStance().prone(this.getSelf()))
				&& c.getStance().facing()
				&& (this.getSelf().getStamina().get() >= 15)
				&& (this.getSelf().canSpend(getMojoSpent()))
				&& (!c.getStance().penetration(this.getSelf()));
	}

	public int getMojoSpent() {
		return 50;
	}

	@Override
	public String describe() {
		return "Take off and fuck your opponent's pussy in the air.";
	}
	public int accuracy(){
		return 0;
	}
	@Override
	public void resolve(Combat c, Character target) {
		String premessage = "";
		getSelf().spendMojo(c, getMojoSpent());
		if(!getSelf().bottom.empty() && getSelfOrgan().isType("cock")) {
			if (getSelf().bottom.size() == 1) {
				premessage = String.format("{self:SUBJECT-ACTION:pull|pulls} down {self:possessive} %s halfway and", getSelf().bottom.get(0).name());
			} else if (getSelf().bottom.size() == 2) {
				premessage = String.format("{self:SUBJECT-ACTION:pull|pulls} down {self:possessive} %s and %s halfway and", getSelf().bottom.get(0).name(), getSelf().bottom.get(1).name());
			}
		}

		premessage = Global.format(premessage, getSelf(), target);
		Result result = target.roll(this, c, accuracy()) ? Result.normal: Result.miss;
		if (this.getSelf().human()) {
			c.write(getSelf(),premessage + deal(c, 0, result, target));
		} else if (target.human()) {
			c.write(getSelf(),premessage + receive(c, 0, result, this.getSelf()));
		}
		if (result == Result.normal) {
			getSelf().emote(Emotion.dominant,50);
			getSelf().emote(Emotion.horny, 30);
			target.emote(Emotion.desperate, 50);
			target.emote(Emotion.nervous, 75);
			c.setStance(new FlyingCarry(this.getSelf(), target));
		} else {
			c.setStance(new StandingOver(target, getSelf()));
		}
	}

	@Override
	public Skill copy(Character target) {
		return new Fly(target);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.fucking;
	}

	@Override
	public String deal(Combat c, int amount,
			Result modifier, Character target) {
		if (modifier == Result.miss){
			return "you grab " + target.name() + " tightly and try to take off. However " +target.pronoun() + " has other ideas. She knees your crotch as you approach and sends you sprawling to the ground.";
		} else {
			return "you grab " + target.name() + " tightly and take off, "
					+ (target.hasDick()&&getSelf().hasPussy() ? "inserting her dick into your hungry " + getSelf().body.getRandomPussy().describe(getSelf()) + "." :
						" holding her helpless in the air and thrusting deep into her wet " + target.body.getRandomPussy().describe(getSelf()) + ".");
		}
	}

	@Override
	public String receive(Combat c, int amount,
			Result modifier, Character target) {
		if (modifier == Result.miss){
			return target.name() + " lunges for you with a hungry look in her eyes. However you have other ideas. You trip her as she approaches and send her sprawling to the floor.";
		} else {
			return "suddenly, " + getSelf().name() + " leaps at you, embracing you tightly"
					+ ". She then flaps her " + getSelf().body.getRandomWings().describe(target) + " hard and before you know it"
					+ " you are twenty feet in the sky held up by her arms and legs."
					+ " Somehow, her dick ended up inside of you in the process and"
					+ " the rythmic movements of her flying arouse you to no end";
		}
	}

	@Override
	public boolean makesContact() {
		return true;
	}
}
