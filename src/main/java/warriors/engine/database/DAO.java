package warriors.engine.database;

import java.sql.Connection;
import java.util.ArrayList;


public abstract class DAO<T> {
	
	protected Connection conn;
	
	public DAO(Connection conn) {
		this.conn = conn;
	}
	
	protected abstract boolean create(Object object);
	
	protected abstract boolean update(Object object);
	
	protected abstract boolean delete(Object object);
	
	protected abstract ArrayList<T> findAll();
}
