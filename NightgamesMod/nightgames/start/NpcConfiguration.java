package nightgames.start;

import nightgames.global.JSONUtils;
import org.json.simple.JSONObject;

import nightgames.characters.NPC;

import java.util.Optional;

public class NpcConfiguration extends CharacterConfiguration {

    // Optional because NpcConfiguration is used for both NPCs and adjustments common to all NPCs
    protected String type;
    public Optional<Boolean> isStartCharacter;

    public final void apply(NPC base) {
        super.apply(base);
        if (gender.isPresent()) {
            base.initialGender = gender.get();
        }
        if (isStartCharacter.isPresent()) {
            base.isStartCharacter = isStartCharacter.get();
        }
    }

    public static NpcConfiguration parse(JSONObject obj) {
        NpcConfiguration cfg = new NpcConfiguration();
        cfg.parseCommon(obj);
        cfg.type = JSONUtils.getIfExists(obj, "type", Object::toString)
                        .orElseThrow(() -> new RuntimeException("Tried parsing NPC without a type."));
        cfg.isStartCharacter = JSONUtils.getIfExists(obj, "start", b -> (boolean) b);

        return cfg;
    }
}
