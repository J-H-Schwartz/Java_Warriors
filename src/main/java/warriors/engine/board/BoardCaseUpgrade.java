package warriors.engine.board;

import warriors.engine.equipements.Bow;
import warriors.engine.equipements.Equipements;
import warriors.engine.equipements.Fireball;
import warriors.engine.equipements.Hammer;
import warriors.engine.equipements.Lightning;
import warriors.engine.equipements.Potion;
import warriors.engine.equipements.PotionLarge;
import warriors.engine.equipements.PotionMedium;
import warriors.engine.equipements.Sword;
import warriors.engine.heroes.HeroCharacter;

public class BoardCaseUpgrade extends BoardCase {
	private Equipements loot;

	public BoardCaseUpgrade(int id, int type) {
		super(id);
		this.caseStatus = 2;
		if (type == 0) {
			this.contains = "Bonus-Arme-Arc";
			this.loot = new Bow();
		} else if (type == 1) {
			this.contains = "Bonus-Arme-Massue";
			this.loot = new Hammer();
		} else if (type == 2) {
			this.contains = "Bonus-Arme-Epée";
			this.loot = new Sword();
		} else if (type == 3) {
			this.contains = "Bonus-Sort-Éclair";
			this.loot = new Lightning();
		} else if (type == 4) {
			this.contains = "Bonus-Sort-Boule de Feu";
			this.loot = new Fireball();
		} else if (type == 5) {
			this.contains = "Bonus-Potion-Mineure";
			this.loot = new Potion();
		} else if (type == 6) {
			this.contains = "Bonus-Potion-Standard";
			this.loot = new PotionMedium();
		} else if (type == 7) {
			this.contains = "Bonus-Potion-Large";
			this.loot = new PotionLarge();
		}
	}

	public Equipements getLoot() {
		return loot;
	}

	@Override
	public String manageCaseEvent(HeroCharacter hero, String tmp) {
		tmp = hero.manageLoot(this.getLoot(), tmp);
		return tmp;
	}
}
