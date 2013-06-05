package xx.sm.state;

import xx.sm.Environment;
import xx.sm.NameDescriptor;


public class FinalState extends SimpleState {

	public FinalState(String parentStateMachine) {
		super(getFinalStateName(parentStateMachine));
	}

	public FinalState(NameDescriptor parentStateMachine) {
		super(getFinalStateName(parentStateMachine));
	}
	
	@Override
	public boolean isFinalState() {
		return true;
	}
	
	@Override
	public void entryAction(Environment env) {
	}
	
	@Override
	public void exitAction(Environment env) {
	}
	
	@Override
	public void doAction(Environment env) {
	}
	
	public static String getFinalStateName(String parentStateMachine) {
		return SpecialStates.FINAL + "__" + parentStateMachine;
	}
	
	public static String getFinalStateName(NameDescriptor parentStateMachine) {
		return getFinalStateName(parentStateMachine.getName());
	}

}
