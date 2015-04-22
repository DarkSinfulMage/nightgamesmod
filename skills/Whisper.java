package skills;

import status.Enthralled;
import global.Global;
import characters.Attribute;
import characters.Character;
import characters.Emotion;
import characters.Trait;

import combat.Combat;
import combat.Result;

public class Whisper extends Skill {

	public Whisper(Character self) {
		super("Whisper", self);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return c.getStance().kiss(self)&&self.canAct()&&!self.has(Trait.direct);
	}
	
	@Override
	public float priorityMod(Combat c) {
		return self.has(Trait.darkpromises) ? 1.0f : 0;
	}

	@Override
	public void resolve(Combat c, Character target) {
		int roll = Global.centeredrandom(4, self.get(Attribute.Dark) / 5, 2);
		int m = 4 + Global.random(6);

		if(target.has(Trait.imagination)) {
			m += 4;
		}
		if(self.has(Trait.darkpromises)) {
			m += 3;
		}
		if(self.has(Trait.darkpromises)&& roll == 4 &&self.canSpend(15) && !target.wary()){
			self.spendMojo(c, 15);
			if(self.human()){
				c.write(self,deal(c,0,Result.special, target));
			}
			else if(target.human()){
				c.write(self,receive(c,0,Result.special, target));
			}
			target.add(new Enthralled(target,self, 4));
		}
		else{
			if(self.human()){
				c.write(self,deal(c,0,Result.normal, target));
			}
			else if(target.human()){
				c.write(self,receive(c,0,Result.normal, target));
			}
		}
		target.tempt(c, self, m);
		target.emote(Emotion.horny, 30);
		self.buildMojo(c, 10);
	}

	@Override
	public boolean requirements() {
		return self.getPure(Attribute.Seduction)>=32 && !self.has(Trait.direct);
	}

	@Override
	public boolean requirements(Character user) {
		return user.getPure(Attribute.Seduction)>=32 && !user.has(Trait.direct);
	}

	@Override
	public Skill copy(Character user) {
		return new Whisper(user);
	}
	public int speed(){
		return 9;
	}
	public Tactics type(Combat c) {
		return Tactics.pleasure;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.special){
			return "You whisper words of domination in "+target.name()+"'s ear, filling her with your darkness. The spirit in her eyes seems to dim as she submits to your will.";
		}
		else {
			return "You whisper sweet nothings in "+target.name()+"'s ear. Judging by her blush, it was fairly effective.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.special){
			return this.self.name() + " whispers in your ear in some eldritch language."
					+ " Her words echo through your head and you feel a"
					+ " strong compulsion to do what she tells you";
		}
		else{
			return self.name()+" whispers some deliciously seductive suggestions in your ear.";
		}
	}

	@Override
	public String describe() {
		return "Arouse opponent by whispering in her ear";
	}
}
