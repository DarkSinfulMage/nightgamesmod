package nightgames.characters.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.json.simple.JSONValue;

import nightgames.characters.BasePersonality.PreferredAttribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Growth;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;
import nightgames.items.Clothing;
import nightgames.items.Item;
import nightgames.items.ItemAmount;

public class DataBackedNPCData implements NPCData {
	List<PreferredAttribute> preferredAttributes;
	List<ItemAmount> purchasedItems;
	List<ItemAmount> startingItems;
	List<CustomStringEntry> portraits;
	List<BodyPart> parts;
	Map<Emotion, Integer> moodThresholds;
	Map<String, List<CustomStringEntry>> characterLines;
	Stack<Clothing> top;
	Stack<Clothing> bottom;
	Stats stats;
	Growth growth;
	Item trophy;
	String name;
	String gender;
	String defaultPortraitName;

	public DataBackedNPCData() {
		preferredAttributes = new ArrayList<>();
		purchasedItems = new ArrayList<>();
		startingItems = new ArrayList<>();
		portraits = new ArrayList<>();
		parts = new ArrayList<>();
		moodThresholds = new HashMap<>();
		characterLines = new HashMap<>();
		top = new Stack<>();
		bottom = new Stack<>();
		stats = new Stats();
		growth = new Growth();
		trophy = Item.MiscTrophy;
		name = "none";
		gender = "female";
		defaultPortraitName = "";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Stats getStats() {
		return stats;
	}

	@Override
	public Stack<Clothing> getTopOutfit() {
		return top;
	}

	@Override
	public Stack<Clothing> getBottomOutfit() {
		return bottom;
	}

	@Override
	public Growth getGrowth() {
		return growth;
	}

	@Override
	public List<PreferredAttribute> getPreferredAttributes() {
		return preferredAttributes;
	}

	@Override
	public List<ItemAmount> getStartingItems() {
		return startingItems;
	}

	@Override
	public List<ItemAmount> getPurchasedItems() {
		return purchasedItems;
	}

	@Override
	public String getLine(String type, Combat c, Character self, Character other) {
		List<CustomStringEntry> lines = characterLines.get(type);
		for (CustomStringEntry line : lines) {
			if (line.meetsRequirements(c, self, other))
				return line.getLine(c, self, other);
		}
		return "";
	}

	@Override
	public Item getTrophy() {
		return trophy;
	}

	@Override
	public boolean checkMood(Character self, Emotion mood, int value) {
		return value >= moodThresholds.get(mood) ;
	}

	@Override
	public List<BodyPart> getBodyParts() {
		return parts;
	}

	@Override
	public String getGender() {
		return gender;
	}

	@Override
	public String getPortraitName(Combat c, Character self, Character other) {
		for (CustomStringEntry line : portraits) {
			if (line.meetsRequirements(c, self, other))
				return line.getLine(c, self, other);
		}
		return "";
	}

	@Override
	public String getDefaultPortraitName() {
		return defaultPortraitName;
	}
}
