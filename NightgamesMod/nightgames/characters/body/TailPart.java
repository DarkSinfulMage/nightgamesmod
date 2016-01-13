package nightgames.characters.body;

import org.json.simple.JSONObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.combat.Combat;

public enum TailPart implements BodyPart,BodyPartMod {
    demonic("demonic ", .2, 1.2, 1),
    cat("cat's ", .3, 1.5, 1.5);

    public String desc;
    public double hotness;
    public double pleasure;
    public double sensitivity;

    TailPart(String desc, double hotness, double pleasure, double sensitivity) {
        this.desc = desc;
        this.hotness = hotness;
        this.pleasure = pleasure;
        this.sensitivity = sensitivity;
    }

    @Override
    public void describeLong(StringBuilder b, Character c) {
        b.append("A lithe " + describe(c) + " swings lazily behind " + c.nameOrPossessivePronoun() + " back.");
    }

    @Override
    public String describe(Character c) {
        return desc + "tail";
    }

    @Override
    public double priority(Character c) {
        return getPleasure(c, null);
    }

    @Override
    public String fullDescribe(Character c) {
        return desc + "tail";
    }

    @Override
    public String toString() {
        return desc + "tail";
    }

    @Override
    public boolean isType(String type) {
        return type.equalsIgnoreCase("tail");
    }

    @Override
    public String getType() {
        return "tail";
    }

    @Override
    public double getHotness(Character self, Character opponent) {
        return hotness;
    }

    @Override
    public double getPleasure(Character self, BodyPart target) {
        return pleasure;
    }

    @Override
    public double getSensitivity(BodyPart target) {
        return sensitivity;
    }

    @Override
    public boolean isReady(Character self) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject save() {
        JSONObject obj = new JSONObject();
        obj.put("enum", name());
        return obj;
    }

    @Override
    public BodyPart load(JSONObject obj) {
        return TailPart.valueOf((String) obj.get("enum"));
    }

    @Override
    public double applyBonuses(Character self, Character opponent, BodyPart target, double damage, Combat c) {
        return 0;
    }

    @Override
    public String getFluids(Character c) {
        return this == TailPart.demonic ? "tail-cum" : "";
    }

    @Override
    public boolean isErogenous() {
        return false;
    }

    @Override
    public boolean isNotable() {
        return true;
    }

    @Override
    public double applyReceiveBonuses(Character self, Character opponent, BodyPart target, double damage, Combat c) {
        return 0;
    }

    @Override
    public BodyPart upgrade() {
        return this;
    }

    @Override
    public BodyPart downgrade() {
        return this;
    }

    @Override
    public String prefix() {
        return "a ";
    }

    @Override
    public int compare(BodyPart other) {
        return 0;
    }

    @Override
    public boolean isVisible(Character c) {
        return true;
    }

    @Override
    public double applySubBonuses(Character self, Character opponent, BodyPart with, BodyPart target, double damage,
                    Combat c) {
        return 0;
    }

    @Override
    public int mod(Attribute a, int total) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void tickHolding(Combat c, Character self, Character opponent, BodyPart otherOrgan) {

    }

    @Override
    public int counterValue(BodyPart other) {
        return 0;
    }

    @Override
    public BodyPartMod getMod() {
        return this;
    }

    @Override
    public String getModType() {
        return name();
    }
}
