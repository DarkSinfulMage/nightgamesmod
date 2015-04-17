package actions;

import areas.Area;
import characters.Character;

public class Move extends Action {
	private Area destination;
	public Move(Area destination) {
		super("Move("+destination.name+")");
		this.destination=destination;
	}

	@Override
	public boolean usable(Character user) {
		return true;
	}

	@Override
	public Movement execute(Character user) {
		user.travel(destination);
		return destination.id();
	}
	public Area getDestination(){
		return destination;
	}

	@Override
	public Movement consider() {
		return destination.id();
	}
}
