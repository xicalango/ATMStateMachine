package xx.sm.transition;

import xx.sm.constraint.Constraint;

public interface Transition extends Constraint {

	String getStartState();
	String getEndState();
	
	String getTriggerSignal();
	
	void executeEffect();

	boolean isPriorizedOver(Transition t);
	
}
