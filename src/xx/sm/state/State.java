package xx.sm.state;

import xx.sm.Environment;
import xx.sm.NameDescriptor;

public interface State extends NameDescriptor{

	String getName();
	
	void entryAction(Environment env);
	void exitAction(Environment env);
	void doAction(Environment env);
	
	boolean isRegionState();
	boolean isFinalState();
	
}
