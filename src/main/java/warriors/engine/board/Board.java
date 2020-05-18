package warriors.engine.board;

import java.util.ArrayList;
import java.util.Collections;

import warriors.contracts.Map;

public class Board implements Map {

	private static final int ENNEMI_GOBELIN_NUMBER = 12;
	private static final int ENNEMI_SORCERER_NUMBER = 10;
	private static final int ENNEMI_DRAGON_NUMBER = 8;
	private static final int BONUS_BOW_NUMBER = 5;
	private static final int BONUS_HAMMER_NUMBER = 3;
	private static final int BONUS_SWORD_NUMBER = 2;
	private static final int BONUS_LIGHTNING_NUMBER = 5;
	private static final int BONUS_FIREBALL_NUMBER = 2;
	private static final int BONUS_SMALLPOTION_NUMBER = 5;
	private static final int BONUS_MEDIUMPOTION_NUMBER = 3;
	private static final int BONUS_LARGEPOTION_NUMBER = 1;

	private String name;
	private int numberOfCase;
	private ArrayList<BoardCase> map;

	public ArrayList<BoardCase> getMapCases() {
		return map;
	}

	public Board(String name) {
		this.map = new ArrayList<BoardCase>();
		this.name = name;
		generateRandomizedMap();
	}

	private void generateRandomizedMap() {
		int index = 0;
		for (int i = 0; i < ENNEMI_DRAGON_NUMBER; i++) {
			map.add(new BoardCaseEnemy(index, EnemyType.DRAGON));
			index++;
		}
		for (int i = 0; i < ENNEMI_GOBELIN_NUMBER; i++) {
			map.add(new BoardCaseEnemy(index, EnemyType.GOBLIN));
			index++;
		}
		for (int i = 0; i < ENNEMI_SORCERER_NUMBER; i++) {
			map.add(new BoardCaseEnemy(index, EnemyType.SORCERER));
			index++;
		}
		for (int i = 0; i < BONUS_BOW_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.BOW));
			index++;
		}
		for (int i = 0; i < BONUS_HAMMER_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.HAMMER));
			index++;
		}
		for (int i = 0; i < BONUS_SWORD_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.SWORD));
			index++;
		}
		for (int i = 0; i < BONUS_LIGHTNING_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.LIGHTNING));
			index++;
		}
		for (int i = 0; i < BONUS_FIREBALL_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.FIREBALL));
			index++;
		}
		for (int i = 0; i < BONUS_SMALLPOTION_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.POTION));
			index++;
		}
		for (int i = 0; i < BONUS_MEDIUMPOTION_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.POTIONM));
			index++;
		}
		for (int i = 0; i < BONUS_LARGEPOTION_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, UpgradeType.POTIONL));
			index++;
		}
		for (int i = 0; i < 8; i++) {
			map.add(new BoardCaseEmpty(index));
			index++;
		}
		Collections.shuffle(map);
		for (int i = 0; i < map.size(); i++) {
			map.get(i).setCaseID(i);
		}
		numberOfCase = map.size();
		System.out.println(String.format("Plateau de %d cases généré !", index));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getNumberOfCase() {
		return numberOfCase;
	}
}
