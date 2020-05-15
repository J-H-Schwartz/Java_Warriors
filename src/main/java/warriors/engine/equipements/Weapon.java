package warriors.engine.equipements;

/**
 * Weapon object Data class.
 * 
 */
public class Weapon extends RightHandEquipement {

	public Weapon() {
		this("Stick", 1);
	}

	public Weapon(String nameArg, int effectArg) {
		super(nameArg, effectArg);
	}
}