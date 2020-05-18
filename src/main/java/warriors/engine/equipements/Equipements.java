package warriors.engine.equipements;

public class Equipements {
	protected String name;
	protected int effect;

	public Equipements(String name, int effect) {
		this.effect = effect;
		this.name = name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}

	public String getName() {
		return this.name;
	}

	public void setEffect(int newEffect) {
		this.effect = newEffect;
	}

	public int getEffect() {
		return this.effect;
	}
}
