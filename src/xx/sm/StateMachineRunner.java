package xx.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import xx.sm.constraint.Constraint;
import xx.sm.constraint.ConstraintAnd;
import xx.sm.constraint.ConstraintNot;
import xx.sm.constraint.ConstraintTrueFalse;
import xx.sm.state.State;
import xx.sm.transition.SpecialTransitions;
import xx.sm.transition.Transition;

public class StateMachineRunner implements ActiveStateConfiguration, Environment {

	private StateMachine machine;
	private List<State> activeStates = new ArrayList<>();
	private Queue<String> signalPool = new LinkedList<>();
	
	private Map<String, Object> environment = new HashMap<>();
	
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
		
		String event = signalPool.poll();
		
		if(event == null) {
			return;
		}
		
		List<Transition> steps = choose(getSteps(event));
		
		if(steps.isEmpty()) {
			//defer
		}
		
		for(Transition t : steps) {
			handleTransition(t);
		}
		
	}
	
	private void handleTransition(Transition t) {
		
		State start = machine.getState(t.getStartState());
		State end = machine.getState(t.getEndState());
		
		start.exitAction(this);
		offerSignal(SpecialTransitions.COMPLETION);
		
		activeStates.remove(start);
		
		t.executeEffect();
		
		end.entryAction(this);
		
		activeStates.add(end);
	}

	private static List<Transition> choose(List<List<Transition>> availableSteps) {
		return availableSteps.get(new Random().nextInt(availableSteps.size()));
	}

	@SuppressWarnings("unchecked")
	private List<List<Transition>> getSteps(String event) {
		List<Transition> enabledSteps = machine.enabled(this, event);
		
		List<Pair<? extends Constraint, ? extends List<? extends Transition>>> steps = getSteps(event,enabledSteps);
		
		List<List<Transition>> trans = new ArrayList<>();
		
		for(Pair<? extends Constraint, ? extends List<? extends Transition>> p : steps) {
			if(p.first.guard(this)) {
				trans.add((List<Transition>) p.second);
			}
		}
		
		return trans;
	}

	private List<Pair<? extends Constraint, ? extends List<? extends Transition>>> getSteps(String event, List<Transition> enabledSteps) {
		List<Pair<? extends Constraint, ? extends List<? extends Transition>>> result = new ArrayList<>();
		
		result.add( new Pair<Constraint, List<Transition>>( ConstraintTrueFalse.True, new ArrayList<Transition>()  ) );
		
		for(Transition t : enabledSteps) {
			
			List<Transition> enSteps2 = new ArrayList<>(enabledSteps);
			enSteps2.remove(t);
			
			for(Pair<? extends Constraint, ? extends List<? extends Transition>> p : getSteps(event, enSteps2)) {
				
				Constraint guard = p.first;
				List<Transition> step = new ArrayList<>(p.second);
				
				if( inConflict(t, step) ) {
					if(higherPrioriy(t, step)) {
						guard = new ConstraintAnd(guard, new ConstraintNot(t));
					}
				} else {
					step.add(t);
					guard = new ConstraintAnd(guard, t);
				}
				
				result.add(new Pair<Constraint, List<Transition>>( guard, step ));
			}
			
		}
		
		return new ArrayList<>(result);
	}

	private boolean higherPrioriy(Transition t,
			List<? extends Transition> second) {
		
		
		
		// TODO Auto-generated method stub
		return false;
	}

	private boolean inConflict(Transition t, List<? extends Transition> second) {
		return second.contains(t);
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
	
	
	public void print() {
		System.out.println(activeStates);
		System.out.println(signalPool);
	}
	
	
	
}
