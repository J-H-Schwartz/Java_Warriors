package warriors.engine.equipements;

/**
 * Spell object Data class.
 * 
 */
public class Spell extends RightHandEquipement {

	public Spell() {
		this("Spark", 1);
	}

	public Spell(String nameArg, int effectArg) {
		super(nameArg, effectArg);
	}

}
