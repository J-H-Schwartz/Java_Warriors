package warriors.engine.database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public abstract class DAO<T> {
	
	protected Connection conn;
	
	public DAO(Connection conn) {
		this.conn = conn;
	}
	
	public abstract void managerInterface(Scanner sc);
	
	protected abstract void create(Statement state, Scanner sc);
	
	protected abstract void update(Statement state, Scanner sc);
	
	protected abstract void list(Statement state);
	
	protected abstract void show(Statement state, Scanner sc);
	
	protected abstract void delete(Statement state, Scanner sc);
}
