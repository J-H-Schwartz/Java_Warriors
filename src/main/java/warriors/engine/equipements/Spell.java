package warriors.engine.equipements;

/**
 * Spell object Data class.
 * 
 */
public class Spell extends Equipements {
	private static final String DEFAULT_SPELL_NAME = "Spark";
	private static final int DEFAULT_SPELL_DMG = 0;

	public Spell() {
		this(DEFAULT_SPELL_NAME, DEFAULT_SPELL_DMG);
	}

	public Spell(String nameArg, int effectArg) {
		super(nameArg, effectArg);
	}

}
