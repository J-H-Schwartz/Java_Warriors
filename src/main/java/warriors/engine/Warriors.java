package warriors.engine;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Map serialization imports.
//import java.io.FileWriter;
//import java.io.Writer;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import warriors.engine.board.InterfaceAdapter;

import warriors.contracts.GameState;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.contracts.WarriorsAPI;
import warriors.engine.board.Board;
import warriors.engine.board.BoardCase;
import warriors.engine.board.JsonBoardCreator;
import warriors.engine.database.DbConnect;
import warriors.engine.database.HeroDao;
import warriors.engine.heroes.Warrior;
import warriors.engine.heroes.Wizard;
import warriors.engine.heroes.HeroCharacter;

public class Warriors implements WarriorsAPI {

	private static final int DICE_FACES = 6;
	private static final String MAP_FOLDER_PATH = "src/main/ressources/maps";

	private ArrayList<Hero> warriors;
	private ArrayList<Map> maps;
	private ArrayList<Game> games;
	
	private Connection conn = DbConnect.dbConnect();

	private int debugDicesIndex;
	private DebugStatus debugStatus;
	private int[] debugFileDices;

	public Warriors(String debugUrl, Scanner sc) {
		warriors = new ArrayList<Hero>();
		maps = new ArrayList<Map>();
		games = new ArrayList<Game>();

		// Create 2 default Characters.
		Hero warrior = new Warrior("WAR", 5, 5);
		Hero wizard = new Wizard("WIZ", 3, 8);
		warriors.add(warrior);
		warriors.add(wizard);

		// Create default random map.
		Board map = new Board("Default_Random_Map");
		maps.add(map);

		// Imports all json board files from path.
		JsonBoardCreator jsb = new JsonBoardCreator();
		try {
			Path dirPath = Paths.get(MAP_FOLDER_PATH);
			try (DirectoryStream<Path> dirPaths = Files.newDirectoryStream(dirPath, "*.{json}")) { // .jdb only
				for (Path file : dirPaths) {
					Board newMap = (Board) jsb.createBoard(file);
					maps.add(newMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Serialize Map to Json format from default random map.
//		try (Writer writer = new FileWriter("src/main/ressources/maps/Map_5.json")) {
//			Gson gson = new GsonBuilder().registerTypeAdapter(BoardCase.class, new InterfaceAdapter<BoardCase>()).create();
//			gson.toJson(map, writer);
//		} catch (Exception e) {
//			System.out.println(e);
//		}

		debugDicesIndex = 0;
		if (isDebugUrlSet(debugUrl)) {
			BufferedReader br;
			String line = "";
			String splitBy = ",";
			String[] tmp;

			// Check for debug argument, get debug dices if needed and switch debug status
			// accordingly
			try {
				br = new BufferedReader(new FileReader(debugUrl));
				line = br.readLine();

				tmp = line.split(splitBy);
				debugFileDices = new int[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					this.debugFileDices[i] = Integer.parseInt(tmp[i]);
				}
				debugStatus = DebugStatus.DEBUG_ON;
				br.close();
			} catch (IOException e) {
				System.out.println("Erreur de chargement du fichier, lancement d'une partie normale.");
				debugStatus = DebugStatus.DEBUG_OFF;
			}
		} else {
			debugStatus = DebugStatus.DEBUG_OFF;
		}
	}

	@Override
	public GameState createGame(String playerName, Hero hero, Map map) {
		if (!(hero instanceof HeroCharacter)) {
			throw new IllegalArgumentException("Le type de héros n'est pas supporté");
		}
		Game newGame = new Game(playerName, hero, map, String.format("Game %d", games.size()));
		games.add(newGame);
		return newGame;
	}

	@Override
	public GameState nextTurn(String gameID) {
		int gameIndex = gameSearch(gameID);
		Game game = games.get(gameIndex);
		String tmp = "";
		int dice = 0;

		// Check for debug status.
		if (this.debugStatus == DebugStatus.DEBUG_ON) {
			dice = this.debugFileDices[debugDicesIndex];
			tmp = "Dé de " + dice + " prédéfini (mode Debug).\n";
		} else {
			dice = GetRandomInt.getRandomInt(DICE_FACES) + 1;
			tmp = "Vous lancez le dé et faites un " + dice + "\n";
		}

		// Check for Board End (Win).
		if (isBoardEndReached(game, dice)) {
			tmp = game.manageGameWin(tmp);
			debugDicesIndex = 0;
		} else {

			// Manage next Turn.
			tmp = getNextCase(dice, game, tmp);
			tmp = manageCaseEvent(game, tmp);
			debugDicesIndex += 1;
		}

		// Check for Hero life value.
		if (isHeroDead(game)) {
			tmp = game.manageGameLoss(tmp);
			debugDicesIndex = 0;
		}
		boolean saveResult = new HeroDao(conn).update((HeroCharacter)game.getCharacter());
		if (!saveResult) {
			tmp = tmp + "\nErreur lors de la sauvegarde !\n";
		}
		game.setLastLog(tmp);
		return game;
	}

	@Override
	public List<? extends Hero> getHeroes() {
		warriors = new HeroDao(conn).findAll();
		return warriors;
	}

	@Override
	public List<? extends Map> getMaps() {
		return maps;
	}

	// Access to specific game through gameID.
	public int gameSearch(String gameId) {
		int result = -1;
		for (int index = 0; index < games.size(); index++) {
			if (games.get(index).getGameId().equals(gameId)) {
				result = index;
			}
		}
		return result;
	}

	// Check if board end is reached.
	private boolean isBoardEndReached(Game game, int dice) {
		return game.getCurrentCase() + dice >= game.getBoard().getMapCases().size();
	}

	// Check if hero is dead.
	private boolean isHeroDead(Game game) {
		return game.getCharacter().getLife() <= 0;
	}

	// Check if Debug URL has been set.
	private boolean isDebugUrlSet(String debugUrl) {
		return !debugUrl.equals(null) && !debugUrl.isEmpty();
	}

	// Moves the character onto the next case.
	private String getNextCase(int dice, Game game, String tmp) {
		game.setCurrentCase(game.getCurrentCase() + dice);
		tmp = tmp + "Vous attérissez sur la case " + game.getCurrentCase() + "\n" + String.format(
				"Cette case contient %s", game.getBoard().getMapCases().get(game.getCurrentCase()).getContains());
		return tmp;
	}

	// Manage board case event in case of enemy or upgrade.
	private String manageCaseEvent(Game game, String tmp) {
		BoardCase actualCase = game.getBoard().getMapCases().get(game.getCurrentCase());
		tmp = actualCase.manageCaseEvent(game.getCharacter(), tmp);
		return tmp;
	}
}
