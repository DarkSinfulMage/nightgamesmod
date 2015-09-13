package nightgames.characters;

import java.util.Optional;

import nightgames.characters.body.BasicCockPart;
import nightgames.characters.body.BreastsPart;
import nightgames.characters.body.PussyPart;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.global.Modifier;
import nightgames.items.Item;
import nightgames.items.clothing.Clothing;

public class Eve extends BasePersonality {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8169646189131720872L;
	public Eve(){
		super();
		character = new NPC("Eve",10,this);
		character.outfitPlan.add(Clothing.getByID("tanktop"));
		character.outfitPlan.add(Clothing.getByID("crotchlesspanties"));
		character.outfitPlan.add(Clothing.getByID("jeans"));
		character.outfitPlan.add(Clothing.getByID("stilettopumps"));
		character.outfitPlan.add(Clothing.getByID("garters"));

		character.change(Modifier.normal);
		character.mod(Attribute.Seduction, 2);
		character.mod(Attribute.Perception, 1);
		Global.gainSkills(character);
		character.add(Trait.exhibitionist);
		character.add(Trait.proheels);
		character.setTrophy(Item.EveTrophy);
		character.plan = Plan.hunting;
		character.mood = Emotion.confident;
		character.body.add(BreastsPart.d);
		character.body.add(BasicCockPart.big);
		character.body.add(PussyPart.normal);
		character.body.finishBody(CharacterSex.herm);
	}

	@Override
	public void setGrowth() {
		growth.stamina = 2;
		growth.arousal = 3;
		growth.mojo = 2;
		growth.bonusStamina = 1;
		growth.bonusArousal = 3;
		growth.bonusMojo = 2;
		preferredAttributes.add(c -> c.get(Attribute.Fetish) < 80 ? Optional.of(Attribute.Fetish) : Optional.empty());
		preferredAttributes.add(c -> Optional.of(Attribute.Seduction));
	}

	public double dickPreference() {
		return 10;
	}

	@Override
	public void rest() {
		super.rest();
		if(!(character.has(Item.Dildo)||character.has(Item.Dildo2))&&character.money>=250){
			character.gain(Item.Dildo);
			character.money-=250;
		}
		if(!(character.has(Item.Onahole)||character.has(Item.Onahole2))&&character.money>=300){
			character.gain(Item.Onahole);
			character.money-=300;
		}
		if(!character.has(Item.Onahole2)&&character.has(Item.Onahole)&&character.money>=300){
			character.remove(Item.Onahole);
			character.gain(Item.Onahole2);
			character.money-=300;
		}
		if(character.money>0){
			Global.getDay().visit("XXX Store", character, Global.random(character.money));
		}
		if(character.money>0){
			Global.getDay().visit("Black Market", character, Global.random(character.money));	
		}
		if(character.money>0){
			Global.getDay().visit("Bookstore", character, Global.random(character.money));
		}
		if(character.money>0){
			Global.getDay().visit("Hardware Store", character, Global.random(character.money));
		}
		Decider.visit(character);
		int r;
		for(int i=0;i<9;i++){
			r=Global.random(4);
			if(r==1){
				if(character.has(Trait.fitnessNut)){
					character.getStamina().gain(2);
				}
				character.getStamina().gain(2);
			}
			else if(r==3){
				if(character.has(Trait.expertGoogler)){
					character.getArousal().gain(5);
				}
				character.getArousal().gain(5);
			}
			else if(r==2){
				if(character.has(Trait.mojoMaster)){
					character.getMojo().gain(2);
				}
				character.getMojo().gain(2);
			}
		}
	}

	@Override
	public String bbLiner(Combat c) {
		return "Eve grins at you and pats her own groin. <i>\"Better you than me, boy.\"</i>";
	}

	@Override
	public String nakedLiner(Combat c) {
		return "Eve seems more comfortable with her cock and balls hanging out than she was with her clothes on. <i>\"Like what you see? We're just getting started.\"</i>";
	}

	@Override
	public String stunLiner(Combat c) {
		return "Eve lets out a soft growl as she lays flat on the floor. <i>\"Enjoy it while you can, boy. As soon as I catch my breath, your ass is mine.\"</i>";
	}

	@Override
	public String taunt(Combat c) {
		return "Eve grins sadistically. <i>\"If you're intimidated by my cock, don't worry. Size isn't everything.\"</i>";
	}

	@Override
	public String temptLiner(Combat c) {
		return "Eve grins sadistically. <i>\"I'm an expert at making people like you squeal.\"</i>";
	}

