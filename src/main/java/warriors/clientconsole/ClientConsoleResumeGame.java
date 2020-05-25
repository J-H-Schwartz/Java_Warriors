package warriors.clientconsole;

import java.util.ArrayList;
import java.util.Scanner;

import warriors.contracts.GameState;
import warriors.contracts.GameStatus;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;
import warriors.engine.database.GameStateDAOManager;
import warriors.engine.heroes.HeroCharacter;

public class ClientConsoleResumeGame {

	public void resumeGame(WarriorsAPI warriors, Scanner sc) {

		ArrayList<GameState> gameList = new GameStateDAOManager().getGames();

		if (gameList.isEmpty()) {
			System.out.println("Aucune sauvegarde. Retour au menu précédent.");
			return;
		}

		((Warriors) warriors).setDBGames(gameList);

		GameState gameState = null;

		for (int i = 0; i < gameList.size(); i++) {
			if (gameList.get(i).getGameStatus() == GameStatus.IN_PROGRESS) {
				System.out.println(String.format(
						"GameID: %s, Player name: %s, Hero ID: %d, Hero name: %s, Map name: %s, Map current case: %d",
						gameList.get(i).getGameId(), gameList.get(i).getPlayerName(),
						((HeroCharacter) gameList.get(i).getHero()).getId(),
						((HeroCharacter) gameList.get(i).getHero()).getName(), gameList.get(i).getMap().getName(),
						gameList.get(i).getCurrentCase()));
			}
		}

		System.out.println("Entrez l'id de la partie que vous voulez charger: ");
		String choice = sc.nextLine();

		for (int i = 0; i < gameList.size(); i++) {
			if (choice.equals(gameList.get(i).getGameId())) {
				gameState = gameList.get(i);
				System.out.println("Partie chargée !");
				break;
			} else if (i == (gameList.size() - 1)) {
				System.out.println("Cette partie n'existe pas.");
				return;
			}
		}

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
}
