package skills;

import global.Global;
import stance.StandingOver;
import status.Bound;
import status.Oiled;
import status.Stsflag;
import characters.Attribute;
import characters.Character;
import characters.Emotion;

import combat.Combat;
import combat.Result;

public class TentaclePorn extends Skill {

	public TentaclePorn(Character self) {
		super("Tentacle Porn", self);
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Fetish)>=12;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return !target.wary() && !c.getStance().sub(self)&&!c.getStance().prone(self)&&!c.getStance().prone(target)&&self.canAct()&&self.getArousal().get()>=20&&self.canSpend(10);
	}

	@Override
	public String describe() {
		return "Create a bunch of hentai tentacles.";
	}

	@Override
	public void resolve(Combat c, Character target) {
		self.spendMojo(c, 10);
		if(target.roll(this, c, accuracy())){
			if(target.nude()){
				int m = Global.random(self.get(Attribute.Fetish)) / 2 + 1;
				if(target.bound()){
					if(self.human()){
						c.write(self,deal(c,0,Result.special, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.special, target));
					}
					target.body.pleasure(null, null, target.body.getRandom("cock"), m, c);
					target.body.pleasure(null, null, target.body.getRandom("pussy"), m, c);
					target.body.pleasure(null, null, target.body.getRandom("breasts"), m, c);
					target.body.pleasure(null, null, target.body.getRandom("ass"), m, c);
					target.emote(Emotion.horny, 10);
				} else if(self.human()){
					c.write(self,deal(c,0,Result.normal, target));
					target.body.pleasure(null, null, target.body.getRandom("skin"), m, c);
				}
				else if(target.human()){
					c.write(self,receive(c,0,Result.normal, target));
					target.body.pleasure(null, null, target.body.getRandom("skin"), m, c);
				}
				if(!target.is(Stsflag.oiled)){
					target.add(new Oiled(target));
				}
				target.emote(Emotion.horny, 20);
			}
			else{
				if(self.human()){
					c.write(self,deal(c,0,Result.weak, target));
				}
				else if(target.human()){
					c.write(self,receive(c,0,Result.weak, target));
				}
			}
			target.add(new Bound(target,Math.min(10+3*self.get(Attribute.Fetish), 30),"tentacles"));
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
	public Skill copy(Character user) {
		return new TentaclePorn(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.positioning;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.miss){
			return "You summon a mass of tentacles that try to snare "+target.name()+", but she nimbly dodges them.";
		}
		else if(modifier == Result.weak){
			return "You summon a mass of phallic tentacles that wrap around "+target.name()+"'s arms, holding her in place.";
		}
		else if(modifier == Result.normal){
			return "You summon a mass of phallic tentacles that wrap around "+target.name()+"'s naked body. They squirm against her and squirt slimy fluids on her body.";
		}
		else{
			return "You summon tentacles to toy with "+target.name()+"'s helpless form. The tentacles toy with her breasts and pentrate her pussy and ass.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.miss){
			return self.name()+" stomps on the ground and a bundle of tentacles erupt from the ground. You're barely able to avoid them.";
		}
		else if(modifier == Result.weak){
			return self.name()+" stomps on the ground and a bundle of tentacles erupt from the ground around you, entangling your arms and legs.";
		}
		else if(modifier == Result.normal){
			return self.name()+" stomps on the ground and a bundle of tentacles erupt from the ground around you, entangling your arms and legs. The slimy appendages " +
					"wriggle over your body and coat you in the slippery liquid.";
		}
		else{
			return self.name()+" summons slimy tentacles that cover your helpless body, tease your dick, and probe your ass.";
		}
	}

}
