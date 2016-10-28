package nightgames.skills.strategy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.combat.Combat;
import nightgames.global.Global;
import nightgames.nskills.tags.SkillTag;
import nightgames.skills.Skill;
import nightgames.skills.Tactics;

public class FuckStrategy implements CombatStrategy {
    @Override
    public double weight(Combat c, Character self) {
        double weight = 1;
        if (self.getMood().equals(Emotion.horny)) {
            weight *= 2;
        }
        return weight;
    }

    @Override
    public Set<Skill> nextSkills(Combat c, Character self) {
        Character other = c.getOther(self);
        Set<Skill> availableSkills = new HashSet<>(self.getSkills());
        Skill.filterAllowedSkills(c, availableSkills, self, other);
        Set<Skill> allowedSkills = availableSkills.stream().filter(skill -> Skill.skillIsUsable(c, skill, other)).collect(Collectors.toSet());
        Set<Skill> fuckSkills = allowedSkills.stream().filter(skill -> Tactics.fucking.equals(skill.type(c))).collect(Collectors.toSet());
        if (!fuckSkills.isEmpty()) {
            return fuckSkills;
        }

        Set<Tactics> positioningTactics = new HashSet<>();
        positioningTactics.add(Tactics.damage);
        positioningTactics.add(Tactics.debuff);
        positioningTactics.add(Tactics.positioning);

        Set<Skill> positioningSkills = allowedSkills.stream().filter(skill -> positioningTactics.contains(skill.type(c))).collect(Collectors.toSet());
        if (!c.getStance().mobile(self) || c.getStance().mobile(other)) {
            return positioningSkills;
        }
        if (!other.crotchAvailable()) {
            allowedSkills.stream().filter(skill -> Tactics.stripping.equals(skill.type(c)));
        }
        if (!self.crotchAvailable()) {
            return allowedSkills.stream().filter(skill -> skill.getTags().contains(SkillTag.undressing)).collect(Collectors.toSet());
        }
        if (other.getArousal().percent() < 15) {
            return allowedSkills.stream().filter(skill -> skill.type(c).equals(Tactics.pleasure)).collect(Collectors.toSet());
        }
        if (self.getArousal().percent() < 15) {
            return allowedSkills.stream().filter(skill -> skill.getTags().contains(SkillTag.pleasureSelf)).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @Override
    public CombatStrategy instance() {
        return new FuckStrategy();
    }
    
    @Override
    public int initialDuration(Combat c, Character self) {
        return Global.random(4, 8);
    }
}