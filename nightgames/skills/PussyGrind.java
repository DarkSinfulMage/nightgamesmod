package nightgames.skills;

import nightgames.characters.Character;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.stance.TribadismStance;

public class PussyGrind extends Skill {

	public PussyGrind(String name, Character self, int cooldown) {
		super(name, self, cooldown);
	}

	public PussyGrind(Character self) {
		super("Grind", self);
	}

	public BodyPart getSelfOrgan() {
		BodyPart res = getSelf().body.getRandomPussy();
		return res;
	}

	public BodyPart getTargetOrgan(Character target) {
		return target.body.getRandomPussy();
	}
	
	public boolean fuckable(Combat c, Character target) {
		return c.getStance().partFor(getSelf()).isType("pussy") && c.getStance().partFor(target).isType("pussy");
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return fuckable(c, target) &&c.getStance().mobile(getSelf())
				&&getSelf().canAct();
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		BodyPart selfO = getSelfOrgan();
		BodyPart targetO = getTargetOrgan(target);
		if(getSelf().human()){
			c.write(getSelf(),deal(c,0,Result.normal, target));
		}
		else if(target.human()){
			c.write(getSelf(),receive(c,0,Result.normal, target));
		}
		c.setStance(new TribadismStance(getSelf(), target));
		int m = 10 + Global.random(10);
		int otherm = 5 + Global.random(6);
		target.body.pleasure(getSelf(), selfO, targetO, m, c);
		getSelf().body.pleasure(target, targetO, selfO, otherm, c);
		return true;
	}

	@Override
	public boolean requirements(Combat c, Character user, Character target) {
		return true;
	}

	@Override
	public Skill copy(Character user) {
		return new PussyGrind(user);
	}
	public int speed(){
		return 2;
	}
	public Tactics type(Combat c) {
		return Tactics.fucking;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.normal){
			return Global.format("You rock your tangled bodies back and forth, grinding your loins into hers. {other:subject} passionately gasps as the stimulation overwhelms her. "
					+ "Soon the floor is drenched with the fruits of your combined labor.", getSelf(), target);
		}
		return "Bad stuff happened";
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.normal){
			return Global.format("{self:SUBJECT} rocks your tangled bodies back and forth, grinding her crotch into yours. You moan passionately as the stimulation overwhelms you. "
					+ "Soon the floor is drenched with the fruits of your combined labor.", getSelf(), target);
		}
		return "Bad stuff happened";
	}

	@Override
	public String describe(Combat c) {
		return "Grinds your pussy against your opponent's.";
	}
	
	@Override
	public boolean makesContact() {
		return true;
	}
}
