package warriors.engine.heroes;

import warriors.engine.equipements.Equipements;

public interface WarriorClassInterface {
	
	String manageLoot(Equipements loot, String type, String tmp);

	String toString();

}