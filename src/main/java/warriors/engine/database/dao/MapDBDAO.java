package warriors.engine.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import warriors.contracts.Map;
import warriors.engine.board.jsonAdapter.JsonDBBoardCreator;

public class MapDBDAO extends DAO<Map> {

	public MapDBDAO(Connection conn) {
		super(conn);
	}

	@Override
	public int create(Map object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(Map object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Map object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Map> findAll() {
		Statement state = null;
		ArrayList<Map> maps = new ArrayList<Map>();
		try {
			state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Maps");
			Map tmpMap = null;
			while (result.next()) {
//				int id = result.getInt("id");
//				String name = result.getString("Name");
//				int NumberOfCase = result.getInt("NumberOfCase");
				String data = result.getString("Data");

				tmpMap = (Map) new JsonDBBoardCreator().createBoard(data);
				maps.add(tmpMap);
			}
			result.close();
			return maps;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
