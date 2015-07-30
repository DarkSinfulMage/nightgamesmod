package nightgames.status;

import java.util.HashSet;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.combat.Combat;

public class Oiled extends Status {

	public Oiled(Character affected) {
		super("Oiled", affected);
		flag(Stsflag.oiled);
	}

	@Override
	public String describe() {
		if(affected.human()){
			return "Your skin is slick with oil and kinda feels weird.";
		}
		else{
			return affected.name()+" is shiny with lubricant, making you more tempted to touch and rub her skin.";
		}
	}

	@Override
	public int mod(Attribute a) {
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
	public int pleasure(Combat c, int x) {
		return x/4;
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
		return 8;
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
	public boolean lingering(){
		return true;
	}

	@Override
	public int value() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new Oiled(newAffected);
	}
}
