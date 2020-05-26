package warriors.engine.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import warriors.contracts.GameState;
import warriors.contracts.GameStatus;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.engine.Game;
import warriors.engine.Warriors;
import warriors.engine.database.HeroDAOManager;
import warriors.engine.heroes.HeroCharacter;

public class GameStateDAO extends DAO<GameState> {

	public GameStateDAO(Connection conn) {
		super(conn);
	}

	@Override
	public int create(GameState gameState) {
		Game newGame = (Game) gameState;
		try {
			int id = -1;
			PreparedStatement state = this.conn.prepareStatement(String.format(
					"INSERT INTO GameState(PlayerName, GameStatus, HeroID, Map, LastLog, CurrentCase, HeroDefaultLife) VALUES ('%s', '%s', '%d', '%s', '%s', '%d', '%d')",
					newGame.getPlayerName(), newGame.getGameStatus().toString(), newGame.getCharacter().getId(),
					newGame.getMap().getName(), newGame.getLastLog(), newGame.getCurrentCase(),
					newGame.getHeroDefaultLife()), Statement.RETURN_GENERATED_KEYS);
			int result = state.executeUpdate();
			try (ResultSet generatedKeys = state.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating Game failed, no ID obtained.");
				}
			}
			if (result == 1) {
				return id;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(GameState gameState) {
		Game gameToUpdate = (Game) gameState;
		try {
			Statement state = this.conn.createStatement();
			int sqlResult = state.executeUpdate(String.format(
					"UPDATE GameState SET PlayerName = '%s', GameStatus = '%s', HeroID = '%d',  Map = '%s', LastLog = '%s', CurrentCase = '%d', HeroDefaultLife = '%d' WHERE id = '%s'",
					gameToUpdate.getPlayerName(), gameToUpdate.getGameStatus().toString(),
					gameToUpdate.getCharacter().getId(), gameToUpdate.getMap().getName(), gameToUpdate.getLastLog(),
					gameToUpdate.getCurrentCase(), gameToUpdate.getHeroDefaultLife(), gameToUpdate.getGameId()));
			if (sqlResult == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(GameState gameState) {
		Game gameToDelete = (Game) gameState;
		try {
			Statement state = this.conn.createStatement();
			int deleteStatus = state
					.executeUpdate(String.format("DELETE FROM GameState WHERE id = '%s'", gameToDelete.getGameId()));
			if (deleteStatus == 0) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<GameState> findAll() {
		Statement state = null;
		ArrayList<GameState> gameList = new ArrayList<GameState>();
		try {
			state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM GameState");
			Game tmpGame = null;
			ArrayList<Map> maps = new ArrayList<Map>();
			ArrayList<Hero> heroes = new HeroDAOManager().getHeroes();
			maps = Warriors.getMapList();
			while (result.next()) {
				int id = result.getInt("id");
				String gameStatusString = result.getString("GameStatus");
				String playerName = result.getString("PlayerName");
				int heroID = result.getInt("HeroID");
				String mapName = result.getString("Map");
				String lastLog = result.getString("LastLog");
				int currentCase = result.getInt("CurrentCase");
				int heroDefaultLife = result.getInt("HeroDefaultLife");
				Hero tmpHero = null;
				Map tmpMap = null;
				for (int i = 0; i < heroes.size(); i++) {
					if (heroID == ((HeroCharacter) heroes.get(i)).getId()) {
						tmpHero = heroes.get(i);
					}
				}
				for (int i = 0; i < maps.size(); i++) {
					if (mapName.equals(maps.get(i).getName())) {
						tmpMap = maps.get(i);
					}
				}
				String gameId = Integer.toString(id);
				GameStatus gameStatus = null;
				if (gameStatusString.equals(GameStatus.IN_PROGRESS.toString())) {
					gameStatus = GameStatus.IN_PROGRESS;
				} else if (gameStatusString.equals(GameStatus.FINISHED.toString())) {
					gameStatus = GameStatus.FINISHED;
				} else if (gameStatusString.equals(GameStatus.GAME_OVER.toString())) {
					gameStatus = GameStatus.GAME_OVER;
				}
				tmpGame = new Game(playerName, tmpHero, tmpMap, gameId, lastLog, gameStatus, currentCase,
						heroDefaultLife);
				gameList.add(tmpGame);
			}
			result.close();
			return gameList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
