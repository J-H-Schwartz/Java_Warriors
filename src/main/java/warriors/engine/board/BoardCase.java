package warriors.engine.board;

import warriors.engine.heroes.HeroCharacter;

public class BoardCase {

	protected int caseID;
	protected CaseType caseStatus;
	protected String contains;

	public BoardCase(int id) {
		this.caseID = id;
	}

	public String manageCaseEvent(HeroCharacter hero, String tmp) {
		tmp = tmp + "\nCette case est vide";
		return tmp;
	}

	/**
	 * @return the caseID
	 */
	public int getCaseID() {
		return caseID;
	}

	/**
	 * @param caseID the caseID to set
	 */
	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}

	/**
	 * @return the caseStatus
	 */
	public CaseType getCaseStatus() {
		return caseStatus;
	}

	/**
	 * @param caseStatus the caseStatus to set
	 */
	public void setCaseStatus(CaseType caseStatus) {
		this.caseStatus = caseStatus;
	}

	/**
	 * @return the contains
	 */
	public String getContains() {
		return contains;
	}

	/**
	 * @param contains the contains to set
	 */
	public void setContains(String contains) {
		this.contains = contains;
	}
}
