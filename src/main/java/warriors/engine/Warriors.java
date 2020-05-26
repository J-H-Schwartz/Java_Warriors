package warriors.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//Map serialization imports.
//import java.io.FileWriter;
//import java.io.Writer;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import warriors.engine.board.InterfaceAdapter;

import warriors.contracts.GameState;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.contracts.WarriorsAPI;
import warriors.engine.board.BoardCase;
//import warriors.engine.database.DbConnect;
import warriors.engine.database.DefaultHeroDAOManager;
import warriors.engine.database.GameStateDAOManager;
import warriors.engine.database.HeroDAOManager;
import warriors.engine.database.MapDAOManager;
import warriors.engine.database.MapLocalDAOManager;
import warriors.engine.heroes.HeroCharacter;

public class Warriors implements WarriorsAPI {

	private static final int DICE_FACES = 6;

	private ArrayList<Hero> warriors;
	private ArrayList<Map> maps;
	private ArrayList<Game> games;

	private int debugDicesIndex;
	private DebugStatus debugStatus;
	private int[] debugFileDices;

	public Warriors(String debugUrl) {
		warriors = new ArrayList<Hero>();
		games = new ArrayList<Game>();
		maps = getMapList();
//		Board map = new Board("Map_3");
//		// Serialize Map to Json format from default random map.
//		try {
//			Gson gson = new GsonBuilder().registerTypeAdapter(BoardCase.class, new InterfaceAdapter<BoardCase>()).create();
//			String jsonString = gson.toJson(map);
//			try {
//				int id = -1;
//				PreparedStatement state = DbConnect.dbConnect().prepareStatement(String.format(
//						"INSERT INTO Maps(Name, NumberOfCases, Data) VALUES ('%s', '%d', '%s')",
//						map.getName(), map.getNumberOfCase(), jsonString),
//						Statement.RETURN_GENERATED_KEYS);
//				state.executeUpdate();
//				try (ResultSet generatedKeys = state.getGeneratedKeys()) {
//					if (generatedKeys.next()) {
//						id = generatedKeys.getInt(1);
//					} else {
//						throw new SQLException("Creating Map failed, no ID obtained.");
//					}
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
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

	public static ArrayList<Map> getMapList() {
		ArrayList<Map> maps = new ArrayList<Map>();
		ArrayList<Map> tmpLocalMaps = new ArrayList<Map>();

		tmpLocalMaps = new MapLocalDAOManager().getMaps();
		maps = new MapDAOManager().getMaps();
		for (int i = 0; i < tmpLocalMaps.size(); i++) {
			maps.add(tmpLocalMaps.get(i));
		}
		return maps;
	}

	@Override
	public GameState createGame(String playerName, Hero hero, Map map) {
		if (!(hero instanceof HeroCharacter)) {
			throw new IllegalArgumentException("Le type de héros n'est pas supporté");
		}
		Game newGame = new Game(playerName, hero, map, String.format("Game %d", games.size()));
		games.add(newGame);
		newGame.setGameID(Integer.toString(new GameStateDAOManager().createGame(newGame)));
		((HeroCharacter) newGame.getHero()).setId(new HeroDAOManager().createHero(hero));
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

		game.setLastLog(tmp);
		boolean saveGame = new GameStateDAOManager().updateGame(game);
		if (!saveGame) {
			tmp = tmp + "\nErreur lors de la sauvegarde de la partie !\n";
		}
		boolean saveHero = new HeroDAOManager().updateHero((HeroCharacter) game.getCharacter());
		if (!saveHero) {
			tmp = tmp + "\nErreur lors de la sauvegarde du personnage !\n";
		}
		return game;
	}

	@Override
	public List<? extends Hero> getHeroes() {
		warriors = new DefaultHeroDAOManager().getHeroes();
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

	public void setDBGames(ArrayList<GameState> gameList) {
		for (int i = 0; i < gameList.size(); i++) {
			games.add((Game) gameList.get(i));
		}
	}
}
