package xx.sm.transition;

import xx.sm.Identifier;

public class CompletionTransition extends SimpleTransition {


	public CompletionTransition(Identifier startState,
			Identifier endState) {
		super(startState, endState, SpecialSignals.COMPLETION);
	}

	public CompletionTransition(String startState, String endState) {
		super(startState, endState, SpecialSignals.COMPLETION.getName());
	}
	
	@Override
	public boolean isPriorizedOver(Transition t) {
		return true;
	}


}
