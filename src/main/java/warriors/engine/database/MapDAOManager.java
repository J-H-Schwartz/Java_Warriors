package warriors.engine.database;

import java.util.ArrayList;

import warriors.contracts.Map;
import warriors.engine.database.dao.DAO;
import warriors.engine.database.dao.DAOFactory;

public class MapDAOManager {

	private static DAO<Map> mapDAO = DAOFactory.getMapDAO();

	public ArrayList<Map> getMaps() {
		ArrayList<Map> mapList = mapDAO.findAll();
		return mapList;
	}
}
