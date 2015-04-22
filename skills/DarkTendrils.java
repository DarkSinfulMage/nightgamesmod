package skills;

import global.Global;
import characters.Attribute;
import characters.Character;

import combat.Combat;
import combat.Result;

import skills.Skill;
import stance.StandingOver;
import status.Bound;

public class DarkTendrils extends Skill {

	public DarkTendrils(Character self) {
		super("Dark Tendrils", self, 4);
	}

	@Override
	public boolean requirements() {
		return self.getPure(Attribute.Dark)>=12;
	}

	@Override
	public boolean requirements(Character user) {
		return user.getPure(Attribute.Dark)>=12;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return !target.wary() && !c.getStance().sub(self)&&!c.getStance().prone(self)&&!c.getStance().prone(target)&&self.canAct();
	}

	@Override
	public String describe() {
		return "Summon shadowy tentacles to grab or trip your opponent: 5 Arousal";
	}

	@Override
	public void resolve(Combat c, Character target) {
		self.arouse(5, c);
		if(target.roll(this, c, accuracy()+self.tohit())){
			if(Global.random(2)==1){
				if(self.human()){
					c.write(self,deal(c,0,Result.normal, target));
				}
				else if(target.human()){
					c.write(self,receive(c,0,Result.normal, target));
				}
				target.add(new Bound(target,Math.min(10+3*self.get(Attribute.Dark), 40),"shadows"));
			} else if(self.check(Attribute.Dark,target.knockdownDC()-self.getMojo().get())){
				if(self.human()){
					c.write(self,deal(c,0,Result.weak, target));
				}
				else if(target.human()){
					c.write(self,receive(c,0,Result.weak, target));
				}
				c.setStance(new StandingOver(self,target));
			} else {
				if(self.human()){
					c.write(self,deal(c,0,Result.miss, target));
				}
				else if(target.human()){
					c.write(self,receive(c,0,Result.miss, target));
				}
			}
		}
		else{
			if(self.human()){
				c.write(self,deal(c,0,Result.miss, target));
			}
			else if(target.human()){
				c.write(self,receive(c,0,Result.miss, target));
			}
		}

	}

	@Override
	public skills.Skill copy(Character user) {
		return new DarkTendrils(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.positioning;
	}

	public int accuracy(){
		return 8;
	}
	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.miss){
			return "You summon dark tentacles to hold "+target.name()+", but she twists away.";
		}
		else  if(modifier == Result.weak){
			return "You summon dark tentacles that take "+target.name()+" feet out from under her.";
		}
		else{
			return "You summon a mass of shadow tendrils that entangle "+target.name()+" and pin her arms in place.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.miss){
			return self.name()+" makes a gesture and evil looking tentacles pop up around you. You dive out of the way as they try to grab you.";
		}
		else  if(modifier == Result.weak){
			return "Your shadow seems to come to life as dark tendrils wrap around your legs and bring you to the floor.";
		}
		else{
			return self.name()+" summons shadowy tentacles that snare your arms and hold you in place.";
		}
	}

}
