package stance;

import characters.Character;

public class StandingOver extends Position {

	public StandingOver(Character top, Character bottom) {
		super(top, bottom,Stance.standingover);
		
	}

	@Override
	public String describe() {
		if(top.human()){
			return "You are standing over "+bottom.name()+", who is helpless on the ground.";
		}
		else{
			return "You are flat on your back, while "+top.name()+" stands over you.";
		}
	}

	@Override
	public boolean mobile(Character c) {
		return true;
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
		return c==top;
	}

	@Override
	public boolean prone(Character c) {
		return c==bottom;
	}

	@Override
	public boolean feet(Character c) {
		return c==top;
	}

	@Override
	public boolean oral(Character c) {
		return c==top;
	}

	@Override
	public boolean behind(Character c) {
		return false;
	}

	@Override
	public boolean penetration(Character c) {
		return false;
	}
	
	@Override
	public boolean inserted(Character c) {
		return false;
	}

	@Override
	public Position insert(Character dom, Character inserter) {
		return this;
	}
	@Override
	public float priorityMod(Character self) {
		return dom(self) ? 2.0f : 0;
	}
}
