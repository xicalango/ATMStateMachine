package xx.sm.state;

import xx.sm.Environment;
import xx.sm.Identifier;

public class SimpleState implements State {

	private String name;
	
	@Override
	public String getName() {
		return name;
	}
	
	public SimpleState(String name) {
		super();
		this.name = name;
	}
	
	public SimpleState(Identifier name) {
		super();
		this.name = name.getName();
	}

	@Override
	public void entryAction(Environment env) {
		System.out.println("entryAction");
	}

	@Override
	public void exitAction(Environment env) {
		System.out.println("exitAction");
	}

	@Override
	public void doAction(Environment env) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRegionState() {
		return false;
	}

	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean isFinalState() {
		return false;
	}
}
