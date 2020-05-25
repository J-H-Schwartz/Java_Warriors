package warriors.engine.board;

import warriors.engine.enemies.Enemy;
import warriors.engine.enemies.EnemyDragon;
import warriors.engine.enemies.EnemyGoblin;
import warriors.engine.enemies.EnemySorcerer;
import warriors.engine.heroes.HeroCharacter;

public class BoardCaseEnemy extends BoardCase {
	private Enemy enemy;

	public BoardCaseEnemy(int id, EnemyType type) {
		super(id);
		this.caseStatus = CaseType.ENEMY_CASE;
		if (type == EnemyType.GOBLIN) {
			this.contains = "Ennemi-Goblin-1";
			this.enemy = new EnemyGoblin();
		} else if (type == EnemyType.SORCERER) {
			this.contains = "Ennemi-Sorcerer-2";
			this.enemy = new EnemySorcerer();
		} else if (type == EnemyType.DRAGON) {
			this.contains = "Ennemi-Dragon-3";
			this.enemy = new EnemyDragon();
		}
	}
	
	public BoardCaseEnemy(int id, EnemyType type, int life, int attackPower) {
		super(id);
		this.caseStatus = CaseType.ENEMY_CASE;
		if (type == EnemyType.GOBLIN) {
			this.contains = "Ennemi-Goblin-1";
			this.enemy = new EnemyGoblin(life, attackPower);
		} else if (type == EnemyType.SORCERER) {
			this.contains = "Ennemi-Sorcerer-2";
			this.enemy = new EnemySorcerer(life, attackPower);
		} else if (type == EnemyType.DRAGON) {
			this.contains = "Ennemi-Dragon-4";
			this.enemy = new EnemyDragon(life, attackPower);
		}
	}

	@Override
	public String manageCaseEvent(HeroCharacter hero, String tmp) {
		tmp = hero.attack(tmp, enemy);
		if (enemy.getLife() <= 0) {
			tmp = tmp + String.format("\nVous avez tué le %s ennemi.", enemy.getName());
		} else {
			tmp = enemy.attack(tmp, hero);
			tmp = tmp + String.format("\nLe %s ennemi senfuit. Il vous reste %d points de vie.", enemy.getName(),
					hero.getLife());
		}
		return tmp;
	}

	/**
	 * @return the ennemi
	 */
	public Enemy getEnemy() {
		return enemy;
	}

	/**
	 * @param ennemi the ennemi to set
	 */
	public void setEnemy(Enemy ennemi) {
		this.enemy = ennemi;
	}
}
