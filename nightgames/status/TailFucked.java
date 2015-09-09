package nightgames.status;

import org.json.simple.JSONObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.body.BodyPart;
import nightgames.characters.custom.requirement.EitherInsertedRequirement;
import nightgames.combat.Combat;
import nightgames.global.Global;
import nightgames.global.JSONUtils;

public class TailFucked extends Status {
	private String target;
	private Character other;

	public TailFucked(Character affected, Character other, String hole) {
		super(hole.equals("ass") ? "Tail Pegged" : "Tail Fucked", affected);
		this.target = hole;
		this.other = other;
		requirements.add(new EitherInsertedRequirement(true));
		flag(Stsflag.bound);
	}

	@Override
	public String initialMessage(Combat c, boolean replaced) {
		BodyPart hole = affected.body.getRandom(target);
		BodyPart tail = other.body.getRandom("tail");
		if (hole == null || tail == null) { return ""; }
		return Global.capitalizeFirstLetter(String.format("%s now fucking %s %s with %s %s\n", other.subjectAction("are", "is"), affected.nameOrPossessivePronoun(), hole.describe(affected), other.possessivePronoun(), tail.describe(other)));
	}

	@Override
	public String describe() {
		BodyPart hole = affected.body.getRandom(target);
		BodyPart tail = other.body.getRandom("tail");
		if (hole == null || tail == null) { return ""; }
		if(affected.human()){
			return Global.capitalizeFirstLetter(String.format("%s fucking %s %s with %s %s\n", other.subjectAction("are", "is"), affected.nameOrPossessivePronoun(), hole.describe(affected), other.possessivePronoun(), tail.describe(other)));
		}
		else{
			return Global.capitalizeFirstLetter(String.format("%s fucking %s %s with %s %s\n", other.subjectAction("are", "is"), affected.nameOrPossessivePronoun(), hole.describe(affected), other.possessivePronoun(), tail.describe(other)));
		}
	}

	@Override
	public float fitnessModifier () {
		return -3;
	}

	@Override
	public int mod(Attribute a) {
		return 0;
	}

	@Override
	public void tick(Combat c) {
		BodyPart hole = affected.body.getRandom(target);
		BodyPart tail = other.body.getRandom("tail");
		if (hole == null || tail == null) {
			return;
		}
		c.write(other, Global.capitalizeFirstLetter(Global.format("{other:name-possessive} {other:body-part:tail} relentlessly fucks {self:name-do} in {self:possessive} {self:body-part:"+target+"}", affected, other)));
		affected.body.pleasure(other, tail, hole, 10, c);
		other.body.pleasure(other, hole, tail, 2, c);
		affected.emote(Emotion.desperate, 10);
		affected.emote(Emotion.nervous, 10);
	}
	@Override
	public int damage(Combat c, int x) {
		return 0;
	}

	@Override
	public double pleasure(Combat c, double x) {
		return 0;
	}

	@Override
	public int weakened(int x) {
		return 0;
	}

	@Override
	public int tempted(int x) {
		return 0;
	}

	@Override
	public int evade() {
		return -5;
	}

	@Override
	public int escape() {
		return -5;
	}

	@Override
	public int gainmojo(int x) {
		return 0;
	}

	@Override
	public int spendmojo(int x) {
		return 0;
	}

	@Override
	public int counter() {
		return -10;
	}
	public String toString(){
		return "Tail fucked";
	}

	@Override
	public int value() {
		return 0;
	}

	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new TailFucked(newAffected, newOther, target);
	}
	@SuppressWarnings("unchecked")
	public JSONObject saveToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", getClass().getSimpleName());
		obj.put("target", target);
		return obj;
	}

	public Status loadFromJSON(JSONObject obj) {
		return new TailFucked(null, null, JSONUtils.readString(obj, "target"));
	}

	@Override
	public int regen(Combat c) { return 0; }
}
