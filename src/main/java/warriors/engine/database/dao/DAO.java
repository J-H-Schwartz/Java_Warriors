package warriors.engine.database.dao;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {

	protected Connection conn;
	protected String filePath;

	public DAO(Connection conn) {
		this.conn = conn;
	}

	public DAO(String filePath) {
		this.filePath = filePath;
	}

	public abstract int create(T object);

	public abstract boolean update(T object);

	public abstract boolean delete(T object);

	public abstract ArrayList<T> findAll();
}
