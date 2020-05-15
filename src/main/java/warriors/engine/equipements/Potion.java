package warriors.engine.equipements;

/**
 * Potion object Data class.
 * 
 */
public class Potion extends LeftHandEquipement {

	public Potion() {
		this("Potion mineure", 1);
	}

	public Potion(String nameArg, int effectArg) {
		super(nameArg, effectArg);
	}

}
