package xx.sm.constraint;

import xx.sm.Environment;

public class ConstraintNot implements Constraint {

	private Constraint c;
	
	public ConstraintNot(Constraint c) {
		super();
		this.c = c;
	}

	@Override
	public boolean guard(Environment env) {
		return !c.guard(env);
	}

	@Override
	public String toString() {
		return "(not " + c + ")";
	}
	
}
