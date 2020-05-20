package warriors.engine.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import warriors.contracts.Hero;
import warriors.engine.heroes.Warrior;
import warriors.engine.heroes.Wizard;

public class DbCharacterManager extends DAO<Hero> {

	public DbCharacterManager(Connection conn) {
		super(conn);
	}

	@Override
	public void managerInterface(Scanner sc) {
		Statement state;
		try {
			// Création d'un objet Statement
			state = conn.createStatement();
			String choice = "";

			while (!choice.equals("0")) {
				System.out.println(
						"Que voulez-vous faire ?\n1 - Lister personnages\n2 - Créer personnage\n3 - Supprimer personnage\n4 - Afficher un personnage\n5 - Modifier personnage\n0 - Quitter\n");
				choice = sc.nextLine();
				if (choice.equals("1")) {
					this.list(state);
				} else if (choice.equals("2")) {
					this.create(state, sc);
				} else if (choice.equals("3")) {
					this.delete(state, sc);
				} else if (choice.equals("4")) {
					this.show(state, sc);
				} else if (choice.equals("5")) {
					this.update(state, sc);
				} else if (choice.equals("0")) {
					System.out.println("Retour au menu précédent.");
				} else
					System.out.println("Commande non reconnue.");
			}
			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void create(Statement state, Scanner sc) {
		String className = "";
		do {
			System.out.println("Classe du personnage (Warrior, Wizard):");
			className = sc.nextLine();
		} while (!className.equals("Warrior") && !className.equals("Wizard"));
		System.out.println("Nom du personnage :");
		String name = sc.nextLine();
		System.out.println("Image du personnage :");
		String imageUrl = sc.nextLine();
		System.out.println("Arme du personnage :");
		String weapon = sc.nextLine();
		System.out.println("Bouclier du personnage :");
		String shield = sc.nextLine();
		System.out.println("Vie du personnage :");
		int life = sc.nextInt();
		sc.nextLine();
		System.out.println("Attaque du personnage :");
		int attackLevel = sc.nextInt();
		sc.nextLine();
		try {
			state.execute(String.format(
					"INSERT INTO Hero(Type, Name, ImageUrl, Life, AttackLevel, Stuff, Shield) VALUES ('%s', '%s', '%s', '%d', '%d', '%s', '%s')",
					className, name, imageUrl, life, attackLevel, weapon, shield));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void update(Statement state, Scanner sc) {
		int choice = -1;
		while (choice != 0) {
			list(state);
			System.out.println("Entrez l'id du personnage à modifier (0 pour quitter): ");
			choice = Integer.parseInt(sc.nextLine());
			if (choice != 0) {
				System.out.println(
						"Que voulez-vous modifier ? Entrez le champ en toutes lettres\n(Type, Name, ImageURL, Life, AttackLevel, Stuff, Shield) :\n");
				String field = sc.nextLine();
				System.out.println("Entrez la nouvelle valeur: ");
				String value = sc.nextLine();
				try {

					int sqlResult = state.executeUpdate(
							String.format("UPDATE Hero SET %s = '%s' WHERE id = '%s'", field, value, choice));
					if (sqlResult == 0) {
						System.out.println("La modification a échoué.");
					} else {
						System.out.println("Le personnage a été modifié avec succès.");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Retour au menu précédent.");
	}

	@Override
	protected void list(Statement state) {
		try {
			// L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM Hero");
			// On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();

			System.out.println("\n**********************************");
			// On affiche le nom des colonnes
			for (int i = 1; i <= resultMeta.getColumnCount(); i++)
				System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

			System.out.println("\n**********************************");

			while (result.next()) {
				for (int i = 1; i <= resultMeta.getColumnCount(); i++)
					System.out.print("\t" + result.getObject(i).toString() + "\t |");

				System.out.println("\n---------------------------------");

			}

			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void show(Statement state, Scanner sc) {
		String choice = "";
		while (!choice.equals("0")) {
			list(state);
			System.out.println("Entrez l'id du personnage à Afficher (0 pour Quitter): ");
			choice = sc.nextLine();
			try {
				if (!choice.equals("0")) {
					// L'objet ResultSet contient le résultat de la requête SQL
					ResultSet result = state.executeQuery(String.format("SELECT * FROM Hero WHERE id = '%s'", choice));
					// On récupère les MetaData
					ResultSetMetaData resultMeta = result.getMetaData();

					System.out.println("\n**********************************");
					// On affiche le nom des colonnes
					for (int i = 1; i <= resultMeta.getColumnCount(); i++)
						System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

					System.out.println("\n**********************************");

					while (result.next()) {
						for (int i = 1; i <= resultMeta.getColumnCount(); i++)
							System.out.print("\t" + result.getObject(i).toString() + "\t |");

						System.out.println("\n---------------------------------");

					}
					result.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Retour au menu précédent.");
	}

	@Override
	protected void delete(Statement state, Scanner sc) {
		String choice = "";
		while (!choice.equals("0")) {
			list(state);
			System.out.println("Entrez l'id du personnage à supprimer (0 pour Quitter): ");
			choice = sc.nextLine();
			try {
				if (!choice.equals("0")) {
					int deleteStatus = state.executeUpdate(String.format("DELETE FROM Hero WHERE id = '%s'", choice));
					if (deleteStatus == 0) {
						System.out.println("Le personnage séléctionné n'existe pas. Recommencez.");
					} else {
						System.out.println("Le personnage a bien été supprimé.");
					}
				}
			} catch (SQLException e) {
				System.out.println("Commande SQL erronée.");
			}
		}
		System.out.println("Retour au menu précédent.");
	}

	public ArrayList<Hero> dbHeroesGetter() {
		Statement state;
		ArrayList<Hero> heroes = new ArrayList<Hero>();

		// Création d'un objet Statement
		try {
			state = this.conn.createStatement();
			try {
				// L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM Hero");
				// On récupère les MetaData

				while (result.next()) {
					if (result.getObject("Type").equals("Warrior")) {
						heroes.add(new Warrior((String) result.getObject("Name"), (int) result.getObject("Life"),
								(int) result.getObject("AttackLevel")));
					} else if (result.getObject("Type").equals("Wizard")) {
						heroes.add(new Wizard((String) result.getObject("Name"), (int) result.getObject("Life"),
								(int) result.getObject("AttackLevel")));
					}
				}

				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return heroes;
	}
}
