package xx.sm.state;

import xx.sm.Environment;

public class FinalState implements State {

	@Override
	public String getName() {
		return "__final";
	}

	@Override
	public void entryAction(Environment env) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAction(Environment env) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doAction(Environment env) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isRegionState() {
		return false;
	}

}
