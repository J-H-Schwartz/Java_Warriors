package warriors.engine.equipements;

/**
 * Potion object Data class.
 * 
 */
public class Potion extends Equipements {
	private static final String POTION_NAME = "Potion mineure";
	private static final int POTION_EFFECT = 1;

	public Potion() {
		super(POTION_NAME, POTION_EFFECT);
	}

}
