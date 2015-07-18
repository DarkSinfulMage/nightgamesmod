package skills;

import items.Item;
import pet.Slime;
import characters.Attribute;
import characters.Character;
import characters.Trait;

import combat.Combat;
import combat.Result;

public class SpawnSlime extends Skill {

	public SpawnSlime(Character self) {
		super("Create Slime", self);
	}

	@Override
	public boolean requirements(Character user) {
		return user.get(Attribute.Science)>=3;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		return getSelf().canAct()&&c.getStance().mobile(getSelf())&&!c.getStance().prone(getSelf())&&getSelf().pet==null&&getSelf().has(Item.Battery)&&getSelf().canSpend(5);
	}

	@Override
	public String describe() {
		return "Creates a mindless, but living slime to attack your opponent: 1 Battery";
	}

	@Override
	public void resolve(Combat c, Character target) {
		getSelf().spendMojo(c, 5);
		getSelf().consume(Item.Battery, 1);
		int power = 3;
		int ac = 3;
		if(getSelf().has(Trait.leadership)){
			power++;
		}
		if(getSelf().human()){
			c.write(getSelf(),deal(c,0,Result.normal, target));
		}
		else if(target.human()){
			c.write(getSelf(),receive(c,0,Result.normal, target));
		}
		getSelf().pet=new Slime(getSelf(),power,ac);
	}

	@Override
	public Skill copy(Character user) {
		return new SpawnSlime(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.summoning;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		return "You dispense blue slime on the floor and send a charge through it to animate it. The slime itself is not technically alive, but an extension of a larger " +
				"creature kept in Jett's lab.";
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		return getSelf().name()+" points a device at the floor and releases a blob of blue slime. The blob starts to move like a living thing and briefly takes on a vaguely humanoid shape " +
				"and smiles at you.";
	}

}
