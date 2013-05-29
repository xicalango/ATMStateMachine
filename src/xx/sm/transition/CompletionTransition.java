package xx.sm.transition;

import xx.sm.NameDescriptor;

public class CompletionTransition extends SimpleTransition {


	public CompletionTransition(NameDescriptor startState,
			NameDescriptor endState) {
		super(startState, endState, SpecialTransitions.COMPLETION);
	}

	public CompletionTransition(String startState, String endState) {
		super(startState, endState, SpecialTransitions.COMPLETION.getName());
	}
	
	@Override
	public boolean isPriorizedOver(Transition t) {
		return true;
	}


}
