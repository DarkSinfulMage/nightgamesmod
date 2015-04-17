package characters;
import items.Clothing;
import items.Item;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.Icon;

import characters.body.BreastsPart;
import characters.body.CockPart;

import skills.Skill;
import skills.Cunnilingus;
import skills.Escape;
import skills.Finger;
import skills.Flick;
import skills.Focus;
import skills.FondleBreasts;
import skills.Fuck;
import skills.Kiss;
import skills.Knee;
import skills.LickNipples;
import skills.Maneuver;
import skills.Nothing;
import skills.PerfectTouch;
import skills.Recover;
import skills.Restrain;
import skills.Shove;
import skills.Slap;
import skills.Spank;
import skills.Straddle;
import skills.StripBottom;
import skills.StripTop;
import skills.Struggle;
import skills.SuckNeck;
import skills.Tackle;
import skills.Tactics;
import skills.Tickle;
import skills.Trip;
import stance.Behind;
import status.Horny;
import status.Masochistic;
import status.Status;
import status.Enthralled;
import status.Stsflag;
import trap.Trap;
import global.Global;
import global.Modifier;
import gui.ActionButton;
import gui.GUI;
import combat.Combat;
import combat.Encounter;
import combat.Result;

import actions.Action;
import actions.Move;
import areas.Area;
import areas.Deployable;


public class Player extends Character {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7547460660118646782L;
	public GUI gui;
	public int attpoints;
	public String sex;
	
	public Player(String name, String sex) {
		super(name, 1);
		outfit[0].add(Clothing.Tshirt);
		outfit[1].add(Clothing.boxers);
		outfit[1].add(Clothing.jeans);
		closet.add(Clothing.Tshirt);
		closet.add(Clothing.boxers);
		closet.add(Clothing.jeans);
		//debug2
		change(Modifier.normal);
		attpoints=0;
		setUnderwear(Item.PlayerTrophy);
		body.finishBody(sex);
	}
	public Player(String name) {
		this(name, "male");
	}


	@Override
	public String describe(int per) {
		String description="<i>";
		for(Status s:status){
			description = description+s.describe()+"<br>";
		}
		description = description+"</i>";
		if(top.empty()&&bottom.empty()){
			description = description+"You are completely naked.<br>";
		}
		else{
			if(top.empty()){
				description = description+"You are shirtless and ";
				if(!bottom.empty()){
					description=description+"wearing ";
				}
			}
			else{
				description = description+"You are wearing a "+top.peek().getName()+" and ";
			}
			if(bottom.empty()){
				description = description+"are naked from the waist down.<br>";
			}
			else{
				description = description+bottom.peek().getName()+".<br>";
			}
		}
		if(per>=5 && this.status.size() > 0){
			description += "<br>List of statuses:<br><i>";
			for (Status s : this.status) {
				description += s + ", ";
			}
			description += "</i><br>";
		}
		return description;
	}

	@Override
	public void victory(Combat c, Result flag) {
		if(c.getStance().penetration(this)){
			getMojo().gain(2);
			if(has(Trait.mojoMaster)){
				getMojo().gain(2);
			}
		}
		if(c.p1.human()){
			c.p2.defeat(c,flag);
		}
		else{
			c.p1.defeat(c,flag);
		}

	}

	@Override
	public void defeat(Combat c, Result flag) {
		c.write("Bad thing");
	}

