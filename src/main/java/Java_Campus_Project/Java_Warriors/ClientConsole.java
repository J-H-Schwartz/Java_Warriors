package Java_Campus_Project.Java_Warriors;

import java.util.Scanner;

import warriors.contracts.GameState;
import warriors.contracts.GameStatus;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;

public class ClientConsole {

	private static String MENU_COMMENCER_PARTIE = "1";
	private static String MENU_QUITTER = "2";

	public static void main(String[] args) {
		WarriorsAPI warriors;
		if (args.length > 0 && args[0].equals("--debug")) {
			warriors = new Warriors(args[1]);
		} else {
			warriors = new Warriors("");
		}

		Scanner sc = new Scanner(System.in);
		String menuChoice = "";
		do {
			menuChoice = displayMenu(sc);
			if (menuChoice.equals(MENU_COMMENCER_PARTIE)) {
				startGame(warriors, sc);
			}
		} while (!menuChoice.equals(MENU_QUITTER));
		sc.close();
		System.out.println("� bient�t");
	}

	private static void startGame(WarriorsAPI warriors, Scanner sc) {
		System.out.println();
		System.out.println("Entrez votre nom:");
		String playerName = sc.nextLine();

		System.out.println("Choisissez votre h�ro:");
		for (int i = 0; i < warriors.getHeroes().size(); i++) {
			Hero heroe = warriors.getHeroes().get(i);
			System.out.println(i + 1 + " - " + heroe.getName());
			System.out.println("    Force d'attaque : " + heroe.getAttackLevel());
			System.out.println("    Niveau de vie : " + heroe.getLife());
		}
		Hero chosenHeroe = warriors.getHeroes().get(Integer.parseInt(sc.nextLine()) - 1);

		System.out.println("Choisissez votre map:");
		for (int i = 0; i < warriors.getMaps().size(); i++) {
			Map map = warriors.getMaps().get(i);
			System.out.println(i + 1 + " - " + map.getName());
		}
		Map choosenMap = warriors.getMaps().get(Integer.parseInt(sc.nextLine()) - 1);

		GameState gameState = warriors.createGame(playerName, chosenHeroe, choosenMap);
		String gameId = gameState.getGameId();
		while (gameState.getGameStatus() == GameStatus.IN_PROGRESS) {
			System.out.println(gameState.getLastLog());
			System.out.println("\nAppuyer sur une touche pour lancer le d�");
			if (sc.hasNext()) {
				sc.nextLine();
				gameState = warriors.nextTurn(gameId);
			}
		}

		System.out.println(gameState.getLastLog());
	}

	private static String displayMenu(Scanner sc) {
		System.out.println();
		System.out.println("================== Java Warriors ==================");
		System.out.println("1 - Commencer une partie");
		System.out.println("2 - Quitter");
		if (sc.hasNext()) {
			String choice = sc.nextLine();
			return choice;
		}
		return "";
	}
}
