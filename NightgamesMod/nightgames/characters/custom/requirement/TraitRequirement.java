package nightgames.characters.custom.requirement;

import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.combat.Combat;

public class TraitRequirement implements CustomRequirement {
    Trait trait;

    public TraitRequirement(Trait trait) {
        this.trait = trait;
    }

    @Override
    public boolean meets(Combat c, Character self, Character other) {
        return self.has(trait);
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TraitRequirement that = (TraitRequirement) o;

        return trait == that.trait;

    }

    @Override public int hashCode() {
        return trait.hashCode();
    }
}
