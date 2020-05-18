package warriors.engine.equipements;

public class Fireball extends Spell {
	private static final String FIREBALL_SPELL_NAME = "Boule de Feu";
	private static final int FIREBALL_SPELL_DMG = 7;
	
	public Fireball() {
		super(FIREBALL_SPELL_NAME, FIREBALL_SPELL_DMG);
	}
}
