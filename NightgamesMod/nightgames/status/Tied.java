package nightgames.status;

import org.json.simple.JSONObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;
import nightgames.global.JSONUtils;

public class Tied extends DurationStatus {

	public Tied(Character affected) {
		super("Tied Up", affected, 5);
		flag(Stsflag.tied);
	}

	public Tied(Character affected, int duration) {
		super("Tied Up", affected, duration);
		flag(Stsflag.tied);
	}

	@Override
	public String describe(Combat c) {
		if (affected.human()) {
			return "The rope wrapped around you digs into your body, but only slows you down a bit.";
		}

		return affected.name()
				+ " squirms against the rope, but you know you tied it well.";
	}

	@Override
	public int mod(Attribute a) {
		if (a == Attribute.Speed) {
			return -1;
		}
		return 0;
	}

	@Override
	public int regen(Combat c) {
		return 0;
	}

	@Override
	public int damage(Combat c, int x) {
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
		return -10;
	}

	@Override
	public int escape() {
		return 0;
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
		return 0;
	}

	@Override
	public int value() {
		return 0;
	}

	public void turn(Combat c) {
	}

	public Status copy(Character target) {
		return new Tied(target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject saveToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", getClass().getSimpleName());
		obj.put("duration", getDuration());
		return obj;
	}

	@Override
	public Status loadFromJSON(JSONObject obj) {
		return new Tied(null, JSONUtils.readInteger(obj, "duration"));
	}

	@Override
	public String initialMessage(Combat c, boolean replaced) {
		return String.format("%s now partially tied up.\n",
				affected.subjectAction("are", "is"));
	}

	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new Tied(newAffected);
	}

	@Override
	public double pleasure(Combat c, BodyPart withPart, BodyPart targetPart, double x) {
		return 0;
	}

}
