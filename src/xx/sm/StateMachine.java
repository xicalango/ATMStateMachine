package xx.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xx.collections.HashArrayMapList;
import xx.collections.MapCollection;
import xx.sm.state.RegionState;
import xx.sm.state.State;
import xx.sm.transition.Transition;

public class StateMachine {

	private Map<String, State> states = new HashMap<>();
	private MapCollection<String, Transition> leavingTransitions = new HashArrayMapList<>();
	private MapCollection<String, StateMachine> subStateMachines = new HashArrayMapList<>();
	private StateMachineFactory factory;
	
	private StateMachine() {}
	
	public List<Transition> enabled(ActiveStateConfiguration conf, String signal) {
		
		List<Transition> availableTransitions = new ArrayList<>();
		
		for( State s : conf.getActiveStates() ) {
			
			if(s.isRegionState()) {
				
				List<StateMachine> sms = subStateMachines.get(s.getName());
				
				for(StateMachine sm : sms) {
					availableTransitions.addAll(sm.enabled(conf, signal));
				}
				
			} else {
			
				List<Transition> leavingTrans = leavingTransitions.get(s.getName());
				
				for(Transition t : leavingTrans) {
					if(t.getTriggerSignal().equals(signal)) {
						availableTransitions.add(t);
					}
				}
			
			}
			
		}
		
		return availableTransitions;
		
	}
	
	public State getState(String stateName) {
		
		if(states.containsKey(stateName)) {
			return states.get(stateName);
		}
		
		for(List<StateMachine> sms : subStateMachines.getValues()) {
			for(StateMachine sm : sms) {
				State s = sm.getState(stateName);
				if(s != null) {
					return s;
				}
			}
		}
		
		return null;
		
	}
	
	public State getState(NameDescriptor stateName) {
		return getState(stateName.getName());
	}
	
	public State getStartState() {
		return getState(factory.getStartState());
	}

	public static StateMachine compile(StateMachineFactory factory) {
		StateMachine result = new StateMachine();
		
		result.factory = factory;
		
		for(State s : factory.getStates()) {
			result.states.put(s.getName(), s);
			
			if(s.isRegionState()) {
				RegionState rs = (RegionState) s;
				
				for(StateMachineFactory smf : rs.getSubStatemachines()) {
					StateMachine compiledSubstate = StateMachine.compile(smf);
					result.subStateMachines.append(s.getName(), compiledSubstate);
				}
				
			}
			
		}
		
		for(Transition t : factory.getTransitions()) {
			result.leavingTransitions.append(t.getStartState(), t);
		}
		
		return result;
	}
}
