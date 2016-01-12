package nightgames.characters.body;

public interface BodyPartMod {
	BodyPartMod noMod = () -> "none";
	String getModType();
	public default boolean partHasThisMod(BodyPart other) {
		if (other != null) {
			return this.equals(other.getMod());
		}
		return false;
	}
}
