package warriors.engine.database;

import java.util.ArrayList;

import warriors.contracts.Hero;
import warriors.engine.database.dao.DAO;
import warriors.engine.database.dao.DAOFactory;

public class HeroDAOManager {	
	private static DAO<Hero> heroDAO = DAOFactory.getHeroDAO();
	
	public ArrayList<Hero> getHeroes() {
		ArrayList<Hero> heroList = heroDAO.findAll();
		return heroList;
	}
	
	public int createHero(Hero hero) {
		int result = heroDAO.create(hero);
		return result;
	}
	
	public boolean updateHero(Hero hero) {
		boolean result = heroDAO.update(hero);
		return result;
	}
	
	public boolean deleteHero(Hero hero) {
		boolean result = heroDAO.delete(hero);
		return result;
	}
}
