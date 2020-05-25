package warriors.engine.database;

import java.util.ArrayList;

import warriors.contracts.Hero;
import warriors.engine.database.dao.DAO;
import warriors.engine.database.dao.DAOFactory;

public class DefaultHeroDAOManager {
	private static DAO<Hero> defaultHeroDAO = DAOFactory.getDefaultHeroDAO();

	public ArrayList<Hero> getHeroes() {
		ArrayList<Hero> heroList = defaultHeroDAO.findAll();
		return heroList;
	}

	public int createHero(Hero hero) {
		int result = defaultHeroDAO.create(hero);
		return result;
	}

	public boolean updateHero(Hero hero) {
		boolean result = defaultHeroDAO.update(hero);
		return result;
	}

	public boolean deleteHero(Hero hero) {
		boolean result = defaultHeroDAO.delete(hero);
		return result;
	}
}
