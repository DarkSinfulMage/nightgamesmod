package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.status.Stsflag;
import nightgames.status.Unreadable;

public class Wait extends Skill {

	public Wait(Character self) {
		super("Wait", self);
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return getSelf().canRespond();
	}

	@Override
	public int getMojoBuilt(Combat c) {
		if(bluff()){
			return 0;
		} else if(focused()&&!c.getStance().sub(getSelf())){
			return 25;
		} else {
			return 15;
		}
	}
	@Override
	public int getMojoCost(Combat c) {
		if(bluff()){
			return 20;
		} else {
			return 0;
		}
	}
	@Override
	public boolean resolve(Combat c, Character target) {
		if(bluff()){
			int m = Global.random(25);
			getSelf().spendMojo(c, 20);
			if(getSelf().human()){
				c.write(getSelf(),deal(c,m,Result.special, target));
			}
			else{
				c.write(getSelf(),receive(c,m,Result.special, target));
			}
			getSelf().heal(c, m);
			getSelf().calm(c, 50 - m);
			getSelf().add(c, new Unreadable(getSelf()));
		}
		else if(focused()&&!c.getStance().sub(getSelf())){
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.strong, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.strong, target));
			}
			getSelf().heal(c, Global.random(4));
			getSelf().calm(c, Global.random(8));
		}
		else{
			if(getSelf().human()){
				c.write(getSelf(),deal(c,0,Result.normal, target));
			}
			else if(target.human()){
				c.write(getSelf(),receive(c,0,Result.normal, target));
			}
			getSelf().heal(c, Global.random(4));
		}
		return true;
	}

	@Override
	public boolean requirements(Character user) {
		return true;
	}

	@Override
	public Skill copy(Character user) {
		return new Wait(user);
	}
	public int speed(){
		return 0;
	}

	@Override
	public Tactics type(Combat c) {
		if(bluff()||focused()){
			return Tactics.calming;
		}
		else{
			return Tactics.misc;
		}
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.special){
			return "You force yourself to look less tired and horny than you actually are. You even start to believe it yourself.";
		}
		else if(modifier==Result.strong){
			return "You take a moment to clear your thoughts, focusing your mind and calming your body.";
		}
		else{
			return "You bide your time, waiting to see what "+target.name()+" will do.";
		}
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		if(modifier==Result.special){
			return "Despite your best efforts, "+getSelf().name()+" is still looking as calm and composed as ever. Either you aren't getting to her at all, or she's good at hiding it.";
		}
		else if(modifier==Result.strong){
			return getSelf().name()+" closes her eyes and takes a deep breath. When she opens her eyes, she seems more composed.";
		}
		else{
			return getSelf().name()+" hesitates, watching you closely.";
		}
	}

	@Override
	public String getLabel(Combat c){
		if(bluff()){
			return "Bluff";
		}
		else if(focused()){
			return "Focus";
		}
		else{
			return getName(c);
		}
	}

	@Override
	public String describe() {
		if(bluff()){
			return "Regain some stamina and lower arousal. Hides current status from opponent.";
		}
		else if(focused()){
			return "Calm yourself and gain some mojo";
		}
		else{
			return "Do nothing";
		}
	}

	private boolean focused(){
		return getSelf().get(Attribute.Cunning)>=15 && !getSelf().has(Trait.undisciplined)&&getSelf().canRespond();
	}

	private boolean bluff(){
		return !getSelf().is(Stsflag.unreadable) && getSelf().has(Trait.pokerface)&&getSelf().get(Attribute.Cunning)>=9&&getSelf().canSpend(20)&&getSelf().canRespond();
	}
}
