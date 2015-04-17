package skills;

import status.Charmed;
import status.Trance;
import global.Global;
import characters.Attribute;
import characters.Character;

import combat.Combat;
import combat.Result;

public class Purr extends Skill {

	public Purr(Character self) {
		super("Purr", self);
	}

	@Override
	public boolean requirements() {
		return self.getPure(Attribute.Animism)>=9;
	}

	@Override
	public boolean requirements(Character user) {
		return user.getPure(Attribute.Animism)>=9;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return self.canAct()&&c.getStance().mobile(self)&&self.getArousal().percent()>=20;
	}

	@Override
	public String describe() {
		return "Purr cutely to try to charm your opponent";
	}

	@Override
	public void resolve(Combat c, Character target) {
		if(Global.random(target.getLevel())<=self.get(Attribute.Animism)*self.getArousal().percent()/100 && !target.wary()){
			if(self.human()){
				c.write(self,deal(c,0,Result.normal, target));
			}else if(target.human()){
				c.write(self,receive(c,0,Result.normal, target));
			}
			target.add(new Charmed(target));
		} else {
			if(self.human()){
				c.write(self,deal(c,0,Result.miss, target));
			}else if(target.human()){
				c.write(self,receive(c,0,Result.miss, target));
			}
		}
	}

	@Override
	public Skill copy(Character user) {
		return new Purr(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.debuff;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.miss){
			return "You let out a soft purr and give "+target.name()+" your best puppy dog eyes. She smiles, but then aims a quick punch at your groin, which you barely avoid. " +
					"Maybe you shouldn't have mixed your animal metaphors.";
		}
		else{
			return "You give "+target.name()+" an affectionate purr and your most disarming smile. Her battle aura melts away and she pats your head, completely taken with your " +
					"endearing behavior.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.miss){
			return self.name()+" slumps submissively and purrs. It's cute, but she's not going to get the better of you.";
		}
		else{
			return self.name()+" purrs cutely, and looks up at you with sad eyes. Oh God, she's so adorable! It'd be mean to beat her too quickly. Maybe you should let her get some " +
					"attacks in while you enjoy watching her earnest efforts.";
		}
	}

}
