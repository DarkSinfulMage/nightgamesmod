package actions;

import trap.Trap;
import global.Global;
import global.Modifier;
import characters.Character;

public class SetTrap extends Action {
	private Trap trap;
	public SetTrap(Trap trap) {
		super("Set("+trap.toString()+")");
		this.trap=trap;
	}

	@Override
	public boolean usable(Character user) {
		return trap.recipe(user)&&!user.location().open()&&trap.requirements(user)
				&&user.location().env.size()<5
				&&(!user.human()||Global.getMatch().condition!=Modifier.noitems);
	}

	@Override
	public Movement execute(Character user) {
		try {
			user.location().place(trap.getClass().newInstance());
			String message = trap.setup(user);
			if(user.human()){
				Global.gui().message(message);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return Movement.trap;
	}

	@Override
	public Movement consider() {
		return Movement.trap;
	}

}
