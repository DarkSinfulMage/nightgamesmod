package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.items.clothing.ClothingSlot;

public class StripTop extends Skill {

	public StripTop(Character self) {
		super("Strip Top", self);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return c.getStance().reachTop(getSelf())&&!target.breastsAvailable()&&getSelf().canAct();
	}

	@Override
	public int getMojoCost(Combat c) {
		return 10;
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		int difficulty = target.getOutfit().getTopOfSlot(ClothingSlot.top).dc()
				+(target.getLevel())
				+(target.getStamina().percent() / 5
				- target.getArousal().percent()) / 4
				- (!target.canAct()||c.getStance().sub(target) ? 20 : 0);
		if (getSelf().check(Attribute.Cunning, difficulty)||!target.canAct()) {
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.normal, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.normal, target));
			}
			target.strip(ClothingSlot.top, c);
			if(getSelf().human()&&target.mostlyNude()){
				c.write(target,target.nakedLiner(c));
			}
			target.emote(Emotion.nervous, 10);
		}
		else{
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.miss, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.miss, target));
			}
			target.weaken(c, Global.random(6)+getSelf().get(Attribute.Power)/4);
			return false;
		}
		return true;
	}

	@Override
	public boolean requirements(Combat c, Character user, Character target) {
		return user.get(Attribute.Cunning) >= 3;
	}

	@Override
	public Skill copy(Character user) {
		return new StripTop(user);
	}
	public int speed(){
		return 3;
	}
	public Tactics type(Combat c) {
		return Tactics.stripping;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.miss){
			return "You attempt to strip off "+target.name()+"'s "+target.getOutfit().getTopOfSlot(ClothingSlot.top).getName()+", but she shoves you away.";
		}else{
			return "After a brief struggle, you manage to pull off "+target.name()+"'s "+target.getOutfit().getTopOfSlot(ClothingSlot.top).getName()+".";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.miss){
			return getSelf().name()+" tries to yank off your "+target.getOutfit().getTopOfSlot(ClothingSlot.top).getName()+", but you manage to hang onto it.";
		}else{
			return getSelf().name()+" grabs a hold of your "+target.getOutfit().getTopOfSlot(ClothingSlot.top).getName()+" and yanks it off before you can stop her.";
		}
	}

	@Override
	public String describe(Combat c) {
		return "Attempt to remove opponent's top. More likely to succeed if she's weakened and aroused";
	}

	@Override
	public boolean makesContact() {
		return true;
	}
}
