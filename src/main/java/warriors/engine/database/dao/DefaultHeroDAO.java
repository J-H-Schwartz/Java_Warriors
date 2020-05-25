package warriors.engine.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import warriors.contracts.Hero;
import warriors.engine.heroes.HeroCharacter;
import warriors.engine.heroes.Warrior;
import warriors.engine.heroes.Wizard;

public class DefaultHeroDAO extends DAO<Hero> {

	public DefaultHeroDAO(Connection conn) {
		super(conn);

	}

	@Override
	public int create(Hero hero) {
		HeroCharacter newHero = (HeroCharacter) hero;
		try {
			int id = -1;
			PreparedStatement state = this.conn.prepareStatement(String.format(
					"INSERT INTO DefaultHero(Type, Name, ImageUrl, Life, AttackLevel, Stuff, Shield) VALUES ('%s', '%s', '%s', '%d', '%d', '%s', '%s')",
					newHero.getClassName(), newHero.getName(), newHero.getImageUrl(), newHero.getLife(),
					newHero.getAttackLevel(), newHero.getRightHand().getName(), newHero.getShield()),
					Statement.RETURN_GENERATED_KEYS);
			int result = state.executeUpdate();
			try (ResultSet generatedKeys = state.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating Hero failed, no ID obtained.");
				}
			}
			if (result == 1) {
				return id;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Hero hero) {
		try {
			Statement state = this.conn.createStatement();
			int sqlResult = state.executeUpdate(String.format(
					"UPDATE DefaultHero SET Name = '%s', ImageUrl = '%s', Life = '%s',  AttackLevel = '%s', Stuff = '%s', Shield = '%s' WHERE id = '%s'",
					((HeroCharacter) hero).getName(), ((HeroCharacter) hero).getImageUrl(),
					Integer.toString(((HeroCharacter) hero).getLife()),
					Integer.toString(((HeroCharacter) hero).getAttackLevel()),
					((HeroCharacter) hero).getRightHand().getName(), ((HeroCharacter) hero).getShield(),
					Integer.toString(((HeroCharacter) hero).getId())));
			if (sqlResult == 0) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Hero hero) {
		try {
			Statement state = this.conn.createStatement();
			int deleteStatus = state
					.executeUpdate(String.format("DELETE FROM DefaultHero WHERE id = '%s'", ((HeroCharacter) hero).getId()));
			if (deleteStatus == 0) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Hero> findAll() {
		Statement state = null;
		ArrayList<Hero> heroList = new ArrayList<Hero>();
		try {
			state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM DefaultHero");
			HeroCharacter tmpHero = null;
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("Name");
				String type = result.getString("Type");
				String ImageUrl = result.getString("ImageUrl");
				String rightHand = result.getString("Stuff");
				String shield = result.getString("shield");
				int life = result.getInt("Life");
				int attackLevel = result.getInt("AttackLevel");
				if (type.equals("Warrior")) {
					tmpHero = new Warrior(name, life, attackLevel, ImageUrl, rightHand, shield);
					tmpHero.setId(id);
				} else if (type.equals("Wizard")) {
					tmpHero = new Wizard(name, life, attackLevel, ImageUrl, rightHand, shield);
					tmpHero.setId(id);
				}
				heroList.add(tmpHero);
			}
			result.close();
			return heroList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}