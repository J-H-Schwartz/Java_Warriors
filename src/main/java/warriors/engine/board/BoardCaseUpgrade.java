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

	public BoardCaseUpgrade(int id, UpgradeType type) {
		super(id);
		this.caseStatus = CaseType.UPGRADE_CASE;
		if (type == UpgradeType.BOW) {
			this.contains = "Bonus-Arme-Arc";
			this.loot = new Bow();
		} else if (type == UpgradeType.HAMMER) {
			this.contains = "Bonus-Arme-Massue";
			this.loot = new Hammer();
		} else if (type == UpgradeType.SWORD) {
			this.contains = "Bonus-Arme-Epée";
			this.loot = new Sword();
		} else if (type == UpgradeType.LIGHTNING) {
			this.contains = "Bonus-Sort-Éclair";
			this.loot = new Lightning();
		} else if (type == UpgradeType.FIREBALL) {
			this.contains = "Bonus-Sort-Boule de Feu";
			this.loot = new Fireball();
		} else if (type == UpgradeType.POTION) {
			this.contains = "Bonus-Potion-Mineure";
			this.loot = new Potion();
		} else if (type == UpgradeType.POTIONM) {
			this.contains = "Bonus-Potion-Standard";
			this.loot = new PotionMedium();
		} else if (type == UpgradeType.POTIONL) {
			this.contains = "Bonus-Potion-Large";
			this.loot = new PotionLarge();
		}
	}

	public Equipements getLoot() {
		return loot;
	}

	@Override
	public String manageCaseEvent(HeroCharacter hero, String tmp) {
		String[] containment = this.contains.split("-");
		tmp = hero.manageLoot(this.getLoot(), containment[1], tmp);
		return tmp;
	}
}
