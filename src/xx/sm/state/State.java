package xx.sm.state;

import xx.sm.Environment;

public interface State {

	String getName();
	
	void entryAction(Environment env);
	void exitAction(Environment env);
	void doAction(Environment env);
	
	boolean isRegionState();
	
}
