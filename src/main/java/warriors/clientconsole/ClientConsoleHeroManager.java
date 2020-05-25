package warriors.clientconsole;

import java.util.ArrayList;
import java.util.Scanner;

import warriors.contracts.Hero;
import warriors.engine.database.DefaultHeroDAOManager;
import warriors.engine.heroes.HeroCharacter;
import warriors.engine.heroes.Warrior;
import warriors.engine.heroes.Wizard;

public class ClientConsoleHeroManager {

	private static DefaultHeroDAOManager defaultHeroDAOManager = new DefaultHeroDAOManager();

	public void managerInterface(Scanner sc) {

		String choice = "";
		while (!choice.equals("0")) {
			System.out.println(
					"Que voulez-vous faire ?\n1 - Lister personnages\n2 - Créer personnage\n3 - Supprimer personnage\n4 - Afficher un personnage\n5 - Modifier personnage\n0 - Quitter\n");
			choice = sc.nextLine();
			if (choice.equals("1")) {
				this.listHero();
			} else if (choice.equals("2")) {
				this.createHero(sc);
			} else if (choice.equals("3")) {
				this.deleteHero(sc);
			} else if (choice.equals("4")) {
				this.showHero(sc);
			} else if (choice.equals("5")) {
				this.updateHero(sc);
			} else if (choice.equals("0")) {
				System.out.println("Retour au menu précédent.");
			} else
				System.out.println("Commande non reconnue.");
		}
	}

	protected void createHero(Scanner sc) {
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

		HeroCharacter newChar = null;
		if (className.equals("Warrior")) {
			newChar = new Warrior(name, life, attackLevel, imageUrl, weapon, shield);
		} else if (className.equals("Wizard")) {
			newChar = new Wizard(name, life, attackLevel, imageUrl, weapon, shield);
		} else {
			System.out.println("Cette classe n'existe pas. Retour au menu précédent.");
			return;
		}
		int result = defaultHeroDAOManager.createHero(newChar);
		if (result >= 0) {
			System.out.println("Personnage ajouté à la base de données.");
		} else {
			System.out.println("Echec, un problème a été rencontré pendant la création.");
		}
	}

	protected void updateHero(Scanner sc) {
		int choice = -1;
		while (choice != 0) {
			listHero();
			int heroIndex = -1;
			System.out.println("Entrez l'id du personnage à modifier (0 pour quitter): ");
			choice = Integer.parseInt(sc.nextLine());
			if (choice != 0) {
				ArrayList<Hero> heroList = defaultHeroDAOManager.getHeroes();
				do {
					heroIndex++;
				} while ((heroIndex < heroList.size())
						&& (((HeroCharacter) heroList.get(heroIndex)).getId() != choice));
				if (heroIndex != heroList.size()) {
					System.out.println(
							"Que voulez-vous modifier ? Entrez le champ en toutes lettres\n(Name, ImageURL, Life, AttackLevel, Stuff, Shield) :\n");
					String field = sc.nextLine();
					System.out.println("Entrez la nouvelle valeur: ");
					String value = sc.nextLine();
					if (field.equals("Name")) {
						((HeroCharacter) heroList.get(heroIndex)).setName(value);
					} else if (field.equals("ImageUrl")) {
						((HeroCharacter) heroList.get(heroIndex)).setImageUrl(value);
					} else if (field.equals("Life")) {
						try {
							((HeroCharacter) heroList.get(heroIndex)).setLife(Integer.parseInt(value));
						} catch (NumberFormatException e) {
							System.out.println(
									"Vous devez entrer un nombre si vous souhaitez modifier ce champ. Retour au menu précédent.");
							return;
						}
					} else if (field.equals("AttackLevel")) {
						try {
							((HeroCharacter) heroList.get(heroIndex)).setAttackLevel(Integer.parseInt(value));
						} catch (NumberFormatException e) {
							System.out.println(
									"Vous devez entrer un nombre si vous souhaitez modifier ce champ. Retour au menu précédent.");
							return;
						}
					} else if (field.equals("Stuff")) {
						((HeroCharacter) heroList.get(heroIndex)).getRightHand().setName(value);
					} else if (field.equals("Shield")) {
						((HeroCharacter) heroList.get(heroIndex)).setShield(value);
					}
					boolean result = defaultHeroDAOManager.updateHero((HeroCharacter) heroList.get(heroIndex));
					if (result) {
						System.out.println("Modification réussie.");
					} else {
						System.out.println("Echec de la modification.");
					}
				} else {
					System.out.println("Ce personnage n'existe pas.");
				}
			}
		}
		System.out.println("Retour au menu précédent.");
	}

