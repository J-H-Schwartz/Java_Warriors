package warriors.engine.database.dao;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import warriors.contracts.Map;
import warriors.engine.board.Board;
import warriors.engine.board.jsonAdapter.JsonBoardCreator;

public class MapLocalDAO extends DAO<Map> {

	public MapLocalDAO(String filepath) {
		super(filepath);
	}

	@Override
	public int create(Map object) {
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
		ArrayList<Map> maps = new ArrayList<Map>();
		Board map = new Board("Default_Random_Map");
		maps.add(map);

		// Imports all json board files from path.
		JsonBoardCreator jsb = new JsonBoardCreator();
		try {
			Path dirPath = Paths.get(this.filePath);
			try (DirectoryStream<Path> dirPaths = Files.newDirectoryStream(dirPath, "*.{json}")) { // .jdb only
				for (Path file : dirPaths) {
					Board newMap = (Board) jsb.createBoard(file);
					maps.add(newMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}

}