	@Override
	public void act(Combat c) {
		gui.clearCommand();
		Character target;
		if(c.p1==this){
			target=c.p2;
		}
		else{
			target=c.p1;
		}
		HashSet<Skill> damage = new HashSet<Skill>();
		HashSet<Skill> pleasure = new HashSet<Skill>();
		HashSet<Skill> fucking = new HashSet<Skill>();
		HashSet<Skill> position = new HashSet<Skill>();
		HashSet<Skill> debuff = new HashSet<Skill>();
		HashSet<Skill> recovery = new HashSet<Skill>();
		HashSet<Skill> summoning = new HashSet<Skill>();
		HashSet<Skill> stripping = new HashSet<Skill>();
		HashSet<Skill> misc = new HashSet<Skill>();
		
		for(Skill a:skills){
			if(Skill.skillIsUsable(c, a, target)){
				if(a.type(c)==Tactics.damage){
					damage.add(a);
				}
				else if(a.type(c)==Tactics.pleasure){
					pleasure.add(a);
				}else if(a.type(c)==Tactics.fucking){
					fucking.add(a);
				}
				else if(a.type(c)==Tactics.positioning){
					position.add(a);
				}
				else if(a.type(c)==Tactics.debuff){
					debuff.add(a);
				}
				else if(a.type(c)==Tactics.recovery||a.type(c)==Tactics.calming){
					recovery.add(a);
				}
				else if(a.type(c)==Tactics.summoning){
					summoning.add(a);
				}
				else if(a.type(c)==Tactics.stripping){
					stripping.add(a);
				}
				else{
					misc.add(a);
				}
			}
		}
		for(Skill a: stripping){
			gui.addSkill(a,c);
		}
		for(Skill a: position){
			gui.addSkill(a,c);
		}
		for(Skill a: fucking){
			gui.addSkill(a,c);
		}
		for(Skill a: pleasure){
			gui.addSkill(a,c);
		}
		if(Global.getMatch().condition!=Modifier.pacifist){
			for(Skill a: damage){
				gui.addSkill(a,c);
			}
		}
		for(Skill a: debuff){
			gui.addSkill(a,c);
		}
		for(Skill a: summoning){
			gui.addSkill(a,c);
		}
		for(Skill a: recovery){
			gui.addSkill(a,c);
		}
		for(Skill a: misc){
			gui.addSkill(a,c);
		}
		gui.showSkills(0);
	}

	@Override
	public boolean human() {
		return true;
	}

	@Override
	public void draw(Combat c, Result flag) {
		if(c.getStance().penetration(this)){
			c.p1.getMojo().gain(3);
			c.p2.getMojo().gain(3);
		}
		if(c.p1.human()){
			c.p2.draw(c,flag);
		}
		else{
			c.p1.draw(c,flag);
		}

	}
	public void change(Modifier rule){
		if(rule==Modifier.nudist){
			top.clear();
			bottom.clear();
		}
		else if(rule==Modifier.pantsman){
			top.clear();
			bottom.clear();
			bottom.add(Clothing.boxers);
		}
		else{
			top=(Stack<Clothing>) outfit[0].clone();
			bottom=(Stack<Clothing>) outfit[1].clone();
		}
	}

	@Override
	public String bbLiner() {
		return null;
	}

	@Override
	public String nakedLiner() {
		return null;
	}

	@Override
	public String stunLiner() {
		return null;
	}

	@Override
	public String winningLiner() {
		return null;
	}

	@Override
	public String taunt() {
		return null;
	}

	@Override
	public void detect() {
		for(Area adjacent:location.adjacent){
			if(adjacent.ping(get(Attribute.Perception))){
				Global.gui().message("You hear something in the <b>"+adjacent.name+"</b>.");
			}
		}
	}
	@Override
	public void faceOff(Character opponent, Encounter enc) {
		gui.message("You run into <b>"+opponent.name+"</b> and you both hesitate for a moment, deciding whether to attack or retreat.");
		assessOpponent(opponent);
		gui.promptFF(enc,opponent);
	}

	private void assessOpponent(Character opponent){
		String arousal;
		String stamina;
		if(opponent.state==State.webbed){
			gui.message("She is naked and helpless<br>");
			return;
		}
		if(get(Attribute.Perception)>=6){
			gui.message("She is level "+opponent.getLevel());
		}
		if(get(Attribute.Perception)>=8){
			gui.message("Her Power is "+opponent.get(Attribute.Power)+", her Cunning is "+opponent.get(Attribute.Cunning)+", and her Seduction is "+opponent.get(Attribute.Seduction));
		}
		if(opponent.nude()||opponent.state==State.shower){
			gui.message("She is completely naked.");
		}
		else{
			gui.message("She is dressed and ready to fight.");
		}
		if(get(Attribute.Perception)>=4){
			if(opponent.getArousal().percent()>70){
				arousal="horny";
			}
			else if(opponent.getArousal().percent()>30){
				arousal="slightly aroused";
			}
			else{
				arousal="composed";
			}
			if(opponent.getStamina().percent()<50){
				stamina="tired";
			}
			else{
				stamina="eager";
			}
			gui.message("She looks "+stamina+" and "+arousal+".");
		}
	}
	@Override
	public void spy(Character opponent, Encounter enc) {
		gui.message("You spot <b>"+opponent.name+"</b> but she hasn't seen you yet. You could probably catch her off guard, or you could remain hidden and hope she doesn't notice you.");
		assessOpponent(opponent);
		gui.promptAmbush(enc, opponent);
	}

