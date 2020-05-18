package warriors.engine.equipements;

/**
 * Weapon object Data class.
 * 
 */
public class Weapon extends Equipements {
	private static final String DEFAULT_WEAPON_NAME = "Stick";
	private static final int DEFAULT_WEAPON_DMG = 0;

	public Weapon() {
		this(DEFAULT_WEAPON_NAME, DEFAULT_WEAPON_DMG);
	}

	public Weapon(String nameArg, int effectArg) {
		super(nameArg, effectArg);
	}
}