	protected void listHero() {
		ArrayList<Hero> heroes = defaultHeroDAOManager.getHeroes();
		for (int i = 0; i < heroes.size(); i++) {
			System.out.println(String.format("Personnage: %d, %s, %s, %s, %d, %d, %s, %s",
					((HeroCharacter) heroes.get(i)).getId(), ((HeroCharacter) heroes.get(i)).getClassName(),
					((HeroCharacter) heroes.get(i)).getName(), ((HeroCharacter) heroes.get(i)).getImageUrl(),
					((HeroCharacter) heroes.get(i)).getLife(), ((HeroCharacter) heroes.get(i)).getAttackLevel(),
					((HeroCharacter) heroes.get(i)).getRightHand().getName(),
					((HeroCharacter) heroes.get(i)).getShield()));
		}
	}

	protected void showHero(Scanner sc) {
		String choice = "";
		while (!choice.equals("0")) {
			listHero();
			System.out.println("Entrez l'id du personnage à Afficher (0 pour Quitter): ");
			choice = sc.nextLine();
//					// L'objet ResultSet contient le résultat de la requête SQL
//					ResultSet result = state.executeQuery(String.format("SELECT * FROM Hero WHERE id = '%s'", choice));
//					// On récupère les MetaData
//					ResultSetMetaData resultMeta = result.getMetaData();
//
//					System.out.println("\n**********************************");
//					// On affiche le nom des colonnes
//					for (int i = 1; i <= resultMeta.getColumnCount(); i++)
//						System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
//
//					System.out.println("\n**********************************");
//
//					while (result.next()) {
//						for (int i = 1; i <= resultMeta.getColumnCount(); i++)
//							System.out.print("\t" + result.getObject(i).toString() + "\t |");
//
//						System.out.println("\n---------------------------------");
//
//					}
			if (!choice.equals("0")) {
				ArrayList<Hero> heroes = defaultHeroDAOManager.getHeroes();
				for (int i = 0; i < heroes.size(); i++) {
					if (Integer.toString(((HeroCharacter) heroes.get(i)).getId()).equals(choice)) {
						System.out.println(String.format("Personnage: %d, %s, %s, %s, %d, %d, %s, %s",
								((HeroCharacter) heroes.get(i)).getId(), ((HeroCharacter) heroes.get(i)).getClassName(),
								((HeroCharacter) heroes.get(i)).getName(),
								((HeroCharacter) heroes.get(i)).getImageUrl(),
								((HeroCharacter) heroes.get(i)).getLife(),
								((HeroCharacter) heroes.get(i)).getAttackLevel(),
								((HeroCharacter) heroes.get(i)).getRightHand().getName(),
								((HeroCharacter) heroes.get(i)).getShield()));
						return;
					}
				}
				System.out.println("Personnage non trouvé.");
			}
		}
		System.out.println("Retour au menu précédent.");
	}

	protected void deleteHero(Scanner sc) {
		String choice = "";
		int heroIndex = -1;
		while (!choice.equals("0")) {
			listHero();
			System.out.println("Entrez l'id du personnage à supprimer (0 pour Quitter): ");
			choice = sc.nextLine();
			try {
				if (!choice.equals("0")) {
					ArrayList<Hero> heroList = defaultHeroDAOManager.getHeroes();
					do {
						heroIndex++;
					} while ((heroIndex < heroList.size())
							&& (((HeroCharacter) heroList.get(heroIndex)).getId() != Integer.parseInt(choice)));
					if (heroIndex != heroList.size()) {
						boolean deleteStatus = defaultHeroDAOManager
								.deleteHero((HeroCharacter) heroList.get(heroIndex));
						if (deleteStatus) {
							System.out.println("Le personnage a bien été supprimé.");
						} else {
							System.out.println("Le personnage séléctionné n'existe pas. Recommencez.");
						}
					} else {
						System.out.println("Ce personnage n'existe pas.");
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Vous devez entrer un nombre.");
			}
		}
		System.out.println("Retour au menu précédent.");
	}

}
