package stance;


import combat.Combat;

import characters.Character;
import characters.Trait;

public class AnalProne extends AnalSexStance {

	public AnalProne(Character top, Character bottom) {
		super(top, bottom, Stance.anal);
	}

	@Override
	public String describe() {
		if(top.human()){
			return String.format("You're holding %s legs over your shoulder while your cock in buried in %s's ass.",
					bottom.nameOrPossessivePronoun(), bottom.possessivePronoun());
		} else if (top.has(Trait.strapped)){
			return "You're flat on your back with your feet over your head while " + top.name() + " pegs you with her strapon dildo.";
		}
		else{
			return top.name()+" is fucking you in the ass";
		}
	}

	@Override
	public boolean mobile(Character c) {
		return c==top;
	}

	@Override
	public boolean kiss(Character c) {
		return true;
	}

	@Override
	public boolean dom(Character c) {
		return c==top;
	}

	@Override
	public boolean sub(Character c) {
		return c==bottom;
	}

	@Override
	public boolean reachTop(Character c) {
		return true;
	}

	@Override
	public boolean reachBottom(Character c) {
		return c==top;
	}

	@Override
	public boolean prone(Character c) {
		return c==bottom;
	}

	@Override
	public boolean feet(Character c) {
		return false;
	}

	@Override
	public boolean oral(Character c) {
		return false;
	}

	@Override
	public boolean behind(Character c) {
		return false;
	}

	@Override
	public boolean penetration(Character c) {
		return c==top;
	}

	@Override
	public boolean inserted(Character c) {
		return c==top;
	}

	@Override
	public Position insert(Character dom, Character inserter) {
		return new Mount(top,bottom);
	}

	@Override
	public void checkOngoing(Combat c){
		Character inserter = inserted(top) ? top : bottom;
		Character inserted = inserted(top) ? bottom : top;
		
		if(!inserter.hasDick()&&!inserter.has(Trait.strapped)){
			if(inserted.human()){
				c.write("With "+inserter.name()+"'s pole gone, your ass gets a respite.");
			} else {
				c.write(inserted.name() + " sighs with relief with your dick gone.");
			}
			c.setStance(insert(top, bottom));
		}
		if (inserted.body.getRandom("ass") == null) {
			if(inserted.human()){
				c.write("With your asshole suddenly disappearing, " + inserter.name() + "'s dick pops out of what was once your sphincter.");
			} else {
				c.write("Your dick pops out of " + inserted.name() + " as her asshole shrinks and disappears.");
			}
			c.setStance(insert(top, bottom));
		}
	}

	public Position reverse() {
		if (top.has(Trait.strapped)) {
			return new Mount(bottom, top);
		} else {
			return new AnalCowgirl(bottom, top);
		}
	}
}