	@Override
	public void move() {
		gui.clearCommand();
		if(state==State.combat){
			if(!location.fight.battle()){
				Global.getMatch().resume();
			}
		}
		else if(busy>0){
			busy--;
		}
		else if (this.is(Stsflag.enthralled)) {
			Character master;
			master = ((Enthralled)getStatus(Stsflag.enthralled)).master;
			if(master!=null){
			Move compelled = findPath(master.location());
			gui.message("You feel an irresistible compulsion to head to the <b>"
					+ master.location().name+"</b>");
			if (compelled != null)
				this.gui.addAction(compelled, this);
			}
		}
		else if(state==State.shower||state==State.lostclothes){
			bathe();
		}
		else if(state==State.crafting){
			craft();
		}
		else if(state==State.searching){
			search();
		}
		else if(state==State.resupplying){
			resupply();
		}
		else if(state==State.webbed){
			gui.message("You eventually manage to get an arm free, which you then use to extract yourself from the trap.");
			state=State.ready;
		}
		else if(state==State.masturbating){
			masturbate();
		}
		else{
			gui.message(location.description+"<p>");
			for(Deployable trap: location.env){
				if(trap.owner()==this){
					gui.message("You've set a "+trap.toString()+" here.");
				}
			}
			detect();
			if(!location.encounter(this)){
				for(Area path:location.adjacent){
					gui.addAction(new Move(path),this);
				}
				for(Action act:Global.getActions()){
					if(act.usable(this)){
						gui.addAction(act,this);
					}
				}
			}
		}
	}
	public void ding(){
		level++;
		attpoints+=2;
		gui.message("You've gained a Level!<br>Select which attributes to increase.");
		gui.ding();
		if(has(Trait.fitnessNut)){
			getStamina().gain(1);
		}
		getStamina().gain(2);
		if(has(Trait.expertGoogler)){
			getArousal().gain(2);
		}
		getArousal().gain(4);
		if(has(Trait.mojoMaster)){
			getMojo().gain(1);
		}
		getMojo().gain(1);
	}
	public void flee(Area location2) {
		Area[] adjacent = location2.adjacent.toArray(new Area[location2.adjacent.size()]);
		Area destination = adjacent[Global.random(adjacent.length)];
		gui.message("You dash away and escape into the <b>"+destination.name+"</b>");
		travel(destination);
		location2.endEncounter();
	}
	public void bathe(){
		status.clear();
		stamina.fill();
		if(location.name=="Showers"){
			gui.message("You let the hot water wash away your exhaustion and soon you're back to peak condition");
		}
		if(location.name=="Pool"){
			gui.message("The hot water soothes and relaxes your muscles. You feel a bit exposed, skinny-dipping in such an open area. You decide it's time to get moving.");
		}
		if(state==State.lostclothes){
			gui.message("Your clothes aren't where you left them. Someone must have come by and taken them.");
		}
		state=State.ready;
	}
	public void craft(){
		int roll = Global.random(10);
		if(check(Attribute.Cunning, 25)){
			if(roll==9){
				gain(Item.Aphrodisiac);
				gain(Item.DisSol);
			}
			else if(roll>=5){
				gain(Item.Aphrodisiac);
			}
			else{
				gain(Item.Lubricant);
				gain(Item.Sedative);
			}
		}
		else if(check(Attribute.Cunning, 20)){
			if(roll==9){
				gain(Item.Aphrodisiac);
			}
			else if(roll>=7){
				gain(Item.DisSol);
			}
			else if(roll>=5){
				gain(Item.Lubricant);
			}
			else if(roll>=3){
				gain(Item.Sedative);
			}
			else{
				gain(Item.EnergyDrink);
			}
		}
		else if(check(Attribute.Cunning, 15)){
			if(roll==9){
				gain(Item.Aphrodisiac);
			}
			else if(roll>=8){
				gain(Item.DisSol);
			}
			else if(roll>=7){
				gain(Item.Lubricant);
			}
			else if(roll>=6){
				gain(Item.EnergyDrink);
			}
			else{
				gui.message("Your concoction turns a sickly color and releases a foul smelling smoke. You trash it before you do any more damage.");
			}
		}
		else{
			if(roll>=7){
				gain(Item.Lubricant);
			}
			else if(roll>=5){
				gain(Item.Sedative);
			}
			else{
				gui.message("Your concoction turns a sickly color and releases a foul smelling smoke. You trash it before you do any more damage.");
			}
		}
		state=State.ready;
	}
	public void search(){
		int roll = Global.random(10);
		switch(roll){
		case 9:
			gain(Item.Tripwire);
			gain(Item.Tripwire);
			break;
		case 8:
			gain(Item.ZipTie);
			gain(Item.ZipTie);
			gain(Item.ZipTie);
			break;
		case 7:
			gain(Item.Phone);
			break;
		case 6:
			gain(Item.Rope);
			break;
		case 5:
			gain(Item.Spring);
			break;
		default:
			gui.message("You don't find anything useful");
		}
		state=State.ready;
	}
	public void masturbate(){
		gui.message("You hurriedly stroke yourself off, eager to finish before someone catches you. After what seems like an eternity, you ejaculate into a tissue and " +
				"throw it in the trash. Looks like you got away with it.");
		arousal.empty();
		state=State.ready;
	}

