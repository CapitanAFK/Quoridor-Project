package Gameplay;

public class GameplayFacade {
	private Controls controls;
	private Quoridor quoridor;
	private Rules rules;
	
	public GameplayFacade(){
		quoridor = new Quoridor(controls, rules);
	}

	public Controls getControls() {
		return controls;
	}

	public void setControls(Controls controls) {
		this.controls = controls;
	}

	public Quoridor getQuoridor() {
		return quoridor;
	}

	public void setQuoridor(Quoridor quoridor) {
		this.quoridor = quoridor;
	}

	public Rules getRules() {
		return rules;
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}
	

}
