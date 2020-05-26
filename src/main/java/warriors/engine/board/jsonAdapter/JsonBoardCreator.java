package warriors.engine.board.jsonAdapter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.stream.JsonReader;

import warriors.contracts.Map;
import warriors.engine.board.Board;
import warriors.engine.board.BoardCase;

public class JsonBoardCreator implements InstanceCreator<Map> {
	private Path file;

	public Board createBoard(Path file) {
		this.file = file;
		Board newMap = (Board) createInstance(getClass());
		return newMap;
	}

	@Override
	public Board createInstance(Type arg0) {
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
			gson = new GsonBuilder().registerTypeAdapter(BoardCase.class, new InterfaceAdapter<BoardCase>()).create();
			map = gson.fromJson(jsonreader, Board.class);
			return map;
		} else {
			try {
				jsonreader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

}