	@Override
	public void showerScene(Character target, Encounter encounter) {
		if(target.location().name=="Showers"){
			gui.message("You hear running water coming from the first floor showers. There shouldn't be any residents on this floor right now, so it's likely one " +
					"of your opponents. You peek inside and sure enough, <b>"+target.name()+"</b> is taking a shower and looking quite vulnerable. Do you take advantage " +
					"of her carelessness?");
		}
		else if(target.location().name=="Pool"){
			gui.message("You stumble upon <b>"+target.name+"</b> skinny dipping in the pool. She hasn't noticed you yet. It would be pretty easy to catch her off-guard.");
		}
		assessOpponent(target);
		gui.promptShower(encounter, target);
	}

	@Override
	public void intervene(Encounter enc, Character p1, Character p2) {
		gui.message("You find <b>"+p1.name()+"</b> and <b>"+p2.name()+"</b> fighting too intensely to notice your arrival. If you intervene now, it'll essentially decide the winner.");
		gui.promptIntervene(enc,p1,p2);
	}

	@Override
	public void intervene3p(Combat c, Character target, Character assist) {
		this.gainXP(20+lvlBonus(target));
		target.defeated(this);
		assist.gainAttraction(this, 1);
		c.write("You take your time, approaching "+target.name()+" and "+assist.name()+" stealthily. "+assist.name()+" notices you first and before her reaction " +
				"gives you away, you quickly lunge and grab "+target.name()+" from behind. She freezes in surprise for just a second, but that's all you need to " +
				"restrain her arms and leave her completely helpless. Both your hands are occupied holding her, so you focus on kissing and licking the " +
				"sensitive nape of her neck.<p>");
	}

	@Override
	public void victory3p(Combat c, Character target, Character assist) {
		this.gainXP(20+lvlBonus(target));
		target.gainXP(10+target.lvlBonus(this)+target.lvlBonus(assist));
		target.arousal.empty();
		if(target.has(Trait.insatiable)){
			target.arousal.restore((int) (arousal.max()*.2));
		}
		dress(c);
		target.undress(c);
		if(c.clothespile.contains(target.outfit[1].firstElement())){
			this.gain(target.getUnderwear());
		}
		target.defeated(this);
		c.write(target.name()+"'s arms are firmly pinned, so she tries to kick you ineffectually. You catch her ankles and slowly begin kissing and licking your way " +
				"up her legs while gently, but firmly, forcing them apart. By the time you reach her inner thighs, she's given up trying to resist. Since you no " +
				"longer need to hold her legs, you can focus on her flooded pussy. You pump two fingers in and out of her while licking and sucking her clit. In no " +
				"time at all, she's trembling and moaning in orgasm.");
		gainAttraction(target,1);
		target.gainAttraction(this,1);
	}
	
