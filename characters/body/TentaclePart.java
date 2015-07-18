package characters.body;

import global.Global;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import combat.Combat;

import status.Stsflag;
import characters.Character;

public class TentaclePart extends GenericBodyPart {
	public String attachpoint;
	String fluids;
	static String allowedAttachTypes[] = {"ass", "mouth", "pussy", "hands", "feet", "tail", "cock"};
	public static TentaclePart randomTentacle(String desc, Body body, String fluids, double hotness, double pleasure, double sensitivity) {
		Set<String> avail = new HashSet<String>(Arrays.asList(allowedAttachTypes));
		Set<String> parts = new HashSet<String>();
		for (BodyPart p : body.getCurrentParts()) {
			if (p instanceof TentaclePart) {
				avail.remove(((TentaclePart) p).attachpoint);
			}
			parts.add(p.getType());
		}
	
		avail.retainAll(parts);
		String type;
		ArrayList<String> availList = new ArrayList<String>(avail);
		if (avail.size() > 0) {
			type = availList.get(Global.random(availList.size()));
		} else {
			type = "back";
		}
		TentaclePart part = new TentaclePart(desc, type, fluids, hotness, pleasure, sensitivity);
		return part;
	}
	public TentaclePart(String desc, String attachpoint, String fluids, double hotness, double pleasure, double sensitivity) {
		super(desc, "", hotness, pleasure, sensitivity, true, "tentacles", "");
		this.attachpoint = attachpoint;
		this.fluids = fluids;
	}
	public static String synonyms[] = {
		"mass", "clump", "nest", "group",
	};

	@Override
	public void describeLong(StringBuilder b, Character c) {
		if (c.body.has(attachpoint)) {
			b.append("A " + Global.pickRandom(synonyms) + " of ");
			b.append(describe(c));
			b.append(" sprouts from " + c.nameOrPossessivePronoun() + " " + attachpoint + ".");
		} else {
			b.append("A " + Global.pickRandom(synonyms) + " of ");
			b.append(describe(c));
			b.append(" sprouts from " + c.nameOrPossessivePronoun() + " back.");
		}
	}

	@Override
	public String describe(Character c) {
		return desc;
	}
	@Override
	public String fullDescribe(Character c) {
		return attachpoint + " " + desc;
	}

	@Override
	public double applySubBonuses(Character self, Character opponent, BodyPart with, BodyPart target, double damage, Combat c) {
		if (with.isType(attachpoint) && Global.random(3) >-1) {
			c.write(self, Global.format("Additionally, {self:name-possessive} " + fullDescribe(self) + " takes the opportunity to squirm against {other:name-possessive} " + target.fullDescribe(opponent), self,opponent));
			opponent.body.pleasure(self, this, target, 5, c);
		}
		return 0;
	}

	@Override
	public boolean isReady(Character c) {
		return true;
	}

	@Override
	public String getFluids(Character c) {
		return fluids;
	}

	public Map<String,Object> saveToDict() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("desc",			desc);
		res.put("attachpoint",		attachpoint);
		res.put("hotness",		hotness);
		res.put("pleasure",		pleasure);
		res.put("sensitivity",	sensitivity);
		res.put("fluids",		fluids);

		return res;
	}

	public BodyPart loadFromDict(Map<String,Object> dict) {
		try {
		GenericBodyPart part = new TentaclePart(
									(String)dict.get("desc"),
									(String)dict.get("attachpoint"),
									(String)dict.get("fluids"),
									(Double)dict.get("hotness"),
									(Double)dict.get("pleasure"),
									(Double)dict.get("sensitivity"));
			return part;
		} catch (ClassCastException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
}