	@Override
	public String victory(Combat c,Result flag) {
		character.arousal.empty();
		return "You try valiantly to outlast Eve, but it's no good. She's got you right where she wants you. She pins you down and skillfully strokes your cock. You feel pleasure rapidly " +
				"building in your abdomen as you pass the point of no return. You can't hold back anymore. You're about to ejaculate in her hands. She grins evily and fondles your balls " +
				"softly while continuing her handjob. <p>You suddenly feel a powerful shock run through you. It's a pleasurable sensation, but lacks the sense of release you get from an " +
				"orgasm and leaves you unsatisfied. You try to sit up to figure out what happened, but your body doesn't obey. You can't move! You're still breathing and can look around, but your arms and " +
				"legs are completely numb. Maybe numb isn't the right word. You can't move a finger, but you can accutely feel every touch. Eve kneads your sack with both hands, and you " +
				"feel and unnatural warth flow into you. <i>\"Feeling pretty helpless and frustrated now? I overloaded your nervous system by denying your orgasm. It's not a very useful " +
				"technique because it only works on someone who is about to cum. It does let me play with you much longer, so it's the best technique.\"</i> <p>You're still fully erect and " +
				"leaking pre-cum, but she's abandoned your dick and you can't lift a finger to help yourself. <i>\"Do you want to cum? Not quite yet. I'm still preparing you.\"</i> You " +
				"feel a churning in your testicles and the urge to ejaculate suddenly increases. It feels like she's turned up your semen production, leaving you even more pent-up. It's " +
				"making your balls much more sensitive, so her fondling is giving you a lot of pleasure, but no release. If she's trying to drive you insane, she's doing a pretty good " +
				"job of it.<p>Eve suddenly lets go of your junk and straddles your waist. She rubs her slit along your cock while idly stroking herself. <i>\"Are you feeling desperate yet? " +
				"You're almost ready for the big moment. You just need to figure out the million dollar question.\"</i> She leans close to your face with a wicked grin. <i>\"What fetish " +
				"did I imbue you with?\"</i> Of course she gave you an artificial fetish. That's why you're feeling so unnaturally horny. You're getting incredibly turned on from being at " +
				"Eve's mercy, and the fact that she's torturing you and keeping you on edge.<p>So that's it. She's imbued you with masochism. <i>\"Bingo!\"</i> Eve slams her knee into your " +
				"sensitive balls. You're hit with simultaneous waves of pleasure and agony. A fountain of cum shoots from your cock, even as you're busy curling up into the fetal position. " +
				"Through the haze of pain, you're vaguely aware that Eve is jerking off while watching your reaction, and she soon adds her ejaculation to the pool of semen you shot out. " +
				"<i>\"You sure put on a hell of a show, boy. Don't worry, I didn't hit you nearly as hard as it must have felt. You'll be ok in a couple minutes.\"</i>";
	}

	@Override
	public String defeat(Combat c,Result flag) {
		return "As you pleasure Eve, she gradually stops fighting back, apparently more interested in enjoying her orgasm than in the outcome of the fight. You grab her throbbing " +
				"cock and pump it rapidly. She lets out a scream of pleasure as she cums and fires powerful jets of semen into the air. She relaxes on the floor with a blissful " +
				"expression, idly playing with her own fluids and occasionally licking them off her fingers. <p>You wave your unsatisfied erection in front of her face to remind her " +
				"that she still owes you an orgasm. Eve smiles up at you lewdly. <i>\"Don't worry, boy. I'll get you off properly, but I don't want you just sitting back and enjoying " +
				"it. Sucking you dry is bound to get me all hot and bothered again, and I don't plan to walk away with a boner. Make sure you give as good as you get.\"</i> She's pretty " +
				"demanding for a loser. Fortunately for her, you're in a generous mood. Eve seems eager to hold up her side of the deal. She takes most of your cock into her her mouth " +
				"without much difficulty and begins to explore your length with her tongue. You watch her dick harden again, sooner than you would have expected.";
	}

	@Override
	public String describe(Combat c) {
			return "If there's one word to describe Eve's appearance, it would have to be 'wild'. Her face is quite pretty, though her eyes are an unnerving silver color. " +
					"She has bright purple hair gathered in a messy ponytail, a variety of tattoos decorating her extremely shapely body, and of couse it's " +
					"impossible to miss the larger than average cock and balls hanging between between her legs.";
	}

	@Override
	public String draw(Combat c,Result flag) {
		return "";
	}
	@Override
	public boolean fightFlight(Character opponent) {
		return !character.mostlyNude()||opponent.mostlyNude();
	}
	@Override
	public boolean attack(Character opponent) {
		return true;
	}

	@Override
	public String victory3p(Combat c, Character target, Character assist) {
		if(target.human()){
			return "";
		}
		else{
			return "";
		}
	}
	@Override
	public String intervene3p(Combat c, Character target, Character assist) {
		if(target.human()){
			return "";
		}
		else{
			return "";
		}
	}
	@Override
	public String startBattle(Character other) {
		return "Eve gives you a dominant grin and cracks her knuckles. <i>\"Come on boy, let's play.\"</i>";
	}
	@Override
	public boolean fit() {
		return !character.mostlyNude()&&character.getStamina().percent()>=50;
	}
	@Override
	public String night() {
		return "";
	}
	public void advance(){
		
	}

	public boolean checkMood(Emotion mood, int value) {
		switch(mood){
		case horny: case dominant:
			return value>=50;
		case nervous: case desperate:
			return value>=150;
		default:
			return value>=100;
		}
	}

	@Override
	public String orgasmLiner(Combat c) {
		return "";
	}

	@Override
	public String makeOrgasmLiner(Combat c) {
		return "";
	}
}
