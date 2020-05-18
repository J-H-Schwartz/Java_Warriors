package warriors.engine.equipements;

public class Sword extends Weapon{
	private static final String SWORD_WEAPON_NAME = "Epée";
	private static final int SWORD_WEAPON_DMG = 5;
	
	public Sword() {
		super(SWORD_WEAPON_NAME, SWORD_WEAPON_DMG);
	}
}
