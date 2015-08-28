package nightgames.skills;

import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.stance.Behind;
import nightgames.stance.Stance;
import nightgames.status.Flatfooted;

public class Diversion extends Skill {

	public Diversion(Character self) {
		super("Diversion", self);
	}

	@Override
	public boolean requirements(Combat c, Character user, Character target) {
		return user.has(Trait.misdirection);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return !target.wary() && getSelf().canAct()&&c.getStance().mobile(getSelf())&&!getSelf().nude()&&!c.getStance().prone(getSelf())&&!c.getStance().penetration(getSelf());
	}

	@Override
	public int getMojoBuilt(Combat c) {
		return 25;
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		if(getSelf().topless()){
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.special, target));
			}
			else{
				c.write(getSelf(),receive(c,0,Result.special, target));
			}
			getSelf().strip(1, c);
		}
		else{
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.normal, target));
			}
			else{
				c.write(getSelf(),receive(c,0,Result.normal, target));
			}
			getSelf().strip(0, c);
		}
		c.setStance(new Behind(getSelf(),target));
		target.add(c, new Flatfooted(target,1));
		return true;
	}

	@Override
	public Skill copy(Character user) {
		return new Diversion(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.positioning;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.normal){
			return "You quickly strip off your "+getSelf().top.peek().getName()+" and throw it to the right, while you jump to the left. "+target.getName()+" catches your discarded clothing, " +
					"losing sight of you in the process.";
		}
		else{
			return "You quickly strip off your "+getSelf().bottom.peek().getName()+" and throw it to the right, while you jump to the left. "+target.getName()+" catches your discarded clothing, " +
					"losing sight of you in the process.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character attacker) {
		if(modifier==Result.normal){
			return "You lose sight of "+getSelf().name()+" for just a moment, but then see her moving behind you in your peripheral vision. You quickly spin around and grab her, " +
					"but you find yourself holding just her "+getSelf().top.peek().getName()+". Wait... what the fuck?";
		}
		else{
			return "You lose sight of "+getSelf().name()+" for just a moment, but then see her moving behind you in your peripheral vision. You quickly spin around and grab her, " +
					"but you find yourself holding just her "+getSelf().bottom.peek().getName()+". Wait... what the fuck?";
		}
	}

	@Override
	public String describe() {
		return "Throws your clothes as a distraction";
	}

}
