package xx.sm.transition;

import xx.sm.Environment;
import xx.sm.Identifier;

public class SimpleTransition implements Transition  {

	private String startState;
	private String endState;
	private String triggerSignal;
	
	public SimpleTransition(String startState, String endState,
			String triggerSignal) {
		super();
		this.startState = startState;
		this.endState = endState;
		this.triggerSignal = triggerSignal;
	}
	
	public SimpleTransition(Identifier startState, Identifier endState,
			Identifier triggerSignal) {
		super();
		this.startState = startState.getName();
		this.endState = endState.getName();
		this.triggerSignal = triggerSignal.getName();
	}

	@Override
	public boolean guard(Environment env) {
		return true;
	}

	@Override
	public String getStartState() {
		return startState;
	}

	@Override
	public String getEndState() {
		return endState;
	}

	@Override
	public String getTriggerSignal() {
		return triggerSignal;
	}

	@Override
	public void executeEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPriorizedOver(Transition t) {
		return false;
	}

	@Override
	public String toString() {
		return "[" + startState + " -> " + endState + " (with " + triggerSignal + ")]";
	}
	
}
