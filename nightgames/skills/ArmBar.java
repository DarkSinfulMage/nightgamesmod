package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;

public class ArmBar extends Skill {

	public ArmBar(Character self) {
		super("Armbar", self);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return c.getStance().dom(getSelf())&&c.getStance().reachTop(target)&&getSelf().canAct()&&!getSelf().has(Trait.undisciplined)&&!c.getStance().penetration(getSelf());
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		if(target.roll(this, c, accuracy())){
			int m = Global.random(10)+getSelf().get(Attribute.Power)/2;
			if(getSelf().human()){
				c.write(getSelf(),deal(c,m,Result.normal, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,m,Result.normal, target));
			}
			target.pain(c, m);
			target.emote(Emotion.angry,15);
		}
		else{
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.miss, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.miss, target));
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Power)>=20 && !user.has(Trait.undisciplined);
	}

	@Override
	public Skill copy(Character user) {
		return new ArmBar(user);
	}
	
	public int speed(){
		return 2;
	}
	public Tactics type(Combat c) {
		return Tactics.damage;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.miss){
			return "You grab at "+target.name()+"'s arm, but she pulls it free.";
		}
		else{
			return "You grab "+target.name()+"'s arm at the wrist and pull it to your chest in the traditional judo submission technique.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.miss){
			return getSelf().name()+" grabs your wrist, but you pry it out of her grasp.";
		}
		else{
			return getSelf().name()+" pulls your arm between her legs, forcibly overextending your elbow. The pain almost makes you tap out, but you manage to yank your arm " +
				"out of her grip.";
		}
	}

	@Override
	public String describe() {
		return "A judo submission hold that hyperextends the arm.";
	}

	@Override
	public boolean makesContact() {
		return true;
	}
}
