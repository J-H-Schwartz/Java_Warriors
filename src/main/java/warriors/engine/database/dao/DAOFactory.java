package warriors.engine.database.dao;

import java.sql.Connection;

import warriors.contracts.GameState;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.engine.database.DbConnect;

public class DAOFactory {
	private static final Connection conn = DbConnect.dbConnect();
	private static final String filePath = "src/main/ressources/maps";

	public static DAO<Hero> getHeroDAO() {
		return new HeroDAO(conn);
	}

	public static DAO<Hero> getDefaultHeroDAO() {
		return new DefaultHeroDAO(conn);
	}

	public static DAO<GameState> getGameStateDAO() {
		return new GameStateDAO(conn);
	}

	public static DAO<Map> getMapDAO() {
		return new MapDBDAO(conn);
	}

	public static DAO<Map> getMapLocalDAO() {
		return new MapLocalDAO(filePath);
	}
}
