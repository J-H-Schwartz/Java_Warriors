package warriors.engine.enemies;

public class EnemyDragon extends Enemy {
	private static final String DRAGON_NAME = "Dragon";
	private static final int DRAGON_LIFE = 15;
	private static final int DRAGON_AP = 4;

	public EnemyDragon() {
		super(DRAGON_NAME, DRAGON_LIFE, DRAGON_AP);
	}
	
	public EnemyDragon(int life, int attackPower) {
		super(DRAGON_NAME, life, attackPower);
	}
}
