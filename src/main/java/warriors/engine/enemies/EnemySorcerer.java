package warriors.engine.enemies;

public class EnemySorcerer extends Enemy {
	private static final String SORCERER_NAME = "Sorcerer";
	private static final int SORCERER_LIFE = 9;
	private static final int SORCERER_AP = 2;

	public EnemySorcerer() {
		super(SORCERER_NAME, SORCERER_LIFE, SORCERER_AP);
	}
	
	public EnemySorcerer(int life, int attackPower) {
		super(SORCERER_NAME, life, attackPower);
	}
}
