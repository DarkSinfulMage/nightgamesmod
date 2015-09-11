package nightgames.stance;


import java.util.ArrayList;
import java.util.List;

import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.characters.body.BodyPart;
import nightgames.global.Global;

public class Behind extends AbstractBehindStance {

	public Behind(Character top, Character bottom) {
		super(top, bottom,Stance.behind);
	}

	@Override
	public String describe() {
		if(top.human()){
			return "You are holding "+bottom.name()+" from behind.";
		}
		else{
			return top.name()+" is holding you from behind.";
		}
	}

	public String image() {
		if (top.hasPussy() && bottom.hasPussy()) {
			return "behind_ff.jpg";
		} else if (bottom.hasPussy()) {
			return "behind_m.jpg";
		} else {
			return "behind_f.jpg";
		}
	}

	@Override
	public boolean mobile(Character c) {
		return c==top;
	}

	@Override
	public boolean kiss(Character c) {
		return c==top;
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
		return c==top;
	}

	@Override
	public boolean reachBottom(Character c) {
		return true;
	}

	@Override
	public boolean prone(Character c) {
		return false;
	}

	@Override
	public boolean feet(Character c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean oral(Character c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean behind(Character c) {
		return c==top;
	}

	@Override
	public boolean inserted(Character c) {
		return false;
	}

	@Override
	public float priorityMod(Character self) {
		return (self.hasInsertable() ? 2 : 1) * getSubDomBonus(self, 1.0f);
	}
	
	@Override
	public BodyPart topPart() {
		return top.body.getRandomInsertable();
	}
	
	@Override
	public BodyPart bottomPart() {
		return bottom.body.getRandomPussy();
	}
}
