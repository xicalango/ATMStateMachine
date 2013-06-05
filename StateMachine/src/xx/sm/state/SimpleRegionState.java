package xx.sm.state;

import java.util.Arrays;
import java.util.List;

import xx.sm.Environment;
import xx.sm.Identifier;
import xx.sm.StateMachineFactory;

public class SimpleRegionState extends SimpleState implements RegionState {

	private List<StateMachineFactory> stateMachineFactories;
	
	public SimpleRegionState(String name, StateMachineFactory... stateMachineFactories) {
		super(name);
		
		this.stateMachineFactories = Arrays.asList(stateMachineFactories);
	}

	public SimpleRegionState(Identifier name, StateMachineFactory... stateMachineFactories) {
		this(name.getName(), stateMachineFactories);
	}
	
	@Override
	public List<StateMachineFactory> getSubStatemachines() {
		return stateMachineFactories;
	}

	@Override
	public boolean isRegionState() {
		return true;
	}
}
