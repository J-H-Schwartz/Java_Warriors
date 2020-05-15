package warriors.engine.heroes;

import warriors.engine.equipements.Equipements;
import warriors.engine.equipements.LeftHandEquipement;
import warriors.engine.equipements.Potion;
import warriors.engine.equipements.RightHandEquipement;
import warriors.engine.equipements.Spell;
import warriors.engine.equipements.Weapon;

/**
 * Warrior object Data class.
 * 
 */
public class Warrior extends HeroCharacter implements WarriorClassInterface {

	/** Warrior max life constant */
	public static final int WARRIOR_MAX_LIFE = 10;

	/** Warrior min life constant */
	public static final int WARRIOR_MIN_LIFE = 5;

	/** Warrior max Attack power constant */
	public static final int WARRIOR_MAX_ATTACK_POWER = 10;

	/** Warrior max Attack power constant */
	public static final int WARRIOR_MIN_ATTACK_POWER = 5;

	public Warrior(String nameArg, int lifeArg, int attackPowerArg) {
		this.className = "Warrior";
		this.name = nameArg;
		this.life = lifeArg;
		this.attackLevel = attackPowerArg;
		this.rightHand = new Weapon();
		this.leftHand = new Potion();
	}

	@Override
	public String manageLoot(Equipements loot, String tmp) {
		if (loot instanceof Weapon) {
			if (loot.getEffect() > this.getRightHand().getEffect()) {
				this.setRightHand((RightHandEquipement) loot);
				tmp = tmp + String.format(
						"\nVous avez trouvé une nouvelle arme ! %s, bonus d'attaque: %d\nVotre puissance d'attaque s'élève à %d",
						loot.getName(), loot.getEffect(), this.getAttackMove());
			} else {
				tmp = tmp + String.format(
						"\nVous avez trouvé une nouvelle arme, mais la votre est meilleure. %s, bonus d'attaque: %d",
						loot.getName(), loot.getEffect());
			}
		} else if (loot instanceof Spell) {
			tmp = tmp + String.format(
					"\nVous avez trouvé un sort mais ne pouvez vous en équiper. %s, bonus d'attaque: %d",
					loot.getName(), loot.getEffect());
		} else if (loot instanceof Potion){
			this.setLife(this.getLife() + loot.getEffect());
			tmp = tmp + String.format("\nVous avez trouvé une potion de soin. %s, soin: %d\nVotre vie passe à %d.",
					loot.getName(), loot.getEffect(), this.getLife());
		}
		return tmp;
	}

	@Override
	public String toString() {
		return "Personnage " + this.name + " " + this.raceName + " " + this.className + "\nLife : " + this.life
				+ " Attack Power : " + this.attackLevel + " Weapon : " + this.rightHand.getName() + " Potion : "
				+ this.leftHand.getName();
	}

	/**
	 * @return the weapon
	 */
	public RightHandEquipement getWeapon() {
		return rightHand;
	}

	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(Weapon weapon) {
		this.rightHand = weapon;
	}

	/**
	 * @return the shield
	 */
	public LeftHandEquipement getShield() {
		return leftHand;
	}

	/**
	 * @param shield the shield to set
	 */
	public void setPotion(Potion potion) {
		this.leftHand = potion;
	}

	@Override
	public void setLife(int newLife) {
		if (newLife > WARRIOR_MAX_LIFE) {
			this.life = WARRIOR_MAX_LIFE;
		} else if (newLife < 0) {
			this.life = 0;
		} else {
			this.life = newLife;
		}
	}

	@Override
	public void setAttackLevel(int attackPower) {
		if (attackPower > WARRIOR_MAX_ATTACK_POWER) {
			this.attackLevel = WARRIOR_MAX_ATTACK_POWER;
		} else if (attackPower < WARRIOR_MIN_ATTACK_POWER) {
			this.attackLevel = WARRIOR_MIN_ATTACK_POWER;
		} else {
			this.attackLevel = attackPower;
		}
	}
}