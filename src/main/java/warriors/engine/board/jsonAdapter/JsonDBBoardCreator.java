package warriors.engine.board.jsonAdapter;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import warriors.contracts.Map;
import warriors.engine.board.Board;
import warriors.engine.board.BoardCase;

public class JsonDBBoardCreator implements InstanceCreator<Map> {
	private String jsonString;

	public Board createBoard(String jsonString) {
		this.jsonString = jsonString;
		Board newMap = (Board) createInstance(getClass());
		return newMap;
	}

	@Override
	public Board createInstance(Type arg0) {
		Gson gson = null;
		Board map = null;
		gson = new GsonBuilder().registerTypeAdapter(BoardCase.class, new InterfaceAdapter<BoardCase>()).create();
		map = gson.fromJson(jsonString, Board.class);
		return map;
	}
}
