package warriors.engine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	private static Connection conn;
	private static String url = "jdbc:mysql://localhost:3306/Java_Warriors?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = "javajdbc";
	private static String passwd = "jdbcmdp";

	public static Connection dbConnect() {

		if (conn == null) {
			try {
				conn = DriverManager.getConnection(url, user, passwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conn;
	}
}
