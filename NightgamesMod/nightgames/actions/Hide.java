package nightgames.actions;

import nightgames.characters.Character;
import nightgames.characters.State;
import nightgames.global.GameState;

public class Hide extends Action {

    /**
     * 
     */
    private static final long serialVersionUID = 9222848242102511020L;

    public Hide() {
        super("Hide");

    }

    @Override
    public boolean usable(Character user) {
        return !(user.location().open() || user.location().corridor() || user.state == State.hidden) && !user.bound();
    }

    @Override
    public Movement execute(Character user) {
        if (user.human()) {
            GameState.gui().message("You find a decent hiding place and wait for unwary opponents.");
        }
        user.state = State.hidden;
        return Movement.hide;
    }

    @Override
    public Movement consider() {
        return Movement.hide;
    }
}
