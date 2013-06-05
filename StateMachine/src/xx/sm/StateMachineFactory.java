package xx.sm;

import java.util.List;

import xx.sm.state.State;
import xx.sm.transition.Transition;

public interface StateMachineFactory {

	String getStartState();
	
	List<? extends State> getStates();
	List<? extends Transition> getTransitions();

}
