package xx.sm.state;

import java.util.List;

import xx.sm.StateMachineFactory;

public interface RegionState extends State {

	List<StateMachineFactory> getSubStatemachines();
	
}
