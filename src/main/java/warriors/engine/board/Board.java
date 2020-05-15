package warriors.engine.board;

import java.util.ArrayList;
import java.util.Collections;

import warriors.contracts.Map;

public class Board implements Map {

	protected static final int ENNEMI_GOBELIN_CASE = 0;
	protected static final int ENNEMI_SORCERER_CASE = 1;
	protected static final int ENNEMI_DRAGON_CASE = 2;
	protected static final int BONUS_BOW_CASE = 0;
	protected static final int BONUS_HAMMER_CASE = 1;
	protected static final int BONUS_SWORD_CASE = 2;
	protected static final int BONUS_LIGHTNING_CASE = 3;
	protected static final int BONUS_FIREBALL_CASE = 4;
	protected static final int BONUS_SMALLPOTION_CASE = 5;
	protected static final int BONUS_MEDIUMPOTION_CASE = 6;
	protected static final int BONUS_LARGEPOTION_CASE = 7;

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

	protected Board() {
		
	}
	
	public Board(String name) {
		this.map = new ArrayList<BoardCase>();
		this.name = name;
		generateMap();
	}

	private void generateMap() {
		numberOfCase = 64;
		int index = 0;
		for (int i = 0; i < ENNEMI_DRAGON_NUMBER; i++) {
			map.add(new BoardCaseEnnemy(index, ENNEMI_DRAGON_CASE));
			index++;
		}
		for (int i = 0; i < ENNEMI_GOBELIN_NUMBER; i++) {
			map.add(new BoardCaseEnnemy(index, ENNEMI_GOBELIN_CASE));
			index++;
		}
		for (int i = 0; i < ENNEMI_SORCERER_NUMBER; i++) {
			map.add(new BoardCaseEnnemy(index, ENNEMI_SORCERER_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_BOW_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_BOW_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_HAMMER_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_HAMMER_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_SWORD_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_SWORD_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_LIGHTNING_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_LIGHTNING_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_FIREBALL_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_FIREBALL_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_SMALLPOTION_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_SMALLPOTION_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_MEDIUMPOTION_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_MEDIUMPOTION_CASE));
			index++;
		}
		for (int i = 0; i < BONUS_LARGEPOTION_NUMBER; i++) {
			map.add(new BoardCaseUpgrade(index, BONUS_LARGEPOTION_CASE));
			index++;
		}
		for (int i = 0; i < 8; i++) {
			map.add(new BoardCase(index));
			index++;
		}
		Collections.shuffle(map);
		for (int i = 0; i < map.size(); i++) {
			map.get(i).setCaseID(i);
		}
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
