package warriors.engine.board;

public class BoardCaseEmpty extends BoardCase{

	public BoardCaseEmpty(int id) {
		super(id);
		this.caseStatus = CaseType.EMPTY_CASE;
		this.contains = "Empty";
	}
}
