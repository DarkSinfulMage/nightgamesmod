package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;

public class Stomp extends Skill {

	public Stomp(Character self) {
		super("Stomp", self);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return !c.getStance().prone(getSelf())&&c.getStance().prone(target)&&c.getStance().feet(getSelf())&&getSelf().canAct()&&!getSelf().has(Trait.softheart)&&!c.getStance().penetration(getSelf());
	}

	@Override
	public int getMojoCost(Combat c) {
		return 20;
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		if(getSelf().has(Trait.heeldrop)&&target.pantsless()){
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.special, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.special, target));
				if(Global.random(5)>=1){
					c.write(getSelf(),getSelf().bbLiner());
				}
			}
			if(target.has(Trait.achilles)){
				target.pain(c, 20);
			}
			target.pain(c, 30-(Global.random(2)*target.bottom.size()));
			target.calm(c, Global.random(30));
		}
		else if(target.has(Trait.armored)){
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.weak, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.weak, target));
			}
			target.pain(c, 5-(Global.random(3)*target.bottom.size()));
			target.calm(c, Global.random(10)+10);
		}
		else{
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.normal, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.normal, target));
				if(Global.random(5)>=1){
					c.write(getSelf(),getSelf().bbLiner());
				}
			}
			if(target.has(Trait.achilles)){
				target.pain(c, 20);
			}
			target.pain(c, 20-(Global.random(3)*target.bottom.size()));
			target.calm(c, Global.random(30)+10);
		}
		target.emote(Emotion.angry,25);
		return true;
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Power)>=16 && !user.has(Trait.softheart);
	}

	@Override
	public Skill copy(Character user) {
		return new Stomp(user);
	}
	public int speed(){
		return 4;
	}
	public Tactics type(Combat c) {
		return Tactics.damage;
	}
	public String getLabel(Combat c){
		if(getSelf().has(Trait.heeldrop)){
			return "Double Legdrop";
		}
		else{
			return getName(c);
		}
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.special){
			if(target.hasBalls()){
				return "You push "+target.name()+"'s legs apart, exposing her private parts. Her girl-cock is hanging over her testicles, so you prod it with your foot " +
						"to push it out of the way. She becomes slightly aroused at your touch and your attention, not realizing you're planning something extremely " +
						"painful. You're going to feel a bit bad about this, but probably not nearly as much as she will. You jump up, lifting both legs and coming " +
						"down in a double legdrop directly hitting her unprotected jewels.";
			}
			else{
				return "You push "+target.name()+"'s legs apart, fully exposing her womanhood. She flushes in shame, apparently misunderstanding you intentions, which " +
					"compels you to mutter a quick apology before you jump up and slam your heel into her vulnerable quim.";
			}
		}
		else if(modifier==Result.weak){
			return "You step between "+target.name()+"'s legs and stomp down on her groin. Your foot hits something solid and she doesn't seem terribly affected.";
		}
		else{
			if(target.hasBalls()){
				return "You pull "+target.name()+"'s legs open and stomp on her vulnerable balls. She cries out in pain and curls up in the fetal position.";
			}
			else{
				return "You step between "+target.name()+"'s legs and stomp down on her sensitive pussy. She cries out in pain and rubs her injured girl bits.";
			}
		}		
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.special){
			return getSelf().name()+" forces your legs open and begins prodding your genitals with her foot. You're slightly aroused by her attention, but she's not giving " +
					"you a proper footjob, she's mostly just playing with your balls. Too late, you realize that she's actually lining up her targets. Two torrents of pain " +
					"erupt from your delicates as her feet crash down on them.";
		}
		else if(modifier==Result.weak){
			return getSelf().name()+" grabs your ankles and stomps down on your armored groin, doing little damage.";
		}
		else{
			return getSelf().name()+" grabs your ankles and stomps down on your unprotected jewels. You curl up in the fetal position, groaning in agony.";
		}		
	}

	@Override
	public String describe() {
		return "Stomps on your opponent's groin for extreme damage: 20 mojo";
	}

	@Override
	public boolean makesContact() {
		return true;
	}
}
