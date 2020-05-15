package warriors.engine.board;

import java.util.ArrayList;

import warriors.contracts.Map;

public class JsonBoard extends Board implements Map{

	private String name;
	private int numberOfCase;
	private ArrayList<BoardCase> map;

	public ArrayList<BoardCase> getMapCases() {
		return map;
	}

	public JsonBoard(Board mapBoard) {
		this.map = mapBoard.getMapCases();
		this.name = mapBoard.getName();
		this.numberOfCase = mapBoard.getNumberOfCase();
		generateMap();
	}

	private void generateMap() {
		String[] caseContains = new String[2];
		for (int i = 0; i < numberOfCase; i++) {
			caseContains = map.get(i).getContains().split("-");
			if (caseContains[0].equals("Ennemi")) {
				if (caseContains[1].equals("Goblin")) {
					map.remove(i);
					map.add(i, new BoardCaseEnnemy(i, ENNEMI_GOBELIN_CASE));
				} else if (caseContains[1].equals("Sorcerer")) {
					map.remove(i);
					map.add(i, new BoardCaseEnnemy(i, ENNEMI_SORCERER_CASE));
				} else if (caseContains[1].equals("Dragon")) {
					map.remove(i);
					map.add(i, new BoardCaseEnnemy(i, ENNEMI_DRAGON_CASE));
				}
			} else if (caseContains[0].equals("Bonus")) {
				if (caseContains[2].equals("Arc")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_BOW_CASE));
				} else if (caseContains[2].equals("Massue")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_HAMMER_CASE));
				} else if (caseContains[2].equals("Epée")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_SWORD_CASE));
				} else if (caseContains[2].equals("Éclair")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_LIGHTNING_CASE));
				} else if (caseContains[2].equals("Boule de Feu")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_FIREBALL_CASE));
				} else if (caseContains[2].equals("Mineure")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_SMALLPOTION_CASE));
				} else if (caseContains[2].equals("Standard")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_MEDIUMPOTION_CASE));
				} else if (caseContains[2].equals("Large")) {
					map.remove(i);
					map.add(i, new BoardCaseUpgrade(i, BONUS_LARGEPOTION_CASE));
				}
			} else if (caseContains[0].equals("Empty")) {
				map.remove(i);
				map.add(i, new BoardCase(i));
			}
		}
		System.out.println(String.format("Plateau de %d cases généré !", map.size()));
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
