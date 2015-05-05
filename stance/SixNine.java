package stance;


import characters.Character;

public class SixNine extends Position {

	public SixNine(Character top, Character bottom) {
		super(top, bottom,Stance.sixnine);
	}

	@Override
	public String describe() {
		if(top.human()){
			return "You are on top of "+bottom.name()+" in the 69 position. Her pussy is right in front of your face and you can feel her breath on your dick.";
		} else {
			return "You and "+top.name()+" are on the floor in 69 position. She's sitting on top of you with her pussy right in front of your face and your dick in her mouth.";
		}
	}

	@Override
	public boolean mobile(Character c) {
		return c==top;
	}

	@Override
	public boolean kiss(Character c) {
		return false;
	}

	@Override
	public boolean facing() {
		return false;
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
		return false;
	}

	@Override
	public boolean reachBottom(Character c) {
		return true;
	}

	@Override
	public boolean prone(Character c) {
		return true;
	}

	@Override
	public boolean feet(Character c) {
		return false;
	}

	@Override
	public boolean oral(Character c) {
		return true;
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
}
