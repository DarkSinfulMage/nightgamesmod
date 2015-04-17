package skills;

import items.Item;
import status.Hypersensitive;
import global.Global;
import global.Modifier;
import characters.Attribute;
import characters.Character;
import characters.Trait;

import combat.Combat;
import combat.Result;

public class Tickle extends Skill {

	public Tickle(Character self) {
		super("Tickle", self);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return self.canAct()&&(c.getStance().mobile(self)||c.getStance().dom(self))&&(c.getStance().reachTop(self)||c.getStance().reachBottom(self));
	}

	@Override
	public void resolve(Combat c, Character target) {
		if(target.roll(this, c, accuracy()+self.tohit())){
			if(target.pantsless()&&c.getStance().reachBottom(self)&&!c.getStance().penetration(self)){
				if(self.has(Item.Tickler2)&&Global.random(2)==1&&self.canSpend(10)&&(!self.human()||Global.getMatch().condition!=Modifier.notoys)){
					if(self.human()){
						c.write(self,deal(c,0,Result.critical, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.critical, target));
					}
					self.spendMojo(10);
					target.add(new Hypersensitive(target));
				}
				else if(self.has(Trait.ticklemonster)&&target.nude()){
					if(self.human()){
						c.write(self,deal(c,0,Result.special, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.special, target));
					}
					target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 6 + Global.random(8), c);
					target.weaken(c, Global.random(4));
					self.buildMojo(15);							
				}	
				else if(hastickler()&&(!self.human()||Global.getMatch().condition!=Modifier.notoys)){
					if(self.human()){
						c.write(self,deal(c,0,Result.strong, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.strong, target));
					}
					self.buildMojo(10);	
				}
				else{
					if(self.human()){
						c.write(self,deal(c,0,Result.normal, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.normal, target));
					}
				}
				if(target.has(Trait.ticklish)){
					target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 4 + Global.random(8), c);
					target.weaken(c, 6+target.get(Attribute.Perception)+Global.random(10));
				}
				else{
					target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 2+Global.random(4), c);
					target.weaken(c, 2+target.get(Attribute.Perception)+Global.random(6));
				}
			}
			else if(hastickler()&&Global.random(2)==1&&(!self.human()||Global.getMatch().condition!=Modifier.notoys)){
				if(target.topless()&&c.getStance().reachTop(self)){
					if(self.human()){
						c.write(self,deal(c,0,Result.item, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.item, target));
					}
					if(target.has(Trait.ticklish)){
						target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 6 + Global.random(8), c);
						target.weaken(c, 6+target.get(Attribute.Perception)+Global.random(4));
					}
					else{
						target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 4 + Global.random(4), c);
						target.weaken(c, 2+target.get(Attribute.Perception));
					}
				}
				else{
					if(self.human()){
						c.write(self,deal(c,0,Result.weak, target));
					}
					else if(target.human()){
						c.write(self,receive(c,0,Result.weak, target));
					}
					if(target.has(Trait.ticklish)){
						target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 4 + Global.random(4), c);
						target.weaken(c, 2+target.get(Attribute.Perception)+Global.random(4));
					}
					else{
						target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), 4 + Global.random(2), c);
						target.weaken(c, target.get(Attribute.Perception));
					}				
				}
			}
			else{
				if(self.human()){
					c.write(self,deal(c,0,Result.normal, target));
				}
				else if(target.human()){
					c.write(self,receive(c,0,Result.normal, target));
				}
				if(target.has(Trait.ticklish)){
					int m = 6+Global.random(3)-(target.top.size()+target.bottom.size());
					target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), m, c);
					target.weaken(c, 5+Global.random(3)+target.get(Attribute.Perception)-(target.top.size()+target.bottom.size()));
				}
				else{
					int m = 4+Global.random(3)-(target.top.size()+target.bottom.size());
					target.body.pleasure(self, self.body.getRandom("hands"), target.body.getRandom("skin"), m, c);
					target.weaken(c, Global.random(3+target.get(Attribute.Perception))-(target.top.size()+target.bottom.size()));
				}				
				self.buildMojo(10);
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
	public boolean requirements() {
		return true;
	}

	@Override
	public boolean requirements(Character user) {
		return true;
	}

	@Override
	public Skill copy(Character user) {
		return new Tickle(user);
	}
	public int speed(){
		return 7;
	}
	public Tactics type(Combat c) {
		return Tactics.pleasure;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.miss){
			return "You try to tickle "+target.name()+", but she squirms away.";
		}
		else if(modifier == Result.special){
			return "You work your fingers across "+target.name()+"'s most ticklish and most erogenous zones until she's a writhing in pleasure and can't even make coherent words.";
		}
		else if(modifier == Result.critical){
			return "You brush your tickler over "+target.name()+"'s body, causing her to shiver and retreat. When you tickle her again, she yelps and almost falls down. " +
					"It seems like your special feathers made her more sensitive than usual.";
		}
		else if(modifier == Result.strong){
			return "You run your tickler across "+target.name()+"'s sensitive thighs and pussy. She can't help but let out a quiet whimper of pleasure.";
		}
		else if(modifier == Result.item){
			return "You tease "+target.name()+"'s naked upper body with your feather tickler, paying close attention to her nipples.";
		}
		else if(modifier == Result.weak){
			return "You catch "+target.name()+" off guard by tickling her neck and ears.";
		}
		else{
			return "You tickle "+target.name()+"'s sides as she giggles and squirms.";
		}
		
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier == Result.miss){
			return self.name()+" tries to tickle you, but fails to find a sensitive spot.";
		}
		else if(modifier == Result.special){
			return self.name()+" tickles your nude body mercilessly, gradually working her way to your dick and balls. As her fingers start tormenting you privates, you struggle to " +
					"clear your head enough to keep from ejaculating immediately.";
		}
		else if(modifier == Result.critical){
			return self.name()+" teases your dick and balls with her feather tickler. After she stops, you feel an unnatural sensitivity where the feathers touched you.";
		}
		else if(modifier == Result.strong){
			return self.name()+" brushes her tickler over your balls and teases the sensitive head of your penis.";
		}
		else if(modifier == Result.item){
			return self.name()+" runs her feather tickler across your nipples and abs.";
		}
		else if(modifier == Result.weak){
			return self.name()+" pulls out a feather tickler and teases any exposed skin she can reach.";
		}
		else{
			return self.name()+" suddenly springs toward you and tickles you relentlessly until you can barely breathe.";
		}
	}

	@Override
	public String describe() {
		return "Tickles opponent, weakening and arousing her. More effective if she's nude";
	}
	private boolean hastickler(){
		return self.has(Item.Tickler)||self.has(Item.Tickler2);
	}
}
