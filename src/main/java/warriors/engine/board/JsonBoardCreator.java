package warriors.engine.board;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.stream.JsonReader;

import warriors.contracts.Map;

public class JsonBoardCreator implements InstanceCreator<Map> {
	private Path file;

	public JsonBoard createBoard(Path file) {
		this.file = file;
		JsonBoard newMap = (JsonBoard) createInstance(getClass());
		return newMap;
	}

	@Override
	public JsonBoard createInstance(Type arg0) {
		FileReader reader = null;
		Gson gson = null;
		Board map = null;
		try {
			reader = new FileReader(this.file.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonReader jsonreader = new JsonReader(reader);
		if (reader != null) {
			gson = new Gson();
			map = gson.fromJson(jsonreader, Board.class);
		}
		return new JsonBoard(map);
	}

}
