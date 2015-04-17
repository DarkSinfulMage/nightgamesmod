package stance;


import characters.Character;

public class Pin extends Position {

	public Pin(Character top, Character bottom) {
		super(top, bottom,Stance.pin);
	}

	@Override
	public String describe() {
		if(top.human()){
			return "You're sitting on "+bottom.name()+", holding her arms in place.";
		}
		else{
			return top.name()+" is pinning you down, leaving you helpless.";
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
		return c==top;
	}

	@Override
	public boolean prone(Character c) {
		return c==bottom;
	}
	@Override
	public boolean inserted(Character c) {
		return false;
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
		return c==top;
	}

	@Override
	public boolean penetration(Character c) {
		return false;
	}
	@Override
	public Position insert(Character dom, Character inserter) {
		if(top.hasDick()&&bottom.hasPussy()){
			return new Missionary(top,bottom);
		}
		else if(top.hasPussy()&&bottom.hasDick()){
			return new Cowgirl(top,bottom);
		}
		else{
			return this;
		}
	}
	@Override
	public float priorityMod(Character self) {
		return dom(self) ? 1.0f : 0;
	}
}