	@Override
	public void gain(Item item) {
		Global.gui().message("<b>You've gained "+item.pre()+item.getName()+"</b>");
		super.gain(item);
	}

	@Override
	public void save(PrintWriter saver) {
		saver.write("P\n");
		super.save(saver);
	}

	@Override
	public void load(Scanner loader) {
		name=loader.next();
		super.load(loader);
	}

	@Override
	public String challenge() {
		return null;
	}

	@Override
	public void promptTrap(Encounter enc,Character target,Trap trap) {
		Global.gui().message("Do you want to take the opportunity to ambush <b>"+target.name()+"</b>?");
		assessOpponent(target);
		gui.promptOpportunity(enc, target, trap);		
	}

	@Override
	public void afterParty() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void counterattack(Character target, Tactics type, Combat c) {
		switch(type){
		case damage:
			c.write("You dodge "+target.name()+"'s slow attack and hit her sensitive tit to stagger her.");
			target.pain(c, 4+Global.random(get(Attribute.Cunning)));
			break;
		case pleasure:
			if(!target.nude()){
				c.write("You pull "+target.name()+" off balance and lick her sensitive ear. She trembles as you nibble on her earlobe.");
				target.body.pleasure(this, body.getRandom("tongue"), target.body.getRandom("ear"), 4+Global.random(get(Attribute.Cunning)), c);
			}
			else{
				c.write("You pull "+target.name()+" to you and rub your thigh against her girl parts.");
				target.body.pleasure(this, body.getRandom("legs"), target.body.getRandomPussy(), 4+Global.random(get(Attribute.Cunning)), c);
			}
			break;
		case positioning:
			c.write(target.name()+" loses her balance while grappling with you. Before she can fall to the floor, you catch her from behind and hold her up.");
			c.setStance(new Behind(this,target));
			break;
		default:
		}
	}

	@Override
	public void eot(Combat c, Character opponent, Skill last) {
		dropStatus();
		if(opponent.pet!=null&&canAct()&&c.getStance().mobile(this)&&!c.getStance().prone(this)){
			if(get(Attribute.Speed)>opponent.pet.ac()*Global.random(20)){
				opponent.pet.caught(c,this);
			}
		}
		int pheromoneChance = opponent.top.size() + opponent.bottom.size();
		if(opponent.has(Trait.pheromones)&&opponent.getArousal().percent()>=20&&Global.random(2 + pheromoneChance)==0){
			c.write(opponent,"<br>Whenever you're near "+opponent.name()+", you feel your body heat up. Something in her scent is making you extremely horny.");
			add(new Horny(this,1,10,opponent.nameOrPossessivePronoun() + " pheromones"));
		}
		if(opponent.has(Trait.smqueen)&&!is(Stsflag.masochism)){
			c.write(String.format("<br>%s seem to shudder in arousal at the thought of pain.", subject()));
			add(new Masochistic(this));
		}
		if(has(Trait.RawSexuality)){
			tempt(c, opponent, arousal.max()/25);
			opponent.tempt(c, this, opponent.arousal.max()/25);
		}
	}

	@Override
	public String nameDirectObject() {
		return "you";
	}

	@Override
	public String subjectAction(String verb, String pluralverb) {
		return subject() + " " + verb;
	}

	@Override
	public String nameOrPossessivePronoun() {
		return "your";
	}

	@Override
	public String possessivePronoun() {
		return "your";
	}

	@Override
	public String reflectivePronoun() {
		return directObject() + "self";
	}
	@Override
	public String subject() {
		return "you";
	}

	@Override
	public String subjectWas() {
		return subject() + " were";
	}

	@Override
	public String pronoun() {
		return "you";
	}

	@Override
	public String directObject() {
		return "you";
	}

	@Override
	public void emote(Emotion emo, int amt) {
		return;
	}
	@Override
	public String getPortrait() {
		// TODO Auto-generated method stub
		return null;
	}
}
