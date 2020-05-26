package warriors.engine.database;

import java.util.ArrayList;

import warriors.contracts.Map;
import warriors.engine.database.dao.DAO;
import warriors.engine.database.dao.DAOFactory;

public class MapLocalDAOManager {
	private static DAO<Map> mapLocalDAO = DAOFactory.getMapLocalDAO();

	public ArrayList<Map> getMaps() {
		ArrayList<Map> mapList = mapLocalDAO.findAll();
		return mapList;
	}
}
