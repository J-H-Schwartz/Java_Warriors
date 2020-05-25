package warriors.engine;

import warriors.contracts.GameState;
import warriors.contracts.GameStatus;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.engine.board.Board;
import warriors.engine.heroes.HeroCharacter;

public class Game implements GameState {

	private static final int START_CASE = -1;

	private String playerName;
	private String gameId;
	private GameStatus gameStatus;
	private Hero hero;
	private Map map;
	private String lastLog;
	private int currentCase;
	private int heroDefaultLife;
	private DebugStatus debugStatus;
	private int[] debugDicesFile;

	public Game(String playerName, Hero hero, Map map, String gameId) {
		this.gameId = gameId;
		this.playerName = playerName;
		this.hero = hero;
		this.heroDefaultLife = hero.getLife();
		this.map = map;
		this.currentCase = START_CASE;
		this.gameStatus = GameStatus.IN_PROGRESS;
		this.lastLog = "Lancement dune nouvelle partie.";
		this.debugStatus = DebugStatus.DEBUG_OFF;
	}
	
	public Game(String playerName, Hero hero, Map map, String gameId, String lastLog, GameStatus gameStatus, int currentCase, int heroDefaultLife) {
		this.gameId = gameId;
		this.playerName = playerName;
		this.hero = hero;
		this.heroDefaultLife = heroDefaultLife;
		this.map = map;
		this.currentCase = currentCase;
		this.gameStatus = gameStatus;
		this.lastLog = lastLog;
		this.debugStatus = DebugStatus.DEBUG_OFF;
	}

	public String manageGameWin(String tmp) {
		this.setGameStatus(GameStatus.FINISHED);
		this.setCurrentCase(START_CASE);
		this.getCharacter().setLife(this.heroDefaultLife);
		this.setDebugStatus(DebugStatus.DEBUG_OFF);
		tmp = tmp + "Vous êtes sorti du donjon et avez gagné la partie !.\n ";
		return tmp;
	}

	public String manageGameLoss(String tmp) {
		this.setGameStatus(GameStatus.GAME_OVER);
		this.setCurrentCase(START_CASE);
		this.getCharacter().setLife(this.heroDefaultLife);
		this.setDebugStatus(DebugStatus.DEBUG_OFF);
		tmp = tmp + String.format("\nVous navez plus de vie. Partie terminée.");
		return tmp;
	}

	/**
	 * @return the debugStatus
	 */
	public DebugStatus getDebugStatus() {
		return debugStatus;
	}

	/**
	 * @param debugStatus the debugStatus to set
	 */
	public void setDebugStatus(DebugStatus debugStatus) {
		this.debugStatus = debugStatus;
	}

	/**
	 * @return the debugDicesFile
	 */
	public int[] getDebugDicesFile() {
		return debugDicesFile;
	}

	/**
	 * @param debugDicesFile the debugDicesFile to set
	 */
	public void setDebugDicesFile(int[] debugDicesFile) {
		this.debugDicesFile = debugDicesFile;
	}
	
	/**
	 * @param currentCase the currentCase to set
	 */
	public void setCurrentCase(int currentCase) {
		this.currentCase = currentCase;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public String getGameId() {
		return gameId;
	}

	@Override
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	@Override
	public Hero getHero() {
		return hero;
	}

	@Override
	public Map getMap() {
		return map;
	}

	@Override
	public String getLastLog() {
		return lastLog;
	}

	@Override
	public int getCurrentCase() {
		return currentCase;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public void setLastLog(String lastLog) {
		this.lastLog = lastLog;
	}

	public HeroCharacter getCharacter() {
		return (HeroCharacter) hero;
	}

	public Board getBoard() {
		return (Board) map;
	}

	/**
	 * @return the heroDefaultLife
	 */
	public int getHeroDefaultLife() {
		return heroDefaultLife;
	}

	/**
	 * @param heroDefaultLife the heroDefaultLife to set
	 */
	public void setHeroDefaultLife(int heroDefaultLife) {
		this.heroDefaultLife = heroDefaultLife;
	}

	public void setGameID(String gameId) {
		this.gameId = gameId;
	}
}
