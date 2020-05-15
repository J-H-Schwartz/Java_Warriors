package warriors.engine.ennemies;

import warriors.engine.heroes.HeroCharacter;

public abstract class Ennemi {

	String name;
	int life;
	int attackPower;

	public Ennemi(String name, int life, int attackPower) {
		this.name = name;
		this.life = life;
		this.attackPower = attackPower;
	}

	public String attack(String tmp, HeroCharacter hero) {
		hero.setLife(hero.getLife() - this.getAttackPower());
		tmp = tmp + String.format("\nLe %s ennemi vous inflige %d dégats.", this.getName(), this.getAttackPower());
		return tmp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		if (life < 0) {
			life = 0;
		}
		this.life = life;
	}

	/**
	 * @return the attackPower
	 */
	public int getAttackPower() {
		return attackPower;
	}

	/**
	 * @param attackPower the attackPower to set
	 */
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
}
