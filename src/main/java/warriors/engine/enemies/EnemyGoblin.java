package warriors.engine.enemies;

public class EnemyGoblin extends Enemy {
	private static final String GOBLIN_NAME = "Goblin";
	private static final int GOBLIN_LIFE = 6;
	private static final int GOBLIN_AP = 1;

	public EnemyGoblin() {
		super(GOBLIN_NAME, GOBLIN_LIFE, GOBLIN_AP);
	}
	
	public EnemyGoblin(int life, int attackPower) {
		super(GOBLIN_NAME, life, attackPower);
	}
}
