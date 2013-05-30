package xx.sm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import xx.sm.constraint.Constraint;
import xx.sm.constraint.ConstraintAnd;
import xx.sm.constraint.ConstraintNot;
import xx.sm.constraint.ConstraintTrueFalse;
import xx.sm.state.State;
import xx.sm.transition.SpecialSignals;
import xx.sm.transition.Transition;

public class StateMachineRunner implements ActiveStateConfiguration, Environment {

	private class SignalComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			
			LOG.info("comapre: " + o1 + ", " + o2);
			
			if(o1.equals(SpecialSignals.COMPLETION.getName())) {
				return -1;
			} else if(o2.equals(SpecialSignals.COMPLETION.getName())) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	private StateMachine machine;
	private List<State> activeStates = new ArrayList<>();
	private PriorityQueue<String> signalPool = new PriorityQueue<>(11, new SignalComparator() );
	
	private Map<String, Object> environment = new HashMap<>();
	
	private boolean trace = false;
	
	private final static Logger LOG = Logger.getLogger(StateMachineRunner.class.getName()); 
	
	public boolean isTrace() {
		return trace;
	}

	public void setTrace(boolean trace) {
		this.trace = trace;
		
		if(trace) {
			LOG.setLevel(Level.ALL);
		} else {
			LOG.setLevel(Level.OFF);
		}
		
	}
	
	public StateMachineRunner(StateMachine machine) {
		super();
		this.machine = machine;
		
		activeStates.add(machine.getStartState());
	}

	public void offerSignal(String signal) {
		signalPool.add(signal);
	}

	public void offerSignal(NameDescriptor signal) {
		offerSignal(signal.getName());
	}
	
	public void step() {
		LOG.info("State before step");
		logState();
		
		
		
		String event = signalPool.poll();
		
		LOG.info("Polled signal: " + event);
		
		if(event == null) {
			return;
		}
		
		List<Transition> steps = choose(getSteps(event));
		
		LOG.info("Chosen Steps: " + steps);
		
		if(steps.isEmpty()) {
			//defer
		}
		
		for(Transition t : steps) {
			LOG.info("Handling Transition: " + t);
			
			handleTransition(t);
		}
		
		LOG.info("State after step");
		logState();
	}
	
	private void logState() {
		LOG.info(activeStates.toString());
		LOG.info(signalPool.toString());
	}

	private void handleTransition(Transition t) {
		
		State start = machine.getState(t.getStartState());
		State end = machine.getState(t.getEndState());
		
		start.exitAction(this);
		offerSignal(SpecialSignals.COMPLETION);
		
		activeStates.remove(start);
		
		t.executeEffect();
		
		end.entryAction(this);
		
		activeStates.add(end);
	}

	private static List<Transition> choose(List<List<Transition>> availableSteps) {
		for( List<Transition> ts : availableSteps ) {
			if(!ts.isEmpty()) {
				return ts;
			}
		}

		for( List<Transition> ts : availableSteps ) {
			if(ts.isEmpty()) {
				return ts;
			}
		}
		
		throw new RuntimeException("Darf nicht auftreten");
	}

	@SuppressWarnings("unchecked")
	private List<List<Transition>> getSteps(String event) {
		List<Transition> enabledSteps = machine.enabled(this, event);
		
		List<Pair<? extends Constraint, ? extends List<? extends Transition>>> steps = getSteps2(event, enabledSteps);
		
		LOG.info("Available Steps: " + steps);
		
		List<List<Transition>> trans = new ArrayList<>();
		
		for(Pair<? extends Constraint, ? extends List<? extends Transition>> p : steps) {
			if(p.first.guard(this)) {
				trans.add((List<Transition>) p.second);
			}
		}
		
		LOG.info("Available Transition sets: " + trans);
		
		return trans;
	}

	private List<Pair<? extends Constraint, ? extends List<? extends Transition>>> getSteps2(String event, List<Transition> enabledSteps) {
		List<Pair<? extends Constraint, ? extends List<? extends Transition>>> result = new ArrayList<>();
		
		result.add( new Pair<Constraint, List<Transition>>( ConstraintTrueFalse.True, new ArrayList<Transition>()  ) );
		
		LOG.info("Enabled Steps: " + enabledSteps);
		
		for(Transition t : enabledSteps) {
			
			List<Transition> enSteps2 = new ArrayList<>(enabledSteps);
			enSteps2.remove(t);
			
			List<Pair<? extends Constraint, ? extends List<? extends Transition>>> steps2 = getSteps2(event, enSteps2);
			
			LOG.info("Steps for " + t + " : " + steps2);
			
			for(Pair<? extends Constraint, ? extends List<? extends Transition>> p : steps2) {
				
				Constraint guard = p.first;
				List<Transition> step = new ArrayList<>(p.second);
				
				if( inConflict(t, step) ) {
					
					LOG.info("Transition " + t + " is in Conflict with " + step);
					
					if(higherPrioriy(t, step)) {
						LOG.info("Transition " + t + " has higher priority");
						guard = new ConstraintAnd(guard, new ConstraintNot(t));
					}
				} else {
					LOG.info("Transition " + t + " is not in Conflict with " + step);
					
					step.add(t);
					guard = new ConstraintAnd(guard, t);
				}
				
				result.add(new Pair<Constraint, List<Transition>>( guard, step ));
			}
			
		}
		
		return new ArrayList<>(result);
	}

	private boolean higherPrioriy(Transition t,	List<? extends Transition> second) {
		return false;
	}

	private boolean inConflict(Transition t, List<? extends Transition> second) {
		
		for(Transition testT : second) {
			if(testT.getStartState().equals(testT.getStartState())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<State> getActiveStates() {
		return activeStates;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		return (T)environment.get(key);
	}

	@Override
	public <T> void put(String key, T value) {
		environment.put(key, value);
	}
	
	
	
	
}
