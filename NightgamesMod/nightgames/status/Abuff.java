package nightgames.status;

import org.json.simple.JSONObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.global.JSONUtils;

public class Abuff extends DurationStatus {
	private Attribute	modded;
	private int			value;

	public Abuff(Character affected, Attribute att, int value, int duration) {
		super(String.format("%s %+d", att.toString(), value), affected,
				duration);
		modded = att;
		this.value = value;
	}

	@Override
	public String initialMessage(Combat c, boolean replaced) {
		String person, adjective, modification;
		person = affected.nameOrPossessivePronoun();

		if (Math.abs(value) > 5) {
			adjective = "greatly";
		} else {
			adjective = "";
		}
		if (value > 0) {
			modification = "augmented.";
		} else {
			modification = "sapped.";
		}

		return String.format("%s %s is now %s %s\n", person, modded, adjective,
				modification);
	}

	@Override
	public float fitnessModifier() {
		return value / (2.0f * Math.min(1.0f,
				Math.max(1, affected.getPure(modded)) / 10.0f));
	}

	@Override
	public String describe(Combat c) {
		String person, adjective, modification;

		if (affected.human()) {
			person = "You feel your";
		} else {
			person = affected.name() + "'s";
		}
		if (Math.abs(value) > 5) {
			adjective = "greatly";
		} else {
			adjective = "";
		}
		if (value > 0) {
			modification = "augmented.";
		} else {
			modification = "sapped.";
		}

		return String.format("%s %s is %s %s\n", person, modded, adjective,
				modification);
	}

	@Override
	public int mod(Attribute a) {
		if (a == modded) {
			return value;
		}
		return 0;
	}

	@Override
	public String getVariant() {
		return modded.toString();
	}

	@Override
	public boolean overrides(Status s) {
		return false;
	}

	@Override
	public void replace(Status s) {
		assert s instanceof Abuff;
		Abuff other = (Abuff) s;
		assert other.modded == modded;
		setDuration(Math.max(other.getDuration(), getDuration()));
		value += other.value;
		name = String.format("%s %+d", modded.toString(), value);
	}

	@Override
	public int regen(Combat c) {
		super.regen(c);
		return 0;
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
		return 0;
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
	public boolean lingering() {
		return true;
	}

	@Override
	public int value() {
		return 0;
	}

	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new Abuff(newAffected, modded, value, getDuration());
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject saveToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", getClass().getSimpleName());
		obj.put("modded", modded.name());
		obj.put("value", value);
		obj.put("duration", getDuration());
		return obj;
	}

	@Override
	public Status loadFromJSON(JSONObject obj) {
		return new Abuff(null,
				Attribute.valueOf(JSONUtils.readString(obj, "modded")),
				JSONUtils.readInteger(obj, "value"),
				JSONUtils.readInteger(obj, "duration"));
	}
}
