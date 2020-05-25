package warriors.engine.database;

import java.util.ArrayList;

import warriors.contracts.GameState;
import warriors.engine.database.dao.DAO;
import warriors.engine.database.dao.DAOFactory;

public class GameStateDAOManager {
	
	private static DAO<GameState> gameStateDAO = DAOFactory.getGameStateDAO();

	public ArrayList<GameState> getGames() {
		ArrayList<GameState> gameList = gameStateDAO.findAll();
		return gameList;
	}

	public int createGame(GameState game) {
		int result = gameStateDAO.create(game);
		return result;
	}

	public boolean updateGame(GameState game) {
		boolean result = gameStateDAO.update(game);
		return result;
	}

	public boolean deleteGame(GameState game) {
		boolean result = gameStateDAO.delete(game);
		return result;
	}
}
