package xx.sm.state;

import xx.sm.Environment;
import xx.sm.Identifier;

public interface State extends Identifier{

	String getName();
	
	void entryAction(Environment env);
	void exitAction(Environment env);
	void doAction(Environment env);
	
	boolean isRegionState();
	boolean isFinalState();
	
}
