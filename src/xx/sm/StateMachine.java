package xx.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import xx.collections.HashArrayMapList;
import xx.collections.MapCollection;
import xx.sm.state.RegionState;
import xx.sm.state.State;
import xx.sm.transition.Transition;

public class StateMachine {
	
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(StateMachine.class.getName()); 

	private Map<String, State> states = new HashMap<>();
	private MapCollection<String, Transition> leavingTransitions = new HashArrayMapList<>();
	private MapCollection<String, StateMachine> subStateMachines = new HashArrayMapList<>();
	private StateMachineFactory factory;
	
	private List<State> finalStates = new ArrayList<>();
	
	private StateMachine() {}
	
	public List<Transition> enabled(ActiveStateConfiguration conf, String signal) {
		
		List<Transition> availableTransitions = new ArrayList<>();
		
		for( State s : conf.getActiveStates() ) {

			boolean addSimpleTransitions = true;

			if(s.isRegionState()) {
				
				List<StateMachine> sms = subStateMachines.get(s.getName());
				
				for(StateMachine sm : sms) {
					availableTransitions.addAll(sm.enabled(conf, signal));
					
					if(!sm.isFinished(conf)) {
						addSimpleTransitions = false;
					}
				}
				
			} 
			
			if(addSimpleTransitions) {
			
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
	
	public boolean isFinished(ActiveStateConfiguration conf) {
		
		for(State s : conf.getActiveStates()) {
			if(finalStates.contains(s)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public State getStateRecursive(String stateName) {
		
		if(states.containsKey(stateName)) {
			return states.get(stateName);
		}
		
		for(List<StateMachine> sms : subStateMachines.getValues()) {
			for(StateMachine sm : sms) {
				State s = sm.getStateRecursive(stateName);
				if(s != null) {
					return s;
				}
			}
		}
		
		throw new RuntimeException("State not existing: " + stateName);
		
	}
	
	public State getStateRecursive(NameDescriptor stateName) {
		return getStateRecursive(stateName.getName());
	}

	public State getState(NameDescriptor stateName) {
		return states.get(stateName.getName());
	}

	public State getState(String stateName) {
		return states.get(stateName);
	}
	
	public List<State> getEnteredStatesOutToIn(NameDescriptor stateName) {
		return getEnteredStatesOutToIn(stateName.getName());
	}
	
	public List<State> getEnteredStatesOutToIn(String stateName) {
		
		List<State> outToInList = new ArrayList<>();
		
		if(states.containsKey(stateName)) {
			
			State s = states.get(stateName);
			
			outToInList.add(s);
			
			if(s.isRegionState()) {
				
				List<StateMachine> sms = subStateMachines.get(s.getName());
				
				for(StateMachine sm : sms) {
					List<State> enteredStates = sm.getEnteredStatesOutToIn(sm.getStartState());
					outToInList.addAll(enteredStates);
				}
				
			}
		}
		else {
			
			for( Map.Entry<String, List<StateMachine>> smsEntry : subStateMachines ) {
				
				final String smState = smsEntry.getKey();
				final List<StateMachine> sms = smsEntry.getValue();
				
				boolean found = false;
				
				for( StateMachine sm : sms ) {
					
					State innerState = sm.getState(stateName); 
					
					if(innerState != null) {
						
						outToInList.add(getState(smState));
						outToInList.addAll(sm.getEnteredStatesOutToIn(innerState));
						found = true;
						break;
					}
				}
				
				if(found == true) {
					break;
				}
			}
			
		}
		
		return outToInList;
		
	}
	
	public List<StateMachine> getSubStateMachines(String stateName) {
		return subStateMachines.get(stateName);
	}
	
	public State getStartState() {
		return getStateRecursive(factory.getStartState());
	}

	public static StateMachine compile(StateMachineFactory factory) {
		StateMachine result = new StateMachine();
		
		result.factory = factory;
		
		for(State s : factory.getStates()) {
			result.states.put(s.getName(), s);
			
			if(s.isFinalState()) {
				result.finalStates.add(s);
			}
			
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
