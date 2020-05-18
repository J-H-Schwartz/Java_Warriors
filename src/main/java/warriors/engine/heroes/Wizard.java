package warriors.engine.heroes;

import warriors.engine.equipements.Equipements;
import warriors.engine.equipements.Potion;
import warriors.engine.equipements.Spell;


/**
 * Wizard object Data class.
 * 
 */
public class Wizard extends HeroCharacter implements WizardClassInterface {

	/** Wizard max life constant */
	public static final int WIZARD_MAX_LIFE = 6;

	/** Wizard min life constant */
	public static final int WIZARD_MIN_LIFE = 3;

	/** Wizard max Attack power constant */
	public static final int WIZARD_MAX_ATTACK_POWER = 15;

	/** Wizard min Attack power constant */
	public static final int WIZARD_MIN_ATTACK_POWER = 8;

	public Wizard(String nameArg, int lifeArg, int attackPowerArg) {
		this.className = "Wizard";
		this.name = nameArg;
		this.life = lifeArg;
		this.attackLevel = attackPowerArg;
		this.rightHand = new Spell();
		this.leftHand = new Potion();
	}

	@Override
	public String manageLoot(Equipements loot, String type, String tmp) {
		if (type.equals("Sort")) {
			if (loot.getEffect() > this.getRightHand().getEffect()) {
				this.setRightHand(loot);
				tmp = tmp + String.format(
						"\nVous avez trouvé un nouveau sort ! %s, bonus d'attaque: %d\nVotre puissance d'attaque s'élève à %d",
						loot.getName(), loot.getEffect(), this.getAttackMove());
			} else {
				tmp = tmp + String.format(
						"\nVous avez trouvé un nouveau sort, mais le votre est meilleur. %s, bonus d'attaque: %d",
						loot.getName(), loot.getEffect());
			}
		} else if (type.equals("Arme")) {
			tmp = tmp + String.format(
					"\nVous avez trouvé une arme mais ne pouvez vous en équiper. %s, bonus d'attaque: %d",
					loot.getName(), loot.getEffect());
		} else if (type.equals("Potion")) {
			this.setLife(this.getLife() + loot.getEffect());
			tmp = tmp + String.format("\nVous avez trouvé une potion de soin. %s, soin: %d\nVotre vie passe à %d.",
					loot.getName(), loot.getEffect(), this.getLife());
		}
		return tmp;
	}

	@Override
	public String toString() {
		return "Personnage " + this.name + " " + this.raceName + " " + this.className + "\nLife : " + this.life
				+ " Attack Power : " + this.attackLevel + " Weapon : " + this.rightHand.getName() + " Shield : "
				+ this.leftHand.getName();
	}

	@Override
	public void setLife(int newLife) {
		if (newLife > WIZARD_MAX_LIFE) {
			this.life = WIZARD_MAX_LIFE;
		} else if (newLife < 0) {
			this.life = 0;
		} else {
			this.life = newLife;
		}
	}

	@Override
	public void setAttackLevel(int attackPower) {
		if (attackPower > WIZARD_MAX_ATTACK_POWER) {
			this.attackLevel = WIZARD_MAX_ATTACK_POWER;
		} else if (attackPower < WIZARD_MIN_ATTACK_POWER) {
			this.attackLevel = WIZARD_MIN_ATTACK_POWER;
		} else {
			this.attackLevel = attackPower;
		}
	}

}